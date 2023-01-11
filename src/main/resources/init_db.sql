CREATE DATABASE `taxi_db` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
  CREATE TABLE `manufacturers` (
    `name` varchar(45) DEFAULT NULL,
    `country` varchar(45) DEFAULT NULL,
    `id` int NOT NULL AUTO_INCREMENT,
    `is_deleted` tinyint NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

