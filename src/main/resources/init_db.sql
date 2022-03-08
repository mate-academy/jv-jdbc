CREATE DATABASE `taxi_service` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT COLLATE='N' */;
CREATE TABLE `manufacturers` (
                                 `id` bigint NOT NULL,
                                 `name` varchar(255) DEFAULT NULL,
                                 `country` varchar(255) DEFAULT NULL,
                                 `is_deleted` tinyint NOT NULL DEFAULT '0',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
ALTER TABLE `taxi_service`.`manufacturers`
    CHANGE COLUMN `id` `id` BIGINT NOT NULL AUTO_INCREMENT ;