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
}
