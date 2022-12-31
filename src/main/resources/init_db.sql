CREATE DATABASE `taxi_service` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
SET @id_to_select = <{row_id}>;
SELECT manufacturers.*
    FROM manufacturers
    WHERE manufacturers.id = @id_to_select;