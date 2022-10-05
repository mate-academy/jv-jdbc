CREATE DATABASE `taxi_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `manufacturer` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `name` varchar(45) NOT NULL,
                                `country` varchar(45) NOT NULL,
                                `is_deleted` tinyint NOT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
