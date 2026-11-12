package com.ruoyi.wms.service;

import java.util.List;
import com.ruoyi.wms.domain.WmsOutboundDetail;

/**
 * 出库单明细Service接口
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
public interface IWmsOutboundDetailService 
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
     * 批量删除出库单明细
     * 
     * @param ids 需要删除的出库单明细主键集合
     * @return 结果
     */
    public int deleteWmsOutboundDetailByIds(Long[] ids);

    /**
     * 删除出库单明细信息
     * 
     * @param id 出库单明细主键
     * @return 结果
     */
    public int deleteWmsOutboundDetailById(Long id);
}
