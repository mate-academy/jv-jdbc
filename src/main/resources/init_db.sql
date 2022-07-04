CREATE DATABASE `taxi_db` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
CREATE TABLE `manufacturers` (
                                 `name` varchar(255) DEFAULT NULL,
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `is_deleted` tinyint NOT NULL DEFAULT '0',
                                 `country` varchar(255) DEFAULT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
