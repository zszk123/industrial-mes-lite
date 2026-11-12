-- -------------------------------------------------------------
-- 1. 仓库信息表 (wms_warehouse)
-- -------------------------------------------------------------
DROP TABLE IF EXISTS `wms_warehouse`;
CREATE TABLE `wms_warehouse` (
`id` bigint NOT NULL AUTO_INCREMENT COMMENT '仓库主键ID',
`warehouse_code` varchar(64) NOT NULL COMMENT '仓库编码(如 WH01)',
`warehouse_name` varchar(128) NOT NULL COMMENT '仓库名称(如 原料一号仓)',
`dept_id` bigint NOT NULL COMMENT '归属管理部门ID(关联 sys_dept，用于数据权限)',
`status` char(1) NOT NULL DEFAULT '0' COMMENT '仓库状态(0-正常, 1-停用)',
`remark` varchar(500) DEFAULT NULL COMMENT '备注/地址',
`create_by` varchar(64) DEFAULT '' COMMENT '创建者',
`create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_by` varchar(64) DEFAULT '' COMMENT '更新者',
`update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (`id`),
UNIQUE KEY `uk_warehouse_code` (`warehouse_code`),
KEY `idx_dept_id` (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='仓库信息表';

-- -------------------------------------------------------------
-- 2. 货架/库位信息表 (wms_location)
-- -------------------------------------------------------------
DROP TABLE IF EXISTS `wms_location`;
CREATE TABLE `wms_location` (
 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '库位主键ID',
 `warehouse_id` bigint NOT NULL COMMENT '所属仓库ID',
 `location_code` varchar(64) NOT NULL COMMENT '库位编码(唯一，如 WH01-A-01-01)',
 `zone_code` varchar(32) DEFAULT 'DEFAULT' COMMENT '所属库区(如 A区-电子原料区)',
 `status` char(1) NOT NULL DEFAULT '0' COMMENT '库位状态(0-正常空闲, 1-占用, 2-禁用停用)',
 `remark` varchar(500) DEFAULT NULL COMMENT '备注',
 `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
 `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
 `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 PRIMARY KEY (`id`),
 UNIQUE KEY `uk_location_code` (`location_code`),
 KEY `idx_warehouse_id` (`warehouse_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库位信息表';

-- -------------------------------------------------------------
-- 3. 物料基础信息表 (wms_material) -- 纯静态属性
-- -------------------------------------------------------------
DROP TABLE IF EXISTS `wms_material`;
CREATE TABLE `wms_material` (
 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '物料主键ID',
 `material_code` varchar(64) NOT NULL COMMENT '物料编码(唯一标识)',
 `material_name` varchar(128) NOT NULL COMMENT '物料名称',
 `spec` varchar(128) DEFAULT NULL COMMENT '规格型号',
 `unit` varchar(16) NOT NULL DEFAULT '个' COMMENT '计量单位(个/箱/kg)',
 `remark` varchar(500) DEFAULT NULL COMMENT '备注',
 `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
 `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
 `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 PRIMARY KEY (`id`),
 UNIQUE KEY `uk_material_code` (`material_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料基础信息表';

-- -------------------------------------------------------------
-- 4. 实时库存表 (wms_inventory)
-- 关联: 哪个仓库(warehouse_id) + 哪个库位(location_id) + 哪个物料(material_id)
-- -------------------------------------------------------------
DROP TABLE IF EXISTS `wms_inventory`;
CREATE TABLE `wms_inventory` (
 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '库存主键ID',
 `warehouse_id` bigint NOT NULL COMMENT '所属仓库ID',
 `location_id` bigint NOT NULL COMMENT '所属库位ID',
 `material_id` bigint NOT NULL COMMENT '物料ID',
 `available_qty` int NOT NULL DEFAULT '0' COMMENT '可用库存量(可被领料分配)',
 `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
 `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
 `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 PRIMARY KEY (`id`),
-- 同一个库位上的同一种物料只允许有一条实时库存记录
  UNIQUE KEY `uk_loc_mat` (`location_id`, `material_id`),
  KEY `idx_warehouse_mat` (`warehouse_id`, `material_id`),
  KEY `idx_material_id` (`material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='仓库实时库存表';


-- -------------------------------------------------------------
-- 5. 领料出库单主表 (wms_outbound_order)
-- -------------------------------------------------------------
DROP TABLE IF EXISTS `wms_outbound_order`;
CREATE TABLE `wms_outbound_order` (
`id` bigint NOT NULL AUTO_INCREMENT COMMENT '出库单主键ID',
`order_no` varchar(64) NOT NULL COMMENT '出库单号(唯一业务单号，防重幂等)',
`order_type` tinyint NOT NULL DEFAULT '1' COMMENT '出库类型(1-车间领料, 2-盘亏出库)',
`warehouse_id` bigint NOT NULL COMMENT '出库仓库ID',
`apply_dept_id` bigint NOT NULL COMMENT '申请领料部门ID(如 装配一班 dept_id)',
`status` tinyint NOT NULL DEFAULT '0' COMMENT '单据状态(0-待审核, 1-已审核待出库, 2-已完成出库, 3-已取消)',
`remark` varchar(500) DEFAULT NULL COMMENT '领料原因',
`create_by` varchar(64) DEFAULT '' COMMENT '申请人',
`create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
`update_by` varchar(64) DEFAULT '' COMMENT '审核/出库人',
`update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (`id`),
UNIQUE KEY `uk_order_no` (`order_no`),
KEY `idx_wh_status` (`warehouse_id`, `status`),
KEY `idx_apply_dept` (`apply_dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='领料出库单主表';

-- -------------------------------------------------------------
-- 6. 领料出库单明细表 (wms_outbound_detail)
-- -------------------------------------------------------------
DROP TABLE IF EXISTS `wms_outbound_detail`;
CREATE TABLE `wms_outbound_detail` (
`id` bigint NOT NULL AUTO_INCREMENT COMMENT '明细主键ID',
`order_id` bigint NOT NULL COMMENT '关联主单ID',
`order_no` varchar(64) NOT NULL COMMENT '出库单号',
`material_id` bigint NOT NULL COMMENT '物料ID',
`plan_qty` int NOT NULL COMMENT '计划领料数量',
`actual_qty` int NOT NULL DEFAULT '0' COMMENT '实际已出库数量',
`create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (`id`),
KEY `idx_order_id` (`order_id`),
KEY `idx_material_id` (`material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='领料出库单明细表';

-- -------------------------------------------------------------
-- 7. 库存变动审计流水表 (wms_inventory_record) -- SSOT
-- -------------------------------------------------------------
DROP TABLE IF EXISTS `wms_inventory_record`;
CREATE TABLE `wms_inventory_record` (
`id` bigint NOT NULL AUTO_INCREMENT COMMENT '流水主键ID',
`record_no` varchar(64) NOT NULL COMMENT '流水记录编号',
`order_no` varchar(64) NOT NULL COMMENT '关联业务单号',
`record_type` varchar(32) NOT NULL COMMENT '流水类型(INBOUND, OUTBOUND)',
`warehouse_id` bigint NOT NULL COMMENT '仓库ID',
`location_id` bigint NOT NULL COMMENT '库位ID',
`material_id` bigint NOT NULL COMMENT '物料ID',
`change_qty` int NOT NULL COMMENT '变动数量',
`before_qty` int NOT NULL COMMENT '变动前可用库存',
`after_qty` int NOT NULL COMMENT '变动后可用库存',
`create_by` varchar(64) DEFAULT '' COMMENT '操作人',
`create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
PRIMARY KEY (`id`),
UNIQUE KEY `uk_record_no` (`record_no`),
KEY `idx_order_no` (`order_no`),
KEY `idx_wh_mat` (`warehouse_id`, `material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存变动审计流水表';

-- -------------------------------------------------------------
-- 8. 入库单主表 (wms_inbound_order)
-- -------------------------------------------------------------
DROP TABLE IF EXISTS `wms_inbound_order`;
CREATE TABLE `wms_inbound_order` (
`id` bigint NOT NULL AUTO_INCREMENT COMMENT '入库单主键ID',
`order_no` varchar(64) NOT NULL COMMENT '入库单号(唯一业务单号，防重幂等)',
`order_type` tinyint NOT NULL DEFAULT '1' COMMENT '入库类型(1-采购入库, 2-生产退料入库, 3-盘盈入库)',
`warehouse_id` bigint NOT NULL COMMENT '目标入库仓库ID',
`status` tinyint NOT NULL DEFAULT '0' COMMENT '单据状态(0-待验收, 1-已验收待上架, 2-已完成入库, 3-已取消)',
`remark` varchar(500) DEFAULT NULL COMMENT '入库备注/供应商信息',
`create_by` varchar(64) DEFAULT '' COMMENT '制单人',
`create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '制单时间',
`update_by` varchar(64) DEFAULT '' COMMENT '验收/上架人',
`update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (`id`),
UNIQUE KEY `uk_order_no` (`order_no`),
KEY `idx_wh_status` (`warehouse_id`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='入库单主表';

-- -------------------------------------------------------------
-- 9. 入库单明细表 (wms_inbound_detail) -- 包含合格品与残损质检记录
-- -------------------------------------------------------------
DROP TABLE IF EXISTS `wms_inbound_detail`;
CREATE TABLE `wms_inbound_detail` (
`id` bigint NOT NULL AUTO_INCREMENT COMMENT '明细主键ID',
`order_id` bigint NOT NULL COMMENT '关联入库主单ID',
`order_no` varchar(64) NOT NULL COMMENT '入库单号',
`material_id` bigint NOT NULL COMMENT '物料ID',
`location_id` bigint DEFAULT NULL COMMENT '上架目标库位ID(验收上架时分配)',
`plan_qty` int NOT NULL COMMENT '计划送货数量',
`qualified_qty` int NOT NULL DEFAULT '0' COMMENT '验收合格数量(实际增加到可用库存的数量)',
`defective_qty` int NOT NULL DEFAULT '0' COMMENT '残损/不合格数量',
`defect_reason` varchar(255) DEFAULT NULL COMMENT '残损原因记录',
`create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (`id`),
KEY `idx_order_id` (`order_id`),
KEY `idx_material_id` (`material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='入库单明细表';


-- 1. 插入一个仓库（归属于 dept_id = 101 部门管理）
INSERT INTO `wms_warehouse` (`id`, `warehouse_code`, `warehouse_name`, `dept_id`, `status`, `remark`, `create_by`)
VALUES (1, 'WH-MAIN-01', '电子物料主仓', 101, '0', '厂区A栋一楼西侧', 'admin');

-- 2. 插入两个货架库位
INSERT INTO `wms_location` (`id`, `warehouse_id`, `location_code`, `zone_code`, `status`, `remark`, `create_by`)
VALUES
    (1, 1, 'WH01-A-01-01', 'A区-芯片货架', '0', '第一排第一层', 'admin'),
    (2, 1, 'WH01-B-01-02', 'B区-阻容辅料', '0', '第二排第二层', 'admin');

-- 3. 插入物料基础信息
INSERT INTO `wms_material` (`id`, `material_code`, `material_name`, `spec`, `unit`, `remark`, `create_by`)
VALUES
    (1, 'MAT-MCU-001', '微控制器芯片 STM32F407', 'LQFP-144', '颗', '核心MCU', 'admin'),
    (2, 'MAT-RES-002', '贴片精密电阻 10K', '0805 1%', '包', '通用阻容', 'admin');

-- 4. 插入实时库存（特定库位上的物料）
INSERT INTO `wms_inventory` (`id`, `warehouse_id`, `location_id`, `material_id`, `available_qty`, `locked_qty`, `version`, `create_by`)
VALUES
    (1, 1, 1, 1, 500, 0, 0, 'admin'),   -- 库位1上有 500 颗芯片
    (2, 1, 2, 2, 2000, 0, 0, 'admin');  -- 库位2上有 2000 包电阻