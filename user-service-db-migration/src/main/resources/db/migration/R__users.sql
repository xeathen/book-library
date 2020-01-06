CREATE TABLE IF NOT EXISTS `users`
(
    `id`        BIGINT(11)  NOT NULL AUTO_INCREMENT,
    `user_name` VARCHAR(50) NOT NULL,
    `password`  CHAR(64)    NOT NULL,
    `salt`      CHAR(6)     NOT NULL,
    `iteration` INT(11)     NOT NULL,
    `email`     VARCHAR(50) NOT NULL,
    `status`    VARCHAR(10) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `unique_user_name` (`user_name`),
    UNIQUE INDEX `unique_email`(`email`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

