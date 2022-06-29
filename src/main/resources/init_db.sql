CREATE DATABASE `taxi_service_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `manufacturers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
`name` varchar(255) COLLATE utf8mb4_cs_0900_ai_ci DEFAULT NULL,
`country` varchar(255) COLLATE utf8mb4_cs_0900_ai_ci DEFAULT NULL,
`is_deleted` varchar(45) COLLATE utf8mb4_cs_0900_ai_ci NOT NULL DEFAULT '0',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_cs_0900_ai_ci;
