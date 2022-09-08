CREATE DATABASE `taxi_service_db` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `taxi_service_db`;
CREATE TABLE `manufacturers` (
`id` bigint NOT NULL AUTO_INCREMENT,
`name` varchar(100) NOT NULL,
`country` varchar(100) NOT NULL,
`is_deleted` tinyint NOT NULL DEFAULT '0',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3;
