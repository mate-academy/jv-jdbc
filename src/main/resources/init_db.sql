CREATE SCHEMA `taxi_service_db` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `taxi_service_db`.`manufacturer` (
                                                  `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                  `name` VARCHAR(255) NOT NULL,
                                                  `country` VARCHAR(255) NOT NULL,
                                                  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
                                                  PRIMARY KEY (`id`));
