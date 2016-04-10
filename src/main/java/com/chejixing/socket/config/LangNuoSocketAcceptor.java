package com.chejixing.socket.config;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chejixing.socket.LangNuoMessageHandler;
import com.chejixing.socket.protocol.LangNuoMessageDecoder;
import com.chejixing.socket.protocol.LangNuoMessageEncoder;

@Configuration
public class LangNuoSocketAcceptor {

	@Value("${port.langnuo}")
	private int port;
	@SuppressWarnings("unused")
	private LoggingFilter loggingFilter;
	private LangNuoMessageEncoder encoder;
	private LangNuoMessageDecoder decoder;
	private LangNuoMessageHandler handler;

	@Bean(name="langnuoSocketAcceptor", initMethod = "bind", destroyMethod = "unbind")
	public NioSocketAcceptor getAcceptor() {
		ProtocolCodecFilter codecFilter = new ProtocolCodecFilter(encoder, decoder);

		DefaultIoFilterChainBuilder builder = new DefaultIoFilterChainBuilder();
		builder.addLast("codecFilter", codecFilter);

		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		acceptor.setDefaultLocalAddress(new InetSocketAddress(port));
		acceptor.setHandler(handler);
		acceptor.setFilterChainBuilder(builder);
		acceptor.setReuseAddress(true);
		return acceptor;
	}

	@Autowired
	public void setLoggingFilter(LoggingFilter loggingFilter) {
		this.loggingFilter = loggingFilter;
	}
	@Autowired
	public void setEncoder(LangNuoMessageEncoder encoder) {
		this.encoder = encoder;
	}
	@Autowired
	public void setDecoder(LangNuoMessageDecoder decoder) {
		this.decoder = decoder;
	}
	@Autowired
	public void setHandler(LangNuoMessageHandler handler) {
		this.handler = handler;
	}
	

}
