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
import com.ruoyi.wms.domain.WmsInboundDetail;
import com.ruoyi.wms.service.IWmsInboundDetailService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 入库单明细Controller
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
@RestController
@RequestMapping("/wms/indetail")
public class WmsInboundDetailController extends BaseController
{
    @Autowired
    private IWmsInboundDetailService wmsInboundDetailService;

    /**
     * 查询入库单明细列表
     */
    @PreAuthorize("@ss.hasPermi('wms:indetail:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmsInboundDetail wmsInboundDetail)
    {
        startPage();
        List<WmsInboundDetail> list = wmsInboundDetailService.selectWmsInboundDetailList(wmsInboundDetail);
        return getDataTable(list);
    }

    /**
     * 导出入库单明细列表
     */
    @PreAuthorize("@ss.hasPermi('wms:indetail:export')")
    @Log(title = "入库单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsInboundDetail wmsInboundDetail)
    {
        List<WmsInboundDetail> list = wmsInboundDetailService.selectWmsInboundDetailList(wmsInboundDetail);
        ExcelUtil<WmsInboundDetail> util = new ExcelUtil<WmsInboundDetail>(WmsInboundDetail.class);
        util.exportExcel(response, list, "入库单明细数据");
    }

    /**
     * 获取入库单明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('wms:indetail:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wmsInboundDetailService.selectWmsInboundDetailById(id));
    }

    /**
     * 新增入库单明细
     */
    @PreAuthorize("@ss.hasPermi('wms:indetail:add')")
    @Log(title = "入库单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsInboundDetail wmsInboundDetail)
    {
        return toAjax(wmsInboundDetailService.insertWmsInboundDetail(wmsInboundDetail));
    }

    /**
     * 修改入库单明细
     */
    @PreAuthorize("@ss.hasPermi('wms:indetail:edit')")
    @Log(title = "入库单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsInboundDetail wmsInboundDetail)
    {
        return toAjax(wmsInboundDetailService.updateWmsInboundDetail(wmsInboundDetail));
    }

    /**
     * 删除入库单明细
     */
    @PreAuthorize("@ss.hasPermi('wms:indetail:remove')")
    @Log(title = "入库单明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsInboundDetailService.deleteWmsInboundDetailByIds(ids));
    }
}
