CREATE SCHEMA `taxi_db` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `taxi_db`.`manufacturers` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `country` VARCHAR(255) NULL DEFAULT NULL,
  `is_deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`));

INSERT INTO `taxi_db`.`manufacturers` (`name`, `country`) VALUES ('BMW', 'Germany');
INSERT INTO `taxi_db`.`manufacturers` (`name`, `country`) VALUES ('Peugeot', 'France');