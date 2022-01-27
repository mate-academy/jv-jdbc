CREATE DATABASE `mate_jdbc`;

CREATE TABLE `mate_jdbc`.`manufacturers` (
   `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
   `name` VARCHAR(45) NOT NULL,
   `country` VARCHAR(45) NOT NULL,
   `is_deleted` TINYINT NOT NULL DEFAULT 0,
   PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;
