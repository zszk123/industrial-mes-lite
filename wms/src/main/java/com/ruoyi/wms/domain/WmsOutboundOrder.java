package com.ruoyi.wms.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 出库主单对象 wms_outbound_order
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
public class WmsOutboundOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 出库单主键ID */
    private Long id;

    /** 出库单号(唯一业务单号，防重幂等) */
    @Excel(name = "出库单号(唯一业务单号，防重幂等)")
    private String orderNo;

    /** 出库类型(1-车间领料, 2-盘亏出库) */
    @Excel(name = "出库类型(1-车间领料, 2-盘亏出库)")
    private Long orderType;

    /** 出库仓库ID */
    @Excel(name = "出库仓库ID")
    private Long warehouseId;

    /** 申请领料部门ID(如 装配一班 dept_id) */
    @Excel(name = "申请领料部门ID(如 装配一班 dept_id)")
    private Long applyDeptId;

    /** 单据状态(0-待审核, 1-已审核待出库/已锁库, 2-已完成出库, 3-已取消) */
    @Excel(name = "单据状态(0-待审核, 1-已审核待出库/已锁库, 2-已完成出库, 3-已取消)")
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

    public void setApplyDeptId(Long applyDeptId) 
    {
        this.applyDeptId = applyDeptId;
    }

    public Long getApplyDeptId() 
    {
        return applyDeptId;
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
            .append("applyDeptId", getApplyDeptId())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
