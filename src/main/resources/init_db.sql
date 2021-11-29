CREATE DATABASE `taxi_db` DEFAULT CHARACTER SET utf8 DEFAULT ENCRYPTION='N';

CREATE TABLE `manufactures` (
                                    `id` bigint NOT NULL AUTO_INCREMENT,
                                    `name` VARCHAR(64) DEFAULT NULL,
                                    `country` VARCHAR(64) DEFAULT NULL,
                                    `is_deleted` tinyint NOT NULL DEFAULT 0,
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
