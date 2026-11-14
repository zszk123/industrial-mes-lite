package com.ruoyi.wms.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 库存变动审计流水对象 wms_inventory_record
 *
 * @author ruoyi
 * @date 2026-11-12
 */
public class WmsInventoryRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 流水主键ID */
    private Long id;

    /** 流水记录编号(唯一，防重幂等) */
    private String recordNo;

    /** 关联业务单号 */
    private String orderNo;

    /** 流水类型(INBOUND, OUTBOUND, CANCEL_IN, CANCEL_OUT) */
    private String recordType;

    /** 仓库ID */
    private Long warehouseId;

    /** 库位ID */
    private Long locationId;

    /** 物料ID */
    private Long materialId;

    /** 变动数量 */
    private Long changeQty;

    /** 变动前可用库存 */
    private Long beforeQty;

    /** 变动后可用库存 */
    private Long afterQty;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setRecordNo(String recordNo)
    {
        this.recordNo = recordNo;
    }

    public String getRecordNo()
    {
        return recordNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setRecordType(String recordType)
    {
        this.recordType = recordType;
    }

    public String getRecordType()
    {
        return recordType;
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

    public void setChangeQty(Long changeQty)
    {
        this.changeQty = changeQty;
    }

    public Long getChangeQty()
    {
        return changeQty;
    }

    public void setBeforeQty(Long beforeQty)
    {
        this.beforeQty = beforeQty;
    }

    public Long getBeforeQty()
    {
        return beforeQty;
    }

    public void setAfterQty(Long afterQty)
    {
        this.afterQty = afterQty;
    }

    public Long getAfterQty()
    {
        return afterQty;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("recordNo", getRecordNo())
            .append("orderNo", getOrderNo())
            .append("recordType", getRecordType())
            .append("warehouseId", getWarehouseId())
            .append("locationId", getLocationId())
            .append("materialId", getMaterialId())
            .append("changeQty", getChangeQty())
            .append("beforeQty", getBeforeQty())
            .append("afterQty", getAfterQty())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
