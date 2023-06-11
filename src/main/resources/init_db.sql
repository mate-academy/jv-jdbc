CREATE DATABASE `taxi_service`;

CREATE TABLE `taxi_service`.`manufacturers` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NULL,
    `country` VARCHAR(255) NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`))
        ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;