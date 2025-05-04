CREATE DATABASE `taxi_service_db`;
CREATE TABLE `manufacturers` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `name` varchar(255) DEFAULT NULL,
                                 `country` varchar(255) DEFAULT NULL,
                                 `is_delated` tinyint DEFAULT '0',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
