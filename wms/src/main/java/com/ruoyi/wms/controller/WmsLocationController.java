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
import com.ruoyi.wms.domain.WmsLocation;
import com.ruoyi.wms.service.IWmsLocationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 库位信息Controller
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
@RestController
@RequestMapping("/wms/location")
public class WmsLocationController extends BaseController
{
    @Autowired
    private IWmsLocationService wmsLocationService;

    /**
     * 查询库位信息列表
     */
    @PreAuthorize("@ss.hasPermi('wms:location:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmsLocation wmsLocation)
    {
        startPage();
        List<WmsLocation> list = wmsLocationService.selectWmsLocationList(wmsLocation);
        return getDataTable(list);
    }

    /**
     * 导出库位信息列表
     */
    @PreAuthorize("@ss.hasPermi('wms:location:export')")
    @Log(title = "库位信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsLocation wmsLocation)
    {
        List<WmsLocation> list = wmsLocationService.selectWmsLocationList(wmsLocation);
        ExcelUtil<WmsLocation> util = new ExcelUtil<WmsLocation>(WmsLocation.class);
        util.exportExcel(response, list, "库位信息数据");
    }

    /**
     * 获取库位信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('wms:location:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wmsLocationService.selectWmsLocationById(id));
    }

    /**
     * 新增库位信息
     */
    @PreAuthorize("@ss.hasPermi('wms:location:add')")
    @Log(title = "库位信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsLocation wmsLocation)
    {
        return toAjax(wmsLocationService.insertWmsLocation(wmsLocation));
    }

    /**
     * 修改库位信息
     */
    @PreAuthorize("@ss.hasPermi('wms:location:edit')")
    @Log(title = "库位信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsLocation wmsLocation)
    {
        return toAjax(wmsLocationService.updateWmsLocation(wmsLocation));
    }

    /**
     * 删除库位信息
     */
    @PreAuthorize("@ss.hasPermi('wms:location:remove')")
    @Log(title = "库位信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsLocationService.deleteWmsLocationByIds(ids));
    }
}
