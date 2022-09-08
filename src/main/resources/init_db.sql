CREATE SCHEMA `dbtaxi` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `manufacturers` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `name` varchar(128) NOT NULL,
                                 `country` varchar(128) NOT NULL,
                                 `is_deleted` tinyint NOT NULL DEFAULT '0',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb3;


