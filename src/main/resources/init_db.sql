CREATE SCHEMA `taxi_service`;

CREATE TABLE `taxi_service`.`manufacturers` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `country` VARCHAR(45) NULL,
  `is_deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`));

