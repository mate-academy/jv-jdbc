CREATE DATABASE `manufacturer_db`;
CREATE TABLE `manufacturer_db`.`manufacturers` (
                                              `id` BIGINT NOT NULL,
                                              `name` VARCHAR(45) NULL,
                                              `country` VARCHAR(45) NULL,
                                              `is_deleted` TINYINT NOT NULL DEFAULT 0,
                                              PRIMARY KEY (`id`));
