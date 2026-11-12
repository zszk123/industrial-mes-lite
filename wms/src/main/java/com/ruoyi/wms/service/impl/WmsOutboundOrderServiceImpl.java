package com.ruoyi.wms.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.wms.mapper.WmsOutboundDetailMapper;
import com.ruoyi.wms.mapper.WmsOutboundOrderMapper;
import com.ruoyi.wms.domain.WmsOutboundDetail;
import com.ruoyi.wms.domain.WmsOutboundOrder;
import com.ruoyi.wms.service.IWmsOutboundOrderService;

/**
 * 出库主单Service业务层处理
 *
 * @author ruoyi
 * @date 2026-11-12
 */
@Service
public class WmsOutboundOrderServiceImpl implements IWmsOutboundOrderService
{
    /** 单据状态：待审核 */
    private static final Long STATUS_PENDING = 0L;

    /** 审核操作 Redis 幂等键前缀 */
    private static final String APPROVE_IDEMPOTENT_KEY = "wms:outbound:approve:";

    @Autowired
    private WmsOutboundOrderMapper wmsOutboundOrderMapper;

    @Autowired
    private WmsOutboundDetailMapper wmsOutboundDetailMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 查询出库主单
     * 
     * @param id 出库主单主键
     * @return 出库主单
     */
    @Override
    public WmsOutboundOrder selectWmsOutboundOrderById(Long id)
    {
        return wmsOutboundOrderMapper.selectWmsOutboundOrderById(id);
    }

    /**
     * 查询出库主单列表
     * 
     * @param wmsOutboundOrder 出库主单
     * @return 出库主单
     */
    @Override
    public List<WmsOutboundOrder> selectWmsOutboundOrderList(WmsOutboundOrder wmsOutboundOrder)
    {
        return wmsOutboundOrderMapper.selectWmsOutboundOrderList(wmsOutboundOrder);
    }

    /**
     * 新增出库主单
     * 
     * @param wmsOutboundOrder 出库主单
     * @return 结果
     */
    @Override
    public int insertWmsOutboundOrder(WmsOutboundOrder wmsOutboundOrder)
    {
        wmsOutboundOrder.setCreateTime(DateUtils.getNowDate());
        return wmsOutboundOrderMapper.insertWmsOutboundOrder(wmsOutboundOrder);
    }

    /**
     * 修改出库主单
     * 
     * @param wmsOutboundOrder 出库主单
     * @return 结果
     */
    @Override
    public int updateWmsOutboundOrder(WmsOutboundOrder wmsOutboundOrder)
    {
        wmsOutboundOrder.setUpdateTime(DateUtils.getNowDate());
        return wmsOutboundOrderMapper.updateWmsOutboundOrder(wmsOutboundOrder);
    }

    /**
     * 批量删除出库主单
     * 
     * @param ids 需要删除的出库主单主键
     * @return 结果
     */
    @Override
    public int deleteWmsOutboundOrderByIds(Long[] ids)
    {
        return wmsOutboundOrderMapper.deleteWmsOutboundOrderByIds(ids);
    }

    /**
     * 删除出库主单信息
     * 
     * @param id 出库主单主键
     * @return 结果
     */
    @Override
    public int deleteWmsOutboundOrderById(Long id)
    {
        return wmsOutboundOrderMapper.deleteWmsOutboundOrderById(id);
    }

    /**
     * 审核通过出库主单（待审核 → 已审核待出库，纯审批，不操作库存）
     *
     * 防护层次说明：
     * 1. Redis SETNX（短TTL）：第一道闸，快速挡住网络重发/双击产生的并发重复请求
     * 2. 状态 CAS 更新：真正的幂等闸门，重复审核影响行数为 0，直接拒绝
     * 说明：Redis 操作不参与数据库事务，事务回滚后幂等键靠 TTL（10秒）自动过期，不破坏一致性
     * 库存扣减在"出库完成"接口（1→2）执行，条件 UPDATE 防超卖；后续演进为 Redis Lua 预扣 + MQ 异步
     *
     * @param id 出库主单主键
     * @return 结果
     */
    @Override
    @Transactional
    public int approveOutboundOrder(Long id)
    {
        // 1. 查询主单并校验当前状态
        WmsOutboundOrder order = wmsOutboundOrderMapper.selectWmsOutboundOrderById(id);
        if (order == null)
        {
            throw new ServiceException("出库单不存在");
        }
        if (!STATUS_PENDING.equals(order.getStatus()))
        {
            throw new ServiceException("当前单据状态不允许审核");
        }

        // 2. Redis 幂等：SETNX 成功才放行，失败说明已有相同请求在处理中
        String idempotentKey = APPROVE_IDEMPOTENT_KEY + order.getOrderNo();
        if (!redisCache.setCacheObjectIfAbsent(idempotentKey, "1", 10, TimeUnit.SECONDS))
        {
            throw new ServiceException("审核请求处理中，请勿重复提交");
        }

        // 3. 校验明细非空，避免审批出一张空单
        WmsOutboundDetail detailQuery = new WmsOutboundDetail();
        detailQuery.setOrderId(id);
        List<WmsOutboundDetail> detailList = wmsOutboundDetailMapper.selectWmsOutboundDetailList(detailQuery);
        if (detailList == null || detailList.isEmpty())
        {
            throw new ServiceException("出库单明细为空，无法审核");
        }

        // 4. 状态 CAS：仅当状态仍为"待审核"时更新为"已审核待出库"
        //    影响行数为 0 说明并发场景下被抢先审核，直接拒绝
        int rows = wmsOutboundOrderMapper.approveWmsOutboundOrder(id, SecurityUtils.getUsername());
        if (rows == 0)
        {
            throw new ServiceException("单据状态已变更，请刷新后重试");
        }
        return 1;
    }
}
