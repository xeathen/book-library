CREATE TABLE IF NOT EXISTS `administrators` (
    `id`            BIGINT(11)      NOT NULL AUTO_INCREMENT,
    `admin_name`    VARCHAR(50)     NOT NULL,
    `password`      CHAR(64)        NOT NULL,
    `salt`          CHAR(6)         NOT NULL,
    `iteration`     INT(11)         NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `unique_admin_name` (`admin_name`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

INSERT INTO `administrators`
VALUES (1, 'admin', '5a9ea04701d11e4c40cd378b6097ab6ff3b1b1551f81d4d7ca68553b7c28d802', 'hiasdb', 6);

