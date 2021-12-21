/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : xiaoyuanpt

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 21/12/2021 09:05:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`
(
    `id`          int(10)   NOT NULL AUTO_INCREMENT COMMENT '商品编号',
    `shop_id`     int(10)   NOT NULL COMMENT '商店编号',
    `product_id`  int(255)  NOT NULL COMMENT '商品编号',
    `quantity`    int(255)  NOT NULL COMMENT '数量',
    `selected`    int(255)  NOT NULL COMMENT '是否选中，0-未选中，1选中',
    `create_time` timestamp NULL DEFAULT NULL COMMENT '购物车创建时间',
    `user_id`     int(10)   NOT NULL COMMENT '用户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart`
VALUES (5, 1, 1, 1, 1, '2021-10-26 01:24:37', 4);
INSERT INTO `cart`
VALUES (6, 1, 2, 5, 0, '2021-10-29 01:28:00', 4);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`
(
    `comment_id`    int(10)                                                 NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id`       int(10)                                                 NULL DEFAULT NULL COMMENT '用户id',
    `shop_id`       int(10)                                                 NULL DEFAULT NULL COMMENT '被评论的店铺',
    `reply_user_id` int(10)                                                 NULL DEFAULT NULL COMMENT '被回复用户的id',
    `pid`           int(10)                                                 NULL DEFAULT NULL COMMENT '父id',
    `comment_msg`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回复内容',
    `create_time`   datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `user_name`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
    PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment`
VALUES (1, 6, 1, NULL, 0, '一级', '2021-11-24 00:52:29', NULL);
INSERT INTO `comment`
VALUES (2, 5, 1, NULL, 1, '二级回复1', '2021-11-23 00:52:32', NULL);
INSERT INTO `comment`
VALUES (3, 5, 1, NULL, 1, '二级回复1', '2021-11-23 00:54:02', NULL);
INSERT INTO `comment`
VALUES (4, 5, 1, NULL, 0, '一级一级', '2021-11-23 00:54:06', NULL);
INSERT INTO `comment`
VALUES (5, 5, 1, NULL, 4, '二级回复4', '2021-11-25 00:54:09', NULL);
INSERT INTO `comment`
VALUES (6, 5, 1, NULL, 5, '三级回复', '2021-11-25 23:16:27', NULL);

-- ----------------------------
-- Table structure for coupon_rule
-- ----------------------------
DROP TABLE IF EXISTS `coupon_rule`;
CREATE TABLE `coupon_rule`
(
    `id`          int(10)        NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `jizhun`      decimal(10, 2) NULL DEFAULT NULL COMMENT '满减（达到这个值才能减）（满减所用字段type=1）',
    `reduce`      decimal(10, 2) NULL DEFAULT NULL COMMENT '减少的额度（满减所用字段type=1）',
    `discount`    decimal(10, 2) NULL DEFAULT NULL COMMENT '打折',
    `template_id` int(10)        NOT NULL COMMENT '对应的模板',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coupon_rule
-- ----------------------------

-- ----------------------------
-- Table structure for coupon_template
-- ----------------------------
DROP TABLE IF EXISTS `coupon_template`;
CREATE TABLE `coupon_template`
(
    `id`           int(10)                                                 NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `shop_id`      int(255)                                                NULL DEFAULT NULL COMMENT '优惠券所属商家',
    `available`    int(255)                                                NOT NULL COMMENT '可用状态：0-不可用，1-可用',
    `expired`      int(11)                                                 NOT NULL COMMENT '是否过期：0-过期，1-可用',
    `name`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '优惠券名称',
    `logo`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '优惠券图片',
    `expired_time` timestamp                                               NOT NULL COMMENT '过期时间',
    `create_time`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建时间',
    `rule_id`      int(20)                                                 NOT NULL COMMENT '优惠券规则',
    `type`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '优惠券模板所属类型：0-满减，1-打折',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coupon_template
-- ----------------------------

-- ----------------------------
-- Table structure for evaluation
-- ----------------------------
DROP TABLE IF EXISTS `evaluation`;
CREATE TABLE `evaluation`
(
    `id`         int(10)  NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `evaluation` int(255) NULL DEFAULT NULL COMMENT '给商家进行评分',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of evaluation
-- ----------------------------

-- ----------------------------
-- Table structure for miaosha_product
-- ----------------------------
DROP TABLE IF EXISTS `miaosha_product`;
CREATE TABLE `miaosha_product`
(
    `id`            int(11)                                                 NOT NULL AUTO_INCREMENT,
    `product_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `product_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `status`        int(255)                                                NULL DEFAULT NULL,
    `price`         decimal(10, 2)                                          NULL DEFAULT NULL,
    `detail`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `shop_id`       int(11)                                                 NULL DEFAULT NULL,
    `stock`         int(255)                                                NULL DEFAULT NULL,
    `create_time`   datetime                                                NULL DEFAULT NULL,
    `start_time`    datetime                                                NULL DEFAULT NULL,
    `end_time`      datetime                                                NULL DEFAULT NULL,
    `miaosha_price` decimal(10, 2)                                          NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of miaosha_product
-- ----------------------------
INSERT INTO `miaosha_product`
VALUES (1, '栗子', '/temp-rainy/608940c2-577b-47e9-91f2-b5720a0c6126.jpg', 1, 55.00, 'eeeeeeeeeeeeeeee', 6, 5,
        '2021-11-16 23:20:48', '2021-11-14 23:21:40', '2021-11-22 23:21:48', 5.00);
INSERT INTO `miaosha_product`
VALUES (2, '苹果', '/temp-rainy/608940c2-577b-47e9-91f2-b5720a0c6126.jpg', 1, 56.00, 'eeeeffffffff', 6, 5,
        '2021-11-16 23:22:30', '2021-11-20 14:22:43', '2021-11-24 23:22:47', 5.00);

-- ----------------------------
-- Table structure for miaoshaorder
-- ----------------------------
DROP TABLE IF EXISTS `miaoshaorder`;
CREATE TABLE `miaoshaorder`
(
    `id`            int(11)                                                 NOT NULL AUTO_INCREMENT,
    `product_id`    int(11)                                                 NULL DEFAULT NULL,
    `miaoshaprice`  decimal(10, 2)                                          NULL DEFAULT NULL,
    `create_time`   datetime                                                NULL DEFAULT NULL,
    `status`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `phone`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `address`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `receiver_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `user_id`       int(11)                                                 NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of miaoshaorder
