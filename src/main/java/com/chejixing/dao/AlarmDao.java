package com.chejixing.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chejixing.biz.bean.Alarm;
import com.chejixing.biz.bean.AlarmType;
import com.chejixing.biz.bean.XDevice;
import com.chejixing.biz.bean.XSensor;

@Repository
public class AlarmDao {
	private SessionFactory factory;

	public void update(Alarm alarm) {
		factory.getCurrentSession().update(alarm);
	}

	public void save(Alarm alarm) {
		factory.getCurrentSession().persist(alarm);
	}

	@Autowired
	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

	@SuppressWarnings("unchecked")
	public List<Alarm> getNotClearedAlarms(XDevice device, AlarmType type) {
		return factory.getCurrentSession().createQuery("from Alarm where device=:device and clearTime is null and type=:type").setEntity("device", device)
				.setParameter("type", type).list();
	}

	@SuppressWarnings("unchecked")
	public List<Alarm> getNotClearedAlarms(XSensor sensor, AlarmType type) {
		return factory.getCurrentSession().createQuery("from Alarm where sensor=:sensor and clearTime is null and type=:type").setEntity("sensor", sensor)
				.setParameter("type", type).list();
	}

	public void clearAlarm(XSensor sensor, AlarmType type) {
		factory.getCurrentSession().createQuery("update Alarm set clearTime=:clearTime where sensor=:sensor and clearTime is null and type=:type").setEntity("sensor", sensor)
				.setParameter("type", type).setParameter("clearTime", new Date()).executeUpdate();
	}
	
	public void clearAlarm(XDevice device, AlarmType type) {
		factory.getCurrentSession().createQuery("update Alarm set clearTime=:clearTime where device=:device and clearTime is null and type=:type").setEntity("device", device)
				.setParameter("type", type).setParameter("clearTime", new Date()).executeUpdate();
	}
}
