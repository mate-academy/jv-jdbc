CREATE DATABASE `taxi_service_db` DEFAULT CHARACTER SET utf8  DEFAULT ENCRYPTION='N';
CREATE TABLE `manufacturers` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `name` varchar(255) DEFAULT NULL,
                                 `country` varchar(255) DEFAULT NULL,
                                 `is_deleted` tinyint DEFAULT '0',
                                 PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
