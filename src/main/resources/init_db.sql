CREATE DATABASE `taxi_service_db` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
use `taxi_service_db`;
CREATE TABLE `manufacturer` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `name` varchar(255) NOT NULL,
                                `country` varchar(255) NOT NULL,
                                `is_deleted` tinyint NOT NULL DEFAULT '0',
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;
INSERT INTO `manufacturer`
(`name`,
 `country`)
VALUES
('name1',	'country1'),
('name2',	'country2'),
('name3',	'country3');
