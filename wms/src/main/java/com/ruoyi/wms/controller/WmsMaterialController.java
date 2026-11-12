package com.ruoyi.wms.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.wms.domain.WmsMaterial;
import com.ruoyi.wms.service.IWmsMaterialService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 物料信息Controller
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
@RestController
@RequestMapping("/wms/material")
public class WmsMaterialController extends BaseController
{
    @Autowired
    private IWmsMaterialService wmsMaterialService;

    /**
     * 查询物料信息列表
     */
    @PreAuthorize("@ss.hasPermi('wms:material:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmsMaterial wmsMaterial)
    {
        startPage();
        List<WmsMaterial> list = wmsMaterialService.selectWmsMaterialList(wmsMaterial);
        return getDataTable(list);
    }

    /**
     * 导出物料信息列表
     */
    @PreAuthorize("@ss.hasPermi('wms:material:export')")
    @Log(title = "物料信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsMaterial wmsMaterial)
    {
        List<WmsMaterial> list = wmsMaterialService.selectWmsMaterialList(wmsMaterial);
        ExcelUtil<WmsMaterial> util = new ExcelUtil<WmsMaterial>(WmsMaterial.class);
        util.exportExcel(response, list, "物料信息数据");
    }

    /**
     * 获取物料信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('wms:material:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wmsMaterialService.selectWmsMaterialById(id));
    }

    /**
     * 新增物料信息
     */
    @PreAuthorize("@ss.hasPermi('wms:material:add')")
    @Log(title = "物料信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsMaterial wmsMaterial)
    {
        return toAjax(wmsMaterialService.insertWmsMaterial(wmsMaterial));
    }

    /**
     * 修改物料信息
     */
    @PreAuthorize("@ss.hasPermi('wms:material:edit')")
    @Log(title = "物料信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsMaterial wmsMaterial)
    {
        return toAjax(wmsMaterialService.updateWmsMaterial(wmsMaterial));
    }

    /**
     * 删除物料信息
     */
    @PreAuthorize("@ss.hasPermi('wms:material:remove')")
    @Log(title = "物料信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsMaterialService.deleteWmsMaterialByIds(ids));
    }
}
