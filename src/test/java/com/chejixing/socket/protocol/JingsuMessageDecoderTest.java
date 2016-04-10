package com.chejixing.socket.protocol;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.chejixing.socket.message.JingsuDeviceMessage;

public class JingsuMessageDecoderTest {
	private IoSession session;
	private ProtocolDecoderOutput out;
	private JingsuMessageDecoder decoder;
	private IoBuffer buf;

	@Before
	public void before() {
		session = mock(IoSession.class);
		out = mock(ProtocolDecoderOutput.class);
		decoder = new JingsuMessageDecoder();
		buf = IoBuffer.allocate(50);
	}

	@Test
	public void testNotEnoughBytes() {
		buf.put(new byte[] { 0x00 });
		buf.flip();
		try {
			boolean result = decoder.doDecode(session, buf, out);
			assertFalse(result);
			assertEquals(0, buf.position());
			verify(out, never()).write(anyObject());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testOneMessage() throws Exception {
		// given
		buf.put(new byte[] { 0x00, 0x03, 0x12 });
		buf.put(new byte[] { 0x00, 0x23 });
		buf.put(new byte[] { 0x00, 0x00, 0x03, (byte) 0xeb });
		buf.put(new byte[] { 0x00, (byte) 0xf3 });
		buf.put(new byte[] { 0x00, (byte) 0x29 });
		buf.put(new byte[] { 0x00, (byte) 0xa4 });
		buf.put(new byte[] { 0x00, 0x17 });
		buf.put(new byte[] { 0x00, 0x00 });
		buf.put(new byte[] { 0x00, 0x01 });
		buf.put(new byte[] { 0x00, 0x00 });
		buf.flip();
		
		// when
		boolean result = decoder.doDecode(session, buf, out);
		
		// then
		assertTrue(result);
		assertEquals(0, buf.remaining());
		
		ArgumentCaptor<Object> messageCaptor = ArgumentCaptor.forClass(Object.class);
		verify(out).write(messageCaptor.capture());
		
		JingsuDeviceMessage msg = (JingsuDeviceMessage) messageCaptor.getValue();
		assertEquals("Jingsu1003" ,msg.getDeviceId());
		assertEquals(24.3, msg.getTemp(), 0.01);
		assertEquals(4.1, msg.getHum(), 0.01);
	}
}
