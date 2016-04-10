package com.chejixing.socket;

import static org.junit.Assert.*;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.junit.Test;

import com.chejixing.socket.parser.AuthMessageParser;
import com.chejixing.socket.parser.MessageBodyParser;

public class AuthMessageParserTest {
	private AuthMessageParser parser;
	private IoBuffer buf;
	private String realContent;

	@Test
	public void test(){
		given();
		
		// when
		String body = (String) parser.getBody(buf);
		
		// then
		assertEquals(realContent, body);
	}

	public void given() {
		realContent = "test";
		
		buf = IoBuffer.allocate(100);
		// put one byte to stimulate the real situation.
		buf.put((byte)0x11);
		buf.put(realContent.getBytes(Charset.forName(MessageBodyParser.STRING_CHARSET)));
		// put the end tag
		buf.put((byte)0x00);
		// put more content which should not be parsed
		buf.put("end".getBytes(Charset.forName(MessageBodyParser.STRING_CHARSET)));
		buf.flip();
		// set position to 1
		buf.get();
		
		parser = new AuthMessageParser();
	}
}
