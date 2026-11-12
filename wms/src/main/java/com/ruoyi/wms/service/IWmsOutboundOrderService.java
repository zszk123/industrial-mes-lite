package com.ruoyi.wms.service;

import java.util.List;
import com.ruoyi.wms.domain.WmsOutboundOrder;

/**
 * 出库主单Service接口
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
public interface IWmsOutboundOrderService 
{
    /**
     * 查询出库主单
     * 
     * @param id 出库主单主键
     * @return 出库主单
     */
    public WmsOutboundOrder selectWmsOutboundOrderById(Long id);

    /**
     * 查询出库主单列表
     * 
     * @param wmsOutboundOrder 出库主单
     * @return 出库主单集合
     */
    public List<WmsOutboundOrder> selectWmsOutboundOrderList(WmsOutboundOrder wmsOutboundOrder);

    /**
     * 新增出库主单
     * 
     * @param wmsOutboundOrder 出库主单
     * @return 结果
     */
    public int insertWmsOutboundOrder(WmsOutboundOrder wmsOutboundOrder);

    /**
     * 修改出库主单
     * 
     * @param wmsOutboundOrder 出库主单
     * @return 结果
     */
    public int updateWmsOutboundOrder(WmsOutboundOrder wmsOutboundOrder);

    /**
     * 批量删除出库主单
     * 
     * @param ids 需要删除的出库主单主键集合
     * @return 结果
     */
    public int deleteWmsOutboundOrderByIds(Long[] ids);

    /**
     * 删除出库主单信息
     * 
     * @param id 出库主单主键
     * @return 结果
     */
    public int deleteWmsOutboundOrderById(Long id);

    /**
     * 出库主流程（待审核 → 已完成出库）
     * 流程：Redis 幂等防重 → 事务内逐行扣减库存（条件 UPDATE 防超卖）→ 写 OUTBOUND 审计流水 → 状态 CAS 直改为 2
     * 说明：状态 1（已审核待出库）为后续 MQ 异步化预留的中间态，当前流程不使用
     *
     * @param id 出库主单主键
     * @return 结果
     */
    public int approveOutboundOrder(Long id);

    /**
     * 取消出库单（待审核/已审核待出库/已完成出库 → 已取消；已完成出库 → 回溯流水回补库存后 → 已取消）
     * 流程：查主单状态 → Redis 幂等防重 → 查询 OUTBOUND 流水逐条回补库存 → 状态 CAS 更新 → 删除幂等键
     *
     * @param id 出库主单主键
     * @return 结果
     */
    public int cancelOutboundOrder(Long id);
}
