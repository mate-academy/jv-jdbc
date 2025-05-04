CREATE DATABASE `taxi_service`;
CREATE TABLE `manufacturers` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `name` varchar(100) DEFAULT NULL,
                                 `country` varchar(60) DEFAULT NULL,
                                 `is_deleted` tinyint DEFAULT '0',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
