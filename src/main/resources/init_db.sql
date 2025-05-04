CREATE TABLE `manufacturers` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `name` varchar(255) DEFAULT 'null',
                                 `country` varchar(255) DEFAULT 'null',
                                 `is_deleted` tinyint NOT NULL DEFAULT '0',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3
