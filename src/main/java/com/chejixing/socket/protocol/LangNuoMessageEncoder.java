package com.chejixing.socket.protocol;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LangNuoMessageEncoder extends ProtocolEncoderAdapter {
	private static Logger logger = LoggerFactory.getLogger(LangNuoMessageEncoder.class);
	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		if(message instanceof String){
			IoBuffer buf = IoBuffer.allocate(20);
	        buf.put(message.toString().getBytes());
	        buf.flip();
			out.write(buf);
			
			logger.debug("String message:{} is sent.", message);
		}
		else if(message instanceof IoBuffer){
			session.write(message);
			logger.debug("IoBuffer messge is sent, message hexdump is:{}", ((IoBuffer)message).getHexDump());
		}else{
			logger.error("Unknown response message type:{}", message.getClass().getName());
			throw new IllegalArgumentException("Unknown response message type!");
		}
		
		out.flush();

	}

}
