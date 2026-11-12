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
import com.ruoyi.wms.domain.WmsWarehouse;
import com.ruoyi.wms.service.IWmsWarehouseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 仓库信息Controller
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
@RestController
@RequestMapping("/wms/warehouse")
public class WmsWarehouseController extends BaseController
{
    @Autowired
    private IWmsWarehouseService wmsWarehouseService;

    /**
     * 查询仓库信息列表
     */
    @PreAuthorize("@ss.hasPermi('wms:warehouse:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmsWarehouse wmsWarehouse)
    {
        startPage();
        List<WmsWarehouse> list = wmsWarehouseService.selectWmsWarehouseList(wmsWarehouse);
        return getDataTable(list);
    }

    /**
     * 导出仓库信息列表
     */
    @PreAuthorize("@ss.hasPermi('wms:warehouse:export')")
    @Log(title = "仓库信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsWarehouse wmsWarehouse)
    {
        List<WmsWarehouse> list = wmsWarehouseService.selectWmsWarehouseList(wmsWarehouse);
        ExcelUtil<WmsWarehouse> util = new ExcelUtil<WmsWarehouse>(WmsWarehouse.class);
        util.exportExcel(response, list, "仓库信息数据");
    }

    /**
     * 获取仓库信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('wms:warehouse:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wmsWarehouseService.selectWmsWarehouseById(id));
    }

    /**
     * 新增仓库信息
     */
    @PreAuthorize("@ss.hasPermi('wms:warehouse:add')")
    @Log(title = "仓库信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsWarehouse wmsWarehouse)
    {
        return toAjax(wmsWarehouseService.insertWmsWarehouse(wmsWarehouse));
    }

    /**
     * 修改仓库信息
     */
    @PreAuthorize("@ss.hasPermi('wms:warehouse:edit')")
    @Log(title = "仓库信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsWarehouse wmsWarehouse)
    {
        return toAjax(wmsWarehouseService.updateWmsWarehouse(wmsWarehouse));
    }

    /**
     * 删除仓库信息
     */
    @PreAuthorize("@ss.hasPermi('wms:warehouse:remove')")
    @Log(title = "仓库信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsWarehouseService.deleteWmsWarehouseByIds(ids));
    }
}
