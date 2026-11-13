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
import com.ruoyi.wms.mapper.WmsInventoryMapper;
import com.ruoyi.wms.mapper.WmsInventoryRecordMapper;
import com.ruoyi.wms.mapper.WmsLocationMapper;
import com.ruoyi.wms.mapper.WmsMaterialMapper;
import com.ruoyi.wms.mapper.WmsOutboundDetailMapper;
import com.ruoyi.wms.mapper.WmsOutboundOrderMapper;
import com.ruoyi.wms.domain.WmsInventory;
import com.ruoyi.wms.domain.WmsInventoryRecord;
import com.ruoyi.wms.domain.WmsLocation;
import com.ruoyi.wms.domain.WmsMaterial;
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

    /** 单据状态：已完成出库 */
    private static final Long STATUS_COMPLETED = 2L;

    /** 审核操作 Redis 幂等键前缀 */
    private static final String APPROVE_IDEMPOTENT_KEY = "wms:outbound:approve:";

    /** 取消操作 Redis 幂等键前缀 */
    private static final String CANCEL_IDEMPOTENT_KEY = "wms:outbound:cancel:";

    /** 流水类型：出库扣减 */
    private static final String RECORD_TYPE_OUTBOUND = "OUTBOUND";

    /** 库位状态：正常 */
    private static final String LOCATION_STATUS_NORMAL = "0";

    @Autowired
    private WmsOutboundOrderMapper wmsOutboundOrderMapper;

    @Autowired
    private WmsOutboundDetailMapper wmsOutboundDetailMapper;

    @Autowired
    private WmsInventoryMapper wmsInventoryMapper;

    @Autowired
    private WmsInventoryRecordMapper wmsInventoryRecordMapper;

    @Autowired
    private WmsMaterialMapper wmsMaterialMapper;

    @Autowired
    private WmsLocationMapper wmsLocationMapper;

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
     * 出库主流程（待审核 → 已完成出库）
     *
     * 防护层次说明：
     * 1. Redis SETNX（短TTL）：第一道闸，快速挡住网络重发/双击产生的并发重复请求
     * 2. 条件 UPDATE（available_qty >= 扣减数量）：防超卖核心，原子完成"校验+扣减"
     * 3. 状态 CAS 更新（WHERE status = 0）：真正的幂等闸门，重复请求直接拒绝
     * 4. 唯一键（uk_record_no）：审计流水最终兜底
     * 说明：状态 1（已审核待出库）为后续 MQ 异步化预留的中间态，当前流程 0 直改为 2
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
            throw new ServiceException("当前单据状态不允许出库操作");
        }

        // 2. Redis 幂等：SETNX 成功才放行
        String idempotentKey = APPROVE_IDEMPOTENT_KEY + order.getOrderNo();
        if (!redisCache.setCacheObjectIfAbsent(idempotentKey, "1", 10, TimeUnit.SECONDS))
        {
            throw new ServiceException("出库请求处理中，请勿重复提交");
        }

        // 3. 查询出库明细
        WmsOutboundDetail detailQuery = new WmsOutboundDetail();
        detailQuery.setOrderId(id);
        List<WmsOutboundDetail> detailList = wmsOutboundDetailMapper.selectWmsOutboundDetailList(detailQuery);
        if (detailList == null || detailList.isEmpty())
        {
            throw new ServiceException("出库单明细为空，无法出库");
        }

        // 4. 逐行扣减库存（任一行失败抛异常，事务整体回滚）
        String operator = SecurityUtils.getUsername();
        for (WmsOutboundDetail detail : detailList)
        {
            deductInventoryForDetail(order, detail, operator);
        }

        // 5. 状态 CAS：仅当状态仍为"待审核"时直改为"已完成出库"
        int rows = wmsOutboundOrderMapper.approveWmsOutboundOrder(id, operator);
        if (rows == 0)
        {
            throw new ServiceException("单据状态已变更，请刷新后重试");
        }
        return 1;
    }

    /**
     * 取消出库单
     *
     * 流程2：待审核/已审核待出库 → 已取消，直接修改状态
     * 流程3：已完成出库 → 已取消，查询 OUTBOUND 流水逐条回补库存后修改状态
     *
     * 执行顺序：查主单状态 → Redis 幂等 → 查询流水逐个回溯 → 更改状态 → 删除幂等键
     *
     * @param id 出库主单主键
     * @return 结果
     */
    @Override
    @Transactional
    public int cancelOutboundOrder(Long id)
    {
        // 1. 查询当前主单的状态
        WmsOutboundOrder order = wmsOutboundOrderMapper.selectWmsOutboundOrderById(id);
        if (order == null)
        {
            throw new ServiceException("出库单不存在");
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

        // 3. 已完成出库的单据：查询流水逐个回溯，将出库扣减的库存加回
        //    upsert 语义：库存记录存在则累加，不存在（如被清理）则新建，保证回补不失败
        if (STATUS_COMPLETED.equals(order.getStatus()))
        {
            List<WmsInventoryRecord> records = wmsInventoryRecordMapper.selectRecordsByOrderNo(order.getOrderNo(), RECORD_TYPE_OUTBOUND);
            if (records == null || records.isEmpty())
            {
                throw new ServiceException("未找到出库流水记录，无法回补库存，请联系管理员核查数据");
            }
            for (WmsInventoryRecord record : records)
            {
                // 库位被禁用时才拒绝回溯；仅库存数据不存在不报错，由 upsert 自动创建
                WmsLocation location = wmsLocationMapper.selectWmsLocationById(record.getLocationId());
                if (location == null)
                {
                    throw new ServiceException("库位[" + record.getLocationId() + "]不存在，数据异常，无法回补");
                }
                if (!LOCATION_STATUS_NORMAL.equals(location.getStatus()))
                {
                    throw new ServiceException("库位[" + location.getLocationCode() + "]已禁用，无法回溯");
                }
                wmsInventoryMapper.upsertAddInventory(order.getWarehouseId(), record.getLocationId(),
                        record.getMaterialId(), record.getChangeQty(), operator, operator);
            }
        }

        // 4. 更改状态：CAS，仅当状态仍为"待审核/已审核待出库/已完成出库"时更新为"已取消"
        int rows = wmsOutboundOrderMapper.cancelWmsOutboundOrder(id, operator);
        if (rows == 0)
        {
            throw new ServiceException("单据状态已变更，请刷新后重试");
        }

        // 5. 事务完成后删除幂等键（后续迟到重复请求由状态 CAS 拦截）
        redisCache.deleteObject(idempotentKey);
        return 1;
    }

    /**
     * 按明细扣减库存：同一仓库内按库位可用量降序贪心分配，优先从库存充足的库位扣减
     *
     * @param order 出库主单
     * @param detail 出库明细
     * @param operator 操作人
     */
    private void deductInventoryForDetail(WmsOutboundOrder order, WmsOutboundDetail detail, String operator)
    {
        // 查询物料名称，用于业务异常信息精准提示
        WmsMaterial material = wmsMaterialMapper.selectWmsMaterialById(detail.getMaterialId());
        String materialName = material != null ? material.getMaterialName() : String.valueOf(detail.getMaterialId());

        // 查询该仓库下此物料可用量大于0的库存，按可用量降序
        List<WmsInventory> availableList = wmsInventoryMapper.selectAvailableInventoryList(order.getWarehouseId(), detail.getMaterialId());

        long remaining = detail.getPlanQty();
        for (WmsInventory inventory : availableList)
        {
            if (remaining <= 0L)
            {
                break;
            }
            long deductQty = Math.min(inventory.getAvailableQty(), remaining);

            // 条件 UPDATE 原子完成"校验+扣减"，影响行数为 0 说明可用库存不足或并发被抢占
            int rows = wmsInventoryMapper.deductInventory(inventory.getId(), deductQty);
            if (rows == 0)
            {
                throw new ServiceException("物料[" + materialName + "]库存发生变化，请稍后重试");
            }
            remaining -= deductQty;

            // 当前读取扣减后的真实值（FOR UPDATE 锁定读），回填流水防止并发下快照失真
            long afterQty = wmsInventoryMapper.selectQtyByIdForUpdate(inventory.getId());

            // 写 OUTBOUND 审计流水：record_no 确定性生成（单号+库位+物料），唯一键兜底防重
            WmsInventoryRecord record = new WmsInventoryRecord();
            record.setRecordNo(order.getOrderNo() + "-OUT-" + inventory.getLocationId() + "-" + detail.getMaterialId());
            record.setOrderNo(order.getOrderNo());
            record.setRecordType(RECORD_TYPE_OUTBOUND);
            record.setWarehouseId(order.getWarehouseId());
            record.setLocationId(inventory.getLocationId());
            record.setMaterialId(detail.getMaterialId());
            record.setChangeQty(deductQty);
            record.setBeforeQty(afterQty + deductQty);
            record.setAfterQty(afterQty);
            record.setCreateBy(operator);
            record.setCreateTime(DateUtils.getNowDate());
            wmsInventoryRecordMapper.insertWmsInventoryRecord(record);
        }

        // 遍历完所有库位仍无法凑齐计划数量，判定库存不足
        if (remaining > 0L)
        {
            throw new ServiceException("物料[" + materialName + "]可用库存不足，还缺 " + remaining + " 个");
        }

        // 回填明细实际出库数量（整单一次出库，实际数 = 计划数）
        WmsOutboundDetail actual = new WmsOutboundDetail();
        actual.setId(detail.getId());
        actual.setActualQty(detail.getPlanQty());
        wmsOutboundDetailMapper.updateWmsOutboundDetail(actual);
    }
}
