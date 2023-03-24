CREATE SCHEMA `taxi_service`;

CREATE TABLE `taxi_service`.`manufactures` (
`id` BIGINT(11) NOT NULL AUTO_INCREMENT,
`name` VARCHAR(255) NULL,
`country` VARCHAR(255) NULL,
`is_deleted` TINYINT NOT NULL DEFAULT 0,
PRIMARY KEY (`id`));
