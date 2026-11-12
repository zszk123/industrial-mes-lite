package com.ruoyi.wms.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 仓库实时库存对象 wms_inventory
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
public class WmsInventory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 库存主键ID */
    private Long id;

    /** 所属仓库ID */
    @Excel(name = "所属仓库ID")
    private Long warehouseId;

    /** 所属库位ID */
    @Excel(name = "所属库位ID")
    private Long locationId;

    /** 物料ID */
    @Excel(name = "物料ID")
    private Long materialId;

    /** 可用库存量(可被领料分配) */
    @Excel(name = "可用库存量(可被领料分配)")
    private Long availableQty;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setWarehouseId(Long warehouseId) 
    {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() 
    {
        return warehouseId;
    }

    public void setLocationId(Long locationId) 
    {
        this.locationId = locationId;
    }

    public Long getLocationId() 
    {
        return locationId;
    }

    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }

    public void setAvailableQty(Long availableQty) 
    {
        this.availableQty = availableQty;
    }

    public Long getAvailableQty()
    {
        return availableQty;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("warehouseId", getWarehouseId())
            .append("locationId", getLocationId())
            .append("materialId", getMaterialId())
            .append("availableQty", getAvailableQty())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
