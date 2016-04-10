DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `OpenRevision`()
	LANGUAGE SQL
	NOT DETERMINISTIC
	CONTAINS SQL
	SQL SECURITY DEFINER
	COMMENT '打开修正值'
BEGIN
	update xsys_config set showRevision=1;
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `CloseRevision`()
	LANGUAGE SQL
	NOT DETERMINISTIC
	CONTAINS SQL
	SQL SECURITY DEFINER
	COMMENT '关闭修正值'
BEGIN
	update xsys_config set showRevision=0;
END