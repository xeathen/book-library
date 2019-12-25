SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for books
-- ----------------------------
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT,
    `name`        varchar(100) NOT NULL,
    `author`      varchar(50)  NOT NULL,
    `pub`         varchar(50)  NOT NULL,
    `category`    varchar(50)  NOT NULL,
    `tag`         varchar(50)  NULL DEFAULT NULL,
    `description` varchar(255) NULL DEFAULT NULL,
    `num`         int(11)      NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

SET FOREIGN_KEY_CHECKS = 1;