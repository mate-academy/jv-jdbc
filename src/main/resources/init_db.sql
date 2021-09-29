CREATE DATABASE `taxi_service` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
CREATE TABLE `manufacturers` (
                                 `manufacturers_id` bigint NOT NULL AUTO_INCREMENT,
                                 `manufacturers_name` varchar(255) NOT NULL,
                                 `manufacturers_country` varchar(255) NOT NULL,
                                 `is_deleted` tinyint NOT NULL DEFAULT '0',
                                 PRIMARY KEY (`manufacturers_id`),
                                 UNIQUE KEY `manufacturers_id_UNIQUE` (`manufacturers_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
