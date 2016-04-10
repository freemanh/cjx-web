package com.chejixing.biz.service;

import com.chejixing.biz.bean.XCompany;
import com.chejixing.biz.bean.XDevice;
import com.chejixing.biz.bean.XSensor;

public class BaseDataSetup {
	protected XCompany company;
	protected XDevice device1;
	protected XDevice device2;
	protected XSensor sensorOfDevice1;
	protected XSensor sensorOfDevice2;

	public void setupData(){
		company = new XCompany("test company");
		company.setId(1l);
		
		device1 = new XDevice(company, "device1", "13000000000", "001");
		device1.setId(1l);
		sensorOfDevice1 = new XSensor("sensor1", device1);
		sensorOfDevice1.setId(1l);
		
		
		device2 = new XDevice(company, "device2", "13100000000", "002");
		device2.setId(2l);
		sensorOfDevice2 = new XSensor("sensor2", device2);
		sensorOfDevice2.setId(2l);
	}
}
