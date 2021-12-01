CREATE DATABASE `taxi_service_db` !40100 DEFAULT CHARACTER SET utf8 !80016 DEFAULT ENCRYPTION='N';
CREATE TABLE `manufacturers` (
                                 `name` varchar(255) DEFAULT NULL,
                                 `country` varchar(255) DEFAULT NULL,
                                 `id` bigint NOT NULL DEFAULT '0',
                                 `is_deleted` tinyint NOT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
