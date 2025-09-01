/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : pmp_ai_analysis

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2025-08-17 21:13:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ct_dicom`
-- ----------------------------
DROP TABLE IF EXISTS `ct_dicom`;
CREATE TABLE `ct_dicom` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sop_instance_uid` varchar(255) DEFAULT NULL COMMENT '全球唯一标识符',
  `patient_id` varchar(100) DEFAULT NULL COMMENT '患者ID',
  `patient_name` varchar(255) DEFAULT NULL COMMENT '患者姓名',
  `accession_number` varchar(255) DEFAULT NULL COMMENT '医院唯一标识号（一组CT）',
  `study_id` varchar(255) DEFAULT NULL COMMENT '研究编号',
  `series_number` varchar(255) DEFAULT NULL COMMENT '系列编号',
  `instance_number` int(5) DEFAULT NULL COMMENT '实例编号（当前dicom文件在整组内的序号）',
  `series_date` date DEFAULT NULL COMMENT 'CT扫描日期',
  `series_time` varchar(255) DEFAULT NULL COMMENT 'CT扫描时间',
  `study_description` varchar(255) DEFAULT NULL COMMENT '研究描述',
  `modality` varchar(255) DEFAULT NULL COMMENT '成像模态（CT、MR、DX）',
  `series_description` varchar(255) DEFAULT NULL COMMENT '系列描述',
  `rows` varchar(255) DEFAULT NULL COMMENT '行数',
  `columns` varchar(255) DEFAULT NULL COMMENT '列数',
  `dicom_path` varchar(255) DEFAULT NULL COMMENT 'dicom文件地址',
  `png_path` varchar(255) DEFAULT NULL COMMENT '图片文件地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ct_dicom
-- ----------------------------

-- ----------------------------
-- Table structure for `ct_label_data`
-- ----------------------------
DROP TABLE IF EXISTS `ct_label_data`;
CREATE TABLE `ct_label_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `computer_name` varchar(255) DEFAULT NULL,
  `patient_id` varchar(100) DEFAULT NULL,
  `label_data` mediumtext,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ct_label_data
-- ----------------------------

-- ----------------------------
-- Table structure for `ct_report`
-- ----------------------------
DROP TABLE IF EXISTS `ct_report`;
CREATE TABLE `ct_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `patient_id` varchar(100) DEFAULT NULL COMMENT '患者ID',
  `patient_name` varchar(255) DEFAULT NULL COMMENT '患者姓名',
  `accession_number` varchar(255) DEFAULT NULL COMMENT '医院唯一标识号（一组CT）',
  `is_positive` int(2) DEFAULT NULL COMMENT '是否阳性',
  `positive_rate` double DEFAULT NULL COMMENT '阳性概率',
  `disease_type` int(1) DEFAULT NULL COMMENT '病理分类',
  `disease_level` int(1) DEFAULT NULL COMMENT '病理分级',
  `mesenteric_contracture` int(1) DEFAULT NULL COMMENT '肠系膜挛缩',
  `pci_0_central` int(1) DEFAULT NULL COMMENT 'PCI评分部位',
  `pci_1_right_upper` int(1) DEFAULT NULL COMMENT 'PCI评分部位',
  `pci_2_epigastrium` int(1) DEFAULT NULL COMMENT 'PCI评分部位',
  `pci_3_left_upper` int(1) DEFAULT NULL COMMENT 'PCI评分部位',
  `pci_4_left_flank` int(1) DEFAULT NULL COMMENT 'PCI评分部位',
  `pci_5_left_lower` int(1) DEFAULT NULL COMMENT 'PCI评分部位',
  `pci_6_pelvis` int(1) DEFAULT NULL COMMENT 'PCI评分部位',
  `pci_7_right_lower` int(1) DEFAULT NULL COMMENT 'PCI评分部位',
  `pci_8_right_flank` int(1) DEFAULT NULL COMMENT 'PCI评分部位',
  `pci_9_upper_jejunum` int(1) DEFAULT NULL COMMENT 'PCI评分部位',
  `pci_10_lower_jejunum` int(1) DEFAULT NULL COMMENT 'PCI评分部位',
  `pci_11_upper_ileum` int(1) DEFAULT NULL COMMENT 'PCI评分部位',
  `pci_12_lower_ileum` int(1) DEFAULT NULL COMMENT 'PCI评分部位',
  `pci_score` int(2) DEFAULT NULL COMMENT 'PCI总分',
  `conclusion` text COMMENT '结论',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_accession_number` (`accession_number`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ct_report
-- ----------------------------
INSERT INTO `ct_report` VALUES ('1', '20241225004460', 'ZHANG WAN XIN', '20241225004460', '1', '0.6', '0', '2', '1', '1', '2', '3', '1', '2', '1', '3', '3', '3', '2', '2', '2', '2', '27', '检测结果为：阳性（阳性概率为60%）。病理分类为果冻型，腹部和盆腔病变以大量的果冻样黏液为主。病理分级为高级别黏液腺癌(G2,中分化)：细胞占比>20%，细胞高度异型/侵袭性浸润呈锯齿状/促纤维结缔组织增生中可见不规则的腺管结构。存在肠系膜挛缩现象。可考虑的治疗策略：全身化疗、细胞减灭术(CRS)、腹腔热灌注治疗(HIPEC)。', '2025-07-29 16:29:06');

-- ----------------------------
-- Table structure for `patient`
-- ----------------------------
DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `patient_id` varchar(100) DEFAULT NULL COMMENT '患者ID',
  `patient_name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `sex` varchar(10) DEFAULT NULL COMMENT '性别 M-男，F-女',
  `birth_day` date DEFAULT NULL COMMENT '出生日期',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `patient_index` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of patient
