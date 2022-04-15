create DATABASE taxiService;
create table Manufacturers
(
    id         bigint auto_increment
        primary key,
    name       varchar(50)          null,
    country    varchar(50)          null,
    is_deleted tinyint(1) default 0 not null
);

