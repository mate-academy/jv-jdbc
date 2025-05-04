CREATE TABLE `manufacturers`.`manufacturers` (
  `id` BIGINT(55) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `country` VARCHAR(45) NULL,
  `is_deleted` TINYINT(0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`));
