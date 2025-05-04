CREATE DATABASE 'taxi_service_db' /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE 'taxi_service_db';
CREATE TABLE 'manufacturer' (
    'id' bigint NOT NULL AUTO_INCREMENT,
    'name' varchar(45) DEFAULT NULL,
    'country' varchar(45) DEFAULT NULL,
    'is_deleted' tinyint NOT NULL DEFAULT '0',
    PRIMARY KEY('id')
) ENGINE=InnoDB UTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
