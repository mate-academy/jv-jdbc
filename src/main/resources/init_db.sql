CREATE SCHEMA `hw-jdbc` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `hw-jdbc`.`manufacturers` (
                                           `id` BIGINT NOT NULL AUTO_INCREMENT,
                                           `name` VARCHAR(45) NOT NULL,
                                           `country` VARCHAR(45) NOT NULL,
                                           `is_deleted` TINYINT NULL DEFAULT 0,
                                           PRIMARY KEY (`id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

INSERT INTO `hw-jdbc`.`manufacturers` (`name`, `country`) VALUES ('Li', 'China');
INSERT INTO `hw-jdbc`.`manufacturers` (`name`, `country`) VALUES ('Smith', 'Great Britain');
INSERT INTO `hw-jdbc`.`manufacturers` (`name`, `country`) VALUES ('Tompson', 'USA');
INSERT INTO `hw-jdbc`.`manufacturers` (`name`, `country`) VALUES ('Kovalchuk', 'Ukraine');
INSERT INTO `hw-jdbc`.`manufacturers` (`name`, `country`) VALUES ('Jobs', 'USA');
INSERT INTO `hw-jdbc`.`manufacturers` (`name`, `country`, 'is_deleted') VALUES ('Duda', 'Poland', '1');
