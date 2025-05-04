CREATE
DATABASE IF NOT EXISTS `taxi_db`
CREATE TABLE IF NOT EXISTS `manufacturers`
(
  `id`         bigint      NOT NULL,
  `name`       varchar(45) NOT NULL,
  `country`    varchar(45) NOT NULL,
  `is_deleted` tinyint     NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
