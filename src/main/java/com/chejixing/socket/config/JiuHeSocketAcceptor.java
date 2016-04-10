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

import com.chejixing.socket.JiuheMessageHandler;
import com.chejixing.socket.protocol.JiuheMessageDecoder;
import com.chejixing.socket.protocol.JiuheMessageEncoder;

@Configuration
public class JiuHeSocketAcceptor {
	@Value("${port.jiuhe}")
	private int port;
	@SuppressWarnings("unused")
	private LoggingFilter loggingFilter;
	private JiuheMessageDecoder decoder;
	private JiuheMessageEncoder encoder;
	private JiuheMessageHandler handler;
	
	@Bean(name="jiuheSocketAcceptor", initMethod="bind", destroyMethod="unbind")
	public NioSocketAcceptor getAcceptor(){
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
	public void setDecoder(JiuheMessageDecoder decoder) {
		this.decoder = decoder;
	}
	@Autowired
	public void setEncoder(JiuheMessageEncoder encoder) {
		this.encoder = encoder;
	}
	@Autowired
	public void setHandler(JiuheMessageHandler handler) {
		this.handler = handler;
	}
}
