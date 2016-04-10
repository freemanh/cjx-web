package com.chejixing.socket.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocationReportReq {
	private double lat;
	private double lon;
	private int eleviation;
	private double speed;
	private int direction;
	private Date collectTime;
	private List<Double> temperatures = new ArrayList<Double>();
	private List<Double> humidities = new ArrayList<Double>();

    //add by zjh:告警标志
    private int alarm;

    public int getAlarm() {
        return alarm;
    }

    public void setAlarm(int alarm) {
        this.alarm = alarm;
    }
    //是否为断电告警
    public boolean isDeviceOff(){
        return ((alarm>>8)&0x01)==0x01;

    }

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public int getEleviation() {
		return eleviation;
	}

	public void setEleviation(int eleviation) {
		this.eleviation = eleviation;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}

	public List<Double> getTemperatures() {
		return temperatures;
	}

	public void setTemperatures(List<Double> temperatures) {
		this.temperatures = temperatures;
	}

	public List<Double> getHumidities() {
		return humidities;
	}

	public void setHumidities(List<Double> humidities) {
		this.humidities = humidities;
	}

}