-- ----------------------------
INSERT INTO `miaoshaorder`
VALUES (1, 2, 5.00, '2021-11-20 00:15:14', '0', NULL, NULL, NULL, NULL);
INSERT INTO `miaoshaorder`
VALUES (2, 2, 5.00, '2021-11-20 00:15:27', '0', NULL, NULL, NULL, NULL);
INSERT INTO `miaoshaorder`
VALUES (3, 2, 5.00, '2021-11-21 22:25:26', '0', NULL, NULL, NULL, NULL);
INSERT INTO `miaoshaorder`
VALUES (9, 2, 5.00, '2021-11-21 22:48:36', '0', NULL, NULL, NULL, 4);
INSERT INTO `miaoshaorder`
VALUES (10, 1, 5.00, '2021-11-21 23:58:33', '0', NULL, NULL, NULL, 4);

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`
(
    `id`          int(10)                                                 NOT NULL AUTO_INCREMENT,
    `user_id`     int(11)                                                 NULL DEFAULT NULL,
    `content`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_time` datetime                                                NULL DEFAULT NULL,
    `order_no`    int(10)                                                 NULL DEFAULT NULL COMMENT '无',
    `sqr_id`      int(11)                                                 NULL DEFAULT NULL COMMENT '申请人的id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice`
