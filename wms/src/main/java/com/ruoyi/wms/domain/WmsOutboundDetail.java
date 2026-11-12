package com.ruoyi.wms.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 出库单明细对象 wms_outbound_detail
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
public class WmsOutboundDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 明细主键ID */
    private Long id;

    /** 关联主单ID */
    @Excel(name = "关联主单ID")
    private Long orderId;

    /** 出库单号 */
    @Excel(name = "出库单号")
    private String orderNo;

    /** 物料ID */
    @Excel(name = "物料ID")
    private Long materialId;

    /** 计划领料数量 */
    @Excel(name = "计划领料数量")
    private Long planQty;

    /** 实际已出库数量 */
    @Excel(name = "实际已出库数量")
    private Long actualQty;

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

    public void setPlanQty(Long planQty) 
    {
        this.planQty = planQty;
    }

    public Long getPlanQty() 
    {
        return planQty;
    }

    public void setActualQty(Long actualQty) 
    {
        this.actualQty = actualQty;
    }

    public Long getActualQty() 
    {
        return actualQty;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("orderNo", getOrderNo())
            .append("materialId", getMaterialId())
            .append("planQty", getPlanQty())
            .append("actualQty", getActualQty())
            .append("createTime", getCreateTime())
            .toString();
    }
}
