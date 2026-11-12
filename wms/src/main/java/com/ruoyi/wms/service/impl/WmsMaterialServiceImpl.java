package com.ruoyi.wms.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wms.mapper.WmsMaterialMapper;
import com.ruoyi.wms.domain.WmsMaterial;
import com.ruoyi.wms.service.IWmsMaterialService;

/**
 * 物料信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
@Service
public class WmsMaterialServiceImpl implements IWmsMaterialService 
{
    @Autowired
    private WmsMaterialMapper wmsMaterialMapper;

    /**
     * 查询物料信息
     * 
     * @param id 物料信息主键
     * @return 物料信息
     */
    @Override
    public WmsMaterial selectWmsMaterialById(Long id)
    {
        return wmsMaterialMapper.selectWmsMaterialById(id);
    }

    /**
     * 查询物料信息列表
     * 
     * @param wmsMaterial 物料信息
     * @return 物料信息
     */
    @Override
    public List<WmsMaterial> selectWmsMaterialList(WmsMaterial wmsMaterial)
    {
        return wmsMaterialMapper.selectWmsMaterialList(wmsMaterial);
    }

    /**
     * 新增物料信息
     * 
     * @param wmsMaterial 物料信息
     * @return 结果
     */
    @Override
    public int insertWmsMaterial(WmsMaterial wmsMaterial)
    {
        wmsMaterial.setCreateTime(DateUtils.getNowDate());
        return wmsMaterialMapper.insertWmsMaterial(wmsMaterial);
    }

    /**
     * 修改物料信息
     * 
     * @param wmsMaterial 物料信息
     * @return 结果
     */
    @Override
    public int updateWmsMaterial(WmsMaterial wmsMaterial)
    {
        wmsMaterial.setUpdateTime(DateUtils.getNowDate());
        return wmsMaterialMapper.updateWmsMaterial(wmsMaterial);
    }

    /**
     * 批量删除物料信息
     * 
     * @param ids 需要删除的物料信息主键
     * @return 结果
     */
    @Override
    public int deleteWmsMaterialByIds(Long[] ids)
    {
        return wmsMaterialMapper.deleteWmsMaterialByIds(ids);
    }

    /**
     * 删除物料信息信息
     * 
     * @param id 物料信息主键
     * @return 结果
     */
    @Override
    public int deleteWmsMaterialById(Long id)
    {
        return wmsMaterialMapper.deleteWmsMaterialById(id);
    }
}
