CREATE SCHEMA `taxi_service_db` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `manufacturers` (
  `id` bigint NOT NULL,
  `name` varchar(150) DEFAULT NULL,
  `country` varchar(150) DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
