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

 Date: 03/07/2018 17:51:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
-- 角色
CREATE TABLE `sys_role` (
  `role_id` BIGINT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(100) COMMENT '角色名称',
  `remark` VARCHAR(100) COMMENT '备注',
  `create_time` DATETIME COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='角色';

SET FOREIGN_KEY_CHECKS = 1;
