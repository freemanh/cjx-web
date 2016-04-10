DELIMITER //

CREATE PROCEDURE `deloy_new_device`(IN `username` VARCHAR(50), IN `device_code` VARCHAR(50), IN `sensorCount` TINYINT)
	LANGUAGE SQL
	NOT DETERMINISTIC
	CONTAINS SQL
	SQL SECURITY DEFINER
	COMMENT ''
BEGIN
	DECLARE company_id BIGINT DEFAULT -1;
	DECLARE new_device_id BIGINT;
	DECLARE sensorIndex TINYINT DEFAULT 0;
	DECLARE tableName VARCHAR(20);

	SET tableName = concat('mondata_', device_code);
	SET @createTableText = concat('CREATE TABLE ', tableName, ' LIKE mondata');
	
	PREPARE stmt FROM @createTableText;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	
	SELECT u.company_id into company_id FROM xuser u where u.username=username;
	
	INSERT INTO xdevice(`version`, name, company_id, code, phoneNo) values(0, '新设备', company_id, device_code, device_code);

	SET new_device_id = LAST_INSERT_ID();
	
	WHILE sensorIndex < sensorCount DO
		INSERT INTO xsensor(version, name, sensor_index, device_id) values(0, '新传感器', sensorIndex, new_device_id);
		SET sensorIndex = sensorIndex + 1;
	END WHILE;
	
END //

DELIMITER ;