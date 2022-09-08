CREATE DATABASE `taxi_service_db` DEFAULT CHARACTER SET utf8mb4;
USE `taxi_service_db`;
CREATE TABLE `manufacturers` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `name` varchar(45) DEFAULT NULL,
                                 `country` varchar(45) DEFAULT NULL,
                                 `is_deleted` tinyint NOT NULL DEFAULT '0',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
