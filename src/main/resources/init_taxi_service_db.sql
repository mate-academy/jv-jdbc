CREATE TABLE `taxi_service_db`.`manufacturers` (
  `id` BIGINT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NULL,
  `is_deleted` TINYINT NOT NULL,
  PRIMARY KEY (`id`));