package com.chejixing.biz.bean;

import java.util.Date;

public class XMonData {
	private Long id;
	private String deviceCode;
	private int sensorIndex;
	private Double temperature;
	private Double humidity;
	private Date collectTime;
	
	public XMonData(long id, String deviceCode, int sensorIndex,
			Double temperature, Double humidity, Date collectTime) {
		super();
		this.id = id;
		this.deviceCode = deviceCode;
		this.sensorIndex = sensorIndex;
		this.temperature = temperature;
		this.humidity = humidity;
		this.collectTime = collectTime;
	}
	
	public XMonData(String deviceCode, int sensorIndex, Double temperature,
			Double humidity, Date collectTime) {
		super();
		this.deviceCode = deviceCode;
		this.sensorIndex = sensorIndex;
		this.temperature = temperature;
		this.humidity = humidity;
		this.collectTime = collectTime;
	}



	public XMonData() {
		super();
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}


	public Double getTemperature() {
		return this.temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Date getCollectTime() {
		return this.collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}

	public int getSensorIndex() {
		return sensorIndex;
	}

	public void setSensorIndex(int sensorIndex) {
		this.sensorIndex = sensorIndex;
	}

	public Double getHumidity() {
		return humidity;
	}

	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
