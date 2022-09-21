CREATE DATABASE `taxi_service_db` DEFAULT CHARACTER SET utf8mb4;
USE `taxi_service_db`;
CREATE TABLE manufacturer
(
    `id`         bigint  NOT NULL AUTO_INCREMENT,
    `name`       varchar(45)      DEFAULT NULL,
    `country`    varchar(45)      DEFAULT NULL,
    `is_deleted` tinyint NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
)