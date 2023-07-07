CREATE DATABASE `libray_db` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `manufacturer` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `name` varchar(45) DEFAULT NULL,
                                `country` varchar(45) DEFAULT NULL,
                                `is_deleted` tinyint DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

