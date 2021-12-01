CREATE DATABASE `study_db`;
-- ----------------------------
-- Table structure for `manufacturers`
-- ----------------------------
DROP TABLE IF EXISTS `manufacturers`;
CREATE TABLE `manufacturers`
(
    `id`         bigint       NOT NULL AUTO_INCREMENT,
    `name`       varchar(255) NOT NULL,
    `country`    varchar(255) NOT NULL,
    `is_deleted` tinyint DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
