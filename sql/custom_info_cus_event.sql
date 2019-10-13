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
-- Table structure for table `cus_event`
--

DROP TABLE IF EXISTS `cus_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cus_event` (
  `event_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `event_content` varchar(500) DEFAULT NULL COMMENT '内容',
  `event_apply` varchar(500) DEFAULT NULL COMMENT '反馈',
  `event_next` varchar(500) DEFAULT NULL COMMENT '下次回访建议',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `event_time` date DEFAULT NULL COMMENT '回访的时间',
  `info_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `info_id` (`info_id`),
  CONSTRAINT `cus_event_ibfk_1` FOREIGN KEY (`info_id`) REFERENCES `cus_info` (`info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cus_event`
--

LOCK TABLES `cus_event` WRITE;
/*!40000 ALTER TABLE `cus_event` DISABLE KEYS */;
INSERT INTO `cus_event` VALUES (1,'1111','1111','1111','2019-08-06 04:23:34','2019-08-06',1),(2,'2222','2222','2222','2019-08-06 04:34:56','2019-08-06',1),(3,'1111','1111','1111','2019-08-06 09:34:56','2019-08-06',2),(4,'22222','22222','22222','1970-01-01 11:06:44','2900-09-07',2),(5,'12','121','121','1970-01-01 00:07:25','2000-09-08',1),(6,'23','23','23','1970-01-01 00:07:25','2000-09-08',2),(8,'45','54','32','1970-01-01 00:07:25','2000-09-08',1),(9,'78','54','32','1970-01-01 00:07:25','2000-09-08',1),(10,'353534','1231','12313','1970-01-01 00:07:25','2000-09-08',2),(11,'åè®¿åå®¹','åé¦èè£','åè®¿å»ºè®®','1970-01-01 00:07:25','2000-09-08',10001),(12,'åè®¿åå®¹','åé¦èè£22','åè®¿å»ºè®®2','1970-01-01 00:07:25','2000-09-08',10001),(13,'哈哈哈','哈哈哈','哈哈哈','1970-01-01 00:07:25','2000-09-08',1),(14,'2312','24124','234234','1970-01-01 00:07:25','2000-09-08',1),(15,'回访内容1','圣诞节疯狂撒','都是疯狂拉升 ','1970-01-01 00:07:25','2000-09-08',3),(16,'发顺丰 ','法发送到','撒发生','1970-01-01 00:07:25','2000-09-08',10000),(18,'打发','法 ',' 安防','1970-01-01 00:07:25','2000-09-08',10015);
/*!40000 ALTER TABLE `cus_event` ENABLE KEYS */;
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
