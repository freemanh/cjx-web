package com.chejixing.socket.message;

public class RegisterResp {
	private int serial;
	private byte result = 0;
	private String auth = "chejixing";
	
	public RegisterResp(int serial, byte result, String auth) {
		super();
		this.serial = serial;
		this.result = result;
		this.auth = auth;
	}
	public int getSerial() {
		return serial;
	}
	public void setSerial(int serial) {
		this.serial = serial;
	}
	public byte getResult() {
		return result;
	}
	public void setResult(byte result) {
		this.result = result;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	
	
}
