/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 17/02/2022 14:30:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for device_data
-- ----------------------------
DROP TABLE IF EXISTS `device_data`;
CREATE TABLE `device_data`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品ID',
  `device_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备ID',
  `data_stream` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据流名称',
  `data_value` double(9, 2) NULL DEFAULT NULL COMMENT '数据',
  `date_time` datetime NULL DEFAULT NULL COMMENT '设备数据点产生时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `product_device_id`(`product_id`, `device_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1493417681280308238 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '设备数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for device_event
-- ----------------------------
DROP TABLE IF EXISTS `device_event`;
CREATE TABLE `device_event`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品ID',
  `device_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备ID',
  `date_time` datetime NULL DEFAULT NULL COMMENT '时间',
  `event` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生命周期事件',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '设备在线状态表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
