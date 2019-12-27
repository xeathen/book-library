CREATE TABLE IF NOT EXISTS `reservations`  (
  `id`              int(11)         NOT NULL    AUTO_INCREMENT,
  `user_id`         bigint(11)      NOT NULL,
  `book_id`         bigint(11)      NOT NULL,
  `reserve_time`    timestamp(6)    NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
)
    ENGINE = InnoDB;
    DEFAULT CHARACTER SET = utf8;

