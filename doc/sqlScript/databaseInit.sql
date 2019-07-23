/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.6.35-log : Database - erp
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`erp` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `erp`;

/*Table structure for table `role_permission` */

DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
  `id` bigint(20) NOT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `permission_id` bigint(20) DEFAULT NULL,
  `insert_username` varchar(100) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `update_username` varchar(100) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role_permission` */

insert  into `role_permission`(`id`,`role_id`,`permission_id`,`insert_username`,`insert_time`,`update_username`,`update_time`) values (1,1,1,NULL,NULL,NULL,NULL),(2,2,1,NULL,NULL,NULL,NULL);

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL,
  `permission_name` varchar(100) DEFAULT NULL,
  `url` varchar(2083) DEFAULT NULL,
  `insert_username` varchar(100) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `update_username` varchar(100) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_permission` */

insert  into `sys_permission`(`id`,`permission_name`,`url`,`insert_username`,`insert_time`,`update_username`,`update_time`) values (1,'管理员','/userList',NULL,NULL,NULL,NULL);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL,
  `role_name` varchar(100) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL COMMENT '父角色id',
  `insert_username` varchar(100) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `update_username` varchar(100) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`role_name`,`pid`,`insert_username`,`insert_time`,`update_username`,`update_time`) values (1,'admin',2,NULL,NULL,NULL,NULL),(2,'user',NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `insert_username` varchar(100) DEFAULT NULL,
  `update_username` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`username`,`password`,`update_time`,`insert_time`,`insert_username`,`update_username`) values (1,'test','123','2018-06-14 16:33:13',NULL,NULL,NULL),(2,'admin','123',NULL,NULL,NULL,NULL);

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `insert_username` varchar(100) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `update_username` varchar(100) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

/*Data for the table `user_role` */

insert  into `user_role`(`id`,`user_id`,`role_id`,`insert_username`,`insert_time`,`update_username`,`update_time`) values (1,1,2,NULL,NULL,NULL,NULL),(2,2,1,NULL,NULL,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

create table persistent_logins (username varchar(100) not null, series varchar(64) primary key, token varchar(64) not null, last_used timestamp not null);

drop table if exists sys_config;

/*==============================================================*/
/* Table: sys_config                                            */
/*==============================================================*/
create table sys_config
(
  id                   bigint not null comment '主键',
  parm_name            varchar(100) comment '参数名',
  parm_value           varchar(100) comment '参数值',
  insert_username      varchar(100) comment 'insert_username',
  insert_time          datetime comment 'insert_time',
  update_username      varchar(100) comment 'update_username',
  update_time          datetime comment 'update_time',
  primary key (id)
);

alter table sys_config comment '系统配置表';

ALTER TABLE `sys_config`
  ADD COLUMN `descp` VARCHAR(1000) NULL   COMMENT '描述' AFTER `parm_value`;

ALTER TABLE `sys_user`
  ADD COLUMN `enable_flag` ENUM('Y','N') DEFAULT 'Y'  NULL   COMMENT '是否生效' AFTER `password`;