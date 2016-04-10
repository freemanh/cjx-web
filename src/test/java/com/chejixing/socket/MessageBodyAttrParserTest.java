package com.chejixing.socket;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.chejixing.socket.parser.MessageBodyAttrParser;

public class MessageBodyAttrParserTest {
	private MessageBodyAttrParser p;
	
	@Before
	public void before(){
		p = new MessageBodyAttrParser();
	}

	@Test
	public void testNotSeparate(){
		assertFalse(p.isSeparatedPack((byte)0x00, (byte)0x00));
		assertFalse(p.isSeparatedPack((byte)0x00, (byte)0x00));
	}
	
	@Test
	public void testSeparate(){
		assertTrue(p.isSeparatedPack((byte)0x20, (byte)0x00));
		assertTrue(p.isSeparatedPack((byte)0xf0, (byte)0x00));
	}
	
	@Test
	public void testNotEncrypted(){
		assertFalse(p.isEncrypt((byte)0x00, (byte)0x00));
		assertFalse(p.isEncrypt((byte)0xe3, (byte)0x00));
	}
	
	@Test
	public void testEncrypted(){
		assertTrue(p.isEncrypt((byte)0x04, (byte)0x00));
		assertTrue(p.isEncrypt((byte)0xff, (byte)0x00));
	}
	
	@Test
	public void testBodyLength(){
		assertEquals(0, p.getBodyLength((byte)0x00, (byte)0x00));
		assertEquals(1023, p.getBodyLength((byte)0x03, (byte)0xff));
	}
	
	
}
