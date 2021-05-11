CREATE DATABASE `manufacturerDB` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `manufacturers` (
                                 `manufacturer_id` int NOT NULL AUTO_INCREMENT,
                                 `manufacturer_name` varchar(50) DEFAULT NULL,
                                 `manufacturer_country` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                 `is_deleted` tinyint NOT NULL DEFAULT '0',
                                 PRIMARY KEY (`manufacturer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb3;

