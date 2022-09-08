CREATE SCHEMA `library_db` DEFAULT CHARACTER SET utf8;

CREATE TABLE `library_db`.`manufacturers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  `is_deleted` TINYINT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

ALTER TABLE `library_db`.`manufacturers`
CHANGE COLUMN `is_deleted` `is_deleted` TINYINT NOT NULL DEFAULT 0 ;
INSERT INTO `library_db`.`manufacturers` (`name`, `country`) VALUES ('toyota', 'japan');
INSERT INTO `library_db`.`manufacturers` (`name`, `country`) VALUES ('mersedess', 'germany');
INSERT INTO `library_db`.`manufacturers` (`name`, `country`) VALUES ('honda', 'japan');

