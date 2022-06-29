CREATE SCHEMA `taxi_db` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `taxi_db`.`manufacturers` (
  `id` BIGINT(10) NOT NULL AUTO_INCREMENT,
  `make` VARCHAR(45) NULL,
  `country` VARCHAR(45) NULL,
  `is_deleted` TINYINT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

INSERT INTO `taxi_db`.`manufacturers` (`make`, `country`, `is_deleted`) VALUES ('Toyota', 'Japan', '0');
INSERT INTO `taxi_db`.`manufacturers` (`make`, `country`, `is_deleted`) VALUES ('Ford', 'USA', '0');
