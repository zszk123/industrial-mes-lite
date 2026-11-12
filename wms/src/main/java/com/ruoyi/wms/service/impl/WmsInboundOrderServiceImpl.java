package com.ruoyi.wms.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wms.mapper.WmsInboundOrderMapper;
import com.ruoyi.wms.domain.WmsInboundOrder;
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
    @Autowired
    private WmsInboundOrderMapper wmsInboundOrderMapper;

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
}
