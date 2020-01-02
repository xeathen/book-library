CREATE TABLE IF NOT EXISTS `reservations`  (
  `id`              INT(11)         NOT NULL    AUTO_INCREMENT,
  `user_id`         BIGINT(11)      NOT NULL,
  `book_id`         BIGINT(11)      NOT NULL,
  `reserve_time`    TIMESTAMP(6)    NOT NULL,
  PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

