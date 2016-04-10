package com.chejixing.biz.bean;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "XSensor")
public class XSensor extends XBean implements Serializable {
	private static final long serialVersionUID = -6789135497725736266L;
	private Double temperature;
	private Double humidity;
	private String name;
	private Integer x;
	private Integer y;
	@ManyToOne
	@JoinColumn(name = "device_id")
	private XDevice device;
	@Column(nullable = false)
	private Double maxTemp = 50.0;
	@Column(nullable = false)
	private Double minTemp = 5.0;
	@Column(nullable = false)
	private Double maxHumidity = 100.0;
	@Column(nullable = false)
	private Double minHumidity = 10.0;
	@Enumerated(EnumType.ORDINAL)
	private SensorStatus status;
	@Temporal(TemporalType.TIMESTAMP)
	private Date collectTime;
	@Column(name = "is_over_heat")
	private boolean overheat = false;
	@Column(name = "is_over_hum")
	private boolean overhum = false;
	@Column(name = "last_update_time", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateTime;
	@Column(name = "is_synced")
	private boolean synced = true;
	@Embedded
	private SensorConfig config;

	public XSensor() {
		super();
		config = new SensorConfig();
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public XSensor(String name, XDevice device) {
		super();
		this.name = name;

		device.getSensors().add(this);
		this.device = device;
	}

	/**
	 * @return
	 * @deprecated TODO: remove from biz code to presentation code
	 */
	public String getCode() {
		String code = "";
		if (device != null) {
			code = device.getCode();
		}
		code += this.name;
		return code;
	}

	public SensorStatus getStatus() {
		return status;
	}

	public void setStatus(SensorStatus status) {
		this.status = status;
	}

	public Double getHumidity() {
		return humidity;
	}

	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof XSensor) {
			XSensor u = (XSensor) obj;
			Long uid = u.getId();
			if (uid != null && uid.equals(this.getId())) {
				return true;
			}
		}
		return false;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getX() {
		return this.x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return this.y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public XDevice getDevice() {
		return this.device;
	}

	public void setDevice(XDevice device) {
		this.device = device;
	}

	public Double getMaxTemp() {
		return this.maxTemp;
	}

	public void setMaxTemp(Double maxTemp) {
		this.maxTemp = maxTemp;
	}

	public Double getMinTemp() {
		return this.minTemp;
	}

	public void setMinTemp(Double minTemp) {
		this.minTemp = minTemp;
	}

	public Double getMaxHumidity() {
		return this.maxHumidity;
	}

	public void setMaxHumidity(Double maxHumidity) {
		this.maxHumidity = maxHumidity;
	}

	public Double getMinHumidity() {
		return this.minHumidity;
	}

	public void setMinHumidity(Double minHumidity) {
		this.minHumidity = minHumidity;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}

	public boolean isOverheat() {
		return overheat;
	}

	public void setOverheat(boolean overheat) {
		this.overheat = overheat;
	}

	public boolean isOverhum() {
		return overhum;
	}

	public void setOverhum(boolean overhum) {
		this.overhum = overhum;
	}

	public boolean isSynced() {
		return synced;
	}

	public void setSynced(boolean synced) {
		this.synced = synced;
	}

	public SensorConfig getConfig() {
		return config;
	}

	public void setConfig(SensorConfig config) {
		this.config = config;
	}

}
