CREATE SCHEMA `taxi_service_db` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `taxi_service_db`.`manufacturers` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  `country` VARCHAR(255) NULL,
  PRIMARY KEY (`id`));

ALTER TABLE `taxi_service_db`.`manufacturers`
ADD COLUMN `is_deleted` TINYINT NOT NULL DEFAULT 0 AFTER `country`;

