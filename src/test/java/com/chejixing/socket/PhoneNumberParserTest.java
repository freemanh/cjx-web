package com.chejixing.socket;

import static org.junit.Assert.*;

import org.junit.Test;

import com.chejixing.socket.parser.PhoneNumberParser;

public class PhoneNumberParserTest {
	@Test
	public void test(){
		byte[] bytes = new byte[]{0x01, 0x53, 0x08, 0x03, 0x79, 0x27};
		
		PhoneNumberParser p = new PhoneNumberParser();
		assertEquals("015308037927", p.getPhoneNumber(bytes));
	}
}