VALUES (1, 0, '商铺入驻申请已发出,24小时内会审批完成,请等待', '2021-10-27 13:52:45', 0, NULL);
INSERT INTO `notice`
VALUES (2, 5, 'zhengyi发起商铺入驻,请审核', '2021-10-27 13:52:45', 0, NULL);
INSERT INTO `notice`
VALUES (3, 0, '商铺入驻申请已发出,24小时内会审批完成,请等待', '2021-10-27 13:58:47', 0, NULL);
INSERT INTO `notice`
VALUES (4, 5, 'zhengyi发起商铺入驻,请审核', '2021-10-27 13:58:47', 0, NULL);
INSERT INTO `notice`
VALUES (5, 6, '商铺入驻申请已发出,24小时内会审批完成,请等待', '2021-10-27 14:01:04', 0, NULL);
INSERT INTO `notice`
VALUES (6, 5, 'zhengyi发起商铺入驻,请审核', '2021-10-27 14:01:04', 0, NULL);
INSERT INTO `notice`
VALUES (7, 6, '商铺入驻申请已发出,24小时内会审批完成,请等待', '2021-10-27 14:19:36', 0, NULL);
INSERT INTO `notice`
VALUES (8, 5, 'zhengyi发起商铺入驻,请审核', '2021-10-27 14:19:36', 0, NULL);
INSERT INTO `notice`
VALUES (9, 6, '商铺入驻申请已发出,24小时内会审批完成,请等待', '2021-10-27 14:21:01', 0, 0);
INSERT INTO `notice`
VALUES (10, 5, 'zhengyi发起商铺入驻,请审核', '2021-10-27 14:21:01', 0, 6);
INSERT INTO `notice`
VALUES (11, 6, '商铺入驻申请已发出,24小时内会审批完成,请等待', '2021-10-28 08:40:48', 0, 0);
INSERT INTO `notice`
VALUES (12, 5, 'zhengyi发起商铺入驻,请审核', '2021-10-28 08:40:48', 0, 6);

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`
(
    `id`               int(10)                                                 NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id`          int(10)                                                 NULL DEFAULT NULL COMMENT '用户id',
    `total_price`      decimal(10, 2)                                          NULL DEFAULT NULL COMMENT '订单总价',
    `receiver_name`    varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '买家名字',
    `receiver_mobile`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '买家手机号码',
    `receiver_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收获地址',
    `order_status`     int(255)                                                NULL DEFAULT NULL COMMENT '订单状态：0用户取消，10未付款，20已付款，30已发货，40交易完成',
    `postage`          decimal(7, 2)                                           NULL DEFAULT NULL COMMENT '运费',
    `delivery_time`    timestamp                                               NULL DEFAULT NULL COMMENT '发货时间',
    `pay_time`         timestamp                                               NULL DEFAULT NULL COMMENT '支付时间',
    `end_time`         timestamp                                               NULL DEFAULT NULL COMMENT '交易完成时间',
    `create_time`      timestamp                                               NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`      timestamp                                               NULL DEFAULT NULL COMMENT '更新订单状态的时间',
    `order_no`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 16
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order`
VALUES (4, 23, 35.50, NULL, '2255533', '集美', 15, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order`
VALUES (5, 23, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '5555');
INSERT INTO `order`
VALUES (6, 25, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '5555');
INSERT INTO `order`
VALUES (7, 25, 35.50, NULL, '2255533', '集美', 15, NULL, NULL, NULL, NULL, NULL, NULL, '5555');
INSERT INTO `order`
VALUES (8, 26, 35.50, NULL, '2255533', '集美', 15, NULL, NULL, NULL, NULL, '2021-11-11 19:28:47', '2021-11-11 19:28:47',
        '225');
INSERT INTO `order`
VALUES (9, 4, 99.90, NULL, '18159515555', '厦门市集美区万达广场(集美店)', 10, NULL, NULL, NULL, NULL, '2021-11-11 19:38:17',
        '2021-11-11 19:38:17', 'jingxi21-11-11 下午7:38');
INSERT INTO `order`
VALUES (10, 4, 33.00, NULL, '18912341234', '集美大学', 10, NULL, NULL, NULL, NULL, '2021-11-11 19:47:13',
        '2021-11-11 19:47:13', 'jingxi21-11-11 下午7:47');
INSERT INTO `order`
VALUES (11, 4, 66.00, NULL, '18912341244', '集美大学', 10, NULL, NULL, NULL, NULL, '2021-11-11 19:50:49',
        '2021-11-11 19:50:49', 'jingxi21-11-11 下午7:50');
