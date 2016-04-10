package com.chejixing.socket.message;

import java.util.Date;

public class JingsuDeviceMessage extends GeneralDeviceMessage {
	private boolean poweroff;
	private int failCount;
	private Date date;

	public JingsuDeviceMessage(String deviceId, double temp, double hum, boolean poweroff, int failCount, Date date) {
		super(deviceId, temp, hum);
		this.poweroff = poweroff;
		this.failCount = failCount;
		this.date = date;
	}

	public JingsuDeviceMessage(String deviceId, double temp, double hum, boolean poweroff, int failCount) {
		super(deviceId, temp, hum);
		this.poweroff = poweroff;
		this.failCount = failCount;
		this.date = new Date();
	}

	@Override
	public String toString() {
		return "JingsuDeviceMessage [poweroff=" + poweroff + ", failCount=" + failCount + ", date=" + date + ", deviceId=" + deviceId + ", temp=" + temp + ", hum=" + hum + "]";
	}

	public boolean isPoweroff() {
		return poweroff;
	}

	public void setPoweroff(boolean poweroff) {
		this.poweroff = poweroff;
	}

	public int getFailCount() {
		return failCount;
	}

	public Date getDate() {
		return date;
	}
}
