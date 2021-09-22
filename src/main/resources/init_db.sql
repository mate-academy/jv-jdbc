CREATE TABLE `manufacturers` (
    `id` int NOT NULL,
    `name` varchar(45) DEFAULT NULL,
    `country` varchar(45) DEFAULT NULL,
    `is_deleted` tinyint DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3
ALTER TABLE `library_db`.`manufacturers`
CHANGE COLUMN `id` `id` INT NOT NULL AUTO_INCREMENT ;