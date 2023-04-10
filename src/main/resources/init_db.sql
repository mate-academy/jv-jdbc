CREATE SCHEMA `taxi_service_db` DEFAULT CHARACTER SET utf8;

CREATE TABLE `manufacturers`
(
    `id`         BIGINT  NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(100)     DEFAULT NULL,
    `country`    VARCHAR(100)     DEFAULT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
