CREATE DATABASE taxi_service;

CREATE TABLE `taxi_service`.`manufacturer` (
`id` BIGINT NOT NULL AUTO_INCREMENT ,
`name` TEXT NOT NULL ,
`country` TEXT NOT NULL ,
`is_deleted` BOOLEAN NOT NULL DEFAULT FALSE ,
PRIMARY KEY (`id`)) ENGINE = InnoDB;
