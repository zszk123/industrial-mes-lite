package com.ruoyi.wms.mapper;

import java.util.List;
import com.ruoyi.wms.domain.WmsWarehouse;

/**
 * 仓库信息Mapper接口
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
public interface WmsWarehouseMapper 
{
    /**
     * 查询仓库信息
     * 
     * @param id 仓库信息主键
     * @return 仓库信息
     */
    public WmsWarehouse selectWmsWarehouseById(Long id);

    /**
     * 查询仓库信息列表
     * 
     * @param wmsWarehouse 仓库信息
     * @return 仓库信息集合
     */
    public List<WmsWarehouse> selectWmsWarehouseList(WmsWarehouse wmsWarehouse);

    /**
     * 新增仓库信息
     * 
     * @param wmsWarehouse 仓库信息
     * @return 结果
     */
    public int insertWmsWarehouse(WmsWarehouse wmsWarehouse);

    /**
     * 修改仓库信息
     * 
     * @param wmsWarehouse 仓库信息
     * @return 结果
     */
    public int updateWmsWarehouse(WmsWarehouse wmsWarehouse);

    /**
     * 删除仓库信息
     * 
     * @param id 仓库信息主键
     * @return 结果
     */
    public int deleteWmsWarehouseById(Long id);

    /**
     * 批量删除仓库信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsWarehouseByIds(Long[] ids);
}
