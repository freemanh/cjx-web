package com.chejixing.socket.parser;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("authMessageParser")
public class AuthMessageParser implements MessageBodyParser {
	private static final Logger logger = LoggerFactory.getLogger(AuthMessageParser.class);
	
	@Override
	public Object getBody(IoBuffer buf) {
		try {
			String auth = buf.getString(Charset.forName(MessageBodyParser.STRING_CHARSET).newDecoder());
			logger.debug("auth code:" + auth);
			return auth;
		} catch (CharacterCodingException e) {
			throw new RuntimeException(e);
		}
	}
}
