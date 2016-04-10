package com.chejixing.socket.parser;

import org.apache.mina.proxy.utils.ByteUtilities;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberParser {
	public String getPhoneNumber(byte[] bytes){
		return ByteUtilities.asHex(bytes);
	}
}
