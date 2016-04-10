package com.chejixing.socket.protocol;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.chejixing.socket.message.JingsuSettingCommand;
import com.chejixing.socket.message.JingsuSettingType;
import com.chejixing.util.CRC16Modbus;

@Component
public class JingsuMessageEncoder extends ProtocolEncoderAdapter {
	private static final Logger logger = LoggerFactory.getLogger(JingsuMessageEncoder.class);

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		if (message instanceof String) {
			IoBuffer buf = IoBuffer.allocate(20);
			buf.put(message.toString().getBytes());
			buf.flip();
			logger.debug("Encoded message hex:{}", buf.getHexDump());
			out.write(buf);
		} else if (message instanceof JingsuSettingCommand) {
			JingsuSettingCommand cmd = (JingsuSettingCommand) message;

			IoBuffer buf = IoBuffer.allocate(1024);
			buf.put((byte) 0x00);
			// write command
			buf.put((byte) 0x06);
			// slot 0
			if (cmd.getType().equals(JingsuSettingType.TEMP)) {
				buf.putShort((short) 151);
			} else if (cmd.getType().equals(JingsuSettingType.FREQUECE)) {
				buf.putShort((short) 45);
			} else if (cmd.getType().equals(JingsuSettingType.HUMIDITY)) {
				buf.putShort((short) 152);
			} else if (cmd.getType().equals(JingsuSettingType.ALARM_MODE)) {
				buf.putShort((short) 94);
			} else {
				throw new IllegalArgumentException("Unknow setting type:" + cmd.getType());
			}

			buf.putShort(cmd.getValue());

			buf.flip();
			CRC16Modbus crc = new CRC16Modbus();
			while (buf.hasRemaining()) {
				crc.update(buf.get());
			}

			buf.limit(buf.limit() + 2);
			buf.put(crc.getCrcBytes());

			buf.flip();

			logger.debug("Encoded message bytes:{}", buf.getHexDump());
			out.write(buf);
		} else {
			throw new IllegalArgumentException("Unknow response message type:" + message.getClass());
		}

		out.flush();

		// IoBuffer buf = IoBuffer.allocate(128);
		// buf.put(message.toString().getBytes());
		//
		// //统计流量
		// //统计流量
		// String ip =
		// ((InetSocketAddress)session.getRemoteAddress()).getAddress().getHostAddress();
		// IoCounter.inst.setInc(ip + ".send", buf.position());
		//
		// buf.flip();
		// out.write(buf);
		// out.flush();
		// logger.warn("发送给竞速的响应消息:" + message);
		// logger.warn("发送给竞速的响应字节流:{}",buf.getHexDump());
	}

}
