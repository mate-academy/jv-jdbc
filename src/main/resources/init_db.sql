CREATE DATABASE `company_db` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `manufacturers` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `name` varchar(255) NOT NULL,
                                 `country` varchar(255) DEFAULT NULL,
                                 `is_deleted` tinyint NOT NULL DEFAULT '0',
                                 PRIMARY KEY (`id`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;

INSERT INTO `company_db`.`manufacturers` (`name`, `country`) VALUES ('Samsung', 'South Korea');
INSERT INTO `company_db`.`manufacturers` (`name`, `country`) VALUES ('Sony', 'Japan');
INSERT INTO `company_db`.`manufacturers` (`name`, `country`) VALUES ('OnePlus', 'China');
INSERT INTO `company_db`.`manufacturers` (`name`, `country`) VALUES ('Nokia', 'Finland');
INSERT INTO `company_db`.`manufacturers` (`name`, `country`) VALUES ('BlackBarry', 'Canada');
INSERT INTO `company_db`.`manufacturers` (`name`, `country`) VALUES ('Google', 'USA');
INSERT INTO `company_db`.`manufacturers` (`name`, `country`) VALUES ('Xiaomi', 'China');
INSERT INTO `company_db`.`manufacturers` (`name`, `country`) VALUES ('Apple', 'USA');
INSERT INTO `company_db`.`manufacturers` (`name`, `country`) VALUES ('Huawei', 'China');