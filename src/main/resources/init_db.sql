CREATE SCHEMA `taxi_db`

CREATE TABLE `manufacturers` (
                                `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                `name` VARCHAR(45) NOT NULL,
                                `country` VARCHAR(45) NOT NULL,
                                `is_deleted` TINYINT NOT NULL DEFAULT 0,
                                PRIMARY KEY (`id`)
);
