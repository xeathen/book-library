SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for categories
-- ----------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories`  (
  `id`      int(11)         NOT NULL    AUTO_INCREMENT,
  `name`    varchar(50)     NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB

-- ----------------------------
-- Records of categories
-- ----------------------------
INSERT INTO `categories` VALUES (1, '小说');
INSERT INTO `categories` VALUES (2, '摄影');
INSERT INTO `categories` VALUES (3, '自传');
INSERT INTO `categories` VALUES (5, '长篇小说');

SET FOREIGN_KEY_CHECKS = 1;
