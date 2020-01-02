CREATE TABLE IF NOT EXISTS `categories`  (
  `id`      INT(11)         NOT NULL    AUTO_INCREMENT,
  `name`    VARCHAR(50)     NOT NULL,
  PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;
