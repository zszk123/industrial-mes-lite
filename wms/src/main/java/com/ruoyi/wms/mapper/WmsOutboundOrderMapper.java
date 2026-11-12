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
     * 审核通过（状态 CAS：仅当当前状态为"待审核"时更新为"已审核待出库/已锁库"）
     * WHERE 条件携带 status = 0，天然防止重复审核与并发状态覆盖
     *
     * @param id 出库主单主键
     * @param updateBy 审核人
     * @return 影响行数（0表示单据状态已变更或不存在）
     */
    public int approveWmsOutboundOrder(@Param("id") Long id, @Param("updateBy") String updateBy);
}
