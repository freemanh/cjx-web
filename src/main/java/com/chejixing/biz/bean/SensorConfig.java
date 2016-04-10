package com.chejixing.biz.bean;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SensorConfig {
	@Column(name = "temp_revision", nullable = false)
	private double tempRevision = 0.0;
	@Column(name = "hum_revision", nullable = false)
	private double humRevision = 0.0;
	@Column(name = "upload_frequency")
	private int uploadFrequency = 1;
	@Column(name = "alarm_mode")
	private int alarmMode = 0;

	public SensorConfig(double tempRevision, double humRevision, int uploadFrequency) {
		super();
		this.tempRevision = tempRevision;
		this.humRevision = humRevision;
		this.uploadFrequency = uploadFrequency;
	}

	public SensorConfig(double tempRevision, double humRevision, int uploadFrequency, int alarmMode) {
		super();
		this.tempRevision = tempRevision;
		this.humRevision = humRevision;
		this.uploadFrequency = uploadFrequency;
		this.alarmMode = alarmMode;
	}


	public SensorConfig() {
		super();
	}

	public double getTempRevision() {
		return tempRevision;
	}

	public void setTempRevision(double tempRevision) {
		this.tempRevision = tempRevision;
	}

	public double getHumRevision() {
		return humRevision;
	}

	public void setHumRevision(double humRevision) {
		this.humRevision = humRevision;
	}

	public int getUploadFrequency() {
		return uploadFrequency;
	}

	public void setUploadFrequency(int uploadFrequency) {
		this.uploadFrequency = uploadFrequency;
	}

	public int getAlarmMode() {
		return alarmMode;
	}

	public void setAlarmMode(int alarmMode) {
		this.alarmMode = alarmMode;
	}
}
