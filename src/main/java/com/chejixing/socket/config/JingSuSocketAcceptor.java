package com.chejixing.socket.config;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chejixing.socket.JingsuMessageHandler;
import com.chejixing.socket.protocol.JingsuMessageDecoder;
import com.chejixing.socket.protocol.JingsuMessageEncoder;

@Configuration
public class JingSuSocketAcceptor {
	@Value("${port.jingsu}")
	private int port;
	@SuppressWarnings("unused")
	private LoggingFilter loggingFilter;
	private JingsuMessageDecoder decoder;
	private JingsuMessageEncoder encoder;
	private JingsuMessageHandler handler;

	@Bean(name = "jingsuSocketAcceptor", initMethod = "bind", destroyMethod = "unbind")
	public NioSocketAcceptor getAcceptor() {
		ProtocolCodecFilter codecFilter = new ProtocolCodecFilter(encoder, decoder);

		DefaultIoFilterChainBuilder builder = new DefaultIoFilterChainBuilder();
		builder.addFirst("executor", new ExecutorFilter(100));
		builder.addLast("codecFilter", codecFilter);
		builder.addLast("logger", new LoggingFilter());

		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		acceptor.setDefaultLocalAddress(new InetSocketAddress(port));
		acceptor.setHandler(handler);
		acceptor.setFilterChainBuilder(builder);
		acceptor.setReuseAddress(true);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		return acceptor;
	}

	@Autowired
	public void setLoggingFilter(LoggingFilter loggingFilter) {
		this.loggingFilter = loggingFilter;
	}

	@Autowired
	public void setDecoder(JingsuMessageDecoder decoder) {
		this.decoder = decoder;
	}

	@Autowired
	public void setEncoder(JingsuMessageEncoder encoder) {
		this.encoder = encoder;
	}

	@Autowired
	public void setHandler(JingsuMessageHandler handler) {
		this.handler = handler;
	}

}
