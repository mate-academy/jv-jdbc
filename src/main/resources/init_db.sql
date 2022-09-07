CREATE TABLE `database`.`test`(
    `id`         BIGINT NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(45) NULL,
    `country`    VARCHAR(45) NULL,
    `is_deleted` boolean NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);