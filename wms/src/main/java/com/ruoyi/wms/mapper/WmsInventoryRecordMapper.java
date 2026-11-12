package com.ruoyi.wms.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 按业务单号与流水类型查询流水（取消单据时用于逐条回溯库存）
     *
     * @param orderNo 业务单号
     * @param recordType 流水类型(INBOUND, OUTBOUND)
     * @return 流水集合
     */
    public List<WmsInventoryRecord> selectRecordsByOrderNo(@Param("orderNo") String orderNo, @Param("recordType") String recordType);
}
