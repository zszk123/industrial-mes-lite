package com.ruoyi.wms.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 入库单主对象 wms_inbound_order
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
public class WmsInboundOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 入库单主键ID */
    private Long id;

    /** 入库单号(唯一业务单号，防重幂等) */
    @Excel(name = "入库单号(唯一业务单号，防重幂等)")
    private String orderNo;

    /** 入库类型(1-采购入库, 2-生产退料入库, 3-盘盈入库) */
    @Excel(name = "入库类型(1-采购入库, 2-生产退料入库, 3-盘盈入库)")
    private Long orderType;

    /** 目标入库仓库ID */
    @Excel(name = "目标入库仓库ID")
    private Long warehouseId;

    /** 单据状态(0-待验收, 1-已验收待上架, 2-已完成入库, 3-已取消) */
    @Excel(name = "单据状态(0-待验收, 1-已验收待上架, 2-已完成入库, 3-已取消)")
    private Long status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setOrderNo(String orderNo) 
    {
        this.orderNo = orderNo;
    }

    public String getOrderNo() 
    {
        return orderNo;
    }

    public void setOrderType(Long orderType) 
    {
        this.orderType = orderType;
    }

    public Long getOrderType() 
    {
        return orderType;
    }

    public void setWarehouseId(Long warehouseId) 
    {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() 
    {
        return warehouseId;
    }

    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderNo", getOrderNo())
            .append("orderType", getOrderType())
            .append("warehouseId", getWarehouseId())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
