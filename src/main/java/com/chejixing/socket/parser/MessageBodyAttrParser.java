package com.chejixing.socket.parser;

import org.springframework.stereotype.Component;

@Component
public class MessageBodyAttrParser {
	
	public boolean isSeparatedPack(byte first, byte second){
		// Message body is 2 byte length, the 13th bit represents if this message is long message which should be send separately.
		return ((first & 0x20) == 0?false:true);
	}
	
	public boolean isEncrypt(byte first, byte second){
		// bit10 to bit 12 is identifier for encryption. when all of them are zero, the message is un-encrypted. when bit 10 is 1, it represents
		// RSA encryption is used
		return (first & 0x04) == 0? false: true;
	}
	
	public int getBodyLength(byte first, byte second){
		return ((first & 0x03) << 8) + (second & 0xff);
	}
}
