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
import com.ruoyi.wms.domain.WmsOutboundDetail;
import com.ruoyi.wms.service.IWmsOutboundDetailService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 出库单明细Controller
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
@RestController
@RequestMapping("/wms/outdetail")
public class WmsOutboundDetailController extends BaseController
{
    @Autowired
    private IWmsOutboundDetailService wmsOutboundDetailService;

    /**
     * 查询出库单明细列表
     */
    @PreAuthorize("@ss.hasPermi('wms:outdetail:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmsOutboundDetail wmsOutboundDetail)
    {
        startPage();
        List<WmsOutboundDetail> list = wmsOutboundDetailService.selectWmsOutboundDetailList(wmsOutboundDetail);
        return getDataTable(list);
    }

    /**
     * 导出出库单明细列表
     */
    @PreAuthorize("@ss.hasPermi('wms:outdetail:export')")
    @Log(title = "出库单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsOutboundDetail wmsOutboundDetail)
    {
        List<WmsOutboundDetail> list = wmsOutboundDetailService.selectWmsOutboundDetailList(wmsOutboundDetail);
        ExcelUtil<WmsOutboundDetail> util = new ExcelUtil<WmsOutboundDetail>(WmsOutboundDetail.class);
        util.exportExcel(response, list, "出库单明细数据");
    }

    /**
     * 获取出库单明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('wms:outdetail:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wmsOutboundDetailService.selectWmsOutboundDetailById(id));
    }

    /**
     * 新增出库单明细
     */
    @PreAuthorize("@ss.hasPermi('wms:outdetail:add')")
    @Log(title = "出库单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsOutboundDetail wmsOutboundDetail)
    {
        return toAjax(wmsOutboundDetailService.insertWmsOutboundDetail(wmsOutboundDetail));
    }

    /**
     * 修改出库单明细
     */
    @PreAuthorize("@ss.hasPermi('wms:outdetail:edit')")
    @Log(title = "出库单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsOutboundDetail wmsOutboundDetail)
    {
        return toAjax(wmsOutboundDetailService.updateWmsOutboundDetail(wmsOutboundDetail));
    }

    /**
     * 删除出库单明细
     */
    @PreAuthorize("@ss.hasPermi('wms:outdetail:remove')")
    @Log(title = "出库单明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsOutboundDetailService.deleteWmsOutboundDetailByIds(ids));
    }
}
