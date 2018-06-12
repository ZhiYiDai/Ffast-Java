-- ----------------------------
-- Table structure for t_work_backlog
-- ----------------------------
DROP TABLE IF EXISTS `t_work_backlog`;
CREATE TABLE `t_work_backlog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `content` varchar(2000) DEFAULT NULL COMMENT '内容',
  `pictures` varchar(100) DEFAULT NULL COMMENT '图片',
  `start_time` varchar(20) DEFAULT NULL COMMENT '待办开始时间',
  `finish_time` varchar(20) DEFAULT NULL COMMENT '待办完成时间',
  `from_module` tinyint(4) DEFAULT NULL COMMENT '来源模块',
  `from_id` bigint(20) DEFAULT NULL COMMENT '来源id',
  `priority` tinyint(4) DEFAULT '0' COMMENT '优先级（0=一般1=重要）',
  `user_ids` varchar(100) DEFAULT NULL COMMENT '待办用户',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态(0=未完成1=已完成)',
  `inform_days` int(11) DEFAULT NULL COMMENT '提前多少天提醒',
  `inform_enable` tinyint(4) DEFAULT '1' COMMENT '开启提醒',
  `inform_type` varchar(20) DEFAULT NULL COMMENT '通知渠道',
  `inform_status` tinyint(4) DEFAULT NULL COMMENT '通知状态（0=未通知1=已通知2=已提前通知）',
  `note` varchar(50) DEFAULT NULL COMMENT '备注',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` bigint(20) DEFAULT NULL COMMENT '最后修改人',
  `last_modify_time` varchar(20) DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='待办事项';
