package com.chejixing.socket;

import static org.junit.Assert.*;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.junit.Test;

import com.chejixing.socket.message.RegisterReq;
import com.chejixing.socket.parser.RegistryMessageParser;

public class RegistryMessageParserTest {
	private RegistryMessageParser parser;
	private IoBuffer buf;

	@Test
	public void test(){
		given();
		
		// when
		Object req = parser.getBody(buf);
		
		// then
		assertEquals(RegisterReq.class, req.getClass());
		
		RegisterReq rr = (RegisterReq)req;
		assertEquals("ABC1234", rr.getTerminalId());
	}

	public void given() {
		buf = IoBuffer.allocate(100);
		putProvince();
		putManufacturer();
		putDeviceType();
		putDeviceId();
		putLicensePlateColor();
		putVehicleRegistrationIdentifier();
		buf.flip();
		
		parser = new RegistryMessageParser();
	}

	public void putVehicleRegistrationIdentifier() {
		buf.put("川AHL214".getBytes(Charset.forName("gbk")));
		buf.put((byte)0x00);
	}

	public void putLicensePlateColor() {
		buf.put((byte)0x01);
	}

	public void putDeviceId() {
		String deviceId = "ABC1234";
		buf.put(deviceId.getBytes(Charset.forName("ISO-8859-1")));
	}

	public void putManufacturer() {
		buf.put("cjxcj".getBytes(Charset.forName("ISO-8859-1")));
	}

	public void putProvince() {
		buf.putShort((short) 61);
		buf.putShort((short) 0);
	}

	public void putDeviceType() {
		buf.put("00000000".getBytes(Charset.forName("ISO-8859-1")));
	}
}
