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
import com.ruoyi.wms.mapper.WmsInboundDetailMapper;
import com.ruoyi.wms.mapper.WmsInboundOrderMapper;
import com.ruoyi.wms.mapper.WmsInventoryMapper;
import com.ruoyi.wms.mapper.WmsInventoryRecordMapper;
import com.ruoyi.wms.mapper.WmsLocationMapper;
import com.ruoyi.wms.domain.WmsInboundDetail;
import com.ruoyi.wms.domain.WmsInboundOrder;
import com.ruoyi.wms.domain.WmsInventory;
import com.ruoyi.wms.domain.WmsInventoryRecord;
import com.ruoyi.wms.domain.WmsLocation;
import com.ruoyi.wms.service.IWmsInboundOrderService;

/**
 * 入库单主Service业务层处理
 *
 * @author ruoyi
 * @date 2026-11-12
 */
@Service
public class WmsInboundOrderServiceImpl implements IWmsInboundOrderService
{
    /** 单据状态：待验收 */
    private static final Long STATUS_PENDING = 0L;

    /** 库位状态：正常 */
    private static final String LOCATION_STATUS_NORMAL = "0";

    /** 入库完成 Redis 幂等键前缀 */
    private static final String COMPLETE_IDEMPOTENT_KEY = "wms:inbound:complete:";

    /** 流水类型：入库增加 */
    private static final String RECORD_TYPE_INBOUND = "INBOUND";

    /** 取消操作 Redis 幂等键前缀 */
    private static final String CANCEL_IDEMPOTENT_KEY = "wms:inbound:cancel:";

    @Autowired
    private WmsInboundOrderMapper wmsInboundOrderMapper;

    @Autowired
    private WmsInboundDetailMapper wmsInboundDetailMapper;

    @Autowired
    private WmsInventoryMapper wmsInventoryMapper;

    @Autowired
    private WmsInventoryRecordMapper wmsInventoryRecordMapper;

    @Autowired
    private WmsLocationMapper wmsLocationMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 查询入库单主
     * 
     * @param id 入库单主主键
     * @return 入库单主
     */
    @Override
    public WmsInboundOrder selectWmsInboundOrderById(Long id)
    {
        return wmsInboundOrderMapper.selectWmsInboundOrderById(id);
    }

    /**
     * 查询入库单主列表
     * 
     * @param wmsInboundOrder 入库单主
     * @return 入库单主
     */
    @Override
    public List<WmsInboundOrder> selectWmsInboundOrderList(WmsInboundOrder wmsInboundOrder)
    {
        return wmsInboundOrderMapper.selectWmsInboundOrderList(wmsInboundOrder);
    }

    /**
     * 新增入库单主
     * 
     * @param wmsInboundOrder 入库单主
     * @return 结果
     */
    @Override
    public int insertWmsInboundOrder(WmsInboundOrder wmsInboundOrder)
    {
        wmsInboundOrder.setCreateTime(DateUtils.getNowDate());
        return wmsInboundOrderMapper.insertWmsInboundOrder(wmsInboundOrder);
    }

    /**
     * 修改入库单主
     * 
     * @param wmsInboundOrder 入库单主
     * @return 结果
     */
    @Override
    public int updateWmsInboundOrder(WmsInboundOrder wmsInboundOrder)
    {
        wmsInboundOrder.setUpdateTime(DateUtils.getNowDate());
        return wmsInboundOrderMapper.updateWmsInboundOrder(wmsInboundOrder);
    }

    /**
     * 批量删除入库单主
     * 
     * @param ids 需要删除的入库单主主键
     * @return 结果
     */
    @Override
    public int deleteWmsInboundOrderByIds(Long[] ids)
    {
        return wmsInboundOrderMapper.deleteWmsInboundOrderByIds(ids);
    }

    /**
     * 删除入库单主信息
     * 
     * @param id 入库单主主键
     * @return 结果
     */
    @Override
    public int deleteWmsInboundOrderById(Long id)
    {
        return wmsInboundOrderMapper.deleteWmsInboundOrderById(id);
    }

