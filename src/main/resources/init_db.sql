CREATE SCHEMA `taxi_db` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `manufacturers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `is_deleted` tinyint DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3