SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags`  (
  `id`      int(11)         NOT NULL    AUTO_INCREMENT,
  `name`    varchar(255)    NULL        DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB

-- ----------------------------
-- Records of tags
-- ----------------------------
INSERT INTO `tags` VALUES (1, '生活');
INSERT INTO `tags` VALUES (2, '恐怖');
INSERT INTO `tags` VALUES (3, '推理');
INSERT INTO `tags` VALUES (4, '都市');

SET FOREIGN_KEY_CHECKS = 1;
