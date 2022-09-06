CREATE SCHEMA `taxi_service` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `taxi_service`.`manufacturers` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`));

ALTER TABLE `taxi_service`.`manufacturers`
ADD COLUMN `name` VARCHAR(255) NULL AFTER `id`;

ALTER TABLE `taxi_service`.`manufacturers`
ADD COLUMN `country` VARCHAR(255) NULL AFTER `name`;
