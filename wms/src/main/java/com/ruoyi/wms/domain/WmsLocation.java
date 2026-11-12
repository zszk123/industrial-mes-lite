package com.ruoyi.wms.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 库位信息对象 wms_location
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
public class WmsLocation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 库位主键ID */
    private Long id;

    /** 所属仓库ID */
    @Excel(name = "所属仓库ID")
    private Long warehouseId;

    /** 库位编码(唯一，如 WH01-A-01-01) */
    @Excel(name = "库位编码(唯一，如 WH01-A-01-01)")
    private String locationCode;

    /** 所属库区(如 A区-电子原料区) */
    @Excel(name = "所属库区(如 A区-电子原料区)")
    private String zoneCode;

    /** 库位状态(0-正常空闲, 1-占用, 2-禁用停用) */
    @Excel(name = "库位状态(0-正常空闲, 1-占用, 2-禁用停用)")
    private String status;

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

    public void setLocationCode(String locationCode) 
    {
        this.locationCode = locationCode;
    }

    public String getLocationCode() 
    {
        return locationCode;
    }

    public void setZoneCode(String zoneCode) 
    {
        this.zoneCode = zoneCode;
    }

    public String getZoneCode() 
    {
        return zoneCode;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("warehouseId", getWarehouseId())
            .append("locationCode", getLocationCode())
            .append("zoneCode", getZoneCode())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
