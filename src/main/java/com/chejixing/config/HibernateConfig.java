package com.chejixing.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

@Configuration
public class HibernateConfig {

	@Bean
	public LocalSessionFactoryBean createSessionFactory(DataSource ds) {
		LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
		sf.setDataSource(ds);
		sf.setPackagesToScan("com.chejixing.biz.bean");
		sf.getHibernateProperties().setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		sf.getHibernateProperties().setProperty("hibernate.show_sql", "false");
		sf.getHibernateProperties().setProperty("hibernate.format_sql", "false");

		return sf;
	}
}
