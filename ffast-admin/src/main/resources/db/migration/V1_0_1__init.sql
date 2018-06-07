/*
Navicat MySQL Data Transfer

Source Server         : 39.107.104.190
Source Server Version : 50720
Source Host           : 39.107.104.190:3306
Source Database       : ffast

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-05-22 16:47:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_sys_attach
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_attach`;
CREATE TABLE `t_sys_attach` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `path` varchar(200) NOT NULL COMMENT '路径',
  `name` varchar(50) NOT NULL COMMENT '附件名',
  `size` bigint(20) DEFAULT NULL COMMENT '文件大小',
  `extention` varchar(50) DEFAULT NULL COMMENT '文件后缀',
  `count` int(11) DEFAULT '0' COMMENT '引用计数',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` bigint(20) DEFAULT NULL COMMENT '最后修改人',
  `last_modify_time` varchar(20) DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统_附件';

-- ----------------------------
-- Records of t_sys_attach
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict`;
CREATE TABLE `t_sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典id',
  `dict_type_id` bigint(20) NOT NULL COMMENT '类型ID',
  `code` varchar(50) DEFAULT NULL COMMENT '编号',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父节点',
  `is_sys` tinyint(4) DEFAULT NULL COMMENT '是否系统内置【是1、否0】',
  `weight` int(11) DEFAULT NULL COMMENT '排序',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` bigint(20) DEFAULT NULL COMMENT '最后修改人',
  `last_modify_time` varchar(20) DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `AK_uk_dict` (`dict_type_id`,`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='字典';

-- ----------------------------
-- Records of t_sys_dict
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict_type`;
CREATE TABLE `t_sys_dict_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典类型id',
  `identity` varchar(50) NOT NULL COMMENT '字典标识符',
  `code` varchar(50) DEFAULT NULL COMMENT '编号',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `array` int(11) DEFAULT NULL COMMENT '排序',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父分类',
  `is_tree` tinyint(4) DEFAULT NULL COMMENT '是否为树',
  `is_sys` tinyint(4) DEFAULT NULL COMMENT '是否系统内置【是1、否0】',
  `weight` int(11) DEFAULT NULL,
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` bigint(20) DEFAULT NULL COMMENT '最后修改人',
  `last_modify_time` varchar(20) DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `AK_uk_dict` (`identity`,`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='基础字典类型';

-- ----------------------------
-- Records of t_sys_dict_type
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_log`;
CREATE TABLE `t_sys_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `operation` varchar(250) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户操作',
  `content` varchar(5000) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '日志内容',
  `creator_id` bigint(20) unsigned DEFAULT NULL COMMENT '用户ID',
  `create_time` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '创建时间',
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modify_time` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1679 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_res
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_res`;
CREATE TABLE `t_sys_res` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) NOT NULL COMMENT '标题',
  `identity` varchar(50) DEFAULT NULL COMMENT '资源标识符',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单url',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父资源',
  `parent_ids` varchar(200) DEFAULT NULL COMMENT '父路径',
  `weight` int(11) DEFAULT NULL COMMENT '菜单权重',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `status` tinyint(4) DEFAULT NULL COMMENT '资源类型（1=显示2禁止0=隐藏）',
  `res_type` tinyint(4) NOT NULL COMMENT '资源类型（1=菜单2=权限）',
  `note` varchar(255) DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` bigint(20) DEFAULT NULL COMMENT '最后修改人',
  `last_modify_time` varchar(20) DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_U` (`identity`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统_资源';

-- ----------------------------
-- Records of t_sys_res
-- ----------------------------
INSERT INTO `t_sys_res` VALUES ('1', '系统管理', 'sys', '', null, '', '0', 'gear-b', '1', '1', null, null, '', '1', '2017-11-14 09:42:00');
INSERT INTO `t_sys_res` VALUES ('2', '权限菜单', 'res', '/res', '1', '', '1', '', '1', '1', null, '1', '2017-09-14 15:05:56', null, '2017-09-14 15:05:56');
INSERT INTO `t_sys_res` VALUES ('3', '权限菜单:显示', 'res:list', '/api/sys/res/show', '2', '', null, '12', '1', '2', null, '1', '2017-09-14 15:05:56', '1', '2017-11-14 17:15:49');
INSERT INTO `t_sys_res` VALUES ('4', '权限菜单:添加', 'res:create', '/api/sys/res/create', '2', '', null, '', '1', '2', null, '1', '2017-09-14 15:05:57', '1', '2017-10-11 15:30:20');
INSERT INTO `t_sys_res` VALUES ('5', '权限菜单:更新', 'res:update', '/api/sys/res/update', '2', '', null, '', '1', '2', null, '1', '2017-09-14 15:05:57', '1', '2017-10-11 15:30:24');
INSERT INTO `t_sys_res` VALUES ('6', '权限菜单:删除', 'res:delete', '/api/sys/res/delete', '2', '', null, '', '1', '2', null, '1', '2017-09-14 15:05:57', '1', '2017-10-11 15:30:29');
INSERT INTO `t_sys_res` VALUES ('7', '基础字典', 'dict', '/dict', '1', '', '5', '', '1', '1', null, '1', '2017-09-14 15:06:45', '1', '2017-10-18 11:28:13');
INSERT INTO `t_sys_res` VALUES ('8', '基础字典:显示', 'dict:list', '/api/sys/dict/show', '7', '', null, '', '1', '2', null, '1', '2017-09-14 15:06:45', '1', '2017-10-11 15:30:33');
INSERT INTO `t_sys_res` VALUES ('9', '基础字典:添加', 'dict:create', '/api/sys/dict/create', '7', '', null, '', '1', '2', null, '1', '2017-09-14 15:06:45', '1', '2017-10-11 15:30:37');
INSERT INTO `t_sys_res` VALUES ('10', '基础字典:更新', 'dict:update', '/api/sys/dict/update', '7', '', null, '', '1', '2', null, '1', '2017-09-14 15:06:45', '1', '2017-10-11 15:30:41');
INSERT INTO `t_sys_res` VALUES ('11', '基础字典:删除', 'dict:delete', '/api/sys/dict/delete', '7', '', null, '', '1', '2', null, '1', '2017-09-14 15:06:45', '1', '2017-10-11 15:30:47');
INSERT INTO `t_sys_res` VALUES ('12', '角色管理', 'role', '/role', '1', '', '3', '', '1', '1', null, '1', '2017-09-14 15:07:04', null, '2017-09-14 15:07:04');
INSERT INTO `t_sys_res` VALUES ('13', '角色管理:显示', 'role:list', '/api/sys/role/show', '12', '', null, '', '1', '2', null, '1', '2017-09-14 15:07:04', '1', '2017-10-11 15:30:52');
INSERT INTO `t_sys_res` VALUES ('14', '角色管理:添加', 'role:create', '/api/sys/role/create', '12', '', null, '', '1', '2', null, '1', '2017-09-14 15:07:04', '1', '2017-10-11 15:30:57');
INSERT INTO `t_sys_res` VALUES ('15', '角色管理:更新', 'role:update', '/api/sys/role/update', '12', '', null, '', '1', '2', null, '1', '2017-09-14 15:07:04', '1', '2017-10-11 15:31:00');
INSERT INTO `t_sys_res` VALUES ('16', '角色管理:删除', 'role:delete', '/api/sys/role/delete', '12', '', null, '', '1', '2', null, '1', '2017-09-14 15:07:04', '1', '2017-10-11 15:31:05');
INSERT INTO `t_sys_res` VALUES ('17', '用户管理', 'user', '/user', '1', '', null, '', '1', '1', null, '1', '2017-09-14 15:07:24', null, '2017-09-14 15:07:24');
INSERT INTO `t_sys_res` VALUES ('18', '用户管理:显示', 'user:list', '/api/sys/user/show', '17', '', null, '', '1', '2', null, '1', '2017-09-14 15:07:24', '1', '2017-10-11 15:31:13');
INSERT INTO `t_sys_res` VALUES ('19', '用户管理:添加', 'user:create', '/api/sys/user/create', '17', '', null, '', '1', '2', null, '1', '2017-09-14 15:07:24', '1', '2017-10-11 15:31:19');
INSERT INTO `t_sys_res` VALUES ('20', '用户管理:更新', 'user:update', '/api/sys/user/update', '17', '', null, '', '1', '2', null, '1', '2017-09-14 15:07:25', '1', '2017-10-11 15:31:23');
INSERT INTO `t_sys_res` VALUES ('21', '用户管理:删除', 'user:delete', '/api/sys/user/delete', '17', '', null, '', '1', '2', null, '1', '2017-09-14 15:07:25', '1', '2017-10-11 15:31:29');
INSERT INTO `t_sys_res` VALUES ('22', '系统日志', 'sysLog', '/sysLog', '1', null, '6', null, '1', '1', null, '1', '2017-11-14 17:10:21', null, '2017-11-14 17:10:21');
INSERT INTO `t_sys_res` VALUES ('23', '系统日志:显示', 'sysLog:show', '/api/sysLog/show', '22', null, null, null, '1', '2', null, '1', '2017-11-14 17:10:21', null, '2017-11-14 17:10:21');
INSERT INTO `t_sys_res` VALUES ('24', '系统日志:添加', 'sysLog:create', '/api/sysLog/create', '22', null, null, null, '1', '2', null, '1', '2017-11-14 17:10:21', null, '2017-11-14 17:10:21');
INSERT INTO `t_sys_res` VALUES ('25', '系统日志:修改', 'sysLog:update', '/api/sysLog/update', '22', null, null, null, '1', '2', null, '1', '2017-11-14 17:10:21', null, '2017-11-14 17:10:21');
INSERT INTO `t_sys_res` VALUES ('26', '系统日志:删除', 'sysLog:delete', '/api/sysLog/delete', '22', null, null, null, '1', '2', null, '1', '2017-11-14 17:10:21', null, '2017-11-14 17:10:21');
INSERT INTO `t_sys_res` VALUES ('27', '接口测试', 'request', '/request', '1', null, '7', null, '1', '1', null, '1', '2017-11-16 09:00:37', null, '2017-11-16 09:00:37');
INSERT INTO `t_sys_res` VALUES ('28', '附件管理', 'attach', '/attach', '1', null, '8', null, '1', '1', null, '1', '2017-11-16 09:30:56', '1', '2017-11-21 17:28:05');
INSERT INTO `t_sys_res` VALUES ('231', '代码生成', 'generator', '/generator', '1', null, '8', '', '1', '1', null, null, null, null, null);

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `role` varchar(50) DEFAULT NULL COMMENT '角色标识',
  `description` varchar(200) NOT NULL COMMENT '角色描述',
  `status` tinyint(4) NOT NULL COMMENT '角色状态',
  `is_sys` tinyint(4) DEFAULT NULL COMMENT '是否内置',
  `main` varchar(20) DEFAULT NULL COMMENT '角色主页面',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` bigint(20) DEFAULT NULL COMMENT '最后修改人',
  `last_modify_time` varchar(20) DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `AK_U_Name` (`name`) USING BTREE,
  UNIQUE KEY `AK_U_Role` (`role`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统_角色';

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
INSERT INTO `t_sys_role` VALUES ('1', '超级管理员', null, '超级管理员', '1', '1', null, null, null, null, null);

-- ----------------------------
-- Table structure for t_sys_role_res
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_res`;
CREATE TABLE `t_sys_role_res` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `res_id` bigint(20) NOT NULL,
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` bigint(20) DEFAULT NULL COMMENT '最后修改人',
  `last_modify_time` varchar(20) DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `AK_U` (`role_id`,`res_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统_角色资源';

-- ----------------------------
-- Records of t_sys_role_res
-- ----------------------------
INSERT INTO `t_sys_role_res` VALUES ('29', '1', '1', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('30', '1', '2', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('31', '1', '3', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('32', '1', '4', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('33', '1', '5', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('34', '1', '6', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('35', '1', '7', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('36', '1', '8', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('37', '1', '9', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('38', '1', '10', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('39', '1', '11', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('40', '1', '12', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('41', '1', '13', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('42', '1', '14', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('43', '1', '15', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('44', '1', '16', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('45', '1', '17', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('46', '1', '18', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('47', '1', '19', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('48', '1', '20', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('49', '1', '21', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('50', '1', '22', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('51', '1', '23', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('52', '1', '24', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('53', '1', '25', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('54', '1', '26', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('55', '1', '27', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('56', '1', '28', null, null, null, null);
INSERT INTO `t_sys_role_res` VALUES ('57', '1', '231', null, null, null, null);

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '用户状态【1启用、0禁用】',
  `pwd` varchar(100) NOT NULL COMMENT '密码',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `tel` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `is_lock` tinyint(4) DEFAULT NULL COMMENT '是否锁定【1是、0否】',
  `lock_time` datetime DEFAULT NULL COMMENT '锁定时间',
  `login_count` bigint(20) DEFAULT NULL COMMENT '登录次数',
  `login_failure_count` bigint(20) DEFAULT NULL COMMENT '失败次数',
  `login_ip` varchar(100) DEFAULT NULL COMMENT '登录Ip',
  `login_time` varchar(50) DEFAULT NULL COMMENT '登录时间',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `salt` varchar(50) DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` bigint(20) DEFAULT NULL COMMENT '最后修改人',
  `last_modify_time` varchar(20) DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统_用户';

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
INSERT INTO `t_sys_user` VALUES ('1', 'admin', null, '1', '2dfdfc6637d34078246760ec9ad89d5053f3396b', null, null, null, null, '454', '24', null, '2018-05-22 15:03:47', null, 'dsfgfdgwertwe', null, null, null, null);

-- ----------------------------
-- Table structure for t_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE `t_sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` bigint(20) DEFAULT NULL COMMENT '最后修改人',
  `last_modify_time` varchar(20) DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `AK_U` (`role_id`,`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统_用户角色';

-- ----------------------------
-- Records of t_sys_user_role
-- ----------------------------
INSERT INTO `t_sys_user_role` VALUES ('1', '1', '1', null, null, null, null);
