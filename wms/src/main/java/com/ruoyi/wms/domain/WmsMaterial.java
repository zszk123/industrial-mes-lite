package com.ruoyi.wms.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 物料信息对象 wms_material
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
public class WmsMaterial extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 物料主键ID */
    private Long id;

    /** 物料编码(唯一标识) */
    @Excel(name = "物料编码(唯一标识)")
    private String materialCode;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialName;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String spec;

    /** 计量单位(个/箱/kg) */
    @Excel(name = "计量单位(个/箱/kg)")
    private String unit;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setMaterialCode(String materialCode) 
    {
        this.materialCode = materialCode;
    }

    public String getMaterialCode() 
    {
        return materialCode;
    }

    public void setMaterialName(String materialName) 
    {
        this.materialName = materialName;
    }

    public String getMaterialName() 
    {
        return materialName;
    }

    public void setSpec(String spec) 
    {
        this.spec = spec;
    }

    public String getSpec() 
    {
        return spec;
    }

    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("materialCode", getMaterialCode())
            .append("materialName", getMaterialName())
            .append("spec", getSpec())
            .append("unit", getUnit())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
