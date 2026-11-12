package com.ruoyi.wms.service;

import java.util.List;
import com.ruoyi.wms.domain.WmsInventory;

/**
 * 仓库实时库存Service接口
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
public interface IWmsInventoryService 
{
    /**
     * 查询仓库实时库存
     * 
     * @param id 仓库实时库存主键
     * @return 仓库实时库存
     */
    public WmsInventory selectWmsInventoryById(Long id);

    /**
     * 查询仓库实时库存列表
     * 
     * @param wmsInventory 仓库实时库存
     * @return 仓库实时库存集合
     */
    public List<WmsInventory> selectWmsInventoryList(WmsInventory wmsInventory);

    /**
     * 新增仓库实时库存
     * 
     * @param wmsInventory 仓库实时库存
     * @return 结果
     */
    public int insertWmsInventory(WmsInventory wmsInventory);

    /**
     * 修改仓库实时库存
     * 
     * @param wmsInventory 仓库实时库存
     * @return 结果
     */
    public int updateWmsInventory(WmsInventory wmsInventory);

    /**
     * 批量删除仓库实时库存
     * 
     * @param ids 需要删除的仓库实时库存主键集合
     * @return 结果
     */
    public int deleteWmsInventoryByIds(Long[] ids);

    /**
     * 删除仓库实时库存信息
     * 
     * @param id 仓库实时库存主键
     * @return 结果
     */
    public int deleteWmsInventoryById(Long id);
}
