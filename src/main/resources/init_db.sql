CREATE DATABASE `manufacturers_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `manufacturers` (
  `manufacturer_id` int NOT NULL AUTO_INCREMENT,
  `manufacturer_name` varchar(100) DEFAULT NULL,
  `manufacturer_country` varchar(100) DEFAULT NULL,
  `is_deleted` tinyint DEFAULT '0',
  PRIMARY KEY (`manufacturer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
