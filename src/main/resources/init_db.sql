CREATE DATABASE `manufacture_db` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `manufacturers_db` (
                                    `id` int NOT NULL AUTO_INCREMENT,
                                    `name` varchar(255) DEFAULT NULL,
                                    `country` varchar(255) DEFAULT NULL,
                                    `is_deleted` varchar(45) NOT NULL DEFAULT '0',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
