CREATE DATABASE taxi_service_db CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE taxi_service_db;

CREATE TABLE manufacturers (
    'id' bigint NOT NULL AUTO_INCREMENT,
    'name' varchar(100) DEFAULT NULL,
    'country' varchar(100) DEFAULT NULL,
    'is_deleted' tinyint NOT NULL DEFAULT '0',
    PRIMARY KEY ('id')
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
