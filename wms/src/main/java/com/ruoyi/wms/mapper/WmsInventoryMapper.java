package com.ruoyi.wms.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.wms.domain.WmsInventory;

/**
 * 仓库实时库存Mapper接口
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
public interface WmsInventoryMapper 
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
     * 删除仓库实时库存
     * 
     * @param id 仓库实时库存主键
     * @return 结果
     */
    public int deleteWmsInventoryById(Long id);

    /**
     * 批量删除仓库实时库存
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsInventoryByIds(Long[] ids);

    /**
     * 查询指定仓库下某物料有可用量的库存（可用量大于0，按可用量降序，优先从库存充足的库位扣减）
     *
     * @param warehouseId 仓库ID
     * @param materialId 物料ID
     * @return 可用库存集合
     */
    public List<WmsInventory> selectAvailableInventoryList(@Param("warehouseId") Long warehouseId, @Param("materialId") Long materialId);

    /**
     * 扣减库存
     * WHERE 条件携带 available_qty >= 扣减数量，原子完成"校验+扣减"，防止超卖与并发脏写
     *
     * @param id 库存主键ID
     * @param deductQty 扣减数量
     * @return 影响行数（0表示可用库存不足）
     */
    public int deductInventory(@Param("id") Long id, @Param("deductQty") Long deductQty);

    /**
     * 入库增加库存（存在则累加，不存在则新建）
     * 依赖唯一键 uk_loc_mat(location_id, material_id) 实现 upsert 语义
     *
     * @param warehouseId 仓库ID
     * @param locationId 库位ID
     * @param materialId 物料ID
     * @param addQty 入库数量
     * @param createBy 创建人（首条记录时写入）
     * @param updateBy 更新人（累加时写入）
     * @return 影响行数
     */
    public int upsertAddInventory(@Param("warehouseId") Long warehouseId, @Param("locationId") Long locationId,
                                  @Param("materialId") Long materialId, @Param("addQty") Long addQty,
                                  @Param("createBy") String createBy, @Param("updateBy") String updateBy);

    /**
     * 取消入库时扣回库存（按库位+物料定位，WHERE 携带 available_qty >= 数量防止扣成负数）
     *
     * @param locationId 库位ID
     * @param materialId 物料ID
     * @param changeQty 扣回数量
     * @param updateBy 操作人
     * @return 影响行数（0表示可用库存不足，说明已入库物料被领用，无法取消）
     */
    public int retrieveInventory(@Param("locationId") Long locationId, @Param("materialId") Long materialId,
                                 @Param("changeQty") Long changeQty, @Param("updateBy") String updateBy);
}
