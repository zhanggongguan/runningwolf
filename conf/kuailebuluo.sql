/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : localhost:3306
Source Database       : kuailebuluo

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2013-04-12 14:12:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `feed`
-- ----------------------------
DROP TABLE IF EXISTS `feed`;
CREATE TABLE `feed` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `author` varchar(255) DEFAULT NULL,
  `content` text NOT NULL,
  `hypelink` varchar(255) DEFAULT NULL,
  `refer` varchar(255) DEFAULT NULL,
  `md5` varchar(255) DEFAULT NULL,
  `like` varchar(10) DEFAULT NULL,
  `unlike` varchar(10) DEFAULT NULL,
  `collect` varchar(10) DEFAULT NULL,
  `comment` varchar(11) DEFAULT '0',
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `md5_uniq` (`md5`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=21857 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of feed
-- ----------------------------

-- ----------------------------
-- Table structure for `reguler`
-- ----------------------------
DROP TABLE IF EXISTS `reguler`;
CREATE TABLE `reguler` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `host` varchar(255) NOT NULL,
  `reguler` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `md5` varchar(255) NOT NULL,
  `type` tinyint(3) unsigned zerofill DEFAULT NULL COMMENT '0为翻页，1为内容',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_regular` (`host`,`md5`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reguler
-- ----------------------------
INSERT INTO `reguler` VALUES ('83', 'www.qiushibaike.com', '<a\\shref=\"(?<nextpage>[^\"]+?)\"\\stitle=\"第\\d+页\">', '2013-04-07 00:00:00', 'F139CC4AD02EA7CEED95D0D0CE8C4E0D', '000');
INSERT INTO `reguler` VALUES ('84', 'www.qiushibaike.com', '<div\\sclass=\"detail\">\\s*<a[^>]+?>(?<title>[^>]+?)</a>\\s*</div>\\s*(?:<div\\sclass=\"author\">\\s*<img[^>]+?>\\s*<a[^>]+?>(?<author>[^>]+?)</a></div>)?\\s*<div\\sclass=\"content\"\\stitle=\"(?<date>[^>]+?)\">\\s*(?<content>.+?)\\s*</div>\\s*<div[^>]+?class=\"bar\">\\s*<ul>\\s*<li[^>]+?class=\"up\">\\s*<a[^>]+?>(?<like>\\d*)</a>\\s*</li>\\s*<l[^>]+?class=\"down\">\\s*<a[^>]+?>-(?<unlike>\\d*)</a>\\s*</li>\\s*<li\\sclass=\"comment\">\\s*<a[^>]+?title=\"(?<comment>\\d*)[^>]+?\">', '2013-04-07 00:00:00', 'E1043D6058C8034AFEDF0147F9D13320', '001');

-- ----------------------------
-- Table structure for `seed`
-- ----------------------------
DROP TABLE IF EXISTS `seed`;
CREATE TABLE `seed` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL,
  `crawled_at` datetime DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  `times` int(11) unsigned zerofill DEFAULT '00000000000',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_url` (`url`)
) ENGINE=InnoDB AUTO_INCREMENT=6558 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seed
-- ----------------------------
INSERT INTO `seed` VALUES ('898', 'http://www.qiushibaike.com/', null, '2013-04-07', '00000000001');
INSERT INTO `seed` VALUES ('3462', 'http://www.qiushibaike.com/8hr/page/31?s=4551164', '2013-04-12 11:34:30', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3464', 'http://www.qiushibaike.com/8hr/page/34?s=4551164', '2013-04-12 11:34:30', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3467', 'http://www.qiushibaike.com/8hr/page/29?s=4551164', '2013-04-12 11:34:30', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3468', 'http://www.qiushibaike.com/8hr/page/30?s=4551164', '2013-04-12 11:34:30', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3469', 'http://www.qiushibaike.com/8hr/page/32?s=4551164', '2013-04-12 11:34:30', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3470', 'http://www.qiushibaike.com/8hr/page/33?s=4551164', '2013-04-12 11:34:30', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3486', 'http://www.qiushibaike.com/8hr/page/10?s=4551164', '2013-04-12 11:34:30', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3491', 'http://www.qiushibaike.com/8hr/page/11?s=4551164', '2013-04-12 11:34:30', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3493', 'http://www.qiushibaike.com/8hr/page/14?s=4551164', '2013-04-12 11:34:30', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3497', 'http://www.qiushibaike.com/8hr/page/12?s=4551164', '2013-04-12 11:34:30', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3498', 'http://www.qiushibaike.com/8hr/page/13?s=4551164', '2013-04-12 11:34:49', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3513', 'http://www.qiushibaike.com/8hr/page/2?s=4551164', '2013-04-12 11:34:49', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3518', 'http://www.qiushibaike.com/8hr/page/5?s=4551164', '2013-04-12 11:34:49', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3520', 'http://www.qiushibaike.com/8hr/page/8?s=4551164', '2013-04-12 11:34:49', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3521', 'http://www.qiushibaike.com/8hr/page/9?s=4551164', '2013-04-12 11:34:49', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3524', 'http://www.qiushibaike.com/8hr/page/3?s=4551164', '2013-04-12 11:34:49', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3525', 'http://www.qiushibaike.com/8hr/page/4?s=4551164', '2013-04-12 11:34:49', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3526', 'http://www.qiushibaike.com/8hr/page/6?s=4551164', '2013-04-12 11:34:49', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3527', 'http://www.qiushibaike.com/8hr/page/7?s=4551164', '2013-04-12 11:34:49', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3530', 'http://www.qiushibaike.com/8hr/page/15?s=4551164', '2013-04-12 11:34:49', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3542', 'http://www.qiushibaike.com/8hr/page/16?s=4551164', '2013-04-12 11:35:03', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3548', 'http://www.qiushibaike.com/8hr/page/17?s=4551164', '2013-04-12 11:35:03', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3549', 'http://www.qiushibaike.com/8hr/page/18?s=4551164', '2013-04-12 11:35:03', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3554', 'http://www.qiushibaike.com/8hr/page/19?s=4551164', '2013-04-12 11:35:03', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3560', 'http://www.qiushibaike.com/8hr/page/20?s=4551164', '2013-04-12 11:35:03', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3566', 'http://www.qiushibaike.com/8hr/page/21?s=4551164', '2013-04-12 11:35:03', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3572', 'http://www.qiushibaike.com/8hr/page/22?s=4551164', '2013-04-12 11:35:03', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3578', 'http://www.qiushibaike.com/8hr/page/23?s=4551164', '2013-04-12 11:35:03', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3580', 'http://www.qiushibaike.com/8hr/page/26?s=4551164', '2013-04-12 11:35:03', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3584', 'http://www.qiushibaike.com/8hr/page/24?s=4551164', '2013-04-12 11:35:03', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3585', 'http://www.qiushibaike.com/8hr/page/25?s=4551164', '2013-04-12 11:35:48', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3586', 'http://www.qiushibaike.com/8hr/page/27?s=4551164', '2013-04-12 11:35:48', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3587', 'http://www.qiushibaike.com/8hr/page/28?s=4551164', '2013-04-12 11:35:48', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('3588', 'http://www.qiushibaike.com/8hr/page/35/?s=4551164', '2013-04-12 11:35:48', '2013-04-08', '00000000001');
INSERT INTO `seed` VALUES ('6208', 'http://www.qiushibaike.com/8hr/page/34?s=4552458', '2013-04-12 11:54:28', '2013-04-12', '00000000001');
INSERT INTO `seed` VALUES ('6255', 'http://www.qiushibaike.com/8hr/page/2?s=4552457', '2013-04-12 11:54:28', '2013-04-12', '00000000001');
INSERT INTO `seed` VALUES ('6278', 'http://www.qiushibaike.com/8hr/page/8?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6284', 'http://www.qiushibaike.com/8hr/page/9?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6290', 'http://www.qiushibaike.com/8hr/page/10?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6302', 'http://www.qiushibaike.com/8hr/page/11?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6308', 'http://www.qiushibaike.com/8hr/page/12?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6309', 'http://www.qiushibaike.com/8hr/page/13?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6314', 'http://www.qiushibaike.com/8hr/page/14?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6316', 'http://www.qiushibaike.com/8hr/page/17?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6320', 'http://www.qiushibaike.com/8hr/page/15?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6321', 'http://www.qiushibaike.com/8hr/page/16?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6326', 'http://www.qiushibaike.com/8hr/page/18?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6332', 'http://www.qiushibaike.com/8hr/page/19?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6338', 'http://www.qiushibaike.com/8hr/page/20?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6350', 'http://www.qiushibaike.com/8hr/page/21?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6352', 'http://www.qiushibaike.com/8hr/page/24?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6356', 'http://www.qiushibaike.com/8hr/page/22?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6357', 'http://www.qiushibaike.com/8hr/page/23?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6358', 'http://www.qiushibaike.com/8hr/page/25?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6367', 'http://www.qiushibaike.com/8hr/page/2?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6372', 'http://www.qiushibaike.com/8hr/page/5?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6378', 'http://www.qiushibaike.com/8hr/page/3?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6379', 'http://www.qiushibaike.com/8hr/page/4?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6380', 'http://www.qiushibaike.com/8hr/page/6?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6381', 'http://www.qiushibaike.com/8hr/page/7?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6384', 'http://www.qiushibaike.com/8hr/page/8?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6390', 'http://www.qiushibaike.com/8hr/page/9?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6396', 'http://www.qiushibaike.com/8hr/page/10?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6402', 'http://www.qiushibaike.com/8hr/page/11?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6408', 'http://www.qiushibaike.com/8hr/page/12?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6414', 'http://www.qiushibaike.com/8hr/page/13?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6420', 'http://www.qiushibaike.com/8hr/page/14?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6432', 'http://www.qiushibaike.com/8hr/page/15?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6434', 'http://www.qiushibaike.com/8hr/page/18?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6438', 'http://www.qiushibaike.com/8hr/page/16?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6439', 'http://www.qiushibaike.com/8hr/page/17?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6440', 'http://www.qiushibaike.com/8hr/page/19?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6444', 'http://www.qiushibaike.com/8hr/page/20?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6450', 'http://www.qiushibaike.com/8hr/page/21?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6456', 'http://www.qiushibaike.com/8hr/page/22?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6462', 'http://www.qiushibaike.com/8hr/page/23?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6468', 'http://www.qiushibaike.com/8hr/page/24?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6474', 'http://www.qiushibaike.com/8hr/page/25?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6486', 'http://www.qiushibaike.com/8hr/page/26?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6492', 'http://www.qiushibaike.com/8hr/page/27?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6493', 'http://www.qiushibaike.com/8hr/page/28?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6498', 'http://www.qiushibaike.com/8hr/page/29?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6499', 'http://www.qiushibaike.com/8hr/page/30?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6504', 'http://www.qiushibaike.com/8hr/page/31?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6505', 'http://www.qiushibaike.com/8hr/page/32?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6506', 'http://www.qiushibaike.com/8hr/page/33?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6507', 'http://www.qiushibaike.com/8hr/page/35/?s=4552458', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6509', 'http://www.qiushibaike.com/8hr/page/26?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6515', 'http://www.qiushibaike.com/8hr/page/27?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6521', 'http://www.qiushibaike.com/8hr/page/28?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6532', 'http://www.qiushibaike.com/8hr/page/29?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6533', 'http://www.qiushibaike.com/8hr/page/30?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6539', 'http://www.qiushibaike.com/8hr/page/33?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6543', 'http://www.qiushibaike.com/8hr/page/31?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6544', 'http://www.qiushibaike.com/8hr/page/32?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6545', 'http://www.qiushibaike.com/8hr/page/34?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6550', 'http://www.qiushibaike.com/8hr/page/5?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6552', 'http://www.qiushibaike.com/8hr/page/1', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6553', 'http://www.qiushibaike.com/8hr/page/3?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6554', 'http://www.qiushibaike.com/8hr/page/4?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6555', 'http://www.qiushibaike.com/8hr/page/6?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6556', 'http://www.qiushibaike.com/8hr/page/7?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');
INSERT INTO `seed` VALUES ('6557', 'http://www.qiushibaike.com/8hr/page/35/?s=4552457', '2013-04-12 00:00:00', '2013-04-12', '00000000000');

-- ----------------------------
-- Table structure for `siteprop`
-- ----------------------------
DROP TABLE IF EXISTS `siteprop`;
CREATE TABLE `siteprop` (
  `host` varchar(100) DEFAULT NULL,
  `encoding` varchar(100) DEFAULT NULL,
  UNIQUE KEY `uniq_host` (`host`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of siteprop
-- ----------------------------
INSERT INTO `siteprop` VALUES ('www.qiushibaike.com', 'utf-8');
