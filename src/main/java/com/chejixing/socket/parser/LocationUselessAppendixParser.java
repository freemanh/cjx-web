package com.chejixing.socket.parser;

import org.apache.mina.core.buffer.IoBuffer;
import org.springframework.stereotype.Component;

import com.chejixing.socket.LocationReportReqBuilder;

@Component("uselessAppendixParser")
public class LocationUselessAppendixParser implements LocationAppendixParser {

	@Override
	public void parse(IoBuffer buf, LocationReportReqBuilder builder) {}

}
