package com.ruoyi.wms.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.wms.domain.WmsInboundOrder;

/**
 * 入库单主Mapper接口
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
public interface WmsInboundOrderMapper 
{
    /**
     * 查询入库单主
     * 
     * @param id 入库单主主键
     * @return 入库单主
     */
    public WmsInboundOrder selectWmsInboundOrderById(Long id);

    /**
     * 查询入库单主列表
     * 
     * @param wmsInboundOrder 入库单主
     * @return 入库单主集合
     */
    public List<WmsInboundOrder> selectWmsInboundOrderList(WmsInboundOrder wmsInboundOrder);

    /**
     * 新增入库单主
     * 
     * @param wmsInboundOrder 入库单主
     * @return 结果
     */
    public int insertWmsInboundOrder(WmsInboundOrder wmsInboundOrder);

    /**
     * 修改入库单主
     * 
     * @param wmsInboundOrder 入库单主
     * @return 结果
     */
    public int updateWmsInboundOrder(WmsInboundOrder wmsInboundOrder);

    /**
     * 删除入库单主
     * 
     * @param id 入库单主主键
     * @return 结果
     */
    public int deleteWmsInboundOrderById(Long id);

    /**
     * 批量删除入库单主
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsInboundOrderByIds(Long[] ids);

    /**
     * 入库完成（状态 CAS：仅当当前状态为"待验收"时更新为"已完成入库"）
     * WHERE 条件携带 status = 0，天然防止重复入库与并发状态覆盖
     *
     * @param id 入库单主键
     * @param updateBy 验收上架人
     * @return 影响行数（0表示单据状态已变更或不存在）
     */
    public int completeWmsInboundOrder(@Param("id") Long id, @Param("updateBy") String updateBy);

    /**
     * 取消入库单（状态 CAS：仅当当前状态为"待验收/已完成入库"时更新为"已取消"）
     * 状态为"已完成入库"时，需先在业务层回溯 INBOUND 流水扣回库存，再执行本 CAS
     *
     * @param id 入库单主键
     * @param updateBy 取消操作人
     * @return 影响行数（0表示单据已取消或不存在）
     */
    public int cancelWmsInboundOrder(@Param("id") Long id, @Param("updateBy") String updateBy);
}
