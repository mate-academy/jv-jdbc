
CREATE SCHEMA `taxi_service`;

use taxi_service;

create table Manufacturer
(
    id        int auto_increment,
    name      varchar(255) not null,
    country   varchar(255) not null,
    id_delete boolean,
    primary key (id)
);