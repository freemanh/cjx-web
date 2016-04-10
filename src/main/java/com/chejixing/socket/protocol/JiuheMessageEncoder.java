package com.chejixing.socket.protocol;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.chejixing.socket.SocketUtils;
import com.chejixing.socket.message.GeneralResp;
import com.chejixing.socket.message.MessageHeader;
import com.chejixing.socket.message.RegisterResp;

@Component
@Qualifier("messageEncoder")
public class JiuheMessageEncoder extends ProtocolEncoderAdapter{
	private static Logger logger = LoggerFactory.getLogger(JiuheMessageEncoder.class);
	
	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		logger.info("开始编码响应消息...");
		if(message instanceof String){
			out.write(message.toString().getBytes());
		}
		
		@SuppressWarnings("unchecked")
		Map<String, Object> msg = (Map<String, Object>)message;
		
		MessageHeader header = (MessageHeader) msg.get("header");
		
		Object body = msg.get("body");
		IoBuffer bodyBuffer = IoBuffer.allocate(1024);
		bodyBuffer.setAutoExpand(true);
		int bodyLength = 0;
		if(body instanceof GeneralResp){
			logger.info("编码通用响应消息体...");
			GeneralResp resp = (GeneralResp) body;
			bodyBuffer.putUnsignedShort(resp.getSerial());
			bodyBuffer.putUnsignedShort(resp.getId());
			bodyBuffer.put(resp.getResult());
			
		}else if(body instanceof RegisterResp){
			logger.info("编码注册响应消息体...");
			RegisterResp resp = (RegisterResp) body;
			bodyBuffer.putUnsignedShort(resp.getSerial());
			bodyBuffer.put(resp.getResult());
			bodyBuffer.put(resp.getAuth().getBytes(Charset.forName("gbk")));
		}else if(body instanceof Date){
			logger.info("编码授时协议消息体...");
			String str = new SimpleDateFormat("yyMMddHHmmss").format(body);
			for(int i = 0;i < 6 ; i++){
				String temp = str.substring(i * 2, i * 2 + 2);
				byte high = Byte.valueOf(temp.substring(0, 1));
				byte low = Byte.valueOf(temp.substring(1));
				int r = (high << 4) | low;
				bodyBuffer.put((byte)r);
			}
			bodyBuffer.put((byte)0);
			
		}
		bodyBuffer.flip();
		
		bodyLength = bodyBuffer.limit();
		logger.info("消息体编码后长度：" + bodyLength);
		header.setLength(bodyLength);
		
		IoBuffer respBuffer = IoBuffer.allocate(1024);
		respBuffer.setAutoExpand(true);
		respBuffer.put((byte)0x7e);
		respBuffer.putUnsignedShort(header.getId());
		respBuffer.put((byte)0x00);
		respBuffer.putUnsigned((byte)bodyLength);
		
		String phone = header.getPhone();
		for(int i = 0; i < phone.length(); i++){
			String str1 = phone.substring(i, i + 1);
			i++;
			String str2 = phone.substring(i, i + 1);
			
			byte int1 = Byte.valueOf(str1);
			byte int2 = Byte.valueOf(str2);
			
			byte r = (byte)((int1 << 4) | int2);
			respBuffer.put(r);
		}
		
		respBuffer.putUnsignedShort(header.getSerial());
		respBuffer.put(bodyBuffer);
		respBuffer.flip();
		
		respBuffer.skip(1);
		byte check = respBuffer.get();
		while(respBuffer.hasRemaining()){
			byte current = respBuffer.get();
			check ^= current;
		}
		respBuffer.put(check);
		respBuffer.put((byte)0x7e);
		respBuffer.flip();
		logger.debug("响应消息内容:" + respBuffer.getHexDump());
		
		respBuffer = SocketUtils.escape(respBuffer);
		logger.debug("转意后的消息内容：" + respBuffer.getHexDump());
		
		out.write(respBuffer);
		logger.info("响应消息编码完毕");
		out.flush();
	}

}
