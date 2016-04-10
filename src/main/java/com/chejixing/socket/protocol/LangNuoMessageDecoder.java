package com.chejixing.socket.protocol;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.chejixing.socket.message.GeneralDeviceMessage;

@Component
public class LangNuoMessageDecoder extends GeneralMessageDecoder {
	private static final Logger logger = LoggerFactory.getLogger(LangNuoMessageDecoder.class);
	
	@Override
	protected int getMessageLength() {
		return 19;
	}

	@Override
	protected Object parse(IoBuffer buf) {
		logger.debug("starting decode LangNuo message...");
		buf.skip(3);
		short identifier = buf.getShort();
		logger.info("device identifier:{}", identifier);
		int id = buf.getInt();
		logger.info("device id:{}", id);
		double temp = buf.getShort() * 0.1;
		logger.info("temp:{}", temp);
		double hum = buf.getShort() * 0.1;
		logger.info("hum:{}", hum);
		short battery = buf.getShort();
		logger.info("battery:{}", battery);
		short signal = buf.getShort();
		logger.info("signal:{}", signal);
		
		String deviceId = "Langnuo" + id;
		return new GeneralDeviceMessage(deviceId, temp, hum);
	}

}
