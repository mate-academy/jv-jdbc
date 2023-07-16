# CREATE DATABASE `taxi_service_db` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */; # !!!ENCRYPTION ERROR!!!
CREATE DATABASE `taxi_service_db` DEFAULT CHARACTER SET utf8mb3;

CREATE TABLE `manufacturers`
(
    `id`         bigint       NOT NULL AUTO_INCREMENT,
    `name`       varchar(255) NOT NULL,
    `country`    varchar(255) NOT NULL,
    `is_deleted` tinyint      NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

INSERT INTO `taxi_service_db`.`manufacturers` (`name`, `country`)
VALUES ('Vagon', 'USA');
INSERT INTO `taxi_service_db`.`manufacturers` (`name`, `country`)
VALUES ('Pork', 'Ukraine');
INSERT INTO `taxi_service_db`.`manufacturers` (`name`, `country`)
VALUES ('Dover', 'USA');