INSERT INTO `order`
VALUES (12, 4, 199.80, NULL, '18159515511', '浙江省金华市金华市', 10, NULL, NULL, NULL, NULL, '2021-11-12 00:35:52',
        '2021-11-12 00:35:52', 'jingxi21-11-12 上午12:35');
INSERT INTO `order`
VALUES (13, 4, 99.90, NULL, '18159514406', '厦门市集美区厦门市集美区政府', 10, NULL, NULL, NULL, NULL, '2021-11-12 00:36:48',
        '2021-11-12 00:36:48', 'jingxi21-11-12 上午12:36');
INSERT INTO `order`
VALUES (14, 4, 199.80, NULL, '1234567844', '集美大学', 10, NULL, NULL, NULL, NULL, '2021-12-21 08:35:28',
        '2021-12-21 08:35:28', 'jingxi21-12-21 上午8:35');
INSERT INTO `order`
VALUES (15, 4, 199.80, NULL, '1234567844', '集美大学', 10, NULL, NULL, NULL, NULL, '2021-12-21 08:43:17',
        '2021-12-21 08:43:17', 'jingxi21-12-21 上午8:43');

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`
(
    `id`            int(20)                                                 NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `product_id`    int(20)                                                 NULL DEFAULT NULL COMMENT '商品id',
    `product_price` decimal(10, 2)                                          NULL DEFAULT NULL COMMENT '商品价格',
    `shop_id`       int(11)                                                 NULL DEFAULT NULL COMMENT '商店编号',
    `order_no`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `count`         int(11)                                                 NULL DEFAULT NULL COMMENT '数量',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail`
VALUES (1, 5, 25.50, 2, '2555', 5);
INSERT INTO `order_detail`
VALUES (2, 7, 99.90, 6, 'jingxi21-11-11 下午7:38', 1);
INSERT INTO `order_detail`
VALUES (3, 6, 33.00, 6, 'jingxi21-11-11 下午7:47', 1);
INSERT INTO `order_detail`
VALUES (4, 6, 33.00, 6, 'jingxi21-11-11 下午7:50', 2);
INSERT INTO `order_detail`
VALUES (5, 7, 99.90, 6, 'jingxi21-11-12 上午12:35', 2);
INSERT INTO `order_detail`
VALUES (6, 7, 99.90, 6, 'jingxi21-11-12 上午12:36', 1);
INSERT INTO `order_detail`
VALUES (7, 7, 99.90, 6, 'jingxi21-12-21 上午8:35', 2);
INSERT INTO `order_detail`
VALUES (8, 7, 99.90, 6, 'jingxi21-12-21 上午8:43', 2);

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`
(
    `id`            int(11)                                                 NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `product_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
    `product_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品图片',
    `status`        int(255)                                                NULL DEFAULT NULL COMMENT '状态：0-下架，1-上架',
    `price`         decimal(10, 2)                                          NULL DEFAULT NULL COMMENT '价格',
    `detail`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品介绍',
    `shop_id`       int(11)                                                 NULL DEFAULT NULL COMMENT '商品所属商店',
    `stock`         int(255)                                                NULL DEFAULT NULL COMMENT '库存',
    `update_time`   datetime                                                NULL DEFAULT NULL COMMENT '更新时间，创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product`
