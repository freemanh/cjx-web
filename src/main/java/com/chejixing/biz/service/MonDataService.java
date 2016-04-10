package com.chejixing.biz.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chejixing.biz.bean.XDevice;
import com.chejixing.biz.bean.XMonData;
import com.chejixing.biz.bean.XSensor;
import com.chejixing.dao.DeviceDao;
import com.chejixing.dao.MonDataDao;

@Service
public class MonDataService {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(MonDataService.class);
	private MonDataDao monDataDao;
	private DeviceDao deviceDao;
	private AlarmChecker alarmChecker;

	@Transactional
	public void add(String deviceCode, Double temp, Double hum, Date collectTime, boolean powerOff) {
		XDevice device = deviceDao.getByCode(deviceCode);
		alarmChecker.checkPowerOffAlarm(device, powerOff);

		if (null == temp && null == hum) {
			logger.warn("Received temperature and humidity is both null! The monitor data will not be saved!");
		} else {
			List<XSensor> sensors = device.getSensors();
			if (sensors.size() == 2) {
				// some special devices will install two temp sensor, not the
				// standard one temp sensor, one hum sensor
				logger.info("Processing special only temp sensors device");
				XSensor firstSensor = sensors.get(0);
				add(temp, null, collectTime, device, firstSensor, 0);

				XSensor secondSensor = sensors.get(1);
				add(hum, null, collectTime, device, secondSensor, 1);
			} else {
				XSensor sensor = device.getSensors().iterator().next();
				add(temp, hum, collectTime, device, sensor, 0);
			}
		}

	}

	private void add(Double temp, Double hum, Date collectTime, XDevice device, XSensor sensor, int sensorIndex) {
		if (null == sensor.getCollectTime() || collectTime.after(sensor.getCollectTime())) {
			sensor.setCollectTime(collectTime);
			if (null != temp) {
				sensor.setTemperature(temp);
				alarmChecker.checkTempAlarm(sensor, temp);
			}
			if (null != hum) {
				sensor.setHumidity(hum);
				alarmChecker.checkHumAlarm(sensor, hum);
			}
		}

		XMonData monData = new XMonData(device.getCode(), sensorIndex, sensor.getTemperature(), sensor.getHumidity(), collectTime);
		monDataDao.save(monData);
	}

	@Transactional
	public void add(String deviceCode, List<Double> tempList, List<Double> humList, Date collectTime, boolean powerOff) {
		XDevice device = deviceDao.getByCode(deviceCode);
		alarmChecker.checkPowerOffAlarm(device, powerOff);

		if (tempList.size() != humList.size()) {
			throw new IllegalArgumentException("temp list size is:" + tempList.size() + ", but hum list size is:" + humList.size() + ", they should match!");
		}
		for (int i = 0; i < tempList.size(); i++) {
			XSensor sensor = device.getSensors().get(i);
			if (null == sensor) {
				logger.warn("The device:{} has no sensor at index:{}, the temp will not be saved!");
				continue;
			}
			if (null != tempList.get(i) && null != humList.get(i)) {
				Double temp = tempList.get(i) + sensor.getConfig().getTempRevision();
				Double hum = humList.get(i) + sensor.getConfig().getHumRevision();
				add(temp, hum, collectTime, device, sensor, i);
			} else {
				logger.warn("The temp or hum in sensor {} is null!", i);
			}

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
	public void setAlarmChecker(AlarmChecker alarmChecker) {
		this.alarmChecker = alarmChecker;
	}

}
