package com.chejixing.config;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@PropertySource("classpath:config/application.properties")
public class DataSourceConfig {
	private String user;
	private String password;
	private String host;
	private String port;
	private String schema;
	private final String extraParam = "?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8";

	@Bean(name = "dataSource")
	@Profile(value = { "default" })
	public DataSource createDataSource() throws PropertyVetoException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		ds.setUser(user);
		ds.setPassword(password);
		ds.setJdbcUrl(getJdbcUrl(host, port, schema) + extraParam);
		ds.setDriverClass("com.mysql.jdbc.Driver");
		return ds;
	}

	@Bean(name = "dataSource")
	@Profile(value = { "test" })
	public DataSource setupITDataSource() throws PropertyVetoException, SQLException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		ds.setUser(user);
		ds.setPassword(password);
		ds.setJdbcUrl(getJdbcUrl(host, port, schema) + extraParam);
		ds.setDriverClass("com.mysql.jdbc.Driver");
		
		Connection conn = ds.getConnection();
		conn.setAutoCommit(true);
		ScriptUtils.executeSqlScript(conn, new ClassPathResource("/sql/drop_create_schema.sql"));
		ScriptUtils.executeSqlScript(conn, new ClassPathResource("/sql/schema.sql"));
		ScriptUtils.executeSqlScript(conn, new ClassPathResource("/sql/commons.sql"));
		conn.close();
		
		return ds;
	}

	private String getJdbcUrl(String host, String port, String schema) {
		return "jdbc:mysql://" + host + ":" + port + "/" + schema;
	}
	
	@Value("${jdbc.username}")
	public void setUser(String user) {
		this.user = user;
	}

	@Value("${jdbc.pwd}")
	public void setPassword(String password) {
		this.password = password;
	}

	@Value("${jdbc.host}")
	public void setHost(String host) {
		this.host = host;
	}

	@Value("${jdbc.port}")
	public void setPort(String port) {
		this.port = port;
	}

	@Value("${jdbc.schema}")
	public void setSchema(String schema) {
		this.schema = schema;
	}

}
