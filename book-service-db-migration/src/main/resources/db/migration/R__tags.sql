CREATE TABLE IF NOT EXISTS `tags`  (
  `id`      int(11)         NOT NULL    AUTO_INCREMENT,
  `name`    varchar(50)     NOT NULL,
  PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;