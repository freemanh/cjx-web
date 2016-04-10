INSERT INTO `xcompany` (`id`, `version`, `name`)
VALUES (1, 6, '德鑫药业');

INSERT INTO `xuser` (`id`, `version`, `name`, `pwd`, `username`, `company_id`, `enabled`)
VALUES (1, 1, '德鑫药业', 'dexin', 'dexin', 1, 1);

INSERT INTO `XRole` (`id`, `version`, `role`)
VALUES (1, 1, 'ROLE_USER');

INSERT INTO `XRole` (`id`, `version`, `role`)
VALUES (2, 1, 'ROLE_ADMIN');

INSERT INTO `USER_ROLE` (`user_id`, `role_id`)
VALUES (1, 1);

INSERT INTO `xdevice` (`id`, `version`, `ip`, `name`, `x`, `y`, `area_id`, `company_id`, `authId`, `code`, `phoneNo`, `termId`, `status`, `latitude`, `longitude`, `support_power_alarm`)
VALUES
	(1, 0, NULL, '测试设备', NULL, NULL, NULL, 1, NULL, '015098884572', '015098884572', NULL, 1, NULL, NULL, '1');

	
INSERT INTO `xsensor` (`id`, `version`, `code`, `collectTime`, `humidity`, `maxHumidity`, `maxTemp`, `minHumidity`, `minTemp`, `name`, `temperature`, `x`, `y`, `device_id`, `status`, `sensor_index`, `hum_revision`, `temp_revision`)
VALUES
	(1, 1, NULL, NULL, NULL, 100, 50, 10, 5, NULL, NULL, NULL, NULL, 1, NULL, 0, 0, 0);
	
INSERT INTO `xdevice` (`id`, `version`, `ip`, `name`, `x`, `y`, `area_id`, `company_id`, `authId`, `code`, `phoneNo`, `termId`, `status`, `latitude`, `longitude`, `support_power_alarm`)
VALUES
	(2, 0, NULL, '双温度探头竞速测试设备', NULL, NULL, NULL, 1, NULL, '015098884582', '015098884582', NULL, 1, NULL, NULL, '1');

INSERT INTO `xsensor` (`id`, `version`, `code`, `collectTime`, `humidity`, `maxHumidity`, `maxTemp`, `minHumidity`, `minTemp`, `name`, `temperature`, `x`, `y`, `device_id`, `status`, `sensor_index`, `hum_revision`, `temp_revision`)
VALUES
	(2, 1, NULL, NULL, NULL, 100, 50, 10, 5, NULL, NULL, NULL, NULL, 2, NULL, 0, 0, 0);

INSERT INTO `xsensor` (`id`, `version`, `code`, `collectTime`, `humidity`, `maxHumidity`, `maxTemp`, `minHumidity`, `minTemp`, `name`, `temperature`, `x`, `y`, `device_id`, `status`, `sensor_index`, `hum_revision`, `temp_revision`)
VALUES
	(3, 1, NULL, NULL, NULL, 100, 50, 10, 5, NULL, NULL, NULL, NULL, 2, NULL, 1, 0, 0);
	
INSERT INTO `xdevice` (`id`, `version`, `ip`, `name`, `x`, `y`, `area_id`, `company_id`, `authId`, `code`, `phoneNo`, `termId`, `status`, `latitude`, `longitude`, `support_power_alarm`)
VALUES
	(3, 0, NULL, '九合测试设备', NULL, NULL, NULL, 1, NULL, '015098884592', '015098884592', NULL, 1, NULL, NULL, '1');

INSERT INTO `xsensor` (`id`, `version`, `code`, `collectTime`, `humidity`, `maxHumidity`, `maxTemp`, `minHumidity`, `minTemp`, `name`, `temperature`, `x`, `y`, `device_id`, `status`, `sensor_index`, `hum_revision`, `temp_revision`)
VALUES
	(4, 1, NULL, NULL, NULL, 100, 50, 10, 5, NULL, NULL, NULL, NULL, 3, NULL, 0, 0, 0);

INSERT INTO `xsensor` (`id`, `version`, `code`, `collectTime`, `humidity`, `maxHumidity`, `maxTemp`, `minHumidity`, `minTemp`, `name`, `temperature`, `x`, `y`, `device_id`, `status`, `sensor_index`, `hum_revision`, `temp_revision`)
VALUES
	(5, 1, NULL, NULL, NULL, 100, 50, 10, 5, NULL, NULL, NULL, NULL, 3, NULL, 1, 0, 0);
INSERT INTO `xsensor` (`id`, `version`, `code`, `collectTime`, `humidity`, `maxHumidity`, `maxTemp`, `minHumidity`, `minTemp`, `name`, `temperature`, `x`, `y`, `device_id`, `status`, `sensor_index`, `hum_revision`, `temp_revision`)
VALUES
	(6, 1, NULL, NULL, NULL, 100, 50, 10, 5, NULL, NULL, NULL, NULL, 3, NULL, 2, 0, 0);

INSERT INTO `xsensor` (`id`, `version`, `code`, `collectTime`, `humidity`, `maxHumidity`, `maxTemp`, `minHumidity`, `minTemp`, `name`, `temperature`, `x`, `y`, `device_id`, `status`, `sensor_index`, `hum_revision`, `temp_revision`)
VALUES
	(7, 1, NULL, NULL, NULL, 100, 50, 10, 5, NULL, NULL, NULL, NULL, 3, NULL, 3, 0, 0);
	
INSERT INTO `xdevice` (`id`, `version`, `ip`, `name`, `x`, `y`, `area_id`, `company_id`, `authId`, `code`, `phoneNo`, `termId`, `status`, `latitude`, `longitude`, `support_power_alarm`)
VALUES
	(4, 0, NULL, '测试设备', NULL, NULL, NULL, 1, NULL, 'Jingsu66011', '66011', NULL, 1, NULL, NULL, '1');

	
INSERT INTO `xsensor` (`id`, `version`, `code`, `collectTime`, `humidity`, `maxHumidity`, `maxTemp`, `minHumidity`, `minTemp`, `name`, `temperature`, `x`, `y`, `device_id`, `status`, `sensor_index`, `hum_revision`, `temp_revision`)
VALUES
	(8, 0, NULL, NULL, NULL, 100, 50, 10, 5, NULL, NULL, NULL, NULL, 4, NULL, 0, 0, 0);

INSERT INTO `xcompany_alarm_phone_number` (`company_id`, `alarm_phone_number`) values(1, '15308039727');

CREATE TABLE MONDATA_015098884572 like MONDATA;
CREATE TABLE MONDATA_015098884582 like MONDATA;
CREATE TABLE MONDATA_015098884592 like MONDATA;