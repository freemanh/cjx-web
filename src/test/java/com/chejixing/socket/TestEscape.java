package com.chejixing.socket;


import org.apache.mina.core.buffer.IoBuffer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestEscape {
	private IoBuffer unescaped;
	private IoBuffer escaped;
	
	@Before
	public void before(){
		unescaped = IoBuffer.allocate(20);
		unescaped.put(new byte[]{0x30,0x7e,0x08,0x7d,0x55});
		unescaped.flip();
		
		escaped = IoBuffer.allocate(20);
		escaped.put(new byte[]{0x30,0x7d,0x02,0x08,0x7d,0x01,0x55});
		escaped.flip();
	}
	
	@Test
	public void test(){
		IoBuffer escaped = SocketUtils.escape(unescaped);
		Assert.assertEquals(this.escaped.getHexDump(), escaped.getHexDump());
	}
	
	@Test
	public void testUnescape(){
		IoBuffer unescaped = SocketUtils.unescape(escaped);
		Assert.assertEquals(this.unescaped.getHexDump(), unescaped.getHexDump());
	}
}
