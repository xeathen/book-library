CREATE TABLE IF NOT EXISTS `administrators` (
    `id`            BIGINT(11)      NOT NULL AUTO_INCREMENT,
    `admin_name`    VARCHAR(50)     NOT NULL,
    `password`      VARCHAR(50)     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `unique_admin_name` (`admin_name`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

INSERT INTO `administrators`
VALUES (1, 'admin', 'admin');

