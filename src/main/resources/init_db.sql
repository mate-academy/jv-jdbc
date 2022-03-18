CREATE DATABASE `manufacture_db` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `manufacturer` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `name` varchar(45) COLLATE utf8_bin DEFAULT NULL,
                                `country` varchar(45) COLLATE utf8_bin DEFAULT NULL,
                                `is_deleted` tinyint NOT NULL DEFAULT '0',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;


