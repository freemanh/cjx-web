package com.chejixing.socket.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageBodyParserFactory {
	private MessageBodyParser heartBeatMessageParser;
	private MessageBodyParser registryMessageParser;
	private MessageBodyParser authMessageParser;
	private MessageBodyParser locationParser;
	
	public MessageBodyParser getParser(int messageId) {
		MessageBodyParser parser = null;

		switch (messageId) {
		case 2:
			parser = heartBeatMessageParser;
			break;
		case 256:
			parser = registryMessageParser;
			break;
		case 258:
			parser = authMessageParser;
			break;
		case 512:
			parser = locationParser;
			break;
		default:
		}
		if(null == parser){
			throw new IllegalArgumentException("No message body parser con be found for this unknow message id:" + messageId);
		}
		
		return parser;
	}

	@Autowired
	public void setHeartBeatMessageParser(MessageBodyParser heartBeatMessageParser) {
		this.heartBeatMessageParser = heartBeatMessageParser;
	}

	@Autowired
	public void setRegistryMessageParser(MessageBodyParser registryMessageParser) {
		this.registryMessageParser = registryMessageParser;
	}

	@Autowired
	public void setAuthMessageParser(MessageBodyParser authMessageParser) {
		this.authMessageParser = authMessageParser;
	}

	@Autowired
	public void setLocationMessageParser(MessageBodyParser locationParser) {
		this.locationParser = locationParser;
	}
	
	
}
