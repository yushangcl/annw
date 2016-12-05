
-- ----------------------------
-- Table structure for ba_user
-- ----------------------------
CREATE TABLE `ba_user` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password_change_next` int(11) DEFAULT '0' COMMENT '下次登录时修改密码  1是',
  `locked` int(11) DEFAULT NULL COMMENT '是否锁定',
  `locked_date` date DEFAULT NULL COMMENT '锁定时间',
  `face_url` varchar(250) DEFAULT NULL COMMENT '头像url',
  `create_time` date DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `update_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) DEFAULT CHARSET=utf-8 COMMENT='用户信息表';
