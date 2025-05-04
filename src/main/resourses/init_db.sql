create table manufacturers
(
    id bigint auto_increment,
    name varchar(70) not null,
    country varchar(70) not null,
    is_deleted boolean default false not null,
    constraint manufacturers_pk
        primary key (id)
);

