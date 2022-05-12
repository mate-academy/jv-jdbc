CREATE SCHEMA `taxi_db` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `taxi_db`.`manufacturers` (
  `id` BIGINT(11) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `country` VARCHAR(255) NOT NULL,
  `is_delete` TINYINT NOT NULL,
  PRIMARY KEY (`id`));
