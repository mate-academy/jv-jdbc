CREATE DATABASE taxi CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

USE taxi;

CREATE TABLE manufacturer (
                                id bigint NOT NULL AUTO_INCREMENT,
                                name varchar(255) DEFAULT NULL,
                                country varchar(255) DEFAULT NULL,
                                Is_deleted tinyint NOT NULL DEFAULT 0,
                                PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
