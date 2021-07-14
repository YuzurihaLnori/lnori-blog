> > 源码领取方式: [https://github.com/YuzurihaLnori/lnori-blog](https://github.com/YuzurihaLnori/lnori-blog "https://github.com/YuzurihaLnori/lnori-blog")
>
> 本文将从技术栈、功能需求、数据库设计来讲述，前端页面展示可以看我上一篇文章：[【SpringBoot搭建个人博客】- 前端页面展示（一）](http://lnori.mynatapp.cc/page/6.html "【SpringBoot搭建个人博客】- 前端页面展示（一）")
>
> ### 一、技术栈
>
> #### 1.前端
>
> - JS框架：JQuery，Vue
> - CSS框架：[Bootstrap](https://v3.bootcss.com/ "Bootstrap")
> - Markdown编辑器：[编辑器 Markdown](https://pandao.github.io/editor.md/ "编辑器 Markdown")
> - 代码高亮：[代码高亮 prism](https://github.com/PrismJS/prism "编辑器 Markdown")
> - 动画效果：[动画 animate.css](https://daneden.github.io/animate.css/ "编辑器 Markdown")
> - 文章目录：[目录生成 Tocbot](https://tscanlin.github.io/tocbot/ "编辑器 Markdown")
> - 照片墙：[lightbox插件](https://github.com/JavaScript-Kit/jkresponsivegallery "编辑器 Markdown")
>
> #### 2.后端
>
> - 核心框架：SpringBoot 2.1.6.RELEASE
> - 项目构建：jdk1.8、Maven 3
> - 持久层框架：Mybatis
> - 权限框架：Shiro
> - 全局搜索: ElasticSearch
> - 消息队列：RabbitMQ
> - 加密：MD5加密
> - 运行环境：内网穿+Nginx代理
>
> #### 3.数据库
>
> - MySQL 5.7
>
> ### 二、功能需求
>
> 因为是个人博客，所以没有做用户权限管理，只是简单的区分了一下普通用户和管理员用户，这里就根据普通用户和管理员用户来讲述功能需求，其实从上一篇博文的前端页面就能大致的看出需求了
>
> #### 1.普通用户
>
> - 查看文章信息：文章列表、推荐文章、文章标题、文章内容、发布时间、访问量以及评论等信息
> - 查看分类文章：分类列表、分类文章信息
> - 查看时间轴：按照文章时间发布顺序查看文章
> - 搜索文章：导航栏右边搜索框根据关键字搜索
> - 留言：留言并回复
> - 查看相册信息：相册列表、照片名称、照片拍摄地点、时间、照片描述
>
> #### 2.管理员用户（博主）
>
> - 拥有普通用户所有功能权限
> - 登录：在主页路径下加“/system/login.html”，可进入登录页面，根据数据库的用户名和密码进行登录
> - 博客管理：查询博客列表、新增博客、编辑博客、删除博客、搜索博客
> - 分类管理：查询分类列表、新增分类、编辑分类、删除分类
> - 相册管理：查询相册列表、新增照片、编辑照片、删除照片
> - 消息管理：登录后恢复评论留言会显示博主的头像信息，并能显示删除消息按键，可以对消息进行删除
>
> ### 三、数据库设计
>
> #### 1.数据表
> - 博客数据表：tb_blog
> - 分类数据表：tb_type
> - 用户数据表：tb_user
> - 评论数据表：tb_comment
> - 留言数据表：tb_message
> - 相册数据表：tb_picture
> - 资源数据表：tb_res
>
> #### 2.实体关系
> - 博客和分类是多对一的关系：一个博客对应一个分类，一个分类可以对应多个博客
> - 博客和评论是一对多的关系：一个博客可以对应多个评论，一个评论对应一个博客
> - 评论和回复是一对多的关系：一个评论可以对应多个回复，一个回复对应一个评论
>
> > 留言和评论是一样的，还有相册数据表和其他表没有关联
>
> #### 3.实体属性
> - 博客属性：ID、标题、内容、首图、标记、浏览次数、是否推荐、状态、描述、创建时间、更新时间
> - 分类属性：ID、分类名称、创建时间、更新时间
> - 用户属性：ID、昵称、用户名、密码、邮箱、类型、头像、创建时间、更新时间
> - 评论属性：ID、博客ID、父ID、昵称、邮箱、头像、评论内容、是否是管理员、创建时间、更新时间
> - 留言属性：ID、父ID、昵称、邮箱、头像、留言内容、是否是管理员、创建时间、更新时间
> - 相册属性：ID、图片地址、图片描述、图片名称、地点、创建时间、更新时间
>
> #### 4.建表语句
> ```java
> USE `tb_blog`;
> 
> -- ----------------------------
> -- Table structure for tb_blog
> -- ----------------------------
> DROP TABLE IF EXISTS `tb_blog`;
> CREATE TABLE `tb_blog` (
>   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
>   `type_id` int(11) NOT NULL COMMENT '分类id',
>   `first_picture` int(11) DEFAULT NULL COMMENT '博客首图',
>   `title` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '标题',
>   `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
>   `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '内容',
>   `flag` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '标记',
>   `views` int(11) NOT NULL DEFAULT 0 COMMENT '浏览次数',
>   `recommend` tinyint(1) DEFAULT 0 COMMENT '是否推荐(0-不推荐 1-推荐)',
>   `status` tinyint(1) DEFAULT 0 COMMENT '状态(0-保存 1-发布)',
>   `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
>   `update_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
>   PRIMARY KEY (`id`) USING BTREE
> ) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='博客表';
> 
> 
> -- ----------------------------
> -- Table structure for tb_comment
> -- ----------------------------
> DROP TABLE IF EXISTS `tb_comment`;
> CREATE TABLE `tb_comment` (
>   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
>   `blog_id` int(11) NOT NULL COMMENT '博客id',
>   `avatar` VARCHAR(255) NOT NULL COMMENT '头像url',
>   `parent_id` int(11) NOT NULL DEFAULT -1 COMMENT '父id',
>   `is_blogger` tinyint(1) DEFAULT 0 COMMENT '是否是博主(0-否 1-是)',
>   `nick_name` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '昵称',
>   `email` VARCHAR(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
>   `content` text CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '内容',
>   `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
>   `update_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
>   PRIMARY KEY (`id`) USING BTREE
> ) ENGINE=INNODB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='评论表';
> 
> 
> -- ----------------------------
> -- Table structure for tb_type
> -- ----------------------------
> DROP TABLE IF EXISTS `tb_type`;
> CREATE TABLE `tb_type` (
>   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
>   `name` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分类名称',
>   `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
>   `update_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
>   PRIMARY KEY (`id`) USING BTREE
> ) ENGINE=INNODB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='分类表';
> 
> 
> -- ----------------------------
> -- Table structure for tb_message
> -- ----------------------------
> DROP TABLE IF EXISTS `tb_message`;
> CREATE TABLE `tb_message` (
>   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
>   `avatar` VARCHAR(255) NOT NULL COMMENT '头像url',
>   `parent_id` int(11) NOT NULL DEFAULT -1 COMMENT '父id',
>   `is_blogger` tinyint(1) DEFAULT 0 COMMENT '是否是博主(0-否 1-是)',
>   `nick_name` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '昵称',
>   `email` VARCHAR(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
>   `content` text CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '内容',
>   `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
>   `update_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
>   PRIMARY KEY (`id`) USING BTREE
> ) ENGINE=INNODB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='留言表';
> 
> 
> -- ----------------------------
> -- Table structure for tb_picture
> -- ----------------------------
> DROP TABLE IF EXISTS `tb_picture`;
> CREATE TABLE `tb_picture` (
>   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
>   `picture` int(11) NOT NULL COMMENT '照片',
>   `title` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '标题',
>   `address` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '地址',
>   `description` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
>   `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
>   `update_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
>   PRIMARY KEY (`id`) USING BTREE
> ) ENGINE=INNODB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='照片表';
> 
> 
> -- ----------------------------
> -- Table structure for tb_res
> -- ----------------------------
> DROP TABLE IF EXISTS `tb_res`;
> CREATE TABLE `tb_res` (
>   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
>   `type_code` varchar(64) NOT NULL COMMENT '类型code',
>   `path` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '存储路径',
>   `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
>   `update_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
>   PRIMARY KEY (`id`) USING BTREE
> ) ENGINE=INNODB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='资源表';
> 
> 
> -- ----------------------------
> -- Table structure for tb_user
> -- ----------------------------
> DROP TABLE IF EXISTS `tb_user`;
> CREATE TABLE `tb_user` (
>   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
>   `avatar` int(11) DEFAULT NULL COMMENT '头像',
>   `account` VARCHAR(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账号（支持中文、英文、数字）',
>   `password` VARCHAR(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户密码',
>   `name` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '名称',
>   `mobile` VARCHAR(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号',
>   `email` VARCHAR(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
>   `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
>   `update_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
>   PRIMARY KEY (`id`) USING BTREE
> ) ENGINE=INNODB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统用户表';
> 
> ```
