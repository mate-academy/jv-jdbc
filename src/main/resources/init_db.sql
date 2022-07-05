CREATE DATABASE `taxi_service_db`;

CREATE TABLE `manufacturers` (
  `manufacturer_id` bigint NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  `is_deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`manufacturer_id`),
  UNIQUE KEY `manufacturer_id_UNIQUE` (`manufacturer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
