package com.ruoyi.wms.mapper;

import java.util.List;
import com.ruoyi.wms.domain.WmsInboundDetail;

/**
 * 入库单明细Mapper接口
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
public interface WmsInboundDetailMapper 
{
    /**
     * 查询入库单明细
     * 
     * @param id 入库单明细主键
     * @return 入库单明细
     */
    public WmsInboundDetail selectWmsInboundDetailById(Long id);

    /**
     * 查询入库单明细列表
     * 
     * @param wmsInboundDetail 入库单明细
     * @return 入库单明细集合
     */
    public List<WmsInboundDetail> selectWmsInboundDetailList(WmsInboundDetail wmsInboundDetail);

    /**
     * 新增入库单明细
     * 
     * @param wmsInboundDetail 入库单明细
     * @return 结果
     */
    public int insertWmsInboundDetail(WmsInboundDetail wmsInboundDetail);

    /**
     * 修改入库单明细
     * 
     * @param wmsInboundDetail 入库单明细
     * @return 结果
     */
    public int updateWmsInboundDetail(WmsInboundDetail wmsInboundDetail);

    /**
     * 删除入库单明细
     * 
     * @param id 入库单明细主键
     * @return 结果
     */
    public int deleteWmsInboundDetailById(Long id);

    /**
     * 批量删除入库单明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsInboundDetailByIds(Long[] ids);
}