VALUES (1, '桃子', '/temp-rainy/608940c2-577b-47e9-91f2-b5720a0c6126.jpg', 1, 25.00, '22', 6, 4, NULL);
INSERT INTO `product`
VALUES (2, '苹果', '/temp-rainy/a5d84717-1d37-40a6-80fb-83cf54a1db0e.jpg', 1, 25.00, 'ttt', 6, 4, NULL);
INSERT INTO `product`
VALUES (4, '布偶', '/temp-rainy/085754bf-8350-459b-9fdc-6de397cd6a4a.jpg', 555, 333.00, 'sssssssssss', 6, 5, NULL);
INSERT INTO `product`
VALUES (5, '火龙果', '/temp-rainy/be733b11-91b5-4389-9ac2-4b27c52cdcc3.jpg', 555, 55.00, '555', 6, 5, NULL);
INSERT INTO `product`
VALUES (6, '哈密瓜', '/temp-rainy/188d75f7-48fb-4ffb-a66e-740703cbb0f6.jpg', 0, 33.00, '33', 6, 3, NULL);
INSERT INTO `product`
VALUES (7, '连衣裙', '/temp-rainy/3e9fb8d3-9972-4c02-87a1-62b8ab4b0aa9.jpg', 0, 99.90, '秋冬原创文艺女装连衣裙', 6, 26, NULL);
INSERT INTO `product`
VALUES (8, '旗袍', '/temp-rainy/a152c08f-ab53-4835-a78d-f840684dd220.jpg', 0, 88.88, '素木时尚收腰衬衫新款2021年欧洲站春装', 6, 30, NULL);

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop`
(
    `id`          int(20)                                                 NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `shop_name`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商家名称',
    `shop_addr`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商家地址',
    `shop_detail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商店的简介',
    `create_date` timestamp                                               NOT NULL COMMENT '创建时间',
    `status`      int(255)                                                NULL DEFAULT NULL COMMENT '审批：0-未审批，1-审批通过，2-注销',
    `business`    int(255)                                                NULL DEFAULT NULL COMMENT '营业：0-未营业，1-营业中',
    `month_sales` decimal(10, 2)                                          NULL DEFAULT NULL COMMENT '月销售额',
    `shop_type`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商家类型',
    `shop_phone`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商家手机号码',
    `evalute`     decimal(11, 2)                                          NULL DEFAULT NULL COMMENT '商店评分',
    `shop_img`    varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商店图片（暂时可先不做）',
    `user_id`     int(10)                                                 NULL DEFAULT NULL COMMENT '商店所对应的管理员',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 12
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shop
-- ----------------------------
INSERT INTO `shop`
VALUES (6, '良品铺子', '福建泉州丰泽区', '良品铺子是良品铺子股份有限公司旗下的品牌 ，成立于2006年 ，是一个集休闲食品研发、加工分装、零售服务的。', '2021-10-27 14:01:04', 1, 0,
        NULL, '零食', '18159515504', NULL, NULL, 6);
INSERT INTO `shop`
VALUES (7, '乐购', '集美大学', '乐购商城是成立于2021年销售电器手机的商店 。', '2021-10-27 14:19:36', 0, 0, NULL, '电器', '13529833411', NULL, NULL,
        3);
INSERT INTO `shop`
VALUES (9, '乐购55', '华侨大学', '乐购商城是成立于2021年销售电器手机的商店 。', '2021-10-28 08:40:48', 0, 0, NULL, '电器', '15648792235', NULL,
        NULL, 3);
INSERT INTO `shop`
VALUES (10, '唯品会', '上海华东区', '唯品商城是成立于2021年销售电器手机的商店 。', '2021-11-27 18:54:13', 0, 0, NULL, '商铺', '12548333355', NULL,
        NULL, 3);
INSERT INTO `shop`
VALUES (11, '优购', '泉州丰泽区', '优购商铺是主营与电子计算机产品', '2021-11-26 18:56:02', 0, 0, NULL, '电脑', '25533354121', NULL, NULL, 5);

