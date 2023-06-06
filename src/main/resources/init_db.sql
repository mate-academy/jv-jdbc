CREATE SCHEMA `taxi_service_db` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `taxi_service_db`.`manufacturers` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT 'null',
  `country` VARCHAR(255) NULL DEFAULT 'null',
  `is_deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
