use annw;
CREATE TABLE `ba_user_info` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `sex` varchar(20) DEFAULT NULL COMMENT '性别',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `birth` varchar(255) DEFAULT NULL COMMENT '出生日期',
  `website` varchar(255) DEFAULT NULL COMMENT '个人网站',
  `signature` varchar(255) DEFAULT NULL COMMENT '个性签名',
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户信息详情表';