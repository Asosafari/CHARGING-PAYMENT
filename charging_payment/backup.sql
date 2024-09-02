-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: localhost    Database: payment_db
-- ------------------------------------------------------
-- Server version	8.0.39-0ubuntu0.22.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flyway_schema_history`
--

LOCK TABLES `flyway_schema_history` WRITE;
/*!40000 ALTER TABLE `flyway_schema_history` DISABLE KEYS */;
INSERT INTO `flyway_schema_history` VALUES (1,'1','init-mysql-database','SQL','V1__init-mysql-database.sql',-79431351,'restadmin','2024-09-02 21:36:10',61,1),(2,'2','init-mysql-database','SQL','V2__init-mysql-database.sql',-1913333383,'restadmin','2024-09-02 21:36:10',10,1);
/*!40000 ALTER TABLE `flyway_schema_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gateway_payment`
--

DROP TABLE IF EXISTS `gateway_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gateway_payment` (
  `card_number` varchar(255) NOT NULL,
  `cvv` varchar(255) NOT NULL,
  `date_expiry` varchar(255) DEFAULT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK5b9cn90drt1rj4qww4holxpfd` FOREIGN KEY (`id`) REFERENCES `payments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gateway_payment`
--

LOCK TABLES `gateway_payment` WRITE;
/*!40000 ALTER TABLE `gateway_payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `gateway_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `key_payment`
--

DROP TABLE IF EXISTS `key_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `key_payment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `token` varchar(255) NOT NULL,
  `charging_user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `token` (`token`),
  KEY `charging_user_id` (`charging_user_id`),
  CONSTRAINT `key_payment_ibfk_1` FOREIGN KEY (`charging_user_id`) REFERENCES `payment_users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `key_payment`
--

LOCK TABLES `key_payment` WRITE;
/*!40000 ALTER TABLE `key_payment` DISABLE KEYS */;
INSERT INTO `key_payment` VALUES (1,'d978688f-398f-40f4-9f82-8f861c1d8056',1);
/*!40000 ALTER TABLE `key_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_users`
--

DROP TABLE IF EXISTS `payment_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `charging_user_id` bigint NOT NULL,
  `account_balance` decimal(38,2) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `charging_user_id` (`charging_user_id`),
  CONSTRAINT `payment_users_chk_1` CHECK ((`account_balance` >= 0.00))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_users`
--

LOCK TABLES `payment_users` WRITE;
/*!40000 ALTER TABLE `payment_users` DISABLE KEYS */;
INSERT INTO `payment_users` VALUES (1,1,1400000001000.00,'2024-09-02 18:06:53','2024-09-02 21:39:28',0);
/*!40000 ALTER TABLE `payment_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `amount` decimal(38,2) NOT NULL,
  `is_success` tinyint(1) NOT NULL,
  `payment_type` enum('DIRECT','GATEWAY') NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `payment_users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `payments_chk_1` CHECK ((`amount` >= 0.00))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,1,1000.00,1,'DIRECT','2024-09-02 18:09:28',0);
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments_direct`
--

DROP TABLE IF EXISTS `payments_direct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments_direct` (
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKjtm2y6n1tdnd6jxaj3o9s5i0x` FOREIGN KEY (`id`) REFERENCES `payments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments_direct`
--

LOCK TABLES `payments_direct` WRITE;
/*!40000 ALTER TABLE `payments_direct` DISABLE KEYS */;
INSERT INTO `payments_direct` VALUES (1);
/*!40000 ALTER TABLE `payments_direct` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-03  1:17:11
-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: localhost    Database: charging_Db
-- ------------------------------------------------------
-- Server version	8.0.39-0ubuntu0.22.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `authorized_bank_users`
--

DROP TABLE IF EXISTS `authorized_bank_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authorized_bank_users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `bank_user_id` bigint NOT NULL,
  `token_ch` varchar(255) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `bank_user_id` (`bank_user_id`),
  UNIQUE KEY `token_ch` (`token_ch`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `authorized_bank_users_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorized_bank_users`
--

LOCK TABLES `authorized_bank_users` WRITE;
/*!40000 ALTER TABLE `authorized_bank_users` DISABLE KEYS */;
INSERT INTO `authorized_bank_users` VALUES (1,1,1,'d978688f-398f-40f4-9f82-8f861c1d8056','2024-09-02 21:36:53');
/*!40000 ALTER TABLE `authorized_bank_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `basic_charging_plan`
--

DROP TABLE IF EXISTS `basic_charging_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `basic_charging_plan` (
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKl8ovpx60ysgkew15mgwf6jq3t` FOREIGN KEY (`id`) REFERENCES `charging_plans` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `basic_charging_plan`
--

LOCK TABLES `basic_charging_plan` WRITE;
/*!40000 ALTER TABLE `basic_charging_plan` DISABLE KEYS */;
INSERT INTO `basic_charging_plan` VALUES (1);
/*!40000 ALTER TABLE `basic_charging_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `charging_plans`
--

DROP TABLE IF EXISTS `charging_plans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `charging_plans` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `plan_name` varchar(255) NOT NULL,
  `rate_per_unit` decimal(38,2) NOT NULL,
  `price_per_unit` decimal(38,2) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `charging_plan_type` enum('PREMIUM','BASIC','DEFAULT') NOT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `plan_name` (`plan_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `charging_plans`
--

LOCK TABLES `charging_plans` WRITE;
/*!40000 ALTER TABLE `charging_plans` DISABLE KEYS */;
INSERT INTO `charging_plans` VALUES (1,'Basic Plan 1',10.00,100.00,'Basic plan with basic features.','2024-09-02 18:06:58',0,'BASIC','2024-09-02 21:36:57.822252');
/*!40000 ALTER TABLE `charging_plans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `default_charging_plan`
--

DROP TABLE IF EXISTS `default_charging_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `default_charging_plan` (
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK995xgueaslt5rvk4p460gkl9h` FOREIGN KEY (`id`) REFERENCES `charging_plans` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `default_charging_plan`
--

LOCK TABLES `default_charging_plan` WRITE;
/*!40000 ALTER TABLE `default_charging_plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `default_charging_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flyway_schema_history`
--

LOCK TABLES `flyway_schema_history` WRITE;
/*!40000 ALTER TABLE `flyway_schema_history` DISABLE KEYS */;
INSERT INTO `flyway_schema_history` VALUES (1,'1','init-mysql-database','SQL','V1__init-mysql-database.sql',1120739283,'restadmin','2024-09-02 21:36:22',172,1),(2,'2','init-mysql-database','SQL','V2__init-mysql-database.sql',-1865601042,'restadmin','2024-09-02 21:36:22',20,1),(3,'3','init-mysql-database','SQL','V3__init-mysql-database.sql',-1967192312,'restadmin','2024-09-02 21:36:22',14,1);
/*!40000 ALTER TABLE `flyway_schema_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log_entries`
--

DROP TABLE IF EXISTS `log_entries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `log_entries` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `exception` text,
  `level` varchar(255) NOT NULL,
  `message` varchar(255) NOT NULL,
  `timestamp` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log_entries`
--

LOCK TABLES `log_entries` WRITE;
/*!40000 ALTER TABLE `log_entries` DISABLE KEYS */;
/*!40000 ALTER TABLE `log_entries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logging_event`
--

DROP TABLE IF EXISTS `logging_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logging_event` (
  `event_id` bigint NOT NULL AUTO_INCREMENT,
  `timestmp` bigint NOT NULL,
  `formatted_message` text NOT NULL,
  `logger_name` varchar(255) NOT NULL,
  `level_string` varchar(255) NOT NULL,
  `thread_name` varchar(255) DEFAULT NULL,
  `reference_flag` smallint DEFAULT NULL,
  `caller_filename` varchar(255) NOT NULL,
  `caller_class` varchar(255) NOT NULL,
  `caller_method` varchar(255) NOT NULL,
  `caller_line` char(4) NOT NULL,
  `arg0` varchar(255) DEFAULT NULL,
  `arg1` varchar(255) DEFAULT NULL,
  `arg2` varchar(255) DEFAULT NULL,
  `arg3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logging_event`
--

LOCK TABLES `logging_event` WRITE;
/*!40000 ALTER TABLE `logging_event` DISABLE KEYS */;
/*!40000 ALTER TABLE `logging_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logging_event_exception`
--

DROP TABLE IF EXISTS `logging_event_exception`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logging_event_exception` (
  `event_id` bigint NOT NULL,
  `i` smallint NOT NULL,
  `trace_line` varchar(255) NOT NULL,
  PRIMARY KEY (`event_id`,`i`),
  CONSTRAINT `logging_event_exception_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `logging_event` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logging_event_exception`
--

LOCK TABLES `logging_event_exception` WRITE;
/*!40000 ALTER TABLE `logging_event_exception` DISABLE KEYS */;
/*!40000 ALTER TABLE `logging_event_exception` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logging_event_property`
--

DROP TABLE IF EXISTS `logging_event_property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logging_event_property` (
  `event_id` bigint NOT NULL,
  `mapped_key` varchar(255) NOT NULL,
  `mapped_value` text,
  PRIMARY KEY (`event_id`,`mapped_key`),
  CONSTRAINT `logging_event_property_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `logging_event` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logging_event_property`
--

LOCK TABLES `logging_event_property` WRITE;
/*!40000 ALTER TABLE `logging_event_property` DISABLE KEYS */;
/*!40000 ALTER TABLE `logging_event_property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `premium_charging_plan`
--

DROP TABLE IF EXISTS `premium_charging_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `premium_charging_plan` (
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK4aeidno1hlosgqtb1e9cvunjg` FOREIGN KEY (`id`) REFERENCES `charging_plans` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `premium_charging_plan`
--

LOCK TABLES `premium_charging_plan` WRITE;
/*!40000 ALTER TABLE `premium_charging_plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `premium_charging_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `successful_transaction_view`
--

DROP TABLE IF EXISTS `successful_transaction_view`;
/*!50001 DROP VIEW IF EXISTS `successful_transaction_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `successful_transaction_view` AS SELECT 
 1 AS `successful_transaction_id`,
 1 AS `transaction_id`,
 1 AS `user_id`,
 1 AS `username`,
 1 AS `token_ch`,
 1 AS `created_date`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `successful_transactions`
--

DROP TABLE IF EXISTS `successful_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `successful_transactions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `transaction_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `token_ch` varchar(255) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `token_ch` (`token_ch`),
  KEY `transaction_id` (`transaction_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `successful_transactions_ibfk_1` FOREIGN KEY (`transaction_id`) REFERENCES `transactions` (`id`),
  CONSTRAINT `successful_transactions_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `successful_transactions`
--

LOCK TABLES `successful_transactions` WRITE;
/*!40000 ALTER TABLE `successful_transactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `successful_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `transaction_view`
--

DROP TABLE IF EXISTS `transaction_view`;
/*!50001 DROP VIEW IF EXISTS `transaction_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `transaction_view` AS SELECT 
 1 AS `transaction_id`,
 1 AS `user_id`,
 1 AS `username`,
 1 AS `charging_plan_id`,
 1 AS `plan_name`,
 1 AS `amount`,
 1 AS `is_success`,
 1 AS `created_date`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `charging_plan_id` bigint NOT NULL,
  `amount` decimal(38,2) NOT NULL,
  `is_success` tinyint(1) NOT NULL,
  `transaction_type` enum('DIRECT','GATEWAY') NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `charging_plan_id` (`charging_plan_id`),
  CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `transactions_ibfk_2` FOREIGN KEY (`charging_plan_id`) REFERENCES `charging_plans` (`id`),
  CONSTRAINT `transactions_chk_1` CHECK ((`amount` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,1,1,1000.00,1,'DIRECT','2024-09-02 18:09:28',0);
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_charging_plan`
--

DROP TABLE IF EXISTS `user_charging_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_charging_plan` (
  `user_id` bigint NOT NULL,
  `charging_plan_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`charging_plan_id`),
  KEY `charging_plan_id` (`charging_plan_id`),
  CONSTRAINT `user_charging_plan_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `user_charging_plan_ibfk_2` FOREIGN KEY (`charging_plan_id`) REFERENCES `charging_plans` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_charging_plan`
--

LOCK TABLES `user_charging_plan` WRITE;
/*!40000 ALTER TABLE `user_charging_plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_charging_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(12) NOT NULL,
  `password` varchar(12) NOT NULL,
  `email` varchar(255) NOT NULL,
  `balance` decimal(38,2) NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT NULL,
  `version` int NOT NULL DEFAULT '1',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  CONSTRAINT `users_chk_1` CHECK ((`balance` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'user1','password12','usr1@example.com',10600.00,0,'2024-09-02 18:06:42','2024-09-02 18:06:42',1,0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `successful_transaction_view`
--

/*!50001 DROP VIEW IF EXISTS `successful_transaction_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`restadmin`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `successful_transaction_view` AS select `st`.`id` AS `successful_transaction_id`,`st`.`transaction_id` AS `transaction_id`,`st`.`user_id` AS `user_id`,`u`.`username` AS `username`,`st`.`token_ch` AS `token_ch`,`st`.`created_date` AS `created_date` from (`successful_transactions` `st` join `users` `u` on((`st`.`user_id` = `u`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `transaction_view`
--

/*!50001 DROP VIEW IF EXISTS `transaction_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`restadmin`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `transaction_view` AS select `t`.`id` AS `transaction_id`,`t`.`user_id` AS `user_id`,`u`.`username` AS `username`,`t`.`charging_plan_id` AS `charging_plan_id`,`cp`.`plan_name` AS `plan_name`,`t`.`amount` AS `amount`,`t`.`is_success` AS `is_success`,`t`.`created_date` AS `created_date` from ((`transactions` `t` join `users` `u` on((`t`.`user_id` = `u`.`id`))) join `charging_plans` `cp` on((`t`.`charging_plan_id` = `cp`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-03  1:17:11
