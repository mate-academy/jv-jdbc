CREATE SCHEMA `taxi_db` DEFAULT CHARACTER SET utf8;

CREATE TABLE `taxi_db`.` manufacturers`
(
    `id`         BIGINT(8) NOT NULL,
    `name`       VARCHAR(45) NOT NULL,
    `country`    VARCHAR(45) NOT NULL,
    `is_deleted` TINYINT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

ALTER TABLE `taxi_db`.`manufacturers`
    CHANGE COLUMN `id` `id` BIGINT NOT NULL AUTO_INCREMENT ;