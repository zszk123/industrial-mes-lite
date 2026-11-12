package com.ruoyi.wms.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 仓库信息对象 wms_warehouse
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
public class WmsWarehouse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 仓库主键ID */
    private Long id;

    /** 仓库编码(如 WH01) */
    @Excel(name = "仓库编码(如 WH01)")
    private String warehouseCode;

    /** 仓库名称(如 原料一号仓) */
    @Excel(name = "仓库名称(如 原料一号仓)")
    private String warehouseName;

    /** 归属管理部门ID(关联 sys_dept，用于数据权限) */
    @Excel(name = "归属管理部门ID(关联 sys_dept，用于数据权限)")
    private Long deptId;

    /** 仓库状态(0-正常, 1-停用) */
    @Excel(name = "仓库状态(0-正常, 1-停用)")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setWarehouseCode(String warehouseCode) 
    {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseCode() 
    {
        return warehouseCode;
    }

    public void setWarehouseName(String warehouseName) 
    {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseName() 
    {
        return warehouseName;
    }

    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
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
            .append("warehouseCode", getWarehouseCode())
            .append("warehouseName", getWarehouseName())
            .append("deptId", getDeptId())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
