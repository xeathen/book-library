CREATE TABLE IF NOT EXISTS `users`
(
    `id`         bigint(11)     NOT NULL AUTO_INCREMENT,
    `user_name`  varchar(50)    NOT NULL,
    `password`   varchar(50)    NOT NULL,
    `user_email` varchar(50)    NOT NULL,
    `status`     varchar(10)    NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `user_name` (`user_name`, `user_email`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

INSERT INTO `users`
VALUES (1, 'admin', 'admin', 'admin@gmail.com', "ACTIVE");


