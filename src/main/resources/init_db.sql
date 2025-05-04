CREATE DATABASE `taxi_company`;

CREATE TABLE `manufacturers` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `name` varchar(100) NOT NULL,
                                 `country` varchar(50) NOT NULL,
                                 `is_deleted` tinyint NOT NULL DEFAULT '0',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `id_UNIQUE` (`id`));
