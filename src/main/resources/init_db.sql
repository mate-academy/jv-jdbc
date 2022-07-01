CREATE TABLE `taxi_db`.`manufacturers` (
                                           `id` INT NOT NULL AUTO_INCREMENT,
                                           `name` VARCHAR(255) NULL,
                                           `country` VARCHAR(255) NULL,
                                           `is_deleted` VARCHAR(45) NOT NULL DEFAULT 0,
                                           PRIMARY KEY (`id`));
ALTER TABLE `taxi_db`.`manufacturers`
    CHANGE COLUMN `is_deleted` `is_deleted` TINYINT NOT NULL DEFAULT '0' ;
