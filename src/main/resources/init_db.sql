CREATE TABLE `manufacturers` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `country` varchar(255) DEFAULT NULL,
    `is_deleted` tinyint DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

INSERT INTO `manufacturers` (`id`, `name`, `country`, `is_deleted`) VALUES
(1, 'ZAZ', 'Ukraine', 1),
(2, 'Skoda', 'Czech Republic', 0),
(3, 'BMW', 'Germany', 0),
(4, 'Cadillac', 'USA', 0),
(5, 'Kraz', 'Ukraine', 0),
(6, 'LAZ', 'Ukraine', 1);