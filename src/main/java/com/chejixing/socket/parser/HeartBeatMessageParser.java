package com.chejixing.socket.parser;

import org.apache.mina.core.buffer.IoBuffer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.chejixing.socket.message.HeartBeatReq;

@Component
@Qualifier("heartBeanMessageParser")
public class HeartBeatMessageParser implements MessageBodyParser{

	@Override
	public Object getBody(IoBuffer buf) {
		return new HeartBeatReq();
	}

}
