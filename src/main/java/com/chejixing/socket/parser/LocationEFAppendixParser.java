package com.chejixing.socket.parser;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chejixing.socket.LocationReportReqBuilder;

@Component("locationEFAppendixParser")
public class LocationEFAppendixParser implements LocationAppendixParser {
	private static final Logger logger = LoggerFactory
			.getLogger(LocationEFAppendixParser.class);
	private TemperatureParser parser;

	@Override
	public void parse(IoBuffer buf, LocationReportReqBuilder builder) {
		byte flag = buf.get();
		if (flag != (byte)0xe6) {
			logger.warn("Location appendix 0xEF started with:{}, this appendix will be discarded", flag);
			return;
		}
		while(buf.hasRemaining()){
			byte id = buf.get();
			switch (id) {
			case 0x01:
				buf.skip(4);
				break;
			case 0x04:
				buf.skip(6);
				break;
			case 0x05:
				buf.skip(12);
				break;
			case 0x06:
				buf.skip(4);
				break;
			case 0x07:
				builder.addTemps(parser.parse(buf));
				break;
			case 0x09:
				buf.skip(1);
				break;
			}
		}
		

	}

	@Autowired
	public void setParser(TemperatureParser parser) {
		this.parser = parser;
	}
	
	
}
