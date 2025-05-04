CREATE
DATABASE IF NOT EXISTS `taxi_db` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE IF NOT EXISTS `manufacturers`
(
    `id`         bigint      NOT NULL,
    `name`       varchar(45) NOT NULL,
    `country`    varchar(45) NOT NULL,
    `is_deleted` tinyint     NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

