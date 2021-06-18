CREATE DATABASE `library_db` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
CREATE TABLE `library_db`.`manufacturers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NULL,
  `country` VARCHAR(30) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
ALTER TABLE `library_db`.`manufacturers`
CHANGE COLUMN `id` `id` BIGINT NOT NULL AUTO_INCREMENT ;
ALTER TABLE `library_db`.`manufacturers`
ADD COLUMN `is_deleted` TINYINT NULL AFTER `country`;
ALTER TABLE `library_db`.`manufacturers`
CHANGE COLUMN `is_deleted` `is_deleted` TINYINT NOT NULL DEFAULT 0 ;
