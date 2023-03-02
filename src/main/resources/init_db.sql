CREATE DATABASE IF NOT EXISTS `mate`;
USE mate;
CREATE TABLE `manufacturers`
(
    `id`         bigint      NOT NULL AUTO_INCREMENT,
    `name`       varchar(45) NOT NULL,
    `country`    varchar(45) NOT NULL,
    `is_deleted` tinyint     NOT NULL,
    PRIMARY KEY (`id`)
); 