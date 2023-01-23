CREATE SCHEMA IF NOT EXISTS `taxiservice` DEFAULT CHARACTER SET utf8mb4;
USE `taxiservice`;

CREATE TABLE `taxiservice`.`manufacturers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `is_deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
