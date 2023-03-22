CREATE DATABASE `taxi_service_database` /*!40100 DEFAULT CHARACTER SET utf8mb3 */
    /*!80016 DEFAULT CHARSET='N' */;

CREATE TABLE `taxi_service_database`.`manufacturer` (
            `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
            `name` VARCHAR(255) NULL,
            `country` VARCHAR(255) NULL,
            `is_deleted` TINYINT NOT NULL DEFAULT 0,
             PRIMARY KEY (`id`));
