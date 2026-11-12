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
import com.ruoyi.wms.domain.WmsOutboundOrder;
import com.ruoyi.wms.service.IWmsOutboundOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 出库主单Controller
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
@RestController
@RequestMapping("/wms/outbound")
public class WmsOutboundOrderController extends BaseController
{
    @Autowired
    private IWmsOutboundOrderService wmsOutboundOrderService;

    /**
     * 查询出库主单列表
     */
    @PreAuthorize("@ss.hasPermi('wms:outbound:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmsOutboundOrder wmsOutboundOrder)
    {
        startPage();
        List<WmsOutboundOrder> list = wmsOutboundOrderService.selectWmsOutboundOrderList(wmsOutboundOrder);
        return getDataTable(list);
    }

    /**
     * 导出出库主单列表
     */
    @PreAuthorize("@ss.hasPermi('wms:outbound:export')")
    @Log(title = "出库主单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsOutboundOrder wmsOutboundOrder)
    {
        List<WmsOutboundOrder> list = wmsOutboundOrderService.selectWmsOutboundOrderList(wmsOutboundOrder);
        ExcelUtil<WmsOutboundOrder> util = new ExcelUtil<WmsOutboundOrder>(WmsOutboundOrder.class);
        util.exportExcel(response, list, "出库主单数据");
    }

    /**
     * 获取出库主单详细信息
     */
    @PreAuthorize("@ss.hasPermi('wms:outbound:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wmsOutboundOrderService.selectWmsOutboundOrderById(id));
    }

    /**
     * 新增出库主单
     */
    @PreAuthorize("@ss.hasPermi('wms:outbound:add')")
    @Log(title = "出库主单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsOutboundOrder wmsOutboundOrder)
    {
        return toAjax(wmsOutboundOrderService.insertWmsOutboundOrder(wmsOutboundOrder));
    }

    /**
     * 修改出库主单
     */
    @PreAuthorize("@ss.hasPermi('wms:outbound:edit')")
    @Log(title = "出库主单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsOutboundOrder wmsOutboundOrder)
    {
        return toAjax(wmsOutboundOrderService.updateWmsOutboundOrder(wmsOutboundOrder));
    }

    /**
     * 删除出库主单
     */
    @PreAuthorize("@ss.hasPermi('wms:outbound:remove')")
    @Log(title = "出库主单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsOutboundOrderService.deleteWmsOutboundOrderByIds(ids));
    }
}
