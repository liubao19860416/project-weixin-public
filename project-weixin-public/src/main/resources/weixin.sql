/*
Navicat MySQL Data Transfer

Source Server         : mysql-ali
Source Server Version : 50518
Source Host           : rds1k8d75422ra4z0vk3.mysql.rds.aliyuncs.com:3306
Source Database       : r44aj8852759ajup

Target Server Type    : MYSQL
Target Server Version : 50518
File Encoding         : 65001

Date: 2015-07-20 11:39:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_access_token
-- ----------------------------
DROP TABLE IF EXISTS `t_access_token`;
CREATE TABLE `t_access_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(255) NOT NULL,
  `expiresIn` bigint(255) NOT NULL DEFAULT '0',
  `usersAppId` int(11) NOT NULL,
  `createdDatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_0` (`token`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_access_token
-- ----------------------------

-- ----------------------------
-- Table structure for t_users_app
-- ----------------------------
DROP TABLE IF EXISTS `t_users_app`;
CREATE TABLE `t_users_app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appId` varchar(255) NOT NULL,
  `appSercret` varchar(255) NOT NULL,
  `appToken` varchar(255) NOT NULL,
  `appName` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `createdDatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_users_app
-- ----------------------------
INSERT INTO `t_users_app` VALUES ('1', 'wx1772dcd877ef5da5', '3d68306fe922125be82f189eb77058b2', 'wasabili', '测试帐号', null, '2015-07-20 11:38:08');
INSERT INTO `t_users_app` VALUES ('2', 'wx118da1735da60886', 'a297ef67d5506a61e6542b3c1a3f20c7', 'wasabili', '曲阳艺人', null, '2015-07-20 11:38:08');

-- ----------------------------
-- Table structure for t_users_ubscribe
-- ----------------------------
DROP TABLE IF EXISTS `t_users_ubscribe`;
CREATE TABLE `t_users_ubscribe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wxId` int(11) DEFAULT NULL COMMENT '微信Id',
  `openId` varchar(100) DEFAULT NULL COMMENT 'openId',
  `souce` varchar(255) DEFAULT NULL COMMENT '来源',
  `params` varchar(1000) DEFAULT NULL COMMENT '其他参数',
  `status` int(11) DEFAULT NULL,
  `insertTime` timestamp NULL DEFAULT NULL COMMENT '添加时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1601060 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_users_ubscribe
-- ----------------------------

-- ----------------------------
-- Table structure for t_wxuserinfo
-- ----------------------------
DROP TABLE IF EXISTS `t_wxuserinfo`;
CREATE TABLE `t_wxuserinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `subscribe` int(11) DEFAULT NULL COMMENT '用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。',
  `openid` varchar(60) DEFAULT NULL,
  `nickname` varchar(40) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL COMMENT '用户的性别，值为1时是男性，值为2时是女性，值为0时是未知',
  `city` varchar(30) DEFAULT NULL COMMENT '用户所在城市',
  `province` varchar(30) DEFAULT NULL COMMENT '省份',
  `country` varchar(30) DEFAULT NULL COMMENT '国家',
  `groupId` int(11) DEFAULT NULL COMMENT '组',
  `language` varchar(20) DEFAULT NULL COMMENT '语言',
  `headimgurl` varchar(300) DEFAULT NULL COMMENT '头像Url',
  `subscribeTime` double DEFAULT NULL COMMENT '关注时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `wxId` int(11) DEFAULT NULL COMMENT '微信Id',
  `uuid` varchar(40) DEFAULT NULL COMMENT '用户唯一标示',
  `stats` int(11) DEFAULT NULL COMMENT '状态',
  `updateTime` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `insertTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`),
  KEY `t_wx_user_uuid` (`uuid`) USING BTREE,
  KEY `t_wx_user_openid` (`openid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7686 DEFAULT CHARSET=utf8 COMMENT='订阅用户';

-- ----------------------------
-- Records of t_wxuserinfo
-- ----------------------------
