package com.chejixing.biz.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chejixing.biz.bean.Alarm;
import com.chejixing.biz.bean.AlarmType;
import com.chejixing.biz.bean.DeviceStatus;
import com.chejixing.biz.bean.XDevice;
import com.chejixing.biz.bean.XSensor;
import com.chejixing.biz.core.AlarmCreationVoter;
import com.chejixing.dao.AlarmDao;

@Component
public class AlarmChecker {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(AlarmChecker.class);

	private AlarmDao alarmDao;
	private AlarmMessageSender alarmMessageSender;
	@Autowired
	private AlarmCreationVoter voter;

	public void checkPowerOffAlarm(XDevice device, boolean powerOff) {
		int count = getOldPowerOffAlarmCount(device);

		if (voter.shouldCreatePowerOffAlarm(device.isSupportPowerAlarm(), powerOff, count)) {
			device.setStatus(DeviceStatus.POWER_OFF);
			Alarm alarm = new Alarm(device, AlarmType.POWEROFF);
			alarmDao.save(alarm);
			alarmMessageSender.exec(alarm);
		}
		if (!powerOff) {
			device.setStatus(DeviceStatus.NORMAL);
			alarmDao.clearAlarm(device, AlarmType.POWEROFF);
		}
	}

	private int getOldPowerOffAlarmCount(XDevice device) {
		List<Alarm> oldAlarms = alarmDao.getNotClearedAlarms(device, AlarmType.POWEROFF);
		return oldAlarms.size();
	}

	public void checkTempAlarm(XSensor sensor, double temp) {
		boolean isOverHeat = isOver(temp, sensor.getMinTemp(), sensor.getMaxTemp());
		sensor.setOverheat(isOverHeat);

		int oldCount = getOldAlarmCount(sensor, AlarmType.OVER_HEAT);
		if (voter.shouldCreateAlarm(isOverHeat, oldCount)) {
			Alarm alarm = new Alarm(sensor.getTemperature(), sensor, sensor.getMinTemp(), sensor.getMaxTemp(), AlarmType.OVER_HEAT);
			alarmDao.save(alarm);
			alarmMessageSender.exec(alarm);
		}

		if (!isOverHeat) {
			alarmDao.clearAlarm(sensor, AlarmType.OVER_HEAT);
		}

	}

	private int getOldAlarmCount(XSensor sensor, AlarmType type) {
		List<Alarm> oldAlarms = alarmDao.getNotClearedAlarms(sensor, type);
		return oldAlarms.size();
	}

	public void checkHumAlarm(XSensor sensor, double hum) {
		boolean isOverHum = isOver(hum, sensor.getMinHumidity(), sensor.getMaxHumidity());
		sensor.setOverhum(isOverHum);

		int oldCount = getOldAlarmCount(sensor, AlarmType.OVER_HUM);
		if (voter.shouldCreateAlarm(isOverHum, oldCount)) {
			Alarm alarm = new Alarm(hum, sensor, sensor.getMinHumidity(), sensor.getMaxHumidity(), AlarmType.OVER_HUM);
			alarmDao.save(alarm);
			alarmMessageSender.exec(alarm);
		}

		if (!isOverHum) {
			alarmDao.clearAlarm(sensor, AlarmType.OVER_HUM);
		}
	}

	public boolean isOver(double value, double min, double max) {
		return (value < min || value > max) ? true : false;
	}

	@Autowired
	public void setAlarmDao(AlarmDao alarmDao) {
		this.alarmDao = alarmDao;
	}

	@Autowired
	public void setAlarmMessageSender(AlarmMessageSender alarmMessageSender) {
		this.alarmMessageSender = alarmMessageSender;
	}

}
