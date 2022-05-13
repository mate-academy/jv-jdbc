CREATE DATABASE `manufacturers` /*!40100 DEFAULT CHARACTER SET utf8mb3 COLLATE utf8_bin */ /*!80016 DEFAULT ENCRYPTION='N' */;
CREATE TABLE `name` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `name` varchar(100) COLLATE utf8_bin NOT NULL,
                        `country` varchar(45) COLLATE utf8_bin NOT NULL,
                        `is_deleted` tinyint NOT NULL DEFAULT '0',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
