package com.chejixing.biz.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "XTextMessage_Audit")
public class XTextMessageAudit extends XBean{
	
	private boolean success = true;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="send_time")
	private Date sendTime = new Date();
	private String message;
	private String phone;
	private String feedback;
	
	public XTextMessageAudit(boolean success, String message, String phone,
			String feedback) {
		super();
		this.success = success;
		this.message = message;
		this.phone = phone;
		this.feedback = feedback;
	}
	
	public XTextMessageAudit() {
		super();
	}

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	
}
