/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80013
Source Host           : localhost:3306
Source Database       : chattingroom

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2019-07-31 18:48:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `friend`
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `list_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `friend_id` varchar(32) DEFAULT NULL,
  `friend_remark` varchar(32) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of friend
-- ----------------------------
INSERT INTO `friend` VALUES ('3', 'FDE2CEE7C2604A5299722523E64CA8AD', '1', 'C11678D5D6634B62A59AB12E792EE597', null, null);
INSERT INTO `friend` VALUES ('4', 'FDE2CEE7C2604A5299722523E64CA8AD', '0', '7A088509B77D43688970E147243D1C71', null, null);

-- ----------------------------
-- Table structure for `friend_list`
-- ----------------------------
DROP TABLE IF EXISTS `friend_list`;
CREATE TABLE `friend_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) DEFAULT NULL,
  `list_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `list_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of friend_list
-- ----------------------------
INSERT INTO `friend_list` VALUES ('3', 'C56CDA7AB8A146D9ABB353BEF2A0A8DF', '0', '我的好友');
INSERT INTO `friend_list` VALUES ('4', 'FDE2CEE7C2604A5299722523E64CA8AD', '0', '我的好友');
INSERT INTO `friend_list` VALUES ('5', 'FDE2CEE7C2604A5299722523E64CA8AD', '1', '亲友团');
INSERT INTO `friend_list` VALUES ('6', '7A088509B77D43688970E147243D1C71', '0', '我的好友');

-- ----------------------------
-- Table structure for `friend_message_record`
-- ----------------------------
DROP TABLE IF EXISTS `friend_message_record`;
CREATE TABLE `friend_message_record` (
  `msg_id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_id` varchar(32) DEFAULT NULL,
  `receiver_id` varchar(32) DEFAULT NULL,
  `send_time` datetime DEFAULT NULL,
  `message` text,
  PRIMARY KEY (`msg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of friend_message_record
-- ----------------------------

-- ----------------------------
-- Table structure for `group`
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` varchar(32) DEFAULT NULL,
  `group_name` varchar(32) DEFAULT NULL,
  `group_description` text,
  `group_head_url` text,
  `create_time` datetime DEFAULT NULL,
  `creater_user_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of group
-- ----------------------------

-- ----------------------------
-- Table structure for `group_message_record`
-- ----------------------------
DROP TABLE IF EXISTS `group_message_record`;
CREATE TABLE `group_message_record` (
  `msg_id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_id` varchar(32) DEFAULT NULL,
  `send_time` datetime DEFAULT NULL,
  `message` text,
  `group_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`msg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of group_message_record
-- ----------------------------

-- ----------------------------
-- Table structure for `group_user`
-- ----------------------------
DROP TABLE IF EXISTS `group_user`;
CREATE TABLE `group_user` (
  `id` int(11) unsigned zerofill NOT NULL,
  `group_id` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `user_type_in_group` int(2) DEFAULT NULL,
  `join_time` datetime DEFAULT NULL,
  `user_nickname` varchar(32) DEFAULT NULL,
  `speak_amount` int(11) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of group_user
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` varchar(32) CHARACTER SET utf32 COLLATE utf32_german2_ci NOT NULL,
  `account_number` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `nickname` varchar(10) DEFAULT NULL,
  `password` varchar(16) DEFAULT NULL,
  `telephone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `sex` varchar(2) DEFAULT NULL,
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `head_url` text,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('7A088509B77D43688970E147243D1C71', 'test2018', 'test2018', 'test2018', '15812345678', '2', 'test2018', '2019-07-17', '2019-07-25 16:30:18', null, '2019-07-25 16:33:08', null);
INSERT INTO `user` VALUES ('C11678D5D6634B62A59AB12E792EE597', 'admin', 'admin', 'admin', '15812345678', '2', 'admin', '2019-07-03', '2019-07-17 14:54:37', null, '2019-07-25 16:32:37', null);
INSERT INTO `user` VALUES ('FDE2CEE7C2604A5299722523E64CA8AD', 'mainguy', 'mainguy', '123456', '5456456464', '2', 'test2018', '2019-07-03', '2019-07-17 14:59:13', '2019-07-29 15:25:06', '2019-07-31 17:06:50', '\\head_icon_img\\mainguy.jpg');
