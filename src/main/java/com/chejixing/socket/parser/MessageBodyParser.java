package com.chejixing.socket.parser;

import org.apache.mina.core.buffer.IoBuffer;

public interface MessageBodyParser {
	public static final String STRING_CHARSET = "GBK";
	Object getBody(IoBuffer buf);
}
