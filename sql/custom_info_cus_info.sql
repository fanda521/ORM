CREATE DATABASE  IF NOT EXISTS `custom_info` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `custom_info`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: custom_info
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cus_info`
--

DROP TABLE IF EXISTS `cus_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cus_info` (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `info_name` varchar(16) DEFAULT '',
  `info_degree` varchar(50) DEFAULT NULL COMMENT '学历',
  `info_sex` varchar(2) DEFAULT '男',
  `info_career` varchar(100) DEFAULT '' COMMENT '职业',
  `info_qq` varchar(15) DEFAULT '',
  `info_wechat` varchar(50) DEFAULT '',
  `info_phone` varchar(15) DEFAULT NULL,
  `info_source` varchar(50) DEFAULT '',
  `info_city` varchar(20) DEFAULT '',
  `info_purpose` varchar(150) DEFAULT '' COMMENT '目的',
  `info_iscus` int(1) DEFAULT '0' COMMENT '是否是客户0表示不是客户',
  `info_service` int(1) DEFAULT '1' COMMENT '是否需要继续跟踪0表示不需要1表示需要继续服务',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `info_time` date DEFAULT NULL COMMENT '信息录入时间',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10016 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cus_info`
--

LOCK TABLES `cus_info` WRITE;
/*!40000 ALTER TABLE `cus_info` DISABLE KEYS */;
INSERT INTO `cus_info` VALUES (1,'wang','本科','男','java工程师','qq123','wecaht123','123456','抖音','深圳','就业',1,1,'1970-01-01 06:12:24','1950-09-08'),(2,'feng ','本科','男','java程序员','qqfeng','wechatfeng','12345678','百度','深圳','就业',1,1,'1970-01-01 06:12:24','2000-09-09'),(3,'大傻','高中','男','幼师','大傻一号','大傻一号','大傻一号','百度','武汉','了解',0,1,'1970-01-01 06:12:24','1950-09-08'),(10000,'陈安','大专','女','教师','qq1234','微信333','333333333','小红书','南昌','兼职',1,1,'1970-01-01 00:03:20','3919-09-07'),(10001,'二哈','本科','1','保安','dfsa','fdsa','sdfa','快手','江西','',1,1,'1970-01-01 06:12:24','1950-09-08'),(10002,'金毛','小学','1','电工','1212','f14142','3242423','知乎','重庆','',1,1,'1970-01-01 06:12:24','1950-09-08'),(10005,'小熊','本科','女','教师','1056015243','wechat12324','8249283','微博','海南','入职',1,1,'1970-01-01 06:12:24','1950-09-08'),(10009,'','','','','11','11','11','','','',1,1,'1970-01-01 06:12:24','1950-09-08'),(10010,'小红','高中','女','幼师','123456789','微信123','1008611','百度','武汉','咨询',1,1,'1970-01-01 06:12:24','1950-09-08'),(10012,'王吉慧','本科','男','java工程师','1056015243','时代峰峻奥斯卡','2124214','知乎','深圳','了解呀',1,1,'1970-01-01 06:12:24','1950-09-08'),(10013,'阿三','博士','男','药理师','qq11','微信11','1213243','微博','海南','',1,1,'1970-01-01 06:12:24','1950-09-08'),(10014,'张三','本科','男','教师','qq121212','wechat12342','phone12313','知乎','广东','',0,1,'1970-01-01 06:12:24','1950-09-08'),(10015,'冯佳豪','博士','男','主播','fjh22','fjh22','2222','番茄社区','深圳','直推',1,1,'1970-01-01 06:12:24','1950-09-08');
/*!40000 ALTER TABLE `cus_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-13  9:31:44
