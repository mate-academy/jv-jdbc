CREATE DATABASE `library_db` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `manufacturers` (
  `name` varchar(111) DEFAULT NULL,
  `country` varchar(111) DEFAULT NULL,
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
