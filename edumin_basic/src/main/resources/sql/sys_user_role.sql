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

 Date: 03/07/2018 17:50:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for s_file_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
-- 用户与角色对应关系
CREATE TABLE `sys_user_role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT COMMENT '用户ID',
  `role_id` BIGINT COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

SET FOREIGN_KEY_CHECKS = 1;
