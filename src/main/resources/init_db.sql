CREATE DATABASE `taxi_service` /*!40100 DEFAULT CHARACTER SET utf8 */;
CREATE TABLE `literary_formats` (
  `format` varchar(255) DEFAULT NULL,
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
