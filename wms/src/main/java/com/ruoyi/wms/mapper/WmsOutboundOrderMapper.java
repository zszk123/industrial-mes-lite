package com.ruoyi.wms.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.wms.domain.WmsOutboundOrder;

/**
 * 出库主单Mapper接口
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
public interface WmsOutboundOrderMapper 
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
     * 删除出库主单
     * 
     * @param id 出库主单主键
     * @return 结果
     */
    public int deleteWmsOutboundOrderById(Long id);

    /**
     * 批量删除出库主单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsOutboundOrderByIds(Long[] ids);

    /**
     * 出库主流程（状态 CAS：仅当当前状态为"待审核"时直改为"已完成出库"）
     * 状态 1（已审核待出库）为后续 MQ 异步化预留的中间态，当前流程不使用
     *
     * @param id 出库主单主键
     * @param updateBy 审核人
     * @return 影响行数（0表示单据状态已变更或不存在）
     */
    public int approveWmsOutboundOrder(@Param("id") Long id, @Param("updateBy") String updateBy);

    /**
     * 取消出库单（状态 CAS：仅当当前状态为"待审核/已审核待出库/已完成出库"时更新为"已取消"）
     * 状态为"已完成出库"时，需先在业务层回溯 OUTBOUND 流水回补库存，再执行本 CAS
     *
     * @param id 出库主单主键
     * @param updateBy 取消操作人
     * @return 影响行数（0表示单据已取消或不存在）
     */
    public int cancelWmsOutboundOrder(@Param("id") Long id, @Param("updateBy") String updateBy);
}
