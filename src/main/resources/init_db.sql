CREATE DATABASE `taxi_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `manufacturers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `is_deleted` tinyint NOT NULL DEFAULT '0',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


