CREATE DATABASE `taxi_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;


CREATE TABLE `manufacturers` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `name` varchar(255) NOT NULL,
                                 `country` varchar(255) DEFAULT NULL,
                                 `is_deleted` tinyint DEFAULT NULL,
                                 PRIMARY KEY (`id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
