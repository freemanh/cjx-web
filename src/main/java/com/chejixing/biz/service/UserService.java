package com.chejixing.biz.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chejixing.biz.bean.XCompany;
import com.chejixing.biz.bean.XUser;

@Service
public class UserService {
	private SessionFactory sessionFactory;
	
	public XCompany getCompanyByUsername(String username){
		Session session = sessionFactory.getCurrentSession();
		XUser user = (XUser) session.createQuery("from XUser where username=?").setString(0, username).uniqueResult();
		return user.getCompany();
	}
	
	public XUser getByUsername(String username){
		Session session = sessionFactory.getCurrentSession();
		return (XUser) session.createQuery("from XUser where username=?").setString(0, username).uniqueResult();
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
