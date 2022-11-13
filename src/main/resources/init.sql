CREATE DATABASE 'taxi_db'
CREATE TABLE 'manufacturers' (
'id' int NOT NULL AUTO_INCRIMENT,
'name' varchar(255) NOT NULL,
'country' varchar(255) NOT NULL,
'is_deleted' tinyint NOT NULL DEFAULT '0',
PRIMARY KEY ('id')
)
ENGINE = InnoDB DEFAULT CHARSET = utf8;