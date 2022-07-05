create schema taxi_service collate utf8_general_ci;

create table manufacturers
(
    id bigint auto_increment primary key,
    name varchar(255) null,
    country varchar(255) null,
    is_deleted tinyint(1) default 0 not null
);
