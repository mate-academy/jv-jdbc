CREATE SCHEMA `taxi_db` DEFAULT CHARACTER SET utf8 ;

/* create table drivers */
CREATE TABLE `taxi_db`.`drivers` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `is_deleted` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_drivers_UNIQUE` (`id` ASC) VISIBLE);

/* create table manufacturers */
CREATE TABLE `taxi_db`.`manufacturers` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `is_deleted` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);

/* create table cars */
  CREATE TABLE `taxi_db`.`cars` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `driver_id` BIGINT NULL,
    `manufacturer_id` BIGINT NULL,
    `is_deleted` TINYINT NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);

    ALTER TABLE `taxi_db`.`cars`
    ADD INDEX `manufacturer_id_idx` (`manufacturer_id` ASC) VISIBLE,
    ADD INDEX `driver_id_idx` (`driver_id` ASC) VISIBLE;
    ;

/* Create relations */
    ALTER TABLE `taxi_db`.`cars`
    ADD CONSTRAINT `driver_id`
      FOREIGN KEY (`driver_id`)
      REFERENCES `taxi_db`.`drivers` (`id`)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
    ADD CONSTRAINT `manufacturer_id`
      FOREIGN KEY (`manufacturer_id`)
      REFERENCES `taxi_db`.`manufacturers` (`id`)
      ON DELETE CASCADE
      ON UPDATE CASCADE;