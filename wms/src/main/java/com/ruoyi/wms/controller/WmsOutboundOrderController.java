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

    /**
     * 出库主流程（待审核 → 已完成出库，扣减库存+写流水+状态直改为2；状态1为后续MQ预留中间态）
     * 接口文档：
     * 请求方式：PUT /wms/outbound/{id}/approve
     * 权限标识：wms:outbound:approve
     * 成功返回：{"code":200,"msg":"操作成功"}
     * 业务失败返回：{"code":500,"msg":"物料[xxx]可用库存不足，还缺 n 个"}（示例）
     */
    @PreAuthorize("@ss.hasPermi('wms:outbound:approve')")
    @Log(title = "出库单审核", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}/approve")
    public AjaxResult approve(@PathVariable("id") Long id)
    {
        return toAjax(wmsOutboundOrderService.approveOutboundOrder(id));
    }

    /**
     * 取消出库单（待审核/已审核待出库/已完成出库 → 已取消；已完成出库 → 回溯流水回补库存后 → 已取消）
     * 接口文档：
     * 请求方式：PUT /wms/outbound/{id}/cancel
     * 权限标识：wms:outbound:cancel
     * 成功返回：{"code":200,"msg":"操作成功"}
     * 业务失败返回：{"code":500,"msg":"单据已取消，请勿重复操作"}（示例）
     */
    @PreAuthorize("@ss.hasPermi('wms:outbound:cancel')")
    @Log(title = "出库单取消", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}/cancel")
    public AjaxResult cancel(@PathVariable("id") Long id)
    {
        return toAjax(wmsOutboundOrderService.cancelOutboundOrder(id));
    }
}
