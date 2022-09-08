CREATE SCHEMA `taxi_service_db` DEFAULT CHARACTER SET utf8 ;
USE `taxi_service_db`;

CREATE TABLE `taxi_service_db`.`manufacturers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  `is_deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
