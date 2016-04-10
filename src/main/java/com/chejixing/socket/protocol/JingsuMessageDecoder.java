package com.chejixing.socket.protocol;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.chejixing.socket.message.JingsuDeviceMessage;
import com.chejixing.socket.message.JingsuSettingResponse;

@Component
public class JingsuMessageDecoder extends CumulativeProtocolDecoder {

	private static final Logger logger = LoggerFactory.getLogger(JingsuMessageDecoder.class);

	@Override
	protected boolean doDecode(IoSession session, IoBuffer buf, ProtocolDecoderOutput out) throws Exception {
		if (buf.remaining() < 2) {
			logger.debug("Not enough bytes to parse, the remaining is:{}", buf.remaining());
			return false;
		}
		short header = buf.getShort();
		switch (header) {
		case 3: {
			return processReadingData(session, buf, out);
		}
		case 6: {
			return processWritingData(buf, out);
		}
		default: {
			throw new IllegalArgumentException(String.format("Unknow message header %#x", header));
		}
		}
	}

	private boolean processWritingData(IoBuffer buf, ProtocolDecoderOutput out) {
		logger.debug("Received a setting command response");
		if (buf.remaining() < 6) {
			return false;
		} else {
			buf.skip(6);
			JingsuSettingResponse resp = new JingsuSettingResponse();
			out.write(resp);
			return true;
		}
	}

	private boolean processReadingData(IoSession session, IoBuffer buf, ProtocolDecoderOutput out) throws ParseException {
		byte bodyLen = buf.get();
		// the last two bytes are CRC
		int remaining = bodyLen + 2;
		if (buf.remaining() < remaining) {
			logger.info("Not enough bytes to parse message body. The body length should be:{}, but it really is:{}", remaining, buf.remaining());
			return false;
		} else {
			short type = buf.getShort();

			switch (type) {
			case 35: {
				int id = buf.getInt();
				double temp = buf.getShort() * 0.1;
				double hum = buf.getShort() * 0.1;
				// battery
				buf.getShort();
				// signal
				buf.getShort();
				// fail count
				int failCount = buf.getShort();
				boolean poweroff = (buf.getUnsignedShort() == 1);
				// CRC
				buf.getShort();

				String deviceCode = "Jingsu" + id;
				out.write(new JingsuDeviceMessage(deviceCode, temp, hum, poweroff, failCount));

				break;
			}
			case 127: {
				int id = buf.getInt();
				String dateStr = String.format("%1$x-%2$x-%3$x", buf.get(), buf.get(), buf.get());
				String timeStr = String.format("%1$x:%2$x:%3$x", buf.get(), buf.get(), buf.get());
				double temp = buf.getShort() * 0.1;
				double hum = buf.getShort() * 0.1;
				short failCount = buf.getShort();
				// skip CRC
				buf.skip(2);
				String deviceCode = "Jingsu" + id;

				Date date = new SimpleDateFormat("yy-MM-dd HH:mm:ss").parse(dateStr + " " + timeStr);

				out.write(new JingsuDeviceMessage(deviceCode, temp, hum, false, failCount, date));
				break;
			}
			case 31: {
				// not support power off
				int id = buf.getInt();
				double temp = buf.getShort() * 0.1;
				double hum = buf.getShort() * 0.1;
				// battery
				buf.getShort();
				// signal
				buf.getShort();
				// fail count
				int failCount = buf.getShort();

				// CRC
				buf.getShort();
				String deviceCode = "Jingsu" + id;
				out.write(new JingsuDeviceMessage(deviceCode, temp, hum, false, failCount));
				break;
			}
			default: {
				logger.warn("Not very sure how to process this type of data:{}, using the same process as 0x23.", String.format("%1$#x", type));
				int id = buf.getInt();
				double temp = buf.getShort() * 0.1;
				double hum = buf.getShort() * 0.1;
				// battery
				buf.getShort();
				// signal
				buf.getShort();
				// fail count
				int failCount = buf.getShort();
				boolean poweroff = (buf.getUnsignedShort() == 1);
				// CRC
				buf.getShort();

				String deviceCode = "Jingsu" + id;
				out.write(new JingsuDeviceMessage(deviceCode, temp, hum, poweroff, failCount));
			}
			}
			return true;
		}
	}

	public static void main(String[] args) {
		byte a = 21;
		byte b = 5;
		byte c = 1;

		try {
			System.out.println(new SimpleDateFormat("yy-MM-dd").parse(String.format("%1$x-%2$x-%3$x", a, b, c)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
