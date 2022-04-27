CREATE DATABASE `taxi_service` DEFAULT CHARACTER SET utf8;
USE `taxi_service`;

CREATE TABLE `manufacturers` (
`id` bigint NOT NULL AUTO_INCREMENT,
`name` varchar(255) DEFAULT NULL,
`country` varchar(255) DEFAULT NULL,
`is_deleted` tinyint NOT NULL DEFAULT '0',
PRIMARY KEY (`id`),
UNIQUE KEY `int_UNIQUE` (`id`));
