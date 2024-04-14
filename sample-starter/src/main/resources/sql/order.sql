#todo 你的建表语句,包含索引

/*方案
1.设置了4张表：订单表信息、收件人信息、支付信息表。
2.mysql通过主从复制实现读写分离，sharding jdbc支持数据库的读写分离
3.sql查询数据使用覆盖索引加子查询的方式
4.订单表进行水平拆分:水平分库和分表，sharding jdbc行表达式分片策略哈希取模，分库键shop_id，分表键user_id
*/

#sql语句：

DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '订单号',
  `user_id` varchar(32) NOT NULL COMMENT '购买人',
  `shop_id` varchar(32) DEFAULT NULL,
  `sku_id` varchar(32) NOT NULL COMMENT 'SkuId',
  `amount` int(11) DEFAULT NULL COMMENT '购买数量',
  `money` decimal(18,4) DEFAULT NULL COMMENT '购买金额',
  `pay_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '购买时间',
  `pay_status` char(2) DEFAULT '0' COMMENT '支付状态',
  `del_flag` tinyint(2) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET FOREIGN_KEY_CHECKS=1;

DROP TABLE IF EXISTS `t_order_receiver`;
CREATE TABLE `t_order_receiver` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` varchar(32) DEFAULT NULL,
  `user_name` varchar(30) DEFAULT NULL,
  `receiver_name` varchar(30) DEFAULT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(32) DEFAULT NULL COMMENT '收货人电话',
  `receiver_post_code` varchar(32) DEFAULT NULL COMMENT '收货人邮编',
  `receiver_province` varchar(32) DEFAULT NULL COMMENT '省份/直辖市',
  `receiver_city` varchar(32) DEFAULT NULL COMMENT '城市',
  `receiver_region` varchar(32) DEFAULT NULL COMMENT '区',
  `receiver_detail_address` varchar(50) DEFAULT NULL COMMENT '详细地址',
  `is_default` tinyint(4) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(2) DEFAULT NULL COMMENT '删除状态【0->未删除；1->已删除】',
  PRIMARY KEY (`id`),
  KEY `idx_uid_status` (`user_id`,`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='收件人信息'
SET FOREIGN_KEY_CHECKS=1;


DROP TABLE IF EXISTS `t_payment_info`;
CREATE TABLE `t_payment_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` varchar(32) DEFAULT NULL COMMENT '订单id',
  `pay_trade_no` varchar(50) DEFAULT NULL COMMENT '交易流水号',
  `total_amount` decimal(18,4) DEFAULT NULL COMMENT '支付总金额',
  `payment_status` varchar(20) DEFAULT NULL COMMENT '支付状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `confirm_time` datetime DEFAULT NULL COMMENT '确认时间',
  `callback_content` varchar(4000) DEFAULT NULL COMMENT '回调内容',
  `callback_time` datetime DEFAULT NULL COMMENT '回调时间',
  PRIMARY KEY (`id`),
  KEY `idx_oid_status` (`order_id`,`payment_status`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='支付信息'
SET FOREIGN_KEY_CHECKS=1;


