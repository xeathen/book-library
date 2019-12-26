SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for authors
-- ----------------------------
DROP TABLE IF EXISTS `authors`;
CREATE TABLE `authors`  (
  `id`      int(11)         NOT NULL    AUTO_INCREMENT,
  `name`    varchar(255)    NULL        DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB

-- ----------------------------
-- Records of authors
-- ----------------------------
INSERT INTO `authors` VALUES (1, '刘慈欣');
INSERT INTO `authors` VALUES (2, '莫言');
INSERT INTO `authors` VALUES (3, '东野圭吾');

SET FOREIGN_KEY_CHECKS = 1;
