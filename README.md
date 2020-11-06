# lnori-blog
个人博客项目（SpringBoot+Mybatis）


博客地址：http://lnori.cn/

### 一、技术栈

#### 1.前端

- JS框架：JQuery
- CSS框架：[Semantic UI](https://semantic-ui.com/ "Semantic UI")
- Markdown编辑器：[编辑器 Markdown](https://pandao.github.io/editor.md/ "编辑器 Markdown")
- 代码高亮：[代码高亮 prism](https://github.com/PrismJS/prism "编辑器 Markdown")
- 动画效果：[动画 animate.css](https://daneden.github.io/animate.css/ "编辑器 Markdown")
- 文章目录：[目录生成 Tocbot](https://tscanlin.github.io/tocbot/ "编辑器 Markdown")
- 照片墙：[lightbox插件](https://github.com/JavaScript-Kit/jkresponsivegallery "编辑器 Markdown")

#### 2.后端

- 核心框架：SpringBoot 2.1.6.RELEASE
- 项目构建：jdk1.8、Maven 3
- 持久层框架：Mybatis
- 模板框架：Thymeleaf
- 权限框架：Shiro
- 全局搜索: ElasticSearch
- 消息队列：RabbitMQ
- 分页插件：PageHelper
- 加密：MD5加密
- 运行环境：暂时域名+Nginx代理

#### 3.数据库

- MySQL 5.7

### 二、功能需求

因为是个人博客，所以没有做用户权限管理，只是简单的区分了一下普通用户和管理员用户，这里就根据普通用户和管理员用户来讲述功能需求，其实从上一篇博文的前端页面就能大致的看出需求了

#### 1.普通用户

- 查看文章信息：文章列表、推荐文章、文章标题、文章内容、发布时间、访问量以及评论等信息
- 查看分类文章：分类列表、分类文章信息
- 查看时间轴：按照文章时间发布顺序查看文章
- 搜索文章：导航栏右边搜索框根据关键字搜索
- 留言：留言并回复
- 查看相册信息：相册列表、照片名称、照片拍摄地点、时间、照片描述

#### 2.管理员用户（忆主）

- 拥有普通用户所有功能权限
- 登录：在主页路径下加“/admin”，可进入登录页面，根据数据库的用户名和密码进行登录
- 文章管理：查询文章列表、新增文章、编辑文章、删除文章、搜索文章
- 分类管理：查询分类列表、新增分类、编辑分类、删除分类
- 友链管理：查询友链列表、新增友链、编辑友链、删除友链
- 相册管理：查询相册列表、新增照片、编辑照片、删除照片
- 消息管理：登录后恢复评论留言会显示栈主的头像信息，并能显示删除消息按键，可以对消息进行删除

### 三、数据库设计

#### 1.数据表
- 博客数据表：tb_blog
- 分类数据表：tb_type
- 用户数据表：tb_user
- 评论数据表：tb_comment
- 留言数据表：tb_message
- 相册数据表：tb_picture

#### 2.实体关系
- 博客和分类是多对一的关系：一个博客对应一个分类，一个分类可以对应多个博客
- 博客和用户是多对一的关系：一个博客对应一个用户，一个用户可以对应多个博客
- 博客和评论是一对多的关系：一个博客可以对应多个评论，一个评论对应一个博客
- 评论和回复是一对多的关系：一个评论可以对应多个回复，一个回复对应一个评论

> 留言和评论是一样的，还有相册数据表和其他表没有关联

#### 3.实体属性
- 博客属性：博客ID、标题、内容、首图、标记、浏览次数、赞赏开启、评论开启、是否发布、描述、创建时间、更新时间
- 分类属性：分类ID、分类名称
- 用户属性：用户ID、昵称、用户名、密码、邮箱、类型、头像、创建时间、更新时间
- 评论属性：评论ID、博客ID、父评论ID、昵称、邮箱、头像、评论内容、是否是父类、是否是管理员、创建时间
- 留言属性：留言ID、父留言ID、昵称、邮箱、头像、留言内容、是否是父类、是否是管理员、创建时间
- 相册属性：图片地址、图片描述、图片名称、地点

#### 4.建表语句
```java
USE `blog`;

CREATE TABLE `tb_blog` (
  `blog_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '博客id',
  `type_id` bigint(20) NOT NULL COMMENT '博客分类id',
  `user_id` bigint(20) NOT NULL COMMENT '博客对应用户id',
  `title` varchar(128) NOT NULL COMMENT '博客标题',
  `content` longtext NOT NULL COMMENT '博客内容',
  `first_picture` varchar(128) NOT NULL COMMENT '博客首图',
  `flag` varchar(128) NOT NULL COMMENT '标记',
  `description` varchar(255) NOT NULL COMMENT '博客描述',
  `views` int(11) NOT NULL DEFAULT '0' COMMENT '博客浏览次数',
  `comment_count` int(11) DEFAULT '0' COMMENT '博客评论总数',
  `recommend` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否推荐：1推荐，0不推荐',
  `comment` tinyint(1) NOT NULL DEFAULT '0' COMMENT '评论是否开启：1开启，0关闭',
  `appreciation` tinyint(1) NOT NULL DEFAULT '0' COMMENT '赞赏是否开启：1开启，0关闭',
  `published` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否发布：1发布，0保存',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`blog_id`),
  KEY `idx_type_id` (`type_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_blog_tag` (
  `blog_id` bigint(20) NOT NULL COMMENT '博客id',
  `tag_id` bigint(20) NOT NULL COMMENT '博客标签id',
  KEY `idx_blog_id` (`blog_id`),
  KEY `idx_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_comment` (
  `comment_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '博客评论id',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父id',
  `blog_id` bigint(20) NOT NULL COMMENT '博客id',
  `nick_name` varchar(64) NOT NULL COMMENT '评论昵称',
  `email` varchar(128) NOT NULL COMMENT '邮箱',
  `content` varchar(255) NOT NULL COMMENT '评论内容',
  `avatar` varchar(128) NOT NULL COMMENT '头像',
  `is_parent` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否为父节点，0为否，1为是',
  `is_admin_comment` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`comment_id`),
  KEY `idx_blog_id` (`blog_id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_message` (
  `message_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '博客留言id',
  `parent_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '父id',
  `nick_name` varchar(64) NOT NULL COMMENT '留言昵称',
  `email` varchar(128) NOT NULL COMMENT '邮箱',
  `content` varchar(255) NOT NULL COMMENT '留言内容',
  `avatar` varchar(128) NOT NULL COMMENT '头像',
  `is_parent` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否为父节点，0为否，1为是',
  `is_admin_message` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`message_id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_picture` (
  `picture_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '照片id',
  `link` varchar(128) NOT NULL COMMENT '链接',
  `title` varchar(64) NOT NULL COMMENT '标题',
  `description` varchar(64) NOT NULL COMMENT '描述',
  `address` varchar(64) NOT NULL COMMENT '地点',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`picture_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_tag` (
  `tag_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '博客标签id',
  `tag_name` varchar(64) NOT NULL COMMENT '标签名称',
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_type` (
  `type_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '博客分类id',
  `type_name` varchar(64) NOT NULL COMMENT '博客分类名称',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '博客用户id',
  `nick_name` varchar(64) NOT NULL COMMENT '用户昵称',
  `username` varchar(128) NOT NULL COMMENT '用户名',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `email` varchar(128) NOT NULL COMMENT '邮箱',
  `avatar` varchar(128) NOT NULL COMMENT '头像',
  `type` int(11) NOT NULL COMMENT '类型',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
```

【点关注，不迷路，欢迎关注本站】

地址：https://lnori.mynatapp.cc/
