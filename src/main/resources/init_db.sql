CREATE SCHEMA `taxi_db` DEFAULT CHARACTER SET utf8 ;

/* create table manufacturers */
CREATE TABLE `taxi_db`.`manufacturers` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `is_deleted` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);
ALTER TABLE `taxi_db`.`manufacturers`
  ADD COLUMN `country` VARCHAR(45) NOT NULL AFTER `name`;
ALTER TABLE `taxi_db`.`manufacturers`
CHANGE COLUMN `is_deleted` `is_deleted` TINYINT NOT NULL DEFAULT 0 ;

