package com.chejixing.biz.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chejixing.biz.bean.XDevice;
import com.chejixing.biz.bean.XMonData;
import com.chejixing.biz.bean.XSensor;
import com.chejixing.dao.DeviceDao;
import com.chejixing.dao.MonDataDao;

/**
 *
 */
@Service
public class ReplacingMonDataService {
	private MonDataDao monDataDao;
	private DeviceDao deviceDao;
	private CombinedDateGenerator dateGenerator;

	@Transactional
	public void replace(Date wrongDate, String wrongDeviceCode,
			short wrongSensorIndex, Date rightDate, String rightDeviceCode,
			short rightSensorIndex) {
		for(int i = 0;i < 24; i++){
			this.replace(wrongDate, i, wrongDeviceCode, wrongSensorIndex, rightDate, i, rightDeviceCode, rightSensorIndex);
		}
		
	}

	@Transactional
	public void replace(Date wrongDate, int wrongHour,
			String wrongDeviceCode, int wrongSensorIndex, Date rightDate,
			int rightHour, String rightDeviceCode, int rightSensorIndex) {
		XDevice wrongDevice = this.deviceDao.getByCode(wrongDeviceCode);
		XSensor wrongSensor = wrongDevice.getSensors().get(wrongSensorIndex);
		
		XDevice rightDevice = this.deviceDao.getByCode(rightDeviceCode);
		XSensor rightSensor = rightDevice.getSensors().get(rightSensorIndex);
		
		
		List<XMonData> wrongData = monDataDao.query(wrongSensor.getId(), wrongDate, wrongHour);
		for(XMonData wrong: wrongData){
			monDataDao.delete(wrong);
		}
		
		List<XMonData> rightData = monDataDao.query(rightSensor.getId(), rightDate, rightHour);
		for(XMonData right: rightData){
			XMonData newData = new XMonData();
			
			newData.setDeviceCode(wrongDevice.getCode());
			newData.setHumidity(right.getHumidity());
			newData.setSensorIndex(wrongSensorIndex);
			newData.setTemperature(right.getTemperature());
			newData.setCollectTime(this.dateGenerator.generate(right.getCollectTime(), wrongDate, wrongHour));
			
			monDataDao.save(newData);
		}
		
	}

	@Autowired
	public void setMonDataDao(MonDataDao monDataDao) {
		this.monDataDao = monDataDao;
	}

	@Autowired
	public void setDeviceDao(DeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}

	@Autowired
	public void setDateGenerator(CombinedDateGenerator dateGenerator) {
		this.dateGenerator = dateGenerator;
	}

	
}
