CREATE DATABASE `taxi_service_db` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE SCHEMA `taxi_service_db` DEFAULT CHARACTER SET utf8 ;

SET GLOBAL time_zone = '+3:00'

CREATE TABLE `taxi_service_db`.`manufacturers` (
  `name` VARCHAR(255) NULL,
  `country` VARCHAR(255) NULL,
  `id` BIGINT(12) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`));

ALTER TABLE `taxi_service_db`.`manufacturers`
CHANGE COLUMN `id` `id` BIGINT(11) NOT NULL ;

ALTER TABLE `taxi_service_db`.`manufacturers`
ADD COLUMN `is_deleted` TINYINT NOT NULL DEFAULT 0 AFTER `id`;