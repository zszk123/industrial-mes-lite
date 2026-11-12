package com.ruoyi.wms.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 入库单明细对象 wms_inbound_detail
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
public class WmsInboundDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 明细主键ID */
    private Long id;

    /** 关联入库主单ID */
    @Excel(name = "关联入库主单ID")
    private Long orderId;

    /** 入库单号 */
    @Excel(name = "入库单号")
    private String orderNo;

    /** 物料ID */
    @Excel(name = "物料ID")
    private Long materialId;

    /** 上架目标库位ID(验收上架时分配) */
    @Excel(name = "上架目标库位ID(验收上架时分配)")
    private Long locationId;

    /** 计划送货数量 */
    @Excel(name = "计划送货数量")
    private Long planQty;

    /** 验收合格数量(实际增加到可用库存的数量) */
    @Excel(name = "验收合格数量(实际增加到可用库存的数量)")
    private Long qualifiedQty;

    /** 残损/不合格数量 */
    @Excel(name = "残损/不合格数量")
    private Long defectiveQty;

    /** 残损原因记录 */
    @Excel(name = "残损原因记录")
    private String defectReason;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }

    public void setOrderNo(String orderNo) 
    {
        this.orderNo = orderNo;
    }

    public String getOrderNo() 
    {
        return orderNo;
    }

    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }

    public void setLocationId(Long locationId) 
    {
        this.locationId = locationId;
    }

    public Long getLocationId() 
    {
        return locationId;
    }

    public void setPlanQty(Long planQty) 
    {
        this.planQty = planQty;
    }

    public Long getPlanQty() 
    {
        return planQty;
    }

    public void setQualifiedQty(Long qualifiedQty) 
    {
        this.qualifiedQty = qualifiedQty;
    }

    public Long getQualifiedQty() 
    {
        return qualifiedQty;
    }

    public void setDefectiveQty(Long defectiveQty) 
    {
        this.defectiveQty = defectiveQty;
    }

    public Long getDefectiveQty() 
    {
        return defectiveQty;
    }

    public void setDefectReason(String defectReason) 
    {
        this.defectReason = defectReason;
    }

    public String getDefectReason() 
    {
        return defectReason;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("orderNo", getOrderNo())
            .append("materialId", getMaterialId())
            .append("locationId", getLocationId())
            .append("planQty", getPlanQty())
            .append("qualifiedQty", getQualifiedQty())
            .append("defectiveQty", getDefectiveQty())
            .append("defectReason", getDefectReason())
            .append("createTime", getCreateTime())
            .toString();
    }
}
