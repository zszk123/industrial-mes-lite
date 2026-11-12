package com.ruoyi.wms.mapper;

import java.util.List;
import com.ruoyi.wms.domain.WmsLocation;

/**
 * 库位信息Mapper接口
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
public interface WmsLocationMapper 
{
    /**
     * 查询库位信息
     * 
     * @param id 库位信息主键
     * @return 库位信息
     */
    public WmsLocation selectWmsLocationById(Long id);

    /**
     * 查询库位信息列表
     * 
     * @param wmsLocation 库位信息
     * @return 库位信息集合
     */
    public List<WmsLocation> selectWmsLocationList(WmsLocation wmsLocation);

    /**
     * 新增库位信息
     * 
     * @param wmsLocation 库位信息
     * @return 结果
     */
    public int insertWmsLocation(WmsLocation wmsLocation);

    /**
     * 修改库位信息
     * 
     * @param wmsLocation 库位信息
     * @return 结果
     */
    public int updateWmsLocation(WmsLocation wmsLocation);

    /**
     * 删除库位信息
     * 
     * @param id 库位信息主键
     * @return 结果
     */
    public int deleteWmsLocationById(Long id);

    /**
     * 批量删除库位信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsLocationByIds(Long[] ids);
}
