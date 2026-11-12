package com.ruoyi.wms.mapper;

import java.util.List;
import com.ruoyi.wms.domain.WmsOutboundDetail;

/**
 * 出库单明细Mapper接口
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
public interface WmsOutboundDetailMapper 
{
    /**
     * 查询出库单明细
     * 
     * @param id 出库单明细主键
     * @return 出库单明细
     */
    public WmsOutboundDetail selectWmsOutboundDetailById(Long id);

    /**
     * 查询出库单明细列表
     * 
     * @param wmsOutboundDetail 出库单明细
     * @return 出库单明细集合
     */
    public List<WmsOutboundDetail> selectWmsOutboundDetailList(WmsOutboundDetail wmsOutboundDetail);

    /**
     * 新增出库单明细
     * 
     * @param wmsOutboundDetail 出库单明细
     * @return 结果
     */
    public int insertWmsOutboundDetail(WmsOutboundDetail wmsOutboundDetail);

    /**
     * 修改出库单明细
     * 
     * @param wmsOutboundDetail 出库单明细
     * @return 结果
     */
    public int updateWmsOutboundDetail(WmsOutboundDetail wmsOutboundDetail);

    /**
     * 删除出库单明细
     * 
     * @param id 出库单明细主键
     * @return 结果
     */
    public int deleteWmsOutboundDetailById(Long id);

    /**
     * 批量删除出库单明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsOutboundDetailByIds(Long[] ids);
}
