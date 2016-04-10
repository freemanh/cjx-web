-- --------------------------------------------------------
-- 主机:                           rdszevquqn3iz3u.mysql.rds.aliyuncs.com
-- 服务器版本:                        5.5.18.1-Alibaba-rds-201406-log - Source distribution
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  8.3.0.4694
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 wh_temp.xalarm 结构
DROP TABLE IF EXISTS `xalarm`;
CREATE TABLE IF NOT EXISTS `xalarm` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `clearTime` datetime DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `descp` varchar(255) DEFAULT NULL,
  `grade` tinyint(4) DEFAULT NULL,
  `messageSent` tinyint(1) NOT NULL,
  `sensor_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 wh_temp.xarea 结构
DROP TABLE IF EXISTS `xarea`;
CREATE TABLE IF NOT EXISTS `xarea` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `x` int(11) DEFAULT NULL,
  `y` int(11) DEFAULT NULL,
  `house_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 wh_temp.xcompany 结构
DROP TABLE IF EXISTS `xcompany`;
CREATE TABLE IF NOT EXISTS `xcompany` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 wh_temp.xcompany_alarm_phone_number 结构
DROP TABLE IF EXISTS `xcompany_alarm_phone_number`;
CREATE TABLE IF NOT EXISTS `xcompany_alarm_phone_number` (
  `company_id` bigint(20) NOT NULL,
  `alarm_phone_number` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 wh_temp.xdevice 结构
DROP TABLE IF EXISTS `xdevice`;
CREATE TABLE IF NOT EXISTS `xdevice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `x` int(11) DEFAULT NULL,
  `y` int(11) DEFAULT NULL,
  `area_id` bigint(20) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `authId` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `phoneNo` varchar(255) DEFAULT NULL,
  `termId` varchar(255) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 wh_temp.xhouse 结构
DROP TABLE IF EXISTS `xhouse`;
CREATE TABLE IF NOT EXISTS `xhouse` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `manager` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 wh_temp.xsensor 结构
DROP TABLE IF EXISTS `xsensor`;
CREATE TABLE IF NOT EXISTS `xsensor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `collectTime` time DEFAULT NULL,
  `humidity` double DEFAULT NULL,
  `maxHumidity` double DEFAULT 100.0,
  `maxTemp` double DEFAULT 50.0,
  `minHumidity` double DEFAULT 10.0,
  `minTemp` double DEFAULT 5.0,
  `name` varchar(255) DEFAULT NULL,
  `temperature` double DEFAULT NULL,
  `x` int(11) DEFAULT NULL,
  `y` int(11) DEFAULT NULL,
  `device_id` bigint(20) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `sensor_index` int(11) DEFAULT NULL,
  `hum_revision` DOUBLE NOT NULL DEFAULT '0',
  `temp_revision` DOUBLE NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 wh_temp.xsys_config 结构
DROP TABLE IF EXISTS `xsys_config`;
CREATE TABLE IF NOT EXISTS `xsys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `showRevision` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 wh_temp.xtextmessage_audit 结构
DROP TABLE IF EXISTS `xtextmessage_audit`;
CREATE TABLE IF NOT EXISTS `xtextmessage_audit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `feedback` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `send_time` datetime DEFAULT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
DROP TABLE IF EXISTS `XUser`;
CREATE TABLE `XUser` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pwd` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `XRole`;
CREATE TABLE `XRole` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `USER_ROLE`;
CREATE TABLE `USER_ROLE` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `mondata`;
CREATE TABLE `mondata` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `collect_time` datetime NOT NULL,
  `device_code` varchar(255) NOT NULL,
  `humidity` double DEFAULT NULL,
  `sensor_index` tinyint(4) NOT NULL,
  `temperature` double DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `original_temp` double DEFAULT NULL,
  `original_humidity` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE xdevice add `support_power_alarm` char(1) NOT NULL DEFAULT '1';

alter table xsensor add column is_over_heat char(1) not null default 0;
alter table xsensor add column is_over_hum char(1) not null default 0;

DROP TABLE IF EXISTS `alarm`;
CREATE TABLE IF NOT EXISTS `alarm` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT 0 NOT NULL,
  `alarm_type` tinyint(4) NOT NULL,
  `device_id` bigint(20) DEFAULT NULL,
  `sensor_id` bigint(20) DEFAULT NULL,
  `max` double NULL,
  `min` double NULL,
  `reading` double NULL,
  `clearTime` datetime DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `descp` varchar(255) DEFAULT NULL,
  `grade` tinyint(4) DEFAULT NULL,
  `messageSent` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE xsensor ADD COLUMN last_update_time datetime NULL DEFAULT NULL;
ALTER TABLE xsensor ADD COLUMN upload_frequency SMALLINT NOT NULL DEFAULT 1;
ALTER TABLE xsensor ADD COLUMN is_synced SMALLINT NOT NULL DEFAULT 1;
ALTER TABLE xsensor ADD COLUMN alarm_mode SMALLINT NOT NULL DEFAULT 0;