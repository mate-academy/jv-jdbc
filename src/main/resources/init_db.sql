CREATE DATABASE taxi_service;
USE taxi_service;
CREATE TABLE manufacturer (
    id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(40),
    country VARCHAR(50),
    is_deleted BOOLEAN DEFAULT false
);