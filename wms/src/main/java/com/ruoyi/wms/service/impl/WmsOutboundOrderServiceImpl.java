package com.ruoyi.wms.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wms.mapper.WmsOutboundOrderMapper;
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
    @Autowired
    private WmsOutboundOrderMapper wmsOutboundOrderMapper;

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
}
