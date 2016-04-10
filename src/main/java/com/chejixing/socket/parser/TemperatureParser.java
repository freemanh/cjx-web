package com.chejixing.socket.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TemperatureParser {
	private static final Logger logger = LoggerFactory.getLogger(TemperatureParser.class);
	
	public List<Double> parse(IoBuffer buf) {
		logger.trace("original temperature data:{}", buf.getSlice(buf.position(), 17).getHexDump());
		
		List<Double> temps = new ArrayList<Double>();
		
		// skip temp type
		buf.skip(1);
		for(int i = 0; i < 4; i++){
			byte status = buf.get();
			if(status == 0){
				boolean positive = (buf.get() == 0 ? true:false);
				int temp = buf.getUnsignedShort() ;
				if(!positive){
					temp = 0 - temp;
				}
				temps.add(temp * 0.1);
				logger.debug("sensor index:{}, temp:{}", i, temp);
			}else{
				// sensor is abnormal, don't read
				buf.skip(3);
				temps.add(null);
				logger.debug("sensor is abnormal with index:{}", i);
			}
		}
		return temps;
	}

}
