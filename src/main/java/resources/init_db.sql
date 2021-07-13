CREATE SCHEMA `manufacturers_db` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `manufacturers_db`.`manufacturers` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(225) NULL DEFAULT NULL,
  `country` VARCHAR(225) NULL DEFAULT NULL,
  `is_deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`));
  SET GLOBAL time_zone = '+3:00';