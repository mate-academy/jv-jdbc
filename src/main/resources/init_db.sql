CREATE TABLE `taxi_db`.`manufacturers` (
                                           `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                           `name` VARCHAR(255) NULL,
                                           `country` VARCHAR(255) NULL,
                                           `is_deleted` TINYINT NULL DEFAULT 0,
                                           PRIMARY KEY (`id`));