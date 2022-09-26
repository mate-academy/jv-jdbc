CREATE TABLE `manufacturer_format` (
                                       `id` bigint NOT NULL AUTO_INCREMENT,
                                       `Name` varchar(45) DEFAULT NULL,
                                       `Country` varchar(45) DEFAULT NULL,
                                       `is_deleted` tinyint NOT NULL DEFAULT '0',
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
