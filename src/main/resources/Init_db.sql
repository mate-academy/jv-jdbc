CREATE DATABASE `taxi_db` /* !40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /* !80016 DEFAULT ENCRYPTION='N' */;
USE 'taxi_db';
CREATE TABLE `manufacturers` (
`id` bigint NOT NULL AUTO_INCREMENT,
`name` varchar(100) DEFAULT NULL,
`country` varchar(100) DEFAULT NULL,
`is_deleted` tinyint(1) NOT NULL DEFAULT '0',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
