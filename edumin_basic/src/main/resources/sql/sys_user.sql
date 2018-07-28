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

 Date: 03/07/2018 17:51:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
-- 系统用户
CREATE TABLE `sys_user` (
  `user_id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `employeeid` VARCHAR(50) NOT NULL COMMENT '员工编号',
  `employeename` VARCHAR(50) NOT NULL COMMENT '员工姓名',
  `birthday` DATETIME COMMENT '生日',
  `password` VARCHAR(100) COMMENT '密码',
  `email` VARCHAR(100) COMMENT '邮箱',
  `mobile` VARCHAR(100) COMMENT '手机号',
  `status` TINYINT COMMENT '状态  0：禁用   1：正常',
  `loginstatus` TINYINT COMMENT '状态  0：离线   1：在线',
  `imgurl` VARCHAR(100) COMMENT '头像图片url',
  `create_time` DATETIME COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX (`username`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='系统用户';

SET FOREIGN_KEY_CHECKS = 1;
