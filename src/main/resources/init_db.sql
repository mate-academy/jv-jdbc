CREATE SCHEMA `manufacturers_scheme` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `manufacturers_scheme`.`manufacturers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  `country` VARCHAR(255) NULL,
  `is_deleted` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`));
