package com.ruoyi.wms.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wms.mapper.WmsLocationMapper;
import com.ruoyi.wms.domain.WmsLocation;
import com.ruoyi.wms.service.IWmsLocationService;

/**
 * 库位信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
@Service
public class WmsLocationServiceImpl implements IWmsLocationService 
{
    @Autowired
    private WmsLocationMapper wmsLocationMapper;

    /**
     * 查询库位信息
     * 
     * @param id 库位信息主键
     * @return 库位信息
     */
    @Override
    public WmsLocation selectWmsLocationById(Long id)
    {
        return wmsLocationMapper.selectWmsLocationById(id);
    }

    /**
     * 查询库位信息列表
     * 
     * @param wmsLocation 库位信息
     * @return 库位信息
     */
    @Override
    public List<WmsLocation> selectWmsLocationList(WmsLocation wmsLocation)
    {
        return wmsLocationMapper.selectWmsLocationList(wmsLocation);
    }

    /**
     * 新增库位信息
     * 
     * @param wmsLocation 库位信息
     * @return 结果
     */
    @Override
    public int insertWmsLocation(WmsLocation wmsLocation)
    {
        wmsLocation.setCreateTime(DateUtils.getNowDate());
        return wmsLocationMapper.insertWmsLocation(wmsLocation);
    }

    /**
     * 修改库位信息
     * 
     * @param wmsLocation 库位信息
     * @return 结果
     */
    @Override
    public int updateWmsLocation(WmsLocation wmsLocation)
    {
        wmsLocation.setUpdateTime(DateUtils.getNowDate());
        return wmsLocationMapper.updateWmsLocation(wmsLocation);
    }

    /**
     * 批量删除库位信息
     * 
     * @param ids 需要删除的库位信息主键
     * @return 结果
     */
    @Override
    public int deleteWmsLocationByIds(Long[] ids)
    {
        return wmsLocationMapper.deleteWmsLocationByIds(ids);
    }

    /**
     * 删除库位信息信息
     * 
     * @param id 库位信息主键
     * @return 结果
     */
    @Override
    public int deleteWmsLocationById(Long id)
    {
        return wmsLocationMapper.deleteWmsLocationById(id);
    }
}
