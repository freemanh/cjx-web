package com.chejixing.socket.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationAppendixParserFactory {
	private LocationAppendixParser locationEFAppendixParser;
	private LocationAppendixParser locationFEAppendixParser;
	private LocationAppendixParser uselessAppendixParser;

	public LocationAppendixParser getParser(byte messageId) {
		switch (messageId) {
		case (byte) 0xef:
			return locationEFAppendixParser;
		case (byte) 0xfe:
			return locationFEAppendixParser;
		default: 
			return uselessAppendixParser;
		}
	}

	@Autowired
	public void setLocationEFAppendixParser(
			LocationAppendixParser locationEFAppendixParser) {
		this.locationEFAppendixParser = locationEFAppendixParser;
	}

	@Autowired
	public void setLocationFEAppendixParser(
			LocationAppendixParser locationFEAppendixParser) {
		this.locationFEAppendixParser = locationFEAppendixParser;
	}
	@Autowired
	public void setUselessAppendixParser(
			LocationAppendixParser uselessAppendixParser) {
		this.uselessAppendixParser = uselessAppendixParser;
	}
	
	
}
