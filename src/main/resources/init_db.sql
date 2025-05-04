CREATE
DATABASE `taxi_service` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `taxi_service`;
CREATE TABLE `manufactures`
(
    `id`         bigint  NOT NULL AUTO_INCREMENT,
    `name`       varchar(45)      DEFAULT NULL,
    `country`    varchar(45)      DEFAULT NULL,
    `is_deleted` tinyint NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
