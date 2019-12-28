CREATE TABLE IF NOT EXISTS `users`
(
    `id`         bigint(11)  NOT NULL AUTO_INCREMENT,
    `user_name`  varchar(50) NOT NULL,
    `password`   varchar(50) NOT NULL,
    `user_email` varchar(50) NOT NULL,
    `status`     bit(1)      NOT NULL DEFAULT b'1',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `user_name` (`user_name`, `user_email`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

INSERT INTO `users`
VALUES (1, 'admin', 'admin', 'admin@gmail.com', b'1');


