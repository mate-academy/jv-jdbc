-- CREATE DATABASE taxi
--     WITH
--     OWNER = postgres
--     ENCODING = 'UTF8'
--     CONNECTION LIMIT = -1;

DROP TABLE IF EXISTS cars;
DROP TABLE IF EXISTS drivers;
DROP TABLE IF EXISTS manufacturers;

CREATE TABLE manufacturers
(
    id        SERIAL,
    name      VARCHAR(100),
    country   VARCHAR(100),
    is_delete boolean,
    PRIMARY KEY (id)
);

ALTER TABLE manufacturers
    OWNER to postgres;

CREATE TABLE drivers
(
    id         SERIAL NOT NULL,
    first_name VARCHAR(35) NOT NULL,
    last_name  VARCHAR(35) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE drivers
    OWNER to postgres;

CREATE TABLE cars
(
    id              SERIAL NOT NULL,
    name            VARCHAR(35) NOT NULL,
    color           VARCHAR(35),
    manufacturer_id BIGINT NOT NULL,
    driver_id       BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (manufacturer_id) REFERENCES manufacturers (id),
    FOREIGN KEY (driver_id) REFERENCES cars (id)
);

ALTER TABLE cars
    OWNER to postgres;
