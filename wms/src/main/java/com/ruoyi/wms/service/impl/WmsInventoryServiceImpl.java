package com.ruoyi.wms.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wms.mapper.WmsInventoryMapper;
import com.ruoyi.wms.domain.WmsInventory;
import com.ruoyi.wms.service.IWmsInventoryService;

/**
 * 仓库实时库存Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
@Service
public class WmsInventoryServiceImpl implements IWmsInventoryService 
{
    @Autowired
    private WmsInventoryMapper wmsInventoryMapper;

    /**
     * 查询仓库实时库存
     * 
     * @param id 仓库实时库存主键
     * @return 仓库实时库存
     */
    @Override
    public WmsInventory selectWmsInventoryById(Long id)
    {
        return wmsInventoryMapper.selectWmsInventoryById(id);
    }

    /**
     * 查询仓库实时库存列表
     * 
     * @param wmsInventory 仓库实时库存
     * @return 仓库实时库存
     */
    @Override
    public List<WmsInventory> selectWmsInventoryList(WmsInventory wmsInventory)
    {
        return wmsInventoryMapper.selectWmsInventoryList(wmsInventory);
    }

    /**
     * 新增仓库实时库存
     * 
     * @param wmsInventory 仓库实时库存
     * @return 结果
     */
    @Override
    public int insertWmsInventory(WmsInventory wmsInventory)
    {
        wmsInventory.setCreateTime(DateUtils.getNowDate());
        return wmsInventoryMapper.insertWmsInventory(wmsInventory);
    }

    /**
     * 修改仓库实时库存
     * 
     * @param wmsInventory 仓库实时库存
     * @return 结果
     */
    @Override
    public int updateWmsInventory(WmsInventory wmsInventory)
    {
        wmsInventory.setUpdateTime(DateUtils.getNowDate());
        return wmsInventoryMapper.updateWmsInventory(wmsInventory);
    }

    /**
     * 批量删除仓库实时库存
     * 
     * @param ids 需要删除的仓库实时库存主键
     * @return 结果
     */
    @Override
    public int deleteWmsInventoryByIds(Long[] ids)
    {
        return wmsInventoryMapper.deleteWmsInventoryByIds(ids);
    }

    /**
     * 删除仓库实时库存信息
     * 
     * @param id 仓库实时库存主键
     * @return 结果
     */
    @Override
    public int deleteWmsInventoryById(Long id)
    {
        return wmsInventoryMapper.deleteWmsInventoryById(id);
    }
}
