CREATE DATABASE `manufacturer_db` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `manufacturers` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
    `country` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
    `is_deleted` tinyint DEFAULT '0',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
