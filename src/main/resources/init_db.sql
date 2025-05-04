CREATE DATABASE `taxi_service` /*!40100 DEFAULT CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci */;

CREATE TABLE `taxi_service`.`manufacturers` (
                                                `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                                `name` VARCHAR(255) NOT NULL,
                                                `country` VARCHAR(255) NULL DEFAULT NULL,
                                                `is_deleted` TINYINT NULL DEFAULT 0,
                                                PRIMARY KEY (`id`));
