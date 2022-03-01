CREATE DATABASE `taxi_db` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `taxi_db`.`manufacturers` (
                                            `id` INT NOT NULL AUTO_INCREMENT,
                                            `name` VARCHAR(255) NULL,
                                            `country` VARCHAR(255) NULL,
                                            `is_deleted` TINYINT NULL DEFAULT 0,
                                            PRIMARY KEY (`id`),
                                            UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE
                                        );
