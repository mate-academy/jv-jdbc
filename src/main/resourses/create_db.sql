CREATE SCHEMA `manufacturers_db` DEFAULT CHARACTER SET UTF8MB4 ;

DROP TABLE IF EXISTS `manufacturers_db`.manufacturers;

CREATE TABLE `manufacturers_db`.`manufacturers` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `country` VARCHAR(255) NOT NULL,
  `is_deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`));
