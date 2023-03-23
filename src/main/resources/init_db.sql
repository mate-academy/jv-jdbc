CREATE DATABASE `taxi_service_db` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
CREATE TABLE `manufacturers` (
                                 `id` BIGINT NOT NULL AUTO_INCREMENT,
                                 `name` VARCHAR(255) DEFAULT NULL,
                                 `country` VARCHAR(255) DEFAULT NULL,
                                 `is_deleted` TINYINT DEFAULT NULL,
                                 PRIMARY KEY (`id`)
                             );
