/*CREATE DATABASE postgres
WITH
     OWNER = postgres
     ENCODING = 'UTF8'
     CONNECTION LIMIT = -1
     IS_TEMPLATE = False;

      CREATE TABLE IF NOT EXISTS  public.manufacturers (
       `id` int NOT NULL AUTO_INCREMENT,
       `name` varchar(45) DEFAULT NULL,
       `country` varchar(45) DEFAULT NULL,
       `is_deleted` tinyint NOT NULL DEFAULT '0',
       PRIMARY KEY (`id`)
       )

        TABLESPACE pg_default;

        ALTER TABLE IF EXISTS public.manufacturers
             OWNER to postgres;*/
        CREATE SCHEMA IF NOT EXISTS `library_db` DEFAULT CHARACTER SET utf8;
        USE `taxi`;
        SET NAMES utf8mb4;