package com.ruoyi.wms.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wms.mapper.WmsOutboundDetailMapper;
import com.ruoyi.wms.domain.WmsOutboundDetail;
import com.ruoyi.wms.service.IWmsOutboundDetailService;

/**
 * 出库单明细Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
@Service
public class WmsOutboundDetailServiceImpl implements IWmsOutboundDetailService 
{
    @Autowired
    private WmsOutboundDetailMapper wmsOutboundDetailMapper;

    /**
     * 查询出库单明细
     * 
     * @param id 出库单明细主键
     * @return 出库单明细
     */
    @Override
    public WmsOutboundDetail selectWmsOutboundDetailById(Long id)
    {
        return wmsOutboundDetailMapper.selectWmsOutboundDetailById(id);
    }

    /**
     * 查询出库单明细列表
     * 
     * @param wmsOutboundDetail 出库单明细
     * @return 出库单明细
     */
    @Override
    public List<WmsOutboundDetail> selectWmsOutboundDetailList(WmsOutboundDetail wmsOutboundDetail)
    {
        return wmsOutboundDetailMapper.selectWmsOutboundDetailList(wmsOutboundDetail);
    }

    /**
     * 新增出库单明细
     * 
     * @param wmsOutboundDetail 出库单明细
     * @return 结果
     */
    @Override
    public int insertWmsOutboundDetail(WmsOutboundDetail wmsOutboundDetail)
    {
        wmsOutboundDetail.setCreateTime(DateUtils.getNowDate());
        return wmsOutboundDetailMapper.insertWmsOutboundDetail(wmsOutboundDetail);
    }

    /**
     * 修改出库单明细
     * 
     * @param wmsOutboundDetail 出库单明细
     * @return 结果
     */
    @Override
    public int updateWmsOutboundDetail(WmsOutboundDetail wmsOutboundDetail)
    {
        return wmsOutboundDetailMapper.updateWmsOutboundDetail(wmsOutboundDetail);
    }

    /**
     * 批量删除出库单明细
     * 
     * @param ids 需要删除的出库单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmsOutboundDetailByIds(Long[] ids)
    {
        return wmsOutboundDetailMapper.deleteWmsOutboundDetailByIds(ids);
    }

    /**
     * 删除出库单明细信息
     * 
     * @param id 出库单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmsOutboundDetailById(Long id)
    {
        return wmsOutboundDetailMapper.deleteWmsOutboundDetailById(id);
    }
}
