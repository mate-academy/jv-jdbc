CREATE SCHEMA IF NOT EXISTS `taxi_db` DEFAULT CHARACTER SET utf8;
USE `taxi_db`;

CREATE TABLE `manufacturers` (
                                           `id` BIGINT NOT NULL AUTO_INCREMENT,
                                           `name` VARCHAR(255) NULL,
                                           `country` VARCHAR(255) NULL,
                                           `is_deleted` TINYINT NOT NULL DEFAULT 0,
                                           PRIMARY KEY (`id`));
