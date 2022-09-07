CREATE DATABASE `database` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `manufacturers` (
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `name`       varchar(45) DEFAULT NULL,
    `country`    varchar(45) DEFAULT NULL,
    `is_deleted` tinyint(1)  DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 19
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
