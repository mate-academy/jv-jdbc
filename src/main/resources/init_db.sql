CREATE
DATABASE `main_schema` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `manufacturers`
(
    `id`         int unsigned NOT NULL AUTO_INCREMENT,
    `name`       varchar(255) DEFAULT NULL,
    `country`    varchar(255) DEFAULT NULL,
    `is_deleted` tinyint      DEFAULT '0',
    PRIMARY KEY (`id`),
    UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
