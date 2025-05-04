CREATE DATABASE `taxi_service` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `taxi_service`;
CREATE TABLE `taxi_service`.`manufacturers` (
                                                `id` INT NOT NULL AUTO_INCREMENT,
                                                `name` VARCHAR(45) NULL,
                                                `country` VARCHAR(45) NULL,
                                                `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
                                                PRIMARY KEY (`id`));


