CREATE SCHEMA `manufacturers` DEFAULT CHARACTER SET utf8;

CREATE TABLE `manufacturers`.`manufacturers` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `name` varchar(45) NOT NULL,
                                 `country` varchar(45) NOT NULL,
                                 `is_deleted` tinyint(1) DEFAULT '0',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb3;

INSERT INTO `manufacturers`.`manufacturers` (`id`,`name`,`country`,`is_deleted`) VALUES (67,'Mercedes','Germany',0);
INSERT INTO `manufacturers`.`manufacturers` (`id`,`name`,`country`,`is_deleted`) VALUES (68,'Honda','Japan',0);
INSERT INTO `manufacturers`.`manufacturers` (`id`,`name`,`country`,`is_deleted`) VALUES (69,'Toyota','Japan',1);