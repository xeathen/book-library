CREATE TABLE IF NOT EXISTS `users` (
    `id`            VARCHAR(256)    NOT NULL,
    `user_name`     VARCHAR(50)     NOT NULL    UNIQUE,
    `password`      VARCHAR(50)     NOT NULL
    PRIMARY KEY (`id`),

)