package com.chejixing.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chejixing.biz.bean.XDevice;

@Repository
public class DeviceDao {
	private SessionFactory factory;

	public XDevice getByCode(String code) {
		return (XDevice) factory.getCurrentSession()
				.createQuery("from XDevice where code=:code")
				.setParameter("code", code).uniqueResult();
	}
	
	public int getSensorCount(String deviceCode){
		return (int) factory.getCurrentSession().createQuery(
				"select count(sensor.id) from XDevice as device join device.sensors as sensor where device.code=:deviceCode")
				.setParameter("deviceCode", deviceCode).uniqueResult();
	}
	
	public void update(XDevice device){
		factory.getCurrentSession().update(device);
	}

	@Autowired
	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

}
