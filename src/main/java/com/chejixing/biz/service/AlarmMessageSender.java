package com.chejixing.biz.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chejixing.biz.bean.Alarm;
import com.chejixing.biz.bean.XCompany;
import com.chejixing.biz.bean.XTextMessageAudit;
import com.chejixing.dao.TextMessageAuditDao;
import com.chejixing.message.MessagerClient;

@Component
public class AlarmMessageSender {
	private static final Logger logger = LoggerFactory.getLogger(AlarmMessageSender.class);
	
	private MessagerClient messagerClient;
	private TextMessageAuditDao textMessageAuditDao;

	public void exec(Alarm alarm) {
		XCompany company = alarm.getDevice().getCompany();
		for (String tel : company.getAlarmPhoneNumbers()) {
			String content = alarm.getFormattedMessage();
			String resp = null;
			try {
				resp = this.messagerClient.send(tel, content);
			} catch (IOException e) {
				logger.error("Failed to send text message!", e);
				resp = "exception happend, please check log file to find more details.";
			}
			
			boolean success = resp.startsWith("success") ? true : false;
			XTextMessageAudit audit = new XTextMessageAudit(success, content, tel, resp);
			textMessageAuditDao.save(audit);
		}
	}

	@Autowired
	public void setMessagerClient(MessagerClient messagerClient) {
		this.messagerClient = messagerClient;
	}

	@Autowired
	public void setTextMessageAuditDao(TextMessageAuditDao textMessageAuditDao) {
		this.textMessageAuditDao = textMessageAuditDao;
	}

}
