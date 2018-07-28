/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50617
 Source Host           : localhost:3306
 Source Schema         : edumin-basic

 Target Server Type    : MySQL
 Target Server Version : 50617
 File Encoding         : 65001

 Date: 03/07/2018 17:51:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
-- 菜单
CREATE TABLE `sys_menu` (
  `menu_id` BIGINT NOT NULL AUTO_INCREMENT,
  `parent_id` BIGINT COMMENT '父菜单ID，一级菜单为0',
  `name` VARCHAR(50) COMMENT '菜单名称',
  `url` VARCHAR(200) COMMENT '菜单URL',
  `perms` VARCHAR(500) COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` INT COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` VARCHAR(50) COMMENT '菜单图标',
  `order_num` INT COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='菜单管理';

SET FOREIGN_KEY_CHECKS = 1;
-- 初始化菜单数据
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('1', '0', '系统管理', NULL, NULL, '0', 'fa fa-cog', '0');
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('2', '0', '文件管理', NULL, NULL, '0', 'fa fa-cog', '0');