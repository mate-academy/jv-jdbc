CREATE USER 'taxiadmin'@'localhost' IDENTIFIED BY '12345678';
 
GRANT ALL PRIVILEGES ON * . * TO 'taxiadmin'@'localhost';

CREATE DATABASE taxi_db;

USE taxi_db;

CREATE Table Manufacturer(
    id          INT NOT NULL AUTO_INCREMENT,
    name        CHAR(30),
    country     CHAR(30),
    isdeleted   BOOLEAN,
    PRIMARY KEY(id)
);

CREATE TABLE Car(
    id          INT NOT NULL AUTO_INCREMENT,
    name        CHAR(30) NOT NULL,
    color       CHAR(30) NOT NULL,
    manufacturer_id INT NOT NULL,
    isdeleted   BOOLEAN,
    FOREIGN KEY (manufacturer_id) 
    REFERENCES manufacturer(id),
    PRIMARY KEY(id)
);

CREATE TABLE Driver(
    id          INT NOT NULL AUTO_INCREMENT,
    firstName   CHAR(30) NOT NULL,
    lastName    CHAR(30) NOT NULL,
    car_id      INT NOT NULL,
    isdeleted   BOOLEAN,
    FOREIGN KEY(car_id)
    REFERENCES car(id),
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


INSERT INTO car(name, color, manufacturer_id)
VALUES(
    "Prius", "White", 1),
    ("Model S", "Black", 2),
    ("Leganza", "Green", 3),
    ("Vantage", "Red", 4),
    ("X5", "Blue", 5),
    ("TT", "Black", 6),
    ("Seed", "Yellow", 7),
    ("Azure", "White", 8),
    ("Octavia", "Orange", 9),
    ("Passat B6", "Blue", 10
);


INSERT INTO driver(firstname, lastname, car_id)
VALUES(
    "Іщенко", "Валерій", 1),
    ("Минько", "Сергій", 2),
    ("Білоус", "Марина", 3),
    ("Шелест", "Володимир", 4),
    ("Кучеренко", "Олександр", 5),
    ("Смоляр", "Іван", 6),
    ("Козуб", "Андрій", 7),
    ("Крутько", "Владислав", 8),
    ("Рябокінь", "Павло", 9),
    ("Ткачук", "Анатолій", 10
);
