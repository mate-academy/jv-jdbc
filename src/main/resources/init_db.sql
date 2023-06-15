CREATE TABLE `taxi_service_db`.`manufacturers` (
  `id` BIGINT(255) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `country` VARCHAR(45) NULL DEFAULT NULL,
  `is_deleted` TINYINT(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`));