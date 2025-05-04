CREATE DATABASE `taxi_db` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
CREATE TABLE `taxi_db`.`manufacturers` (
                                           `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                           `name` VARCHAR(255) NULL DEFAULT NULL,
                                           `country` VARCHAR(255) NULL,
                                           `is_deleted` TINYINT NOT NULL DEFAULT '0',
                                           PRIMARY KEY (`id`));