CREATE TABLE IF NOT EXISTS `books`
(
    `id`               BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(100) NOT NULL,
    `author_id`        INT(11)      NOT NULL,
    `category_id`      INT(11)      NOT NULL,
    `tag_id`           INT(11)      NOT NULL,
    `publishing_house` VARCHAR(50)  NOT NULL,
    `description`      VARCHAR(255) NULL DEFAULT NULL,
    `amount`           INT(11)      NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

