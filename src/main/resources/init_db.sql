CREATE DATABASE `manufacturer` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
CREATE TABLE `car` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `country` varchar(255) NOT NULL,
    `is_deleted` tinyint NOT NULL DEFAULT '0',
     PRIMARY KEY (`id`,`name`,`country`),
     UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;
