CREATE DATABASE `taxi_service_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
CREATE TABLE `manufacturers` (
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb3;

/*Init table*/
UPDATE `taxi_service_db`.`manufacturers` SET `name` = 'Ford', `country` = 'USA' WHERE (`id` = '1');
UPDATE `taxi_service_db`.`manufacturers` SET `name` = 'Tavria', `country` = 'Ukraine' WHERE (`id` = '2');
UPDATE `taxi_service_db`.`manufacturers` SET `name` = 'Fiat', `country` = 'Italy' WHERE (`id` = '3');
UPDATE `taxi_service_db`.`manufacturers` SET `name` = 'AstonMartin', `country` = 'Great Britain' WHERE (`id` = '4');
UPDATE `taxi_service_db`.`manufacturers` SET `name` = 'Renault', `country` = 'France' WHERE (`id` = '5');
UPDATE `taxi_service_db`.`manufacturers` SET `name` = 'Ferrari', `country` = 'Italy' WHERE (`id` = '6');
UPDATE `taxi_service_db`.`manufacturers` SET `name` = 'Lada', `country` = 'Russia' WHERE (`id` = '10');
UPDATE `taxi_service_db`.`manufacturers` SET `name` = 'Volvo', `country` = 'Sweden' WHERE (`id` = '7');
UPDATE `taxi_service_db`.`manufacturers` SET `name` = 'Saab', `country` = 'Sweden' WHERE (`id` = '8');
UPDATE `taxi_service_db`.`manufacturers` SET `name` = 'Tesla', `country` = 'USA' WHERE (`id` = '9');

