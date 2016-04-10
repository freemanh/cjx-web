package com.chejixing.socket.message;

public class RegisterReq {

	private String terminalId;
	private String manufacture;

	public RegisterReq(String manufacture, String terminalId) {
		this.manufacture = manufacture;
		this.terminalId = terminalId;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getManufacture() {
		return manufacture;
	}

	public void setManufacture(String manufacture) {
		this.manufacture = manufacture;
	}
	
}
