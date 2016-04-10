package com.chejixing.biz.bean;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "XDevice")
public class XDevice extends XBean implements Serializable {
    private static final long serialVersionUID = 319280099016411634L;
    private String termId;
    private String authId;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private XCompany company;
    private String name;
    private String ip;
    @ManyToOne
    @JoinColumn(name = "area_id")
    private XArea area;
    @OneToMany(mappedBy = "device",fetch=FetchType.EAGER)
    @OrderColumn(name = "sensor_index")
    private List<XSensor> sensors = new ArrayList<XSensor>();
    private Integer x;
    private Integer y;
    private String phoneNo;
    private String code;
    @Enumerated
    private DeviceStatus status = DeviceStatus.NORMAL;
    private Double longitude;
    private Double latitude;
    @Column(name="support_power_alarm")
    private boolean supportPowerAlarm = true;

    public XDevice() {
        super();
    }
    
    public XDevice(XCompany company, String name, String phoneNo, String code) {
		super();
		this.company = company;
		this.name = name;
		this.phoneNo = phoneNo;
		this.code = code;
	}

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public XCompany getCompany() {
        return company;
    }

    public void setCompany(XCompany company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public XArea getArea() {
        return area;
    }

    public void setArea(XArea area) {
        this.area = area;
    }

    public List<XSensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<XSensor> sensors) {
        this.sensors = sensors;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

	public DeviceStatus getStatus() {
		return status;
	}

	public void setStatus(DeviceStatus status) {
		this.status = status;
	}

	public boolean isSupportPowerAlarm() {
		return supportPowerAlarm;
	}

	public void setSupportPowerAlarm(boolean supportPowerAlarm) {
		this.supportPowerAlarm = supportPowerAlarm;
	}

}
