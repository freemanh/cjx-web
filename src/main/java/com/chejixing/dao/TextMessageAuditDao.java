package com.chejixing.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chejixing.biz.bean.XTextMessageAudit;

@Repository
public class TextMessageAuditDao {
	private SessionFactory sessionFactory;
	
	public void save(XTextMessageAudit t){
		sessionFactory.getCurrentSession().persist(t);
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
}