    /**
     * 入库完成（待验收 → 已完成入库）
     *
     * 防护层次说明：
     * 1. Redis SETNX（短TTL）：第一道闸，快速挡住重复点击与并发请求
     * 2. upsert 加库存：依赖唯一键 uk_loc_mat，存在则累加、不存在则新建，加法无超卖风险
     * 3. 状态 CAS 更新（WHERE status = 0）：真正的幂等闸门，重复入库直接拒绝
     * 4. 唯一键（uk_record_no）：审计流水最终兜底
     *
     * 上架库位来源：上架人先通过"修改入库明细"接口为每行指定 locationId，本接口校验归属与启用状态
     * 入库数量：取明细的 qualifiedQty（验收合格数量），残损数量不入可用库存
     *
     * @param id 入库单主键
     * @return 结果
     */
    @Override
    @Transactional
    public int completeInboundOrder(Long id)
    {
        // 1. 查询主单并校验当前状态
        WmsInboundOrder order = wmsInboundOrderMapper.selectWmsInboundOrderById(id);
        if (order == null)
        {
            throw new ServiceException("入库单不存在");
        }
        if (!STATUS_PENDING.equals(order.getStatus()))
        {
            throw new ServiceException("当前单据状态不允许入库操作");
        }

        // 2. Redis 幂等：SETNX 成功才放行
        String idempotentKey = COMPLETE_IDEMPOTENT_KEY + order.getOrderNo();
        if (!redisCache.setCacheObjectIfAbsent(idempotentKey, "1", 10, TimeUnit.SECONDS))
        {
            throw new ServiceException("入库请求处理中，请勿重复提交");
        }

        // 3. 查询入库明细
        WmsInboundDetail detailQuery = new WmsInboundDetail();
        detailQuery.setOrderId(id);
        List<WmsInboundDetail> detailList = wmsInboundDetailMapper.selectWmsInboundDetailList(detailQuery);
        if (detailList == null || detailList.isEmpty())
        {
            throw new ServiceException("入库单明细为空，无法入库");
        }

        // 4. 逐行上架加库存（任一行失败抛异常，事务整体回滚）
        String operator = SecurityUtils.getUsername();
        for (WmsInboundDetail detail : detailList)
        {
            addInventoryForDetail(order, detail, operator);
        }

        // 5. 状态 CAS：仅当状态仍为"待验收"时更新为"已完成入库"
        int rows = wmsInboundOrderMapper.completeWmsInboundOrder(id, operator);
        if (rows == 0)
        {
            throw new ServiceException("单据状态已变更，请刷新后重试");
        }
        return 1;
    }

