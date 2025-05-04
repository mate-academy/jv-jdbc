CREATE DATABASE `manufacturers`;
CREATE TABLE `name` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `name` varchar(100) COLLATE utf8_bin NOT NULL,
                        `country` varchar(45) COLLATE utf8_bin NOT NULL,
                        `is_deleted` tinyint NOT NULL DEFAULT '0',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
