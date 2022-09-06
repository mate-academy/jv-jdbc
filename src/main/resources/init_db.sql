CREATE SCHEMA `taxi_service` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `taxi_service`.`manufacturers` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`));

ALTER TABLE `taxi_service`.`manufacturers`
ADD COLUMN `name` VARCHAR(255) NULL AFTER `id`;

ALTER TABLE `taxi_service`.`manufacturers`
ADD COLUMN `country` VARCHAR(255) NULL AFTER `name`;

ALTER TABLE `taxi_service`.`manufacturers`
ADD COLUMN `is_deleted` TINYINT NOT NULL DEFAULT 0 AFTER `country`;

ALTER TABLE `taxi_service`.`manufacturers`
CHANGE COLUMN `name` `name` VARCHAR(255) NOT NULL ,
CHANGE COLUMN `country` `country` VARCHAR(255) NOT NULL ;
