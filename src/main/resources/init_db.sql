CREATE DATABASE 'taxi_db' DEFAULT CHARACTER SET utf8 DEFAULT ENCRYPTION= 'N';

create table manufactures
(
    id int auto_increment
        primary key,
    name varchar(255) null,
    country varchar(255) null,
    is_deleted bigint null
);

