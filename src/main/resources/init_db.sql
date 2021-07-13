CREATE SCHEMA `test_db` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `manufacturers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `is_deleted` tinyint DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3;

INSERT INTO `test_db`.`manufacturers` (`name`, `country`) VALUES ('Continental', 'Germany');
INSERT INTO `test_db`.`manufacturers` (`name`, `country`) VALUES ('Michelin', 'France');
INSERT INTO `test_db`.`manufacturers` (`name`, `country`, `is_deleted`) VALUES ('Goodyear', 'USA', '1');
INSERT INTO `test_db`.`manufacturers` (`name`, `country`) VALUES ('Cooper', 'USA');
INSERT INTO `test_db`.`manufacturers` (`name`, `country`) VALUES ('Yokohama', 'Japan');

UPDATE manufacturers SET name = 'Basilius', country = 'Ukraine' WHERE id = 4 AND is_deleted = false;