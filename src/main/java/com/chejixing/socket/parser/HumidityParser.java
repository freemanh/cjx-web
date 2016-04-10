package com.chejixing.socket.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HumidityParser {
	private static final Logger logger = LoggerFactory.getLogger(HumidityParser.class);
	
	public List<Double> parse(IoBuffer buf) {
		List<Double> results = new ArrayList<Double>();
		
		buf.skip(1);
		for(int i = 0; i < 4; i++){
			int index = buf.get();
			
			logger.debug("sensor index：{}", index);
			int sensorStatus = buf.get();
			logger.debug("sensor status:{}：", sensorStatus);
			if(sensorStatus == 0){
				double humidity = buf.getUnsignedShort() * 0.1;
				results.add(index, humidity);
				logger.debug("sensor hum reading:{}", humidity);
			}else{
				buf.skip(2);
				results.add(index, null);
				logger.warn("sensor is abnormal with index{}！", index);
			}
		}
		return results;
	}

}
