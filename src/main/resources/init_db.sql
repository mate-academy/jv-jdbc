CREATE TABLE `taxi_service_db`.`manufacturers` (
`id` bigint NOT NULL AUTO_INCREMENT,
`name` VARCHAR(255) NULL,
`country` VARCHAR(255) NULL,
`is_deleted` tinyint(1) DEFAULT '0',
PRIMARY KEY (`id`));
