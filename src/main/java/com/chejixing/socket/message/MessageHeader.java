package com.chejixing.socket.message;

public class MessageHeader {
	private int id;
	private boolean seperated;
	private int length;
	private String phone;
	private int serial;

	public MessageHeader(int id, boolean seperated, int length, String phone,
			int serial) {
		super();
		this.id = id;
		this.seperated = seperated;
		this.length = length;
		this.phone = phone;
		this.serial = serial;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isSeperated() {
		return seperated;
	}
	public void setSeperated(boolean seperated) {
		this.seperated = seperated;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getSerial() {
		return serial;
	}
	public void setSerial(int serial) {
		this.serial = serial;
	}
	
	
	
}
