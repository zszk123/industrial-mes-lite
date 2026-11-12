package com.ruoyi.wms.service;

import java.util.List;
import com.ruoyi.wms.domain.WmsInboundOrder;

/**
 * 入库单主Service接口
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
public interface IWmsInboundOrderService 
{
    /**
     * 查询入库单主
     * 
     * @param id 入库单主主键
     * @return 入库单主
     */
    public WmsInboundOrder selectWmsInboundOrderById(Long id);

    /**
     * 查询入库单主列表
     * 
     * @param wmsInboundOrder 入库单主
     * @return 入库单主集合
     */
    public List<WmsInboundOrder> selectWmsInboundOrderList(WmsInboundOrder wmsInboundOrder);

    /**
     * 新增入库单主
     * 
     * @param wmsInboundOrder 入库单主
     * @return 结果
     */
    public int insertWmsInboundOrder(WmsInboundOrder wmsInboundOrder);

    /**
     * 修改入库单主
     * 
     * @param wmsInboundOrder 入库单主
     * @return 结果
     */
    public int updateWmsInboundOrder(WmsInboundOrder wmsInboundOrder);

    /**
     * 批量删除入库单主
     * 
     * @param ids 需要删除的入库单主主键集合
     * @return 结果
     */
    public int deleteWmsInboundOrderByIds(Long[] ids);

    /**
     * 删除入库单主信息
     * 
     * @param id 入库单主主键
     * @return 结果
     */
    public int deleteWmsInboundOrderById(Long id);
}
