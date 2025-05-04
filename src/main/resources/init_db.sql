CREATE DATABASE `taxi_service` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;

 CREATE TABLE `manufacturers` (
   `id` bigint NOT NULL AUTO_INCREMENT,
   `name` varchar(45) NOT NULL,
   `country` varchar(45) DEFAULT NULL,
   `is_deleted` varchar(45) DEFAULT 'false',
   PRIMARY KEY (`id`,`name`)
 ) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3;
