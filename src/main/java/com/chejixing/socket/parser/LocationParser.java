package com.chejixing.socket.parser;

import java.text.ParseException;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.proxy.utils.ByteUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.chejixing.socket.LocationReportReqBuilder;

@Component
@Qualifier("locationParser")
public class LocationParser implements MessageBodyParser {
	private static final Logger logger = LoggerFactory.getLogger(LocationParser.class);
	private LocationAppendixParserFactory factory;
	
	@Override
	public Object getBody(IoBuffer buf) {
		int alarm = buf.getInt();
		logger.debug("alarm code：{}", alarm);
		
		int status = buf.getInt();
		logger.debug("status：{}", status);
		
		int lat = buf.getInt();
		logger.debug("lat：{}", lat);
		
		int lon = buf.getInt();
		logger.debug("longitute：{}", lon);
		
		int altitude = buf.getShort();
		logger.debug("altitude：{}", altitude);
		
		int speed = buf.getShort();
		logger.debug("speed：{}", speed);
		
		int direction = buf.getShort();
		logger.debug("vehicle head direction：{}", direction);
		
		byte[] timeBytes = new byte[6];
		buf.get(timeBytes);
		String time = ByteUtilities.asHex(timeBytes);
		logger.debug("collect time:" + time);
		
		LocationReportReqBuilder builder = new LocationReportReqBuilder();
		try {
			builder.setCollectTime(time);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		builder.setDirection(direction);
		builder.setAltitude(altitude);
		builder.setLat(lat);
		builder.setLon(lon);
		builder.setSpeed(speed);

		while (buf.hasRemaining()) {
			byte id = buf.get();
			logger.debug("location suffix message id：{}", id);

			byte length = buf.get();
			logger.debug("location suffix message length：{}", length);
			if(length < 0){
				throw new RuntimeException("location suffix message length should be greater than 0!");
			}
			
			LocationAppendixParser appendixParser = factory.getParser(id);
			appendixParser.parse(buf.getSlice(buf.position(), length), builder);
			buf.skip(length);
		}
		
		return builder.getResult();
	}

	@Autowired
	public void setFactory(LocationAppendixParserFactory factory) {
		this.factory = factory;
	}

}
