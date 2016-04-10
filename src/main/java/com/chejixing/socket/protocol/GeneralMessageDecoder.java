package com.chejixing.socket.protocol;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public abstract class GeneralMessageDecoder extends CumulativeProtocolDecoder {
	private static final Logger logger = LoggerFactory.getLogger(GeneralMessageDecoder.class);

	@Override
	protected boolean doDecode(IoSession session, IoBuffer buf, ProtocolDecoderOutput out) throws Exception {
		logger.debug("Message bytes received: {}", buf.getHexDump());
		if (buf.remaining() < getMessageLength()) {
			logger.debug("not enough byte to parse, the remaining is:{}", buf.remaining());
			return false;
		}
		
		IoBuffer messageBuf = buf.getSlice(buf.position() , getMessageLength());
		if(messageBuf.get() != 0x00){
			throw new IllegalStateException("message should start with 0x00!");
		}
		messageBuf.rewind();
		
		out.write(this.parse(messageBuf));
		buf.skip(getMessageLength());
		
		if(buf.remaining() < getMessageLength()){
			return false;
		}else{
			return true;
		}
	}
	
	protected abstract Object parse(IoBuffer messageBuf);

	protected abstract int getMessageLength();

}
