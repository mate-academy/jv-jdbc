CREATE
DATABASE `taxi_service_db` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
CREATE TABLE `manufacturers`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `name`       varchar(255) DEFAULT NULL,
    `country`    varchar(255) DEFAULT NULL,
    `is_deleted` tinyint      DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
