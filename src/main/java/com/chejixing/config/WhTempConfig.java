package com.chejixing.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import com.chejixing.message.MessagerClient;

@Configuration
@ImportResource(value = { "classpath:/config/applicationContext.xml", "classpath:/config/applicationContext-mvc.xml" })
public class WhTempConfig {
	private SessionFactory sessionFactory;
	private DataSource dataSource;
	private String messageClientId;
	private String messageUrl;

	@Bean
	public static PropertySourcesPlaceholderConfigurer registerPropertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean(name = "txManager")
	public HibernateTransactionManager createTx() {
		HibernateTransactionManager tx = new HibernateTransactionManager();
		tx.setSessionFactory(sessionFactory);
		return tx;
	}

	@Bean
	public UserDetailsService createUserService() {
		JdbcDaoImpl impl = new JdbcDaoImpl();
		impl.setAuthoritiesByUsernameQuery("select user.username, role.role from user_role as ur join xuser as user on ur.user_id=user.id join xrole as role on ur.role_id=role.id  where user.username =?");
		impl.setUsersByUsernameQuery("select username,pwd,enabled from xuser where username=?");
		impl.setDataSource(dataSource);
		return impl;
	}

	@Bean(name = "messagerClient")
	@Profile("default")
	public MessagerClient getMessageClient() {
		return new MessagerClient(messageClientId, messageUrl);
	}

	@Bean(name = "messagerClient")
	@Profile("test")
	public MessagerClient getMessageClientForTest() {
		class FakeMessagerClient extends MessagerClient {
			public FakeMessagerClient(String cpid, String url) {
				super(cpid, url);
			}

			@Override
			public String send(String tel, String content) throws IOException {
				System.out.println("Fake client is sending text message.");
				return "success";
			}

		}
		return new FakeMessagerClient(messageClientId, messageUrl);
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Value("${textmessage.cpid}")
	public void setCpid(String cpid) {
		this.messageClientId = cpid;
	}

	@Value("${textmessage.url}")
	public void setUrl(String url) {
		this.messageUrl = url;
	}

}
