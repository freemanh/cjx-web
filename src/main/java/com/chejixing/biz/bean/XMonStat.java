package com.chejixing.biz.bean;

import javax.persistence.*;
import java.util.Date;

//监测数据统计中间表
//每天统计更新一次
@Table(name = "mon_stat")
public class XMonStat {
    //公司
    @ManyToOne
    @JoinColumn(name = "company_id")
    private XCompany company;
    //设备名称
    @Column(name = "device_code", nullable = false)
    private String deviceName;
    //传感器编号
    @Column(name = "sensor_index", nullable = false)
    private int sensorCode;
    //采集日期
    private Date collectTime;
    //最高温度
    private double maxTemp;
    //最低温度
    private double minTemp;
    //平均温度
    private double avgTemp;
    //最高湿度
    private double maxHum;
    //最低湿度
    private double minHum;
    //平均湿度
    private double avgHum;

    public XCompany getCompany() {
        return company;
    }

    public void setCompany(XCompany company) {
        this.company = company;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getSensorCode() {
        return sensorCode;
    }

    public void setSensorCode(int sensorCode) {
        this.sensorCode = sensorCode;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getAvgTemp() {
        return avgTemp;
    }

    public void setAvgTemp(double avgTemp) {
        this.avgTemp = avgTemp;
    }

    public double getMaxHum() {
        return maxHum;
    }

    public void setMaxHum(double maxHum) {
        this.maxHum = maxHum;
    }

    public double getMinHum() {
        return minHum;
    }

    public void setMinHum(double minHum) {
        this.minHum = minHum;
    }

    public double getAvgHum() {
        return avgHum;
    }

    public void setAvgHum(double avgHum) {
        this.avgHum = avgHum;
    }
}
