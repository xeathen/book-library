CREATE TABLE IF NOT EXISTS `authors`(
  `id`      int(11)         NOT NULL    AUTO_INCREMENT,
  `name`    varchar(100)    NOT NULL,
  PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;
