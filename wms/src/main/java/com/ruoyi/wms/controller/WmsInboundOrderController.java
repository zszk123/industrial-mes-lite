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
import com.ruoyi.wms.domain.WmsInboundOrder;
import com.ruoyi.wms.service.IWmsInboundOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 入库单主Controller
 * 
 * @author ruoyi
 * @date 2026-11-12
 */
@RestController
@RequestMapping("/wms/inbound")
public class WmsInboundOrderController extends BaseController
{
    @Autowired
    private IWmsInboundOrderService wmsInboundOrderService;

    /**
     * 查询入库单主列表
     */
    @PreAuthorize("@ss.hasPermi('wms:inbound:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmsInboundOrder wmsInboundOrder)
    {
        startPage();
        List<WmsInboundOrder> list = wmsInboundOrderService.selectWmsInboundOrderList(wmsInboundOrder);
        return getDataTable(list);
    }

    /**
     * 导出入库单主列表
     */
    @PreAuthorize("@ss.hasPermi('wms:inbound:export')")
    @Log(title = "入库单主", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsInboundOrder wmsInboundOrder)
    {
        List<WmsInboundOrder> list = wmsInboundOrderService.selectWmsInboundOrderList(wmsInboundOrder);
        ExcelUtil<WmsInboundOrder> util = new ExcelUtil<WmsInboundOrder>(WmsInboundOrder.class);
        util.exportExcel(response, list, "入库单主数据");
    }

    /**
     * 获取入库单主详细信息
     */
    @PreAuthorize("@ss.hasPermi('wms:inbound:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wmsInboundOrderService.selectWmsInboundOrderById(id));
    }

    /**
     * 新增入库单主
     */
    @PreAuthorize("@ss.hasPermi('wms:inbound:add')")
    @Log(title = "入库单主", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsInboundOrder wmsInboundOrder)
    {
        return toAjax(wmsInboundOrderService.insertWmsInboundOrder(wmsInboundOrder));
    }

    /**
     * 修改入库单主
     */
    @PreAuthorize("@ss.hasPermi('wms:inbound:edit')")
    @Log(title = "入库单主", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsInboundOrder wmsInboundOrder)
    {
        return toAjax(wmsInboundOrderService.updateWmsInboundOrder(wmsInboundOrder));
    }

    /**
     * 删除入库单主
     */
    @PreAuthorize("@ss.hasPermi('wms:inbound:remove')")
    @Log(title = "入库单主", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsInboundOrderService.deleteWmsInboundOrderByIds(ids));
    }

    /**
     * 入库完成主流程（待验收 → 已完成入库，上架加库存）
     * 接口文档：
     * 请求方式：PUT /wms/inbound/{id}/complete
     * 权限标识：wms:inbound:complete
     * 前置条件：明细已通过"修改入库明细"接口指定上架库位(locationId)和验收合格数量(qualifiedQty)
     * 成功返回：{"code":200,"msg":"操作成功"}
     * 业务失败返回：{"code":500,"msg":"库位不存在或不属于当前入库仓库"}（示例）
     */
    @PreAuthorize("@ss.hasPermi('wms:inbound:complete')")
    @Log(title = "入库完成", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}/complete")
    public AjaxResult complete(@PathVariable("id") Long id)
    {
        return toAjax(wmsInboundOrderService.completeInboundOrder(id));
    }
}
