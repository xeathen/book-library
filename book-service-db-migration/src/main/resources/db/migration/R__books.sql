CREATE TABLE IF NOT EXISTS `books`(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT,
    `name`        varchar(100) NOT NULL,
    `author`      varchar(50)  NOT NULL,
    `pub`         varchar(50)  NOT NULL,
    `category`    varchar(50)  NOT NULL,
    `tag`         varchar(50)  NULL DEFAULT NULL,
    `description` varchar(255) NULL DEFAULT NULL,
    `num`         int(11)      NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

