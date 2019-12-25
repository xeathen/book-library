SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `id`         bigint(11)   NOT NULL  AUTO_INCREMENT,
    `user_name`  varchar(50)  NOT NULL,
    `password`   varchar(50)  NOT NULL,
    `user_email` varchar(50)  NOT NULL,
    `status`     bit(1)       NULL      DEFAULT b'1',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `user_name` (`user_name`, `user_email`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

SET FOREIGN_KEY_CHECKS = 1;

