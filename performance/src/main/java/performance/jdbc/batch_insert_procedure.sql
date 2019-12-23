/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : world

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 23/12/2019 19:29:22
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '姓名',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `item` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '学科',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 ;

SET FOREIGN_KEY_CHECKS = 1;

DROP PROCEDURE IF EXISTS batchInsertUser;
CREATE DEFINER = CURRENT_USER PROCEDURE `batchInsertUser`()
BEGIN
	declare i,age int;
	DECLARE name VARCHAR(30);
    set i=0;
    while i<500000 do
        set name=CONCAT('张三',i);
        set age=10;
        set item=CONCAT('科目',i);
        INSERT INTO `world`.`user`(`id`, `name`, `age`, `item`) VALUES (i, name, age, item);
        set i=i+1;
     end while;
END;
call batchInsertUser();