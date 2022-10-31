CREATE DATABASE IF NOT EXISTS `taxi_db` DEFAULT CHARACTER SET utf8;
USE taxi_db;

CREATE TABLE `manufacturer` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `name` varchar(45) NOT NULL,
                                `country` varchar(45) NOT NULL,
                                `is_deleted` tinyint NOT NULL,
                                PRIMARY KEY (`id`)
);
