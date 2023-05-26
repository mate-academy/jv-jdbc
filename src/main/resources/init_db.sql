-- phpMyAdmin SQL Dump
-- version 5.1.1deb5ubuntu1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 24, 2023 at 11:21 AM
-- Server version: 8.0.33-0ubuntu0.22.04.2
-- PHP Version: 8.1.2-1ubuntu2.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `taxi_service`
--
CREATE DATABASE IF NOT EXISTS `taxi_service` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `taxi_service`;

-- --------------------------------------------------------

--
-- Table structure for table `manufacturer`
--

CREATE TABLE IF NOT EXISTS `manufacturers` (
  `id` bigint(11) NOT NULL,
  `name` varchar(225) COLLATE utf8mb4_unicode_ci NOT NULL,
  `country` varchar(225) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE `manufacturers`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `manufacturers`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;
COMMIT;

CREATE TABLE IF NOT EXISTS `drivers` (
        `id` BIGINT(11) NULL DEFAULT NULL,
        `name` VARCHAR(225) NULL DEFAULT NULL,
        `license_number` VARCHAR(225) NULL DEFAULT NULL,
        `is_deleted` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE='utf8mb4_unicode_ci';

ALTER TABLE `drivers`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `drivers`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

CREATE USER 'taxi_service'@'%' IDENTIFIED BY 'jXmXIUZ@t66.cq7U';
GRANT USAGE ON *.* TO 'taxi_service'@'%';
GRANT EXECUTE, SELECT, SHOW VIEW, ALTER, ALTER ROUTINE, CREATE, CREATE ROUTINE, CREATE TEMPORARY TABLES, CREATE VIEW, DELETE, DROP, EVENT, INDEX, INSERT, REFERENCES, TRIGGER, UPDATE, LOCK TABLES  ON `taxi\_service`.* TO 'taxiservice'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;
