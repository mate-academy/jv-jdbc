-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               8.0.31 - MySQL Community Server - GPL
-- Операционная система:         Win64
-- HeidiSQL Версия:              12.2.0.6576
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Дамп структуры базы данных taxi_service
CREATE DATABASE IF NOT EXISTS `taxi_service` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `taxi_service`;

-- Дамп структуры для таблица taxi_service.drivers
DROP TABLE IF EXISTS `drivers`;
CREATE TABLE IF NOT EXISTS `drivers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `license_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Дамп данных таблицы taxi_service.drivers: ~0 rows (приблизительно)

-- Дамп структуры для таблица taxi_service.manufacturers
DROP TABLE IF EXISTS `manufacturers`;
CREATE TABLE IF NOT EXISTS `manufacturers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `country` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Дамп данных таблицы taxi_service.manufacturers: ~0 rows (приблизительно)
INSERT INTO `manufacturers` (`id`, `name`, `country`, `is_deleted`) VALUES
	(1, 'Chrysler', '🇺🇸USA', 0),
	(2, 'Dodge', '🇺🇸USA', 0),
	(3, 'Jeep', '🇺🇸USA', 0),
	(4, 'Suzuki', '🇯🇵JAPAN', 0),
	(5, 'Audi', '🇩🇪Germany', 0);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
