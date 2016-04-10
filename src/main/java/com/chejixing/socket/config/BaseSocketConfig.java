package com.chejixing.socket.config;

import org.apache.mina.filter.logging.LoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseSocketConfig {
	@Bean(name="loggingFilter")
	public LoggingFilter getLoggingFilter(){
		return new LoggingFilter();
	}
}
