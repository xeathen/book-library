CREATE TABLE IF NOT EXISTS `books`(
    `id`          bigint(20)    NOT NULL    AUTO_INCREMENT,
    `name`        varchar(100)  NOT NULL,
    `author_id`   int(11)       NOT NULL,
    `category_id` int(11)       NOT NULL,
    `tag_id`      int(11)       NOT NULL,
    `pub`         varchar(50)   NOT NULL,
    `description` varchar(255)  NULL DEFAULT NULL,
    `num`         int(11)       NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

