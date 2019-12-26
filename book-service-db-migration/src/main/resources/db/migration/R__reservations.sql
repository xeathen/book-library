SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for reservations
-- ----------------------------
DROP TABLE IF EXISTS `reservations`;
CREATE TABLE `reservations`  (
  `id`          int(11)         NOT NULL    AUTO_INCREMENT,
  `user_id`     bigint(11)      NOT NULL,
  `book_id`     bigint(11)      NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB;

-- ----------------------------
-- Records of reservations
-- ----------------------------
INSERT INTO `reservations` VALUES (1, 1, 3);

SET FOREIGN_KEY_CHECKS = 1;
