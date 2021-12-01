CREATE DATABASE `taxi_service` DEFAULT CHARACTER SET utf8 DEFAULT ENCRYPTION='N';

CREATE TABLE `manufacturers` (
    id         bigint auto_increment
        primary key,
    name       varchar(255)      not null,
    country    varchar(255)      not null,
    is_deleted tinyint default 0 not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

