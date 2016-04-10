package com.chejixing.socket.parser;

import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chejixing.socket.LocationReportReqBuilder;

@Component("locationFEAppendixParser")
public class LocationFEAppendixParser implements LocationAppendixParser{
	private static final Logger logger = LoggerFactory.getLogger(LocationFEAppendixParser.class);
	
	private HumidityParser parser = null;
	
	
	@Override
	public void parse(IoBuffer slice, LocationReportReqBuilder builder) {
		byte flag = slice.get();
		if (flag != (byte)0xe6) {
			logger.warn("Location appendix 0xFE started with:{}, this appendix will be discarded", flag);
			return;
		}
		
		while(slice.hasRemaining()){
			byte id = slice.get();
			logger.debug("0xFE extend message id:{}", id);
			int partialLength = slice.getUnsignedShort();
			
			switch (id) {
			case 16:
				logger.debug("starting parse the humidities...");
				List<Double> humidities = parser.parse(slice);
				builder.addHumidities(humidities);
				break;
			default:
					slice.skip(partialLength);
			}
		}
	}

	
	@Autowired
	public void setParser(HumidityParser parser) {
		this.parser = parser;
	}
}