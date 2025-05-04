CREATE SCHEMA `taxi_db` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `taxi_db`.`manufacturers` (
  `id` BIGINT(10) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `country` VARCHAR(45) NULL,
  `is_deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

INSERT INTO `taxi_db`.`manufacturers` (`name`, `country`, `is_deleted`) VALUES ('Toyota', 'Japan', '0');
INSERT INTO `taxi_db`.`manufacturers` (`name`, `country`, `is_deleted`) VALUES ('Ford', 'USA', '0');
