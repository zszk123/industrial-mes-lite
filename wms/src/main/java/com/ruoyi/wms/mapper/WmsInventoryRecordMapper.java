package com.ruoyi.wms.mapper;

import com.ruoyi.wms.domain.WmsInventoryRecord;

/**
 * 库存变动审计流水Mapper接口
 *
 * @author ruoyi
 * @date 2026-11-12
 */
public interface WmsInventoryRecordMapper
{
    /**
     * 新增库存变动流水
     * record_no 唯一键冲突时将抛出异常，作为流水幂等的最终兜底
     *
     * @param wmsInventoryRecord 流水信息
     * @return 结果
     */
    public int insertWmsInventoryRecord(WmsInventoryRecord wmsInventoryRecord);
}
