package com.chejixing.socket.parser;

import org.apache.mina.core.buffer.IoBuffer;

import com.chejixing.socket.LocationReportReqBuilder;

public interface LocationAppendixParser {
	public void parse(IoBuffer slice, LocationReportReqBuilder builder);
}
