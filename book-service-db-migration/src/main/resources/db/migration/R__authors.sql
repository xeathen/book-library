CREATE TABLE IF NOT EXISTS `authors`    (
  `id`      INT(11)         NOT NULL    AUTO_INCREMENT,
  `name`    VARCHAR(100)    NOT NULL,
  PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;
