SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for books
-- ----------------------------
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books`
(
    `id`          bigint(20)                                                        NOT NULL    AUTO_INCREMENT,
    `name`        varchar(100)  CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NOT NULL,
    `author`      varchar(50)   CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NOT NULL,
    `pub`         varchar(50)   CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NOT NULL,
    `category`    varchar(50)   CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NOT NULL,
    `tag`         varchar(50)   CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NULL        DEFAULT NULL,
    `description` varchar(255)  CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NULL        DEFAULT NULL,
    `num`         int(11)                                                           NOT         NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

SET FOREIGN_KEY_CHECKS = 1;