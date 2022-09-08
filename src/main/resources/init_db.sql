CREATE SCHEMA `dbtaxi` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `manufacturer` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `name` varchar(128) NOT NULL,
                                `country` varchar(128) NOT NULL,
                                `isdeleted` tinyint NOT NULL DEFAULT '0',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb3;

