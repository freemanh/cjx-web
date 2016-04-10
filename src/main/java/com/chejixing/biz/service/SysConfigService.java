package com.chejixing.biz.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chejixing.biz.bean.XSysConfig;

@Service
public class SysConfigService {
	private SessionFactory sessionFactory;
	
	public XSysConfig getSysConfig(){
		return (XSysConfig) this.sessionFactory.getCurrentSession().createQuery("from XSysConfig").uniqueResult();
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
}
