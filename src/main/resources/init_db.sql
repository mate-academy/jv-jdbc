CREATE TABLE `taxi_db`.`manufacturers` (
                                           `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                           `name` VARCHAR(255) NOT NULL,
                                           `country` VARCHAR(255) NOT NULL,
                                           `is_deleted` TINYINT NOT NULL DEFAULT 0,
                                           PRIMARY KEY (`id`));