CREATE DATABASE `taxi_service` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `manufacturer` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `name` varchar(255) DEFAULT NULL,
                                `country` varchar(255) DEFAULT NULL,
                                `is_deleted` tinyint(1) DEFAULT '0',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

