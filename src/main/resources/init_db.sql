create table manufacturers_schema.manufacturers
(
    id         int               not null,
    name       varchar(255)      null,
    country    varchar(255)      null,
    is_deleted tinyint default 0 not null,
);