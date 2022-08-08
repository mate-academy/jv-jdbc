# CREATE SCHEMA taxi_db;

DROP TABLE IF EXISTS manufacturers CASCADE;
# DROP TABLE IF EXISTS cars CASCADE;
# DROP TABLE IF EXISTS drivers CASCADE;

CREATE TABLE manufacturers
(
id SERIAL PRIMARY KEY,
name VARCHAR(30),
country VARCHAR(30),
is_delete BOOLEAN DEFAULT FALSE
);

# CREATE TABLE cars
# (
# id SERIAL PRIMARY KEY,
# manufacturer_id BIGINT REFERENCES manufacturers(id),
# model VARCHAR(30) NOT NULL
# );

# CREATE TABLE drivers
# (
# id SERIAL PRIMARY KEY,
# name VARCHAR(30) NOT NULL,
# car_id BIGINT REFERENCES cars(id)
# );