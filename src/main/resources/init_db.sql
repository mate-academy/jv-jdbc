CREATE SCHEMA `db_manufacturers` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
CREATE TABLE `db_manufacturers`.`manufactures` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `country` VARCHAR(45) NULL,
  `is_deleted` TINYINT NOT NULL,
  PRIMARY KEY (`id`));
