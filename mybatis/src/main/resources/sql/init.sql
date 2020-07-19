CREATE DATABASE `shard01`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '用户表';

CREATE TABLE `health_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `level_name` varchar(50) DEFAULT NULL COMMENT '等级名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '健康等级表';

CREATE TABLE `health_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `level_id` int(11) NOT NULL COMMENT '等级id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '健康记录表';

CREATE TABLE `health_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `record_id` int(11) NOT NULL COMMENT '记录id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `task_name` varchar(50) DEFAULT NULL COMMENT '任务名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '任务表';