-- ----------------------------
INSERT INTO `patient` VALUES ('3', '20250606-145601', 'Anonymous', 'F', '1948-01-05', '2025-07-20 17:25:07');
INSERT INTO `patient` VALUES ('4', '20250630-190924', 'Anonymous', 'F', '1960-01-04', '2025-07-22 14:08:04');
INSERT INTO `patient` VALUES ('5', '20250630-191227', 'Anonymous', 'F', '1959-10-26', '2025-07-23 17:44:25');
INSERT INTO `patient` VALUES ('6', '0003511811-0005', 'ZHANG WAN XIN', 'F', '1953-12-06', '2025-07-26 21:35:44');
INSERT INTO `patient` VALUES ('7', '0003454196-0005', 'YAN QIAN LIAN', 'M', '1957-12-27', '2025-07-27 11:50:37');
INSERT INTO `patient` VALUES ('8', '0003516308-0001', 'WU YUN HUA', 'M', '1954-06-06', '2025-07-27 20:53:11');
INSERT INTO `patient` VALUES ('9', '0003541731-0009', 'REN YUE PING', 'M', '1974-04-30', '2025-07-27 20:53:29');
INSERT INTO `patient` VALUES ('10', '0000737073-0096', 'WANG SHU HUA', 'M', '1978-10-19', '2025-07-27 20:53:45');
INSERT INTO `patient` VALUES ('11', '20250606-145753', 'Anonymous', null, '1964-08-26', '2025-08-06 19:04:14');

-- ----------------------------
-- Table structure for `sys_users`
-- ----------------------------
DROP TABLE IF EXISTS `sys_users`;
CREATE TABLE `sys_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '用户密码',
  `email` varchar(100) DEFAULT NULL COMMENT '用户邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '用户手机号',
  `role` varchar(50) NOT NULL COMMENT '角色名称',
  `full_name` varchar(100) DEFAULT NULL COMMENT '用户全名',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '用户状态(1:启用,0:禁用)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  KEY `idx_username` (`username`),
  KEY `idx_email` (`email`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_users
-- ----------------------------
INSERT INTO `sys_users` VALUES ('1', 'admin', '123456', 'admin@163.com', '', 'admin', '主任医师', '1', '2025-08-03 20:05:05', '2025-08-03 20:05:08');
INSERT INTO `sys_users` VALUES ('2', 'user', '123456', 'user@163.com', null, 'user', '实习医生', '1', '2025-08-08 14:42:02', '2025-08-08 14:42:05');
INSERT INTO `sys_users` VALUES ('5', 'guest', '123456', 'guest@163.com', null, 'guest', '访客', '1', '2025-08-08 16:29:04', '2025-08-08 16:29:07');
