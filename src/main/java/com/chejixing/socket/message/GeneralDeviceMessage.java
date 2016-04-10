package com.chejixing.socket.message;

public class GeneralDeviceMessage {
	protected String deviceId;
	protected double temp;
	protected double hum;
	
	public GeneralDeviceMessage(String deviceId, double temp, double hum) {
		super();
		this.deviceId = deviceId;
		this.temp = temp;
		this.hum = hum;
	}
	
	public GeneralDeviceMessage() {
		super();
	}


	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public double getTemp() {
		return temp;
	}
	public void setTemp(double temp) {
		this.temp = temp;
	}
	public double getHum() {
		return hum;
	}
	public void setHum(double hum) {
		this.hum = hum;
	}
	
}
