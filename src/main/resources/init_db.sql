CREATE TABLE `taxi_service_db`.`manufacturers` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT 'null',
  `country` VARCHAR(45) NULL DEFAULT 'null',
  `is_deleted` TINYINT NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;