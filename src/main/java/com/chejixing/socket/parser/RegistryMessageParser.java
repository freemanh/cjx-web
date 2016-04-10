package com.chejixing.socket.parser;

import java.io.UnsupportedEncodingException;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.chejixing.socket.message.RegisterReq;

@Component
@Qualifier("registryMessageParser")
public class RegistryMessageParser implements MessageBodyParser {
	private static Logger logger = LoggerFactory.getLogger(RegistryMessageParser.class);
	
	@Override
	public Object getBody(IoBuffer buf) {
		// ignore province information
		buf.skip(4);
		
		byte[] manufactureBytes = new byte[5];
		buf.get(manufactureBytes);
		String manufacture;
		try {
			manufacture = new String(manufactureBytes, "iso-8859-1");
			logger.debug("manufacture code：{}", manufacture);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		
		// ignore device type
		buf.skip(8);
		
		byte[] deivceIdBytes = new byte[7];
		buf.get(deivceIdBytes);
		String deviceId;
		try {
			deviceId = new String(deivceIdBytes, "iso-8859-1");
			logger.debug("device id:{}", deviceId);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		
		// ignore vehicle plate color
		buf.skip(1);
		
		String registrationIdent = null;
		try {
			registrationIdent = buf.getString(Charset.forName("gbk").newDecoder());
			logger.debug("vehicle registration identifier:{}", registrationIdent);
		} catch (CharacterCodingException e) {
			throw new RuntimeException(e);
		}
		
		RegisterReq request = new RegisterReq(manufacture, deviceId);
		return request;
	}

}
