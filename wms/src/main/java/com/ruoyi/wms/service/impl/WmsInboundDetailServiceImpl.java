package com.ruoyi.wms.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wms.mapper.WmsInboundDetailMapper;
import com.ruoyi.wms.domain.WmsInboundDetail;
import com.ruoyi.wms.service.IWmsInboundDetailService;

/**
 * 入库单明细Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
@Service
public class WmsInboundDetailServiceImpl implements IWmsInboundDetailService 
{
    @Autowired
    private WmsInboundDetailMapper wmsInboundDetailMapper;

    /**
     * 查询入库单明细
     * 
     * @param id 入库单明细主键
     * @return 入库单明细
     */
    @Override
    public WmsInboundDetail selectWmsInboundDetailById(Long id)
    {
        return wmsInboundDetailMapper.selectWmsInboundDetailById(id);
    }

    /**
     * 查询入库单明细列表
     * 
     * @param wmsInboundDetail 入库单明细
     * @return 入库单明细
     */
    @Override
    public List<WmsInboundDetail> selectWmsInboundDetailList(WmsInboundDetail wmsInboundDetail)
    {
        return wmsInboundDetailMapper.selectWmsInboundDetailList(wmsInboundDetail);
    }

    /**
     * 新增入库单明细
     * 
     * @param wmsInboundDetail 入库单明细
     * @return 结果
     */
    @Override
    public int insertWmsInboundDetail(WmsInboundDetail wmsInboundDetail)
    {
        wmsInboundDetail.setCreateTime(DateUtils.getNowDate());
        return wmsInboundDetailMapper.insertWmsInboundDetail(wmsInboundDetail);
    }

    /**
     * 修改入库单明细
     * 
     * @param wmsInboundDetail 入库单明细
     * @return 结果
     */
    @Override
    public int updateWmsInboundDetail(WmsInboundDetail wmsInboundDetail)
    {
        return wmsInboundDetailMapper.updateWmsInboundDetail(wmsInboundDetail);
    }

    /**
     * 批量删除入库单明细
     * 
     * @param ids 需要删除的入库单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmsInboundDetailByIds(Long[] ids)
    {
        return wmsInboundDetailMapper.deleteWmsInboundDetailByIds(ids);
    }

    /**
     * 删除入库单明细信息
     * 
     * @param id 入库单明细主键
     * @return 结果
     */
    @Override
    public int deleteWmsInboundDetailById(Long id)
    {
        return wmsInboundDetailMapper.deleteWmsInboundDetailById(id);
    }
}
