package com.ruoyi.wms.mapper;

import java.util.List;
import com.ruoyi.wms.domain.WmsMaterial;

/**
 * 物料信息Mapper接口
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
public interface WmsMaterialMapper 
{
    /**
     * 查询物料信息
     * 
     * @param id 物料信息主键
     * @return 物料信息
     */
    public WmsMaterial selectWmsMaterialById(Long id);

    /**
     * 查询物料信息列表
     * 
     * @param wmsMaterial 物料信息
     * @return 物料信息集合
     */
    public List<WmsMaterial> selectWmsMaterialList(WmsMaterial wmsMaterial);

    /**
     * 新增物料信息
     * 
     * @param wmsMaterial 物料信息
     * @return 结果
     */
    public int insertWmsMaterial(WmsMaterial wmsMaterial);

    /**
     * 修改物料信息
     * 
     * @param wmsMaterial 物料信息
     * @return 结果
     */
    public int updateWmsMaterial(WmsMaterial wmsMaterial);

    /**
     * 删除物料信息
     * 
     * @param id 物料信息主键
     * @return 结果
     */
    public int deleteWmsMaterialById(Long id);

    /**
     * 批量删除物料信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsMaterialByIds(Long[] ids);
}
