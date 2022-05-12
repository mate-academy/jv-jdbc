CREATE SCHEMA `taxi_service_schema` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `taxi_service_schema`.`manufacturers` (
                                                       `id` BIGINT(10) NOT NULL,
                                                       `name` VARCHAR(255) NULL,
                                                       `country` VARCHAR(255) NULL,
                                                       `is_deleted` TINYINT NOT NULL DEFAULT 0,
                                                       PRIMARY KEY (`id`));
