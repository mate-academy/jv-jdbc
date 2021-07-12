CREATE DATABASE `manufacturers_db` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
CREATE TABLE `manufacturers` (
                                 `manufacturer_id` bigint NOT NULL AUTO_INCREMENT,
                                 `manufacturer_name` varchar(255) DEFAULT NULL,
                                 `manufacturer_country` varchar(255) DEFAULT NULL,
                                 `is_deleted` tinyint DEFAULT '0',
                                 PRIMARY KEY (`manufacturer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3;

