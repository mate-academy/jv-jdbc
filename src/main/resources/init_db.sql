CREATE SCHEMA `manufacturer_db` DEFAULT CHARACTER SET utf8 ;
USE 'manufacturer_db';

CREATE TABLE IF NOT EXISTS `manufacturers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `is_deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
