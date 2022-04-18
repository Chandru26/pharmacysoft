CREATE DATABASE  IF NOT EXISTS `pharmacysoft` /*!40100 DEFAULT CHARACTER SET utf8 */;
grant all privileges on *.* to 'userpharma'@'localhost' identified by 'gk1261LG';
flush privileges;
USE `pharmacysoft`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: pharmacysoft
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
-- Table structure for table `account_transactions`
--

DROP TABLE IF EXISTS `account_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_transactions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accounts_id` int(11) NOT NULL,
  `payments_id` int(11) DEFAULT '0',
  `expense` decimal(10,2) DEFAULT '0.00',
  `payment` decimal(10,2) DEFAULT '0.00',
  `balance` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_transactions`
--

LOCK TABLES `account_transactions` WRITE;
/*!40000 ALTER TABLE `account_transactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `account_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accounts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(20) DEFAULT NULL,
  `firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `company` varchar(45) DEFAULT NULL,
  `description` varchar(250) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `address_id` int(11) DEFAULT NULL,
  `created_by` int(11) DEFAULT '1',
  `updated_by` int(11) DEFAULT '1',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_accounts_addressid__address_id` (`address_id`),
  KEY `fk_accounts_createdby__app_user_id` (`created_by`),
  KEY `fk_accounts_updatedby__app_user_id` (`updated_by`),
  CONSTRAINT `fk_accounts_addressid__address_id` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `fk_accounts_createdby__app_user_id` FOREIGN KEY (`created_by`) REFERENCES `app_user` (`id`),
  CONSTRAINT `fk_accounts_updatedby__app_user_id` FOREIGN KEY (`updated_by`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,'Mr.','Swamy','Kempa',NULL,'Test','88888','test@test.com',1,1,1,'2017-03-16 10:18:30','2017-03-16 10:18:30');
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address_type` varchar(10) DEFAULT NULL,
  `address1` varchar(60) DEFAULT NULL,
  `address2` varchar(60) DEFAULT NULL,
  `city` varchar(60) DEFAULT NULL,
  `province` varchar(60) DEFAULT NULL,
  `zipcode` varchar(20) DEFAULT NULL,
  `country` varchar(60) DEFAULT NULL,
  `created_by` int(11) DEFAULT '1',
  `updated_by` int(11) DEFAULT '1',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_address_createdby__app_user_id` (`created_by`),
  KEY `fk_address_updatedby__app_user_id` (`updated_by`),
  CONSTRAINT `fk_address_createdby__app_user_id` FOREIGN KEY (`created_by`) REFERENCES `app_user` (`id`),
  CONSTRAINT `fk_address_updatedby__app_user_id` FOREIGN KEY (`updated_by`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'billing','221, 100feet ring road bsk 3rd stage','Banashankari','Bangalore','Karnataka','560085','India',1,1,'2017-01-14 07:42:52','2017-01-14 07:42:52'),(2,'shipping','2nd cross, 3rd main','Vijayanagar','Bangalore','Karnataka','560085','India',1,1,'2017-01-14 07:42:52','2017-01-14 07:42:52'),(3,'shipping','2nd cross, 3rd main','Girinagar','Bangalore','Karnataka','560085','India',1,1,'2017-01-14 07:42:52','2017-01-14 07:42:52'),(4,NULL,'3425 primrose way',NULL,'Cupertino','CA','95014',NULL,2,2,'2017-03-19 22:06:09','2017-03-19 22:06:09');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `app_user`
--

DROP TABLE IF EXISTS `app_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sso_id` varchar(30) NOT NULL,
  `password` varchar(100) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `notes` varchar(230) DEFAULT NULL,
  `resetid` varchar(250) DEFAULT NULL,
  `state` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sso_id` (`sso_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user`
--

LOCK TABLES `app_user` WRITE;
/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
INSERT INTO `app_user` VALUES (1,'admin1','$2a$10$tp1oItevVNGFql6/NBpfRepU1a2sz5vmkxbB1KpzdtIZHbGnMC8Zm','Sam','Smith','samy@xyz.com',NULL,NULL,NULL,'Active'),(2,'demo1','$2a$10$tp1oItevVNGFql6/NBpfRepU1a2sz5vmkxbB1KpzdtIZHbGnMC8Zm','Ram','Krishna','ram@xyz.com',NULL,NULL,NULL,'Active');
/*!40000 ALTER TABLE `app_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `app_user_user_profile`
--

DROP TABLE IF EXISTS `app_user_user_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_user_user_profile` (
  `user_id` int(11) NOT NULL,
  `user_profile_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`user_profile_id`),
  KEY `fk_user_profile` (`user_profile_id`),
  CONSTRAINT `fk_app_user` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`),
  CONSTRAINT `fk_user_profile` FOREIGN KEY (`user_profile_id`) REFERENCES `user_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user_user_profile`
--

LOCK TABLES `app_user_user_profile` WRITE;
/*!40000 ALTER TABLE `app_user_user_profile` DISABLE KEYS */;
INSERT INTO `app_user_user_profile` VALUES (2,1),(1,2);
/*!40000 ALTER TABLE `app_user_user_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `catalogs`
--

DROP TABLE IF EXISTS `catalogs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `catalogs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `barcode` varchar(200) DEFAULT NULL,
  `catl_free` int(200) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `company` varchar(250) DEFAULT NULL,
  `catl_type` varchar(200) DEFAULT NULL,
  `supplier_name` varchar(200) DEFAULT NULL,
  `expiration` varchar(200) DEFAULT NULL,
  `description` varchar(250) DEFAULT NULL,
  `purchase_price` decimal(10,2) DEFAULT NULL,
  `msrp` decimal(10,2) DEFAULT NULL,
  `max_discount` decimal(10,2) DEFAULT NULL,
  `hsn_no` int(100) DEFAULT NULL,
  `tax` decimal(10,2) DEFAULT NULL,
  `quantity` int(100) DEFAULT NULL,
  `gst` int(100) DEFAULT NULL,
  `manufacturer_contacts_id` int(100) DEFAULT NULL,
  `supplier_contacts_id` int(100) DEFAULT NULL,
  `sale_price` decimal(10,2) DEFAULT NULL,
  `market_price` decimal(10,2) DEFAULT NULL,
  `category` varchar(120) DEFAULT NULL,
  `subcategory` varchar(160) DEFAULT NULL,
  `instructions` varchar(250) DEFAULT NULL,
  `location` varchar(160) DEFAULT NULL,
  `created_by` int(11) DEFAULT '1',
  `updated_by` int(11) DEFAULT '1',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, 
  PRIMARY KEY (`id`),
  KEY `fk_catalogs_createdby__app_user_id` (`created_by`),
  KEY `fk_catalogs_updatedby__app_user_id` (`updated_by`),
  CONSTRAINT `fk_catalogs_createdby__app_user_id` FOREIGN KEY (`created_by`) REFERENCES `app_user` (`id`),
  CONSTRAINT `fk_catalogs_updatedby__app_user_id` FOREIGN KEY (`updated_by`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalogs`
--

--
-- Table structure for table `checks`
--

DROP TABLE IF EXISTS `checks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `checks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `payments_id` int(11) DEFAULT '0',
  `check_number` varchar(50) DEFAULT NULL,
  `driver_licence` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_checks_paymentsid__payments_id` (`payments_id`),
  CONSTRAINT `fk_checks_paymentsid__payments_id` FOREIGN KEY (`payments_id`) REFERENCES `payments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checks`
--

LOCK TABLES `checks` WRITE;
/*!40000 ALTER TABLE `checks` DISABLE KEYS */;
/*!40000 ALTER TABLE `checks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contacts`
--

DROP TABLE IF EXISTS `contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contacts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address_id` int(11) DEFAULT '0',
  `title` varchar(20) DEFAULT NULL,
  `firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `middlename` varchar(50) DEFAULT NULL,
  `contact_type` varchar(50) DEFAULT NULL,
  `occupation` varchar(50) DEFAULT NULL,
  `income` varchar(50) DEFAULT NULL,
  `company` varchar(50) DEFAULT NULL,
  `age` varchar(20) DEFAULT NULL,
  `mobile_phone` varchar(20) DEFAULT NULL,
  `home_phone` varchar(20) DEFAULT NULL,
  `office_phone` varchar(20) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `emergency_contactno` varchar(20) DEFAULT NULL,
  `referedby` varchar(60) DEFAULT NULL,
  `photo_id` varchar(200) DEFAULT NULL,
  `photoid_pic` mediumtext,
  `customer_pic` mediumtext,
  `created_by` int(11) DEFAULT '1',
  `updated_by` int(11) DEFAULT '1',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_contacts_createdby__app_user_id` (`created_by`),
  KEY `fk_contacts_updatedby__app_user_id` (`updated_by`),
  CONSTRAINT `fk_contacts_createdby__app_user_id` FOREIGN KEY (`created_by`) REFERENCES `app_user` (`id`),
  CONSTRAINT `fk_contacts_updatedby__app_user_id` FOREIGN KEY (`updated_by`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacts`
--

LOCK TABLES `contacts` WRITE;
/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
INSERT INTO `contacts` VALUES (1,1,'Mr','Kumar','Gowda','S','Individual','Doctor','50000','SBI','39','9886789347','0802679139','08026791399','shee@gmail.com','08026791399','shreedhar',NULL,NULL,NULL,1,1,'2017-01-14 07:42:52','2017-01-14 07:42:52'),(2,2,'Mr','Ashok','Gowda','P','Individual','Doctor','50000','SBI','36','9886789347','08026791399','0802679139','shee@gmail.com','08026791399','shreedhar',NULL,NULL,NULL,1,1,'2017-01-14 07:42:52','2017-01-14 07:42:52'),(3,3,'Mr','Mohan','Kumar','N','Corporate','Doctor','50000','SBI','45','9886789347','08026791399','0802679139','shee@gmail.com','08026791399','shreedhar',NULL,NULL,NULL,1,1,'2017-01-14 07:42:52','2017-01-14 07:42:52'),(4,0,'Mr.','Ganga',NULL,NULL,NULL,NULL,NULL,'tesdfsd',NULL,'4534534',NULL,NULL,'test@swamy.com',NULL,NULL,NULL,NULL,NULL,2,2,'2017-03-16 09:49:04','2017-03-16 09:49:04'),(5,4,NULL,'Shanthas',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'45345345',NULL,NULL,'s@sss.com',NULL,NULL,NULL,NULL,NULL,2,2,'2017-03-19 22:06:09','2017-03-19 22:06:09');
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `creditcards`
--

DROP TABLE IF EXISTS `creditcards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creditcards` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `payments_id` int(11) DEFAULT '0',
  `name` varchar(50) DEFAULT NULL,
  `cctype` varchar(20) DEFAULT NULL,
  `ccnumber` varchar(20) DEFAULT NULL,
  `expdate` varchar(10) DEFAULT NULL,
  `uspid` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_creditcards_paymentsid__payments_id` (`payments_id`),
  CONSTRAINT `fk_creditcards_paymentsid__payments_id` FOREIGN KEY (`payments_id`) REFERENCES `payments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creditcards`
--

LOCK TABLES `creditcards` WRITE;
/*!40000 ALTER TABLE `creditcards` DISABLE KEYS */;
/*!40000 ALTER TABLE `creditcards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoices`
--

DROP TABLE IF EXISTS `invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `invoice_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `title` varchar(50) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `contacts_id` int(11) DEFAULT NULL,
  `cgst_amount` int(50) DEFAULT '0',
  `sgst_amount` int(50) DEFAULT '0',
  `referredby` varchar(45) DEFAULT NULL,
  `payments_id` int(11) DEFAULT '0',
  `payment_method` varchar(20) DEFAULT NULL,
  `check_number` varchar(10) DEFAULT NULL,
  `shipments_id` int(11) DEFAULT '0',
  `total` decimal(10,2) DEFAULT '0.00',
  `discount` decimal(10,2) DEFAULT '0.00',
  `tax` decimal(10,2) DEFAULT '0.00',
  `nettotal` decimal(10,2) DEFAULT '0.00',
  `paid` decimal(10,2) DEFAULT '0.00',
  `balance` decimal(10,2) DEFAULT '0.00',
  `notes` varchar(250) DEFAULT NULL,
  `checkedout` int(1) DEFAULT '0',
  `created_by` int(11) DEFAULT '1',
  `updated_by` int(11) DEFAULT '1',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_invoices_createdby__app_user_id` (`created_by`),
  KEY `fk_invoices_updatedby__app_user_id` (`updated_by`),
  CONSTRAINT `fk_invoices_createdby__app_user_id` FOREIGN KEY (`created_by`) REFERENCES `app_user` (`id`),
  CONSTRAINT `fk_invoices_updatedby__app_user_id` FOREIGN KEY (`updated_by`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices`
--

LOCK TABLES `invoices` WRITE;
/*!40000 ALTER TABLE `invoices` DISABLE KEYS */;
INSERT INTO `invoices` VALUES (1,'2016-09-25 07:00:00','test0','1234',1,NULL,0,NULL,NULL,0,2016.17,0.00,0.00,0.00,2016.17,0.00,NULL,0,1,1,'2017-01-14 07:42:52','2017-01-14 07:42:52',0),(2,'2017-03-21 03:43:58','test1','1234',1,NULL,0,NULL,NULL,0,2016.17,0.00,0.00,0.00,2016.17,0.00,NULL,0,1,1,'2017-01-14 07:42:52','2017-01-14 07:42:52',1),(3,'2017-03-21 03:43:58','test2','1238',1,NULL,0,NULL,NULL,0,2016.17,0.00,0.00,0.00,2016.17,0.00,NULL,0,1,1,'2017-01-14 07:42:52','2017-01-14 07:42:52',1),(4,'2017-03-21 03:43:58',NULL,NULL,0,NULL,0,NULL,NULL,0,1188.00,120.00,108.00,0.00,0.00,1188.00,NULL,0,2,2,'2017-03-10 07:31:42','2017-03-10 07:31:42',1),(5,'2017-03-21 03:43:58',NULL,NULL,0,NULL,0,NULL,NULL,0,297.00,30.00,27.00,0.00,0.00,297.00,NULL,0,2,2,'2017-03-10 19:01:32','2017-03-10 19:01:32',1),(6,'2017-03-21 03:43:58',NULL,NULL,0,NULL,0,NULL,NULL,0,297.00,30.00,27.00,0.00,0.00,297.00,NULL,0,2,2,'2017-03-10 23:27:50','2017-03-10 23:27:50',1),(7,'2017-03-21 03:43:58',NULL,NULL,0,NULL,0,NULL,NULL,0,297.00,30.00,27.00,0.00,0.00,297.00,NULL,0,2,2,'2017-03-10 23:28:23','2017-03-10 23:28:23',1),(8,'2017-03-21 03:43:58',NULL,NULL,0,NULL,0,NULL,NULL,0,891.00,90.00,81.00,0.00,0.00,891.00,NULL,0,2,2,'2017-03-11 00:40:55','2017-03-11 00:40:55',1),(9,'2017-03-21 03:43:58',NULL,NULL,0,NULL,0,NULL,NULL,0,891.00,90.00,81.00,0.00,0.00,891.00,NULL,0,2,2,'2017-03-11 00:41:03','2017-03-11 00:41:03',1),(10,'2017-03-21 03:43:58',NULL,NULL,0,NULL,0,NULL,NULL,0,891.00,90.00,81.00,0.00,0.00,891.00,NULL,0,2,2,'2017-03-11 00:41:07','2017-03-11 00:41:07',1),(11,'2017-03-21 03:43:58',NULL,NULL,0,NULL,0,NULL,NULL,0,891.00,90.00,81.00,0.00,0.00,891.00,NULL,0,2,2,'2017-03-11 00:41:16','2017-03-11 00:41:16',1),(12,'2017-03-21 03:43:58',NULL,NULL,1,NULL,0,'Cash',NULL,0,297.00,30.00,27.00,0.00,297.00,297.00,'test',0,2,2,'2017-03-14 00:21:58','2017-03-14 00:21:58',1),(13,'2017-03-21 03:43:58',NULL,NULL,1,NULL,0,'Cash',NULL,0,297.00,30.00,27.00,0.00,297.00,297.00,'test',0,2,2,'2017-03-14 00:32:27','2017-03-14 00:32:27',1),(14,'2017-03-21 03:43:58',NULL,NULL,1,NULL,0,'Cash',NULL,0,297.00,30.00,27.00,0.00,297.00,297.00,NULL,0,2,2,'2017-03-14 00:38:53','2017-03-14 00:38:53',1),(15,'2017-03-21 03:43:58',NULL,NULL,1,NULL,0,'Cash',NULL,0,297.00,30.00,27.00,0.00,297.00,297.00,NULL,0,2,2,'2017-03-14 00:39:48','2017-03-14 00:39:48',1),(16,'2017-03-21 03:43:58',NULL,NULL,1,NULL,0,'Cash',NULL,0,297.00,30.00,27.00,0.00,297.00,297.00,NULL,0,2,2,'2017-03-14 00:42:20','2017-03-14 00:42:20',1),(17,'2017-03-21 03:46:45',NULL,NULL,1,NULL,0,'Cash',NULL,0,297.00,30.00,27.00,0.00,297.00,297.00,NULL,0,2,2,'2017-03-14 00:45:24','2017-03-14 00:45:24',1),(18,'2017-03-21 03:43:58',NULL,NULL,1,NULL,0,'Cash',NULL,0,297.00,30.00,27.00,0.00,297.00,297.00,NULL,0,2,2,'2017-03-14 00:46:06','2017-03-14 00:46:06',1),(19,'2017-03-21 03:46:45',NULL,NULL,1,NULL,0,'Cash',NULL,0,297.00,30.00,27.00,0.00,297.00,297.00,NULL,0,2,2,'2017-03-14 00:46:28','2017-03-14 00:46:28',1),(20,'2017-03-21 03:46:45',NULL,NULL,1,NULL,0,'Cash',NULL,0,297.00,30.00,27.00,0.00,297.00,297.00,NULL,0,2,2,'2017-03-14 00:52:48','2017-03-14 00:52:48',1),(21,'2017-03-21 03:46:45','Kumar',NULL,1,NULL,0,'Cash',NULL,0,297.00,30.00,27.00,0.00,297.00,297.00,NULL,0,2,2,'2017-03-14 00:54:09','2017-03-14 00:54:09',1),(22,'2017-03-21 03:46:45','Manju',NULL,1,NULL,0,'Cash',NULL,0,198.00,20.00,18.00,0.00,198.00,198.00,NULL,0,2,2,'2017-03-14 02:53:06','2017-03-14 02:53:06',1),(23,'2017-03-21 03:46:45','Shivand',NULL,1,NULL,0,'Check',NULL,0,693.00,70.00,63.00,0.00,693.00,693.00,'Test',0,2,2,'2017-03-16 11:23:52','2017-03-16 11:23:52',1),(24,'2017-03-21 03:46:45','Sridar',NULL,1,NULL,0,'Cash',NULL,0,2970.00,300.00,270.00,0.00,2970.00,2970.00,NULL,0,2,2,'2017-03-19 10:12:51','2017-03-19 10:12:51',1),(25,'2017-03-21 03:46:02','Shanthas',NULL,1,'Swamy',0,'Check','345353',0,297.00,30.00,27.00,0.00,297.00,297.00,'test',0,2,2,'2017-03-19 10:17:15','2017-03-19 10:17:15',0),(26,'2017-03-21 03:46:02','Sandeep',NULL,1,NULL,0,'Cash',NULL,0,297.00,30.00,27.00,0.00,297.00,297.00,'test',0,2,2,'2017-03-19 10:21:05','2017-03-19 10:21:05',0),(27,'2017-03-21 03:46:02','Gagandep',NULL,1,'swamy',0,'Check','324324',0,1089.00,110.00,99.00,0.00,1089.00,1089.00,'test',0,2,2,'2017-03-19 18:48:36','2017-03-19 18:48:36',0),(28,'2017-03-21 03:46:02','Bently',NULL,1,'swamy',0,'Check','2345432',0,1386.00,140.00,126.00,0.00,1386.00,1386.00,'test',0,2,2,'2017-03-19 18:56:30','2017-03-19 18:56:30',0),(29,'2017-03-21 03:46:02','Mercedes',NULL,1,'swamy',1,'Account',NULL,0,792.00,80.00,72.00,0.00,792.00,0.00,'test',0,2,2,'2017-03-19 20:30:37','2017-03-19 20:30:37',0),(30,'2017-03-21 03:46:02','Benz',NULL,1,'swamy',1,'Account',NULL,0,891.00,90.00,81.00,0.00,891.00,0.00,'test',0,2,2,'2017-03-19 20:43:12','2017-03-19 20:43:12',0),(31,'2017-03-21 03:46:02','Volvo',NULL,1,'swMY',1,'Account',NULL,0,594.00,60.00,54.00,0.00,594.00,0.00,'test',0,2,2,'2017-03-19 22:07:25','2017-03-19 22:07:25',0),(32,'2017-03-21 03:46:02','Toyota',NULL,1,'swmy',0,'Cash',NULL,0,105.00,0.00,5.00,0.00,105.00,0.00,'test',0,2,2,'2017-03-20 08:59:13','2017-03-20 08:59:13',0),(33,'2017-03-21 03:46:02','BMW',NULL,1,'Swamy',0,'Cash',NULL,0,105.00,0.00,5.00,0.00,105.00,0.00,'test',0,2,2,'2017-03-20 09:02:53','2017-03-20 09:02:53',0),(34,'2017-03-21 03:46:02','Audi',NULL,1,'swamy',0,'Cash',NULL,0,105.00,0.00,5.00,0.00,105.00,0.00,'test',0,2,2,'2017-03-20 09:09:49','2017-03-20 09:09:49',0),(35,'2017-03-21 03:46:02','Crysler',NULL,1,'swamy',0,'Cash',NULL,0,297.00,30.00,27.00,0.00,297.00,0.00,'test',0,2,2,'2017-03-20 09:23:33','2017-03-20 09:23:33',0),(36,'2017-05-23 03:47:45',NULL,NULL,1,'',0,'',NULL,0,270.00,30.00,27.00,0.00,0.00,297.00,'',0,2,2,'2017-05-23 03:47:45','2017-05-23 03:47:45',0),(37,'2017-05-23 04:01:14',NULL,NULL,1,'Swamy',0,'',NULL,0,360.00,40.00,36.00,0.00,0.00,396.00,'test',0,2,2,'2017-05-23 04:01:14','2017-05-23 04:01:14',0),(38,'2017-05-23 04:09:23',NULL,NULL,1,'swamy',0,'',NULL,0,270.00,30.00,27.00,0.00,0.00,297.00,'test',0,2,2,'2017-05-23 04:09:23','2017-05-23 04:09:23',0),(39,'2017-05-23 04:13:17',NULL,NULL,1,'swamy',0,'',NULL,0,270.00,30.00,27.00,0.00,0.00,297.00,'test',0,2,2,'2017-05-23 04:13:17','2017-05-23 04:13:17',0),(40,'2017-05-23 04:15:19',NULL,NULL,1,'swamy',0,'',NULL,0,270.00,30.00,27.00,0.00,0.00,297.00,'test',0,2,2,'2017-05-23 04:15:19','2017-05-23 04:15:19',0);
/*!40000 ALTER TABLE `invoices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoices_items`
--

DROP TABLE IF EXISTS `invoices_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoices_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `catalogs_id` int(11) DEFAULT '0',
  `invoices_id` int(11) DEFAULT '0',
  `barcode` varchar(20) DEFAULT NULL,
  `name` varchar(250) DEFAULT NULL,
  `expiration` varchar(20) DEFAULT NULL,
  `quantity` int(11) DEFAULT '0',
  `price` decimal(10,2) DEFAULT '0.00',
  `purchase_price` decimal(10,2) DEFAULT NULL,
  `tax` decimal(10,2) DEFAULT '0.00',
  `discount` decimal(10,2) DEFAULT '0.00',
  `subtotal` decimal(10,2) DEFAULT '0.00',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_invoicesitems_invoiceid__invoices_id` (`invoices_id`),
  KEY `fk_invoicesitems_catalogsid__catalogs_id` (`catalogs_id`),
  CONSTRAINT `fk_invoicesitems_catalogsid__catalogs_id` FOREIGN KEY (`catalogs_id`) REFERENCES `catalogs` (`id`),
  CONSTRAINT `fk_invoicesitems_invoiceid__invoices_id` FOREIGN KEY (`invoices_id`) REFERENCES `invoices` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices_items`
--

LOCK TABLES `invoices_items` WRITE;
/*!40000 ALTER TABLE `invoices_items` DISABLE KEYS */;
INSERT INTO `invoices_items` VALUES (5,4,21,'xyz123','Sample','2017-01-15',1,300.00,NULL,10.00,10.00,297.00,'2017-03-13 03:43:12'),(6,1,22,'abcd1234','Test','2017-01-15',1,200.00,NULL,10.00,10.00,198.00,'2017-03-14 03:43:12'),(7,4,23,'xyz123','Sample','2017-01-15',1,300.00,NULL,10.00,10.00,297.00,'2017-03-16 03:43:12'),(8,5,23,'pqrs1234','demo','2017-01-15',1,400.00,NULL,10.00,10.00,396.00,'2017-03-18 03:43:12'),(10,4,25,'xyz123','Sample','2017-01-15',1,300.00,250.00,10.00,10.00,297.00,NULL),(11,4,26,'xyz123','Sample','2017-01-15',1,300.00,250.00,10.00,10.00,297.00,NULL),(12,4,27,'xyz123','Sample','2017-01-15',1,300.00,250.00,10.00,10.00,297.00,NULL),(13,1,27,'abcd1234','Test','2017-01-15',4,200.00,150.00,10.00,10.00,792.00,NULL),(14,4,28,'xyz123','Sample','2017-01-15',2,300.00,250.00,10.00,10.00,594.00,NULL),(15,1,28,'abcd1234','Test','2017-01-15',4,200.00,150.00,10.00,10.00,792.00,NULL),(16,4,29,'xyz123','Sample','2017-01-15',2,300.00,250.00,10.00,10.00,594.00,NULL),(17,1,29,'abcd1234','Test','2017-01-15',1,200.00,150.00,10.00,10.00,198.00,NULL),(18,4,30,'xyz123','Sample','2017-01-15',3,300.00,250.00,10.00,10.00,891.00,NULL),(19,4,31,'xyz123','Sample','2017-01-15',2,300.00,250.00,10.00,10.00,594.00,NULL),(20,8,33,'SK123','Churna','4/8/2017',1,100.00,0.00,5.00,0.00,105.00,NULL),(21,8,34,'SK123','Churna','4/8/2017',1,100.00,0.00,5.00,0.00,105.00,NULL),(22,4,35,'xyz123','Sample','2017-01-15 00:00:00',1,300.00,0.00,10.00,10.00,297.00,NULL),(23,4,40,'xyz123','Sample','1/15/2017',1,300.00,250.00,10.00,10.00,270.00,NULL);
/*!40000 ALTER TABLE `invoices_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address_id` int(11) DEFAULT '0',
  `payment_type` varchar(50) DEFAULT NULL,
  `payment` decimal(10,2) DEFAULT '0.00',
  `created_by` int(11) DEFAULT '1',
  `updated_by` int(11) DEFAULT '1',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_payments_createdby__app_user_id` (`created_by`),
  KEY `fk_payments_updatedby__app_user_id` (`updated_by`),
  CONSTRAINT `fk_payments_createdby__app_user_id` FOREIGN KEY (`created_by`) REFERENCES `app_user` (`id`),
  CONSTRAINT `fk_payments_updatedby__app_user_id` FOREIGN KEY (`updated_by`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchases`
--

DROP TABLE IF EXISTS `purchases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchases` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `purchase_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `title` varchar(50) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `contacts_id` int(11) DEFAULT NULL,
  `payments_id` int(11) DEFAULT '0',
  `cgst_amount` int(50) DEFAULT '0',
  `sgst_amount` int(50) DEFAULT '0',
  `shipments_id` int(11) DEFAULT '0',
  `total` decimal(10,2) DEFAULT '0.00',
  `discount` decimal(10,2) DEFAULT '0.00',
  `tax` decimal(10,2) DEFAULT '0.00',
  `nettotal` decimal(10,2) DEFAULT '0.00',
  `paid` decimal(10,2) DEFAULT '0.00',
  `balance` decimal(10,2) DEFAULT '0.00',
  `billamount` decimal(10,2) DEFAULT '0.00',
  `billno` varchar(20) DEFAULT NULL,
  `notes` varchar(250) DEFAULT NULL,
  `checkedout` int(1) DEFAULT '0',
  `created_by` int(11) DEFAULT '1',
  `updated_by` int(11) DEFAULT '1',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_purchases_createdby__app_user_id` (`created_by`),
  KEY `fk_purchases_updatedby__app_user_id` (`updated_by`),
  CONSTRAINT `fk_purchases_createdby__app_user_id` FOREIGN KEY (`created_by`) REFERENCES `app_user` (`id`),
  CONSTRAINT `fk_purchases_updatedby__app_user_id` FOREIGN KEY (`updated_by`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchases`
--

LOCK TABLES `purchases` WRITE;
/*!40000 ALTER TABLE `purchases` DISABLE KEYS */;
INSERT INTO `purchases` VALUES (1,'2017-03-18 08:44:25','test','test',1,1,1,300.00,10.00,10.00,300.00,300.00,0.00,NULL,'0','test',0,1,1,'2017-03-12 08:54:56','2017-03-18 07:54:56',0),(2,'2017-03-18 09:50:34',NULL,NULL,1,0,0,1089.00,110.00,99.00,0.00,1089.00,1089.00,NULL,'0','test',0,2,2,'2017-03-18 09:50:34','2017-03-18 09:50:34',0),(3,'2017-03-19 22:37:09',NULL,NULL,1,1,0,891.00,90.00,81.00,0.00,891.00,0.00,NULL,'0','test',0,2,2,'2017-03-19 22:37:09','2017-03-19 22:37:09',0),(4,'2017-03-19 23:07:31',NULL,NULL,1,1,0,594.00,60.00,54.00,0.00,594.00,0.00,NULL,'0','test',0,2,2,'2017-03-19 23:07:31','2017-03-19 23:07:31',0),(5,'2017-03-19 23:16:13',NULL,NULL,1,1,0,594.00,60.00,54.00,0.00,594.00,0.00,NULL,'0','test',0,2,2,'2017-03-19 23:16:13','2017-03-19 23:16:13',0),(6,'2017-05-23 05:56:03',NULL,NULL,1,0,0,810.00,75.00,67.50,0.00,0.00,877.50,877.50,'234324','test',0,2,2,'2017-05-23 05:56:03','2017-05-23 05:56:03',0),(7,'2017-05-23 06:28:22',NULL,NULL,1,0,0,675.00,75.00,67.50,0.00,0.00,742.50,742.50,'4444','test',0,2,2,'2017-05-23 06:28:22','2017-05-23 06:28:22',0);
/*!40000 ALTER TABLE `purchases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchases_items`
--

DROP TABLE IF EXISTS `purchases_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchases_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `catalogs_id` int(11) DEFAULT '0',
  `purchases_id` int(11) DEFAULT '0',
  `barcode` varchar(20) DEFAULT NULL,
  `name` varchar(250) DEFAULT NULL,
  `expiration` varchar(20) DEFAULT NULL,
  `freequantity` int(11) DEFAULT '0',
  `quantity` int(11) DEFAULT '0',
  `price` decimal(10,2) DEFAULT '0.00',
  `purchase_price` decimal(10,2) DEFAULT NULL,
  `tax` decimal(10,2) DEFAULT '0.00',
  `discount` decimal(10,2) DEFAULT '0.00',
  `subtotal` decimal(10,2) DEFAULT '0.00',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_purchasesitems_purchasesid_purchases_id` (`purchases_id`),
  KEY `fk_purchasesitems_catalogsid_catalogs_id` (`catalogs_id`),
  CONSTRAINT `fk_purchasesitems_catalogsid_catalogs_id` FOREIGN KEY (`catalogs_id`) REFERENCES `catalogs` (`id`),
  CONSTRAINT `fk_purchasesitems_purchasesid_purchases_id` FOREIGN KEY (`purchases_id`) REFERENCES `purchases` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchases_items`
--

LOCK TABLES `purchases_items` WRITE;
/*!40000 ALTER TABLE `purchases_items` DISABLE KEYS */;
INSERT INTO `purchases_items` VALUES (1,1,1,'abcd1234','test','2016-09-25',0,1,100.00,100.00,10.00,10.00,100.00,'2017-02-25 08:00:00'),(3,4,5,'xyz123','Sample','2017-01-15',0,2,300.00,250.00,10.00,10.00,594.00,'2017-02-25 08:00:00'),(4,4,6,NULL,'Sample','1/15/2017',1,3,300.00,250.00,10.00,10.00,810.00,NULL),(5,4,7,NULL,'Sample','1/15/2017',1,3,300.00,250.00,10.00,10.00,675.00,NULL);
/*!40000 ALTER TABLE `purchases_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settings`
--

DROP TABLE IF EXISTS `settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `settings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `currency` varchar(10) DEFAULT NULL,
  `app_language` varchar(20) DEFAULT NULL,
  `time_zone` varchar(250) DEFAULT NULL,
  `location` varchar(20) DEFAULT NULL,
  `logo` varchar(100) DEFAULT NULL,
  `address` varchar(250) DEFAULT NULL,
  `created_by` int(11) DEFAULT '1',
  `updated_by` int(11) DEFAULT '1',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_settings_createdby__app_user_id` (`created_by`),
  KEY `fk_settings_updatedby__app_user_id` (`updated_by`),
  CONSTRAINT `fk_settings_createdby__app_user_id` FOREIGN KEY (`created_by`) REFERENCES `app_user` (`id`),
  CONSTRAINT `fk_settings_updatedby__app_user_id` FOREIGN KEY (`updated_by`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settings`
--

LOCK TABLES `settings` WRITE;
/*!40000 ALTER TABLE `settings` DISABLE KEYS */;
/*!40000 ALTER TABLE `settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipments`
--

DROP TABLE IF EXISTS `shipments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shipments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address_id` int(11) DEFAULT '0',
  `type` varchar(50) DEFAULT NULL,
  `vendor` varchar(50) DEFAULT NULL,
  `cost` decimal(10,2) DEFAULT '0.00',
  `weight` decimal(10,2) DEFAULT '0.00',
  `hight` decimal(10,2) DEFAULT '0.00',
  `width` decimal(10,2) DEFAULT '0.00',
  `length` decimal(10,2) DEFAULT '0.00',
  `customs_duty` decimal(10,2) DEFAULT '0.00',
  `customs_notes` text,
  `created_by` int(11) DEFAULT '1',
  `updated_by` int(11) DEFAULT '1',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_shipments_createdby__app_user_id` (`created_by`),
  KEY `fk_shipments_updatedby__app_user_id` (`updated_by`),
  CONSTRAINT `fk_shipments_createdby__app_user_id` FOREIGN KEY (`created_by`) REFERENCES `app_user` (`id`),
  CONSTRAINT `fk_shipments_updatedby__app_user_id` FOREIGN KEY (`updated_by`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipments`
--

LOCK TABLES `shipments` WRITE;
/*!40000 ALTER TABLE `shipments` DISABLE KEYS */;
/*!40000 ALTER TABLE `shipments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_profile`
--

DROP TABLE IF EXISTS `user_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_profile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_profile`
--

LOCK TABLES `user_profile` WRITE;
/*!40000 ALTER TABLE `user_profile` DISABLE KEYS */;
INSERT INTO `user_profile` VALUES (2,'ROLE_ADMIN'),(1,'ROLE_USER');
/*!40000 ALTER TABLE `user_profile` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-22 23:33:03
