CREATE TABLE IF NOT EXISTS `users`
(
    `id`         BIGINT(11)     NOT NULL AUTO_INCREMENT,
    `user_name`  VARCHAR(50)    NOT NULL,
    `password`   VARCHAR(50)    NOT NULL,
    `email`      VARCHAR(50)    NOT NULL,
    `status`     VARCHAR(10)    NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `user_name` (`user_name`, `email`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

INSERT INTO `users`
VALUES (1, 'admin', 'admin', 'admin@gmail.com', "ACTIVE");


