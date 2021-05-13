CREATE SCHEMA `hw_jdbc` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `hw_jdbc`.`manufactures` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(255) NULL DEFAULT NULL,
  `Country` VARCHAR(255) NULL DEFAULT NULL,
  `is_deleted` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;