package com.chejixing.socket;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chejixing.biz.service.MonDataService;
import com.chejixing.socket.message.AuthReq;
import com.chejixing.socket.message.GeneralResp;
import com.chejixing.socket.message.HeartBeatReq;
import com.chejixing.socket.message.LocationReportReq;
import com.chejixing.socket.message.MessageHeader;
import com.chejixing.socket.message.RegisterReq;
import com.chejixing.socket.message.RegisterResp;

@Component
public class JiuheMessageHandler extends IoHandlerAdapter {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private MonDataService monDataService;

	private static Logger logger = LoggerFactory
			.getLogger(JiuheMessageHandler.class);

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> msg = (Map<String, Object>) message;

		MessageHeader reqHeader = (MessageHeader) msg.get("header");
		Object reqBody = msg.get("body");

		Object respBody = null;
		int id = -1;
		if (reqBody instanceof HeartBeatReq) {
			logger.info("开始生成心跳消息的响应包(通用响应)...");
			respBody = new GeneralResp(reqHeader.getSerial(), reqHeader.getId(), (byte) 0);
			id = 0x8001;
		} else if (reqBody instanceof RegisterReq) {
			logger.info("开始生成注册请求的响应包...");
			respBody = new RegisterResp(reqHeader.getSerial(), (byte) 0,
					"chejixing");
			id = 0x8100;
		} else if (reqBody instanceof AuthReq) {
			logger.info("开始生成鉴权请求的响应包...");
			respBody = new GeneralResp(reqHeader.getSerial(),
					reqHeader.getId(), (byte) 0);
			id = 0x8001;
		} else {
			logger.info("生成通用响应...");
			if (reqHeader.getId() == 0x0900) {
				respBody = new Date();
				id = 0x8900;
			} else {
				respBody = new GeneralResp(reqHeader.getSerial(),
						reqHeader.getId(), (byte) 0);
				id = 0x8001;
			}
		}

		logger.info("生成响应消息头...");
		String phone = reqHeader.getPhone();
		MessageHeader respHeader = new MessageHeader(id, false, -1, phone,
				reqHeader.getSerial());

		Map<String, Object> resp = new HashMap<String, Object>();
		resp.put("header", respHeader);
		resp.put("body", respBody);

		session.write(resp);

		if (reqBody instanceof LocationReportReq) {
			logger.info("更新当前温湿度信息...");
			LocationReportReq req = (LocationReportReq) reqBody;
			try {
				monDataService.add(phone, req.getTemperatures(), req.getHumidities(), req.getCollectTime(), req.isDeviceOff());
			} catch (Exception e) {
				logger.error("更新当前温湿度数据出现异常", e);
			}
			// by zjh:保存位置信息
			try {
				// monDataService.saveLocation(phone,req);
			} catch (Exception ex) {
				logger.error("保存位置信息失败", ex);
			}
		}

		logger.info("当前消息处理完毕...");
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		logger.warn("catch a exception!", cause);
		session.close(true);
	}

}
