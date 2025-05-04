CREATE DATABASE `taxi_db` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE SCHEMA `taxi_db` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `taxi_db`.`manufacturers` (
                                           `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                           `name` VARCHAR(255) NULL,
                                           `country` VARCHAR(255) NULL,
                                           `is_deleted` TINYINT NOT NULL DEFAULT 0,
                                           PRIMARY KEY (`id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

INSERT INTO `taxi_db`.`manufacturers` (`name`, `country`) VALUES ('Igor', 'Ukraine');
INSERT INTO `taxi_db`.`manufacturers` (`name`, `country`) VALUES ('Alex', 'Ukraine');
INSERT INTO `taxi_db`.`manufacturers` (`name`, `country`) VALUES ('Antony', 'USA');
INSERT INTO `taxi_db`.`manufacturers` (`name`, `country`) VALUES ('Mike','USA');
INSERT INTO `taxi_db`.`manufacturers` (`name`, `country`) VALUES ('Jim', 'USA');
