package com.chejixing.socket.message;

public class GeneralResp {
	private int serial;
	private int id;
	private byte result = 0;
	
	public GeneralResp(int serial, int id, byte result) {
		super();
		this.serial = serial;
		this.id = id;
		this.result = result;
	}
	public int getSerial() {
		return serial;
	}
	public void setSerial(int serial) {
		this.serial = serial;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public byte getResult() {
		return result;
	}
	public void setResult(byte result) {
		this.result = result;
	}
	
	
}
