CREATE SCHEMA `manufacturers_db` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `manufacturers_db`.`manufacturers` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `country` VARCHAR(50) NOT NULL,
  `is_deleted` TINYINT NULL DEFAULT '0',
  PRIMARY KEY (`id`));
