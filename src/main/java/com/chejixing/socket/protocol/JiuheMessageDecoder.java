package com.chejixing.socket.protocol;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chejixing.socket.SocketUtils;
import com.chejixing.socket.message.MessageHeader;
import com.chejixing.socket.parser.MessageBodyAttrParser;
import com.chejixing.socket.parser.MessageBodyParser;
import com.chejixing.socket.parser.MessageBodyParserFactory;
import com.chejixing.socket.parser.PhoneNumberParser;

@Component
public class JiuheMessageDecoder extends CumulativeProtocolDecoder {
	private static Logger logger = LoggerFactory.getLogger(JiuheMessageDecoder.class);
	private PhoneNumberParser phoneNumberParser;
	private MessageBodyParserFactory messageBodyParserFactory;
	private MessageBodyAttrParser attrParser;
	
	private Object parse(IoBuffer buf){
		logger.debug("original bytes[]：{}", buf.getHexDump());
		buf.skip(1);
		buf.limit(buf.limit() - 1);
		
		buf = SocketUtils.unescape(buf);
		logger.debug("unescaped bytes：{}", buf.getHexDump());
		
		int id = buf.getUnsignedShort();
		logger.debug("id:" + id);
		
		byte firstByte = buf.get();
		byte secondByte = buf.get();
		boolean seperated = attrParser.isSeparatedPack(firstByte, secondByte);
		
		int bodyLength = attrParser.getBodyLength(firstByte, secondByte);
		logger.debug("message body length is：{}", bodyLength);
		
		byte[] phoneBytes = new byte[6];
		buf.get(phoneBytes);
		String phoneNumber = phoneNumberParser.getPhoneNumber(phoneBytes);
		logger.debug("phoneNumer：{}", phoneNumber);
		
		int serial = buf.getUnsignedShort();
		logger.debug("serial number：{}", serial);
		
		if(seperated){
			logger.warn("This is a seperated package!");
		}
		
		MessageHeader header = new MessageHeader(id, seperated, bodyLength, phoneNumber, serial);
		MessageBodyParser bodyParser = messageBodyParserFactory.getParser(id);
		
		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("header", header);
		msg.put("body", bodyParser.getBody(buf.getSlice(buf.position(), bodyLength)));
		buf.skip(bodyLength);
		return msg;
	}
	
	@Override
	protected boolean doDecode(IoSession session, IoBuffer buf,
			ProtocolDecoderOutput output) throws Exception {
		int start = buf.position();
		
		byte first = buf.get();
		if(first != 0x7e){
			throw new IllegalArgumentException("Jiuhe message should start with 0x7e!");
		}
		
		logger.debug("received a message from JiuHe device...");
		while (buf.hasRemaining()) {
			byte current = buf.get();
			if (current == 0x7e) {
				int oldLimit = buf.limit();

				buf.flip();
				output.write(this.parse(buf.slice()));

				buf.limit(oldLimit);
				buf.position(oldLimit);
				return true;
			}
		}
		buf.position(start);
		return false;
	}

	@Autowired
	public void setPhoneNumberParser(PhoneNumberParser phoneNumberParser) {
		this.phoneNumberParser = phoneNumberParser;
	}

	@Autowired
	public void setMessageBodyParserFactory(
			MessageBodyParserFactory messageBodyParserFactory) {
		this.messageBodyParserFactory = messageBodyParserFactory;
	}

	@Autowired
	public void setAttrParser(MessageBodyAttrParser attrParser) {
		this.attrParser = attrParser;
	}
	
	


}
