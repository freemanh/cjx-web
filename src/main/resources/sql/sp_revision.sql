DELIMITER //

CREATE PROCEDURE `CloseRevision`(IN `username` VARCHAR(50))
	LANGUAGE SQL
	NOT DETERMINISTIC
	CONTAINS SQL
	SQL SECURITY DEFINER
	COMMENT '关闭修正值'
BEGIN
	update xuser set is_show_revision=0 where xuser.username=username;
END//

CREATE PROCEDURE `OpenRevision`(IN `username` VARCHAR(50))
	LANGUAGE SQL
	NOT DETERMINISTIC
	CONTAINS SQL
	SQL SECURITY DEFINER
	COMMENT '打开修正值'
BEGIN
	update xuser set is_show_revision=1 where xuser.username=username;
END//