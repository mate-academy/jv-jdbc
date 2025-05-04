CREATE USER 'taxiadmin'@'localhost' IDENTIFIED BY '12345678';
 
GRANT ALL PRIVILEGES ON * . * TO 'taxiadmin'@'localhost';

CREATE DATABASE taxi_db;

USE taxi_db;

CREATE TABLE Manufacturer(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    name        CHAR(30),
    country     CHAR(30),
    is_deleted  BOOLEAN DEFAULT FALSE,
    PRIMARY KEY(id)
);

INSERT INTO manufacturer(name, country) 
VALUES(
    "Toyota", "Japan"),
    ("Tesla", "USA"),
    ("Daewoo", "Korea"),
    ("Aston Martin", "Italy"),
    ("BMW", "Germany"),
    ("Audi", "Germany"),
    ("KIA", "Korea"),
    ("Bentley", "Great Britain"),
    ("Skoda", "Czech Republic"),
    ("Volkswagen", "Germany"
);
