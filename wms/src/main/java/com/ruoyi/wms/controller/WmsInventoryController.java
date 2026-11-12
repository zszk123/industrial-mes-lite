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
import com.ruoyi.wms.domain.WmsInventory;
import com.ruoyi.wms.service.IWmsInventoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 仓库实时库存Controller
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
@RestController
@RequestMapping("/wms/inventory")
public class WmsInventoryController extends BaseController
{
    @Autowired
    private IWmsInventoryService wmsInventoryService;

    /**
     * 查询仓库实时库存列表
     */
    @PreAuthorize("@ss.hasPermi('wms:inventory:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmsInventory wmsInventory)
    {
        startPage();
        List<WmsInventory> list = wmsInventoryService.selectWmsInventoryList(wmsInventory);
        return getDataTable(list);
    }

    /**
     * 导出仓库实时库存列表
     */
    @PreAuthorize("@ss.hasPermi('wms:inventory:export')")
    @Log(title = "仓库实时库存", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsInventory wmsInventory)
    {
        List<WmsInventory> list = wmsInventoryService.selectWmsInventoryList(wmsInventory);
        ExcelUtil<WmsInventory> util = new ExcelUtil<WmsInventory>(WmsInventory.class);
        util.exportExcel(response, list, "仓库实时库存数据");
    }

    /**
     * 获取仓库实时库存详细信息
     */
    @PreAuthorize("@ss.hasPermi('wms:inventory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wmsInventoryService.selectWmsInventoryById(id));
    }

    /**
     * 新增仓库实时库存
     */
    @PreAuthorize("@ss.hasPermi('wms:inventory:add')")
    @Log(title = "仓库实时库存", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsInventory wmsInventory)
    {
        return toAjax(wmsInventoryService.insertWmsInventory(wmsInventory));
    }

    /**
     * 修改仓库实时库存
     */
    @PreAuthorize("@ss.hasPermi('wms:inventory:edit')")
    @Log(title = "仓库实时库存", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsInventory wmsInventory)
    {
        return toAjax(wmsInventoryService.updateWmsInventory(wmsInventory));
    }

    /**
     * 删除仓库实时库存
     */
    @PreAuthorize("@ss.hasPermi('wms:inventory:remove')")
    @Log(title = "仓库实时库存", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsInventoryService.deleteWmsInventoryByIds(ids));
    }
}
