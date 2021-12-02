create table manufacturers_schema.manufacturers
(
    id         bigint auto_increment
        primary key,
    name       varchar(255)               null,
    country    varchar(255)               null,
    is_deleted varchar(6) default 'FALSE' not null
);