create schema taxi_service collate utf8_general_ci;

create table manufacturers
(
    id         bigint auto_increment
        primary key,
    name       varchar(255)      not null,
    country    varchar(255)      not null,
    is_deleted tinyint default 0 not null
);

