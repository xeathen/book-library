CREATE TABLE IF NOT EXISTS `authors`(
  `id`      int(11)         NOT NULL    AUTO_INCREMENT,
  `name`    varchar(100)    NULL        DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
