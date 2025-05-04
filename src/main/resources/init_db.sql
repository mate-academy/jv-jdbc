CREATE SCHEMA `taxi_service` DEFAULT CHARACTER SET utf8;
CREATE TABLE `taxi_service`.`manufacturers` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`));
ALTER TABLE `taxi_service`.`manufacturers`
ADD COLUMN `name` VARCHAR(100) NOT NULL AFTER `id`,
ADD COLUMN `country` VARCHAR(100) NULL AFTER `name`,
ADD COLUMN `is_deleted` TINYINT NULL DEFAULT 0 AFTER `country`;


INSERT INTO `taxi_service`.`manufacturers` (`name`, `country`) VALUES ('Toyota', 'Japan'),
 ('Hyundai', 'South Korea'), ('Volkswagen Group', 'Germany'), ('General Motors', 'United States'),
 ('Ford', 'United States'), ('Nissan', 'Japan'), ('Fiat Chrysler Automobiles', 'Italy/United States'),
 ('Renault', 'France'), ('PSA Group', 'France'), ('Suzuki', 'Japan'), ('SAIC', 'China'),
 ('Daimler', 'Germany'), ('BMW', 'Germany'), ('Geely', 'China');