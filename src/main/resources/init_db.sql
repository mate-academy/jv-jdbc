CREATE DATABASE IF NOT EXISTS `taxi_service` DEFAULT CHARACTER SET UTF8MB4  DEFAULT ENCRYPTION='N';
CREATE TABLE IF NOT EXISTS taxi_service.cars (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `model` varchar(255) DEFAULT NULL,
                        `manufacturer` bigint DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;
CREATE TABLE IF NOT EXISTS taxi_service.manufacturers (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `name` varchar(255) DEFAULT NULL,
                                 `country` varchar(255) DEFAULT NULL,
                                 `is_deleted` tinyint NOT NULL DEFAULT '0',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=UTF8MB4;
CREATE TABLE IF NOT EXISTS taxi_service.drivers (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `first_name` varchar(255) DEFAULT NULL,
                           `last_name` varchar(255) DEFAULT NULL,
                           `car` bigint DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;


