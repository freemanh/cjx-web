DELIMITER //

CREATE PROCEDURE `add_user`(IN `username` VARCHAR(50), IN `name` VARCHAR(50))
	LANGUAGE SQL
	NOT DETERMINISTIC
	CONTAINS SQL
	SQL SECURITY DEFINER
	COMMENT ''
BEGIN
	DECLARE new_company_id BIGINT;
	DECLARE new_user_id BIGINT;
	
	insert into xcompany(name) values(name);
	SET new_company_id = LAST_INSERT_ID();
	
	insert into xuser(name, username, pwd, company_id) values(name, username, username, new_company_id);
	SET new_user_id = LAST_INSERT_ID();
	insert into user_role(user_id, role_id) values(new_user_id, 1);
END//

DELIMITER ;