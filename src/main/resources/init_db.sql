CREATE DATABASE `taxi_service_db` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `taxi_service_db`.`manufacturers`
(
    `id`         INT          NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(255) NULL,
    `country`    VARCHAR(255) NULL,
    `is_deleted` TINYINT      NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);
