CREATE SCHEMA `manufacturers_db` DEFAULT CHARACTER SET utf32 ;

CREATE TABLE `manufacturers_db`.`new_table` (
  `id` BIGINT(125) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `country` VARCHAR(45) NULL,
  `is_deleted` TINYINT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;