    /**
     * 按明细上架加库存：校验库位归属与启用状态，upsert 累加可用库存并写 INBOUND 流水
     *
     * @param order 入库主单
     * @param detail 入库明细
     * @param operator 操作人
     */
    private void addInventoryForDetail(WmsInboundOrder order, WmsInboundDetail detail, String operator)
    {
        // 1. 校验明细已指定上架库位
        if (detail.getLocationId() == null)
        {
            throw new ServiceException("入库单号[" + order.getOrderNo() + "]存在未指定上架库位的明细，请先分配库位");
        }

        // 2. 校验库位存在、属于当前入库仓库且已启用
        WmsLocation location = wmsLocationMapper.selectWmsLocationById(detail.getLocationId());
        if (location == null || !order.getWarehouseId().equals(location.getWarehouseId()))
        {
            throw new ServiceException("库位不存在或不属于当前入库仓库");
        }
        if (!LOCATION_STATUS_NORMAL.equals(location.getStatus()))
        {
            throw new ServiceException("库位[" + location.getLocationCode() + "]已停用，无法上架");
        }

        // 3. 校验验收合格数量（入库数量 = 合格数量，残损不入可用库存）
        if (detail.getQualifiedQty() == null || detail.getQualifiedQty() <= 0L)
        {
            throw new ServiceException("入库单号[" + order.getOrderNo() + "]存在未填写验收合格数量的明细，请先验收");
        }
        long addQty = detail.getQualifiedQty();

        // 4. upsert 加库存：唯一键 uk_loc_mat 保证有记录累加、无记录新建
        wmsInventoryMapper.upsertAddInventory(order.getWarehouseId(), detail.getLocationId(),
                detail.getMaterialId(), addQty, operator, operator);

        // 5. 当前读取累加后的真实值（FOR UPDATE 锁定读），回填流水防止并发下快照失真
        Long afterQty = wmsInventoryMapper.selectQtyByLocMatForUpdate(detail.getLocationId(), detail.getMaterialId());

        // 6. 写 INBOUND 审计流水：record_no 确定性生成（单号+库位+物料），唯一键兜底防重
        WmsInventoryRecord record = new WmsInventoryRecord();
        record.setRecordNo(order.getOrderNo() + "-IN-" + detail.getLocationId() + "-" + detail.getMaterialId());
        record.setOrderNo(order.getOrderNo());
        record.setRecordType(RECORD_TYPE_INBOUND);
        record.setWarehouseId(order.getWarehouseId());
        record.setLocationId(detail.getLocationId());
        record.setMaterialId(detail.getMaterialId());
        record.setChangeQty(addQty);
        record.setBeforeQty(afterQty - addQty);
        record.setAfterQty(afterQty);
        record.setCreateBy(operator);
        record.setCreateTime(DateUtils.getNowDate());
        wmsInventoryRecordMapper.insertWmsInventoryRecord(record);
    }

    /**
     * 取消入库单
     *
     * 流程2：待验收 → 已取消，直接修改状态
     * 流程3：已完成入库 → 已取消，查询 INBOUND 流水逐条扣回库存后修改状态
     *
     * 执行顺序：查主单状态 → Redis 幂等 → 查询流水逐个回溯 → 更改状态 → 删除幂等键
     *
     * @param id 入库单主键
     * @return 结果
     */
    @Override
    @Transactional
    public int cancelInboundOrder(Long id)
    {
        // 1. 查询当前主单的状态
        WmsInboundOrder order = wmsInboundOrderMapper.selectWmsInboundOrderById(id);
        if (order == null)
        {
            throw new ServiceException("入库单不存在");
        }
        if (Long.valueOf(3L).equals(order.getStatus()))
        {
            throw new ServiceException("单据已取消，请勿重复操作");
        }

        // 2. Redis 设置幂等
        String idempotentKey = CANCEL_IDEMPOTENT_KEY + order.getOrderNo();
        if (!redisCache.setCacheObjectIfAbsent(idempotentKey, "1", 10, TimeUnit.SECONDS))
        {
            throw new ServiceException("取消请求处理中，请勿重复提交");
        }

        String operator = SecurityUtils.getUsername();

        // 3. 已完成入库的单据：查询流水逐个回溯，将入库增加的库存扣回
        //    WHERE 携带 available_qty >= 数量防负数，扣不动说明物料已被领用
        if (Long.valueOf(1L).equals(order.getStatus()))
        {
            List<WmsInventoryRecord> records = wmsInventoryRecordMapper.selectRecordsByOrderNo(order.getOrderNo(), RECORD_TYPE_INBOUND);
            if (records == null || records.isEmpty())
            {
                throw new ServiceException("未找到入库流水记录，无法扣回库存，请联系管理员核查数据");
            }
            for (WmsInventoryRecord record : records)
            {
                int rows = wmsInventoryMapper.retrieveInventory(record.getLocationId(), record.getMaterialId(),
                        record.getChangeQty(), operator);
                if (rows == 0)
                {
                    throw new ServiceException("物料当前可用库存不足（已被领用），无法取消入库");
                }
            }
        }

        // 4. 更改状态：CAS，仅当状态仍为"待验收/已完成入库"时更新为"已取消"
        int rows = wmsInboundOrderMapper.cancelWmsInboundOrder(id, operator);
        if (rows == 0)
        {
            throw new ServiceException("单据状态已变更，请刷新后重试");
        }
        return 1;
    }
}