-- ----------------------------
-- Table structure for shop_flow
-- ----------------------------
DROP TABLE IF EXISTS `shop_flow`;
CREATE TABLE `shop_flow`
(
    `id`       int(10)                                                 NOT NULL AUTO_INCREMENT,
    `user_id`  int(11)                                                 NOT NULL COMMENT '可以用',
    `result`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结果',
    `reason`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原因',
    `order_no` int(11)                                                 NULL DEFAULT NULL COMMENT '1-通知申请人信息，2-通知平台的信息',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shop_flow
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`         int(10)                                                 NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `username`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
    `password`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
    `status`     int(255)                                                NULL DEFAULT NULL COMMENT '是否开通会员：0-未开通，1-已开通',
    `hb_status`  int(255)                                                NULL DEFAULT NULL COMMENT '会员红包是否发放：0-没发，1-已经发了',
    `salt`       int(255)                                                NULL DEFAULT NULL COMMENT '盐',
    `creat_time` timestamp                                               NULL DEFAULT NULL COMMENT '创建时间',
    `auth`       int(255)                                                NULL DEFAULT NULL COMMENT '1-普通用户，2-商家（商家管理员），3-平台管理员',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user`
VALUES (4, '张三', '8116d4a73306436948401c06d81d7580', 0, 0, 1902, '2021-10-24 13:52:50', 0);
INSERT INTO `user`
VALUES (5, 'zheng', 'f66bb5b4da81c0bfee46f97386c4066b', 0, 0, 1473, '2021-10-24 23:06:19', 2);
INSERT INTO `user`
VALUES (6, 'zhengyi', 'f66bb5b4da81c0bfee46f97386c4066b', 0, 0, 1473, '2021-10-25 12:44:37', 1);
INSERT INTO `user`
VALUES (7, 'zsk', '03291a53baaa5776db8d99632037aa79', 0, 0, 1518, '2021-10-27 18:49:27', 1);
INSERT INTO `user`
VALUES (8, 'zsk555', 'ff0bf0bfbf2c4857a681940e631c6c5a', 0, 0, 1918, '2021-10-27 18:51:01', 1);
INSERT INTO `user`
VALUES (9, 'sss', 'a07a357699b6374d4ff23f31c012f497', 0, 0, 1065, '2021-10-27 18:51:24', NULL);

-- ----------------------------
-- Table structure for wechat
-- ----------------------------
DROP TABLE IF EXISTS `wechat`;
CREATE TABLE `wechat`
(
    `id`       int(11)                                                 NOT NULL AUTO_INCREMENT,
    `fromName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送方名称',
    `toName`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接收方名称',
    `message`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息',
    `dateTime` datetime                                                NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 20
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wechat
-- ----------------------------
INSERT INTO `wechat`
VALUES (3, 'zsk', 'zhengyi', '11222', '2021-11-07 02:09:21');
INSERT INTO `wechat`
VALUES (4, 'zsk', 'zhengyi', '我是zsk，你呢', '2021-11-07 02:10:11');
INSERT INTO `wechat`
VALUES (5, 'zhengyi', 'zsk', '我是zhengyi', '2021-11-07 02:10:23');
INSERT INTO `wechat`
VALUES (6, 'zsk', 'zhengyi', 'ffff', '2021-11-07 16:43:40');
INSERT INTO `wechat`
VALUES (7, 'zhengyi', 'zsk', 'ffffghh', '2021-11-07 16:43:45');
INSERT INTO `wechat`
VALUES (8, 'zhengyi', 'zsk', '我是zheng', '2021-11-07 16:46:01');
INSERT INTO `wechat`
VALUES (9, 'zhengyi', NULL, '在吗', '2021-11-07 19:41:20');
INSERT INTO `wechat`
VALUES (10, 'zhengyi', 'zsk', '在吗', '2021-11-07 19:41:37');
INSERT INTO `wechat`
VALUES (11, 'zsk', 'zhengyi', '我在', '2021-11-07 19:41:42');
INSERT INTO `wechat`
VALUES (12, 'zhengyi', 'zsk', '我是郑艺\n', '2021-11-08 23:58:21');
INSERT INTO `wechat`
VALUES (13, 'zhengyi', 'zsk', '你在吗', '2021-11-09 00:00:46');
INSERT INTO `wechat`
VALUES (14, 'zsk', 'zhengyi', '我在', '2021-11-09 00:00:57');
INSERT INTO `wechat`
VALUES (15, 'zsk', 'zhengyi', '有什么事情吗', '2021-11-09 00:01:04');
INSERT INTO `wechat`
VALUES (16, 'zhengyi', 'zsk', 'ddd', '2021-11-17 18:35:07');
INSERT INTO `wechat`
VALUES (17, 'zhengyi', 'zsk', 'eeee', '2021-11-19 21:37:28');
INSERT INTO `wechat`
VALUES (18, 'zsk', 'zhengyi', 'yyyyy', '2021-11-19 21:37:36');
INSERT INTO `wechat`
VALUES (19, 'zsk', 'zhengyi', '在吗', '2021-11-19 21:37:48');
INSERT INTO `wechat`
VALUES (20, 'zhengyi', 'zsk', '在', '2021-11-19 21:37:55');

SET FOREIGN_KEY_CHECKS = 1;
