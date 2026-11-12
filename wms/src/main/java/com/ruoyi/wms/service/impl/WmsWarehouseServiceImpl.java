package com.ruoyi.wms.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wms.mapper.WmsWarehouseMapper;
import com.ruoyi.wms.domain.WmsWarehouse;
import com.ruoyi.wms.service.IWmsWarehouseService;

/**
 * 仓库信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
@Service
public class WmsWarehouseServiceImpl implements IWmsWarehouseService 
{
    @Autowired
    private WmsWarehouseMapper wmsWarehouseMapper;

    /**
     * 查询仓库信息
     * 
     * @param id 仓库信息主键
     * @return 仓库信息
     */
    @Override
    public WmsWarehouse selectWmsWarehouseById(Long id)
    {
        return wmsWarehouseMapper.selectWmsWarehouseById(id);
    }

    /**
     * 查询仓库信息列表
     * 
     * @param wmsWarehouse 仓库信息
     * @return 仓库信息
     */
    @Override
    public List<WmsWarehouse> selectWmsWarehouseList(WmsWarehouse wmsWarehouse)
    {
        return wmsWarehouseMapper.selectWmsWarehouseList(wmsWarehouse);
    }

    /**
     * 新增仓库信息
     * 
     * @param wmsWarehouse 仓库信息
     * @return 结果
     */
    @Override
    public int insertWmsWarehouse(WmsWarehouse wmsWarehouse)
    {
        wmsWarehouse.setCreateTime(DateUtils.getNowDate());
        return wmsWarehouseMapper.insertWmsWarehouse(wmsWarehouse);
    }

    /**
     * 修改仓库信息
     * 
     * @param wmsWarehouse 仓库信息
     * @return 结果
     */
    @Override
    public int updateWmsWarehouse(WmsWarehouse wmsWarehouse)
    {
        wmsWarehouse.setUpdateTime(DateUtils.getNowDate());
        return wmsWarehouseMapper.updateWmsWarehouse(wmsWarehouse);
    }

    /**
     * 批量删除仓库信息
     * 
     * @param ids 需要删除的仓库信息主键
     * @return 结果
     */
    @Override
    public int deleteWmsWarehouseByIds(Long[] ids)
    {
        return wmsWarehouseMapper.deleteWmsWarehouseByIds(ids);
    }

    /**
     * 删除仓库信息信息
     * 
     * @param id 仓库信息主键
     * @return 结果
     */
    @Override
    public int deleteWmsWarehouseById(Long id)
    {
        return wmsWarehouseMapper.deleteWmsWarehouseById(id);
    }
}
