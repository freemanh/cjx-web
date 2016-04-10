package com.chejixing.socket;

import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chejixing.biz.service.MonDataService;
import com.chejixing.socket.message.GeneralDeviceMessage;

@Component
public class LangNuoMessageHandler  extends IoHandlerAdapter {
	private MonDataService monDataService;

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
		session.write("ALLSU");
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		super.messageReceived(session, message);
		GeneralDeviceMessage msg = (GeneralDeviceMessage) message;
		//TODO confirm if this kind of device support power off alarm
		monDataService.add(msg.getDeviceId(), msg.getTemp(), msg.getHum(), new Date(), false);
		session.write("RECEIVE");
	}
	@Autowired
	public void setMonDataService(MonDataService monDataService) {
		this.monDataService = monDataService;
	}
}
