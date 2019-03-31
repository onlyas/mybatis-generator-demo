
# Dump of table News
# ------------------------------------------------------------

DROP TABLE IF EXISTS `News`;

CREATE TABLE `News` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  `intro` varchar(50) DEFAULT NULL COMMENT '简介',
  `desc` varchar(500) DEFAULT '' COMMENT '内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='新闻表';

LOCK TABLES `News` WRITE;
/*!40000 ALTER TABLE `News` DISABLE KEYS */;

INSERT INTO `News` (`id`, `title`, `intro`, `desc`)
VALUES
	(1,'测试标题','测试简介','测试内容');

/*!40000 ALTER TABLE `News` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Users
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Users`;

CREATE TABLE `Users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `user_sex` varchar(50) DEFAULT NULL COMMENT '性别',
  `user_nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `user_birthday` datetime DEFAULT NULL COMMENT '生日',
  `user_email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;

INSERT INTO `Users` (`id`, `user_name`, `user_sex`, `user_nickname`, `user_birthday`, `user_email`)
VALUES
	(1,'danny','man','Danny','1990-01-01 00:00:00','admin@123.com');

/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;


