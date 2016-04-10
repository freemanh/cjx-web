package com.chejixing.biz.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "XCompany")
public class XCompany extends XBean implements Serializable {
	private static final long serialVersionUID = 2404044466606347658L;

	public XCompany() {
		super();
	}
	
	public XCompany(String name) {
		super();
		this.name = name;
	}
	private String name;

	@ElementCollection
	@CollectionTable(name = "XCOMPANY_ALARM_PHONE_NUMBER", joinColumns = @JoinColumn(name = "company_id"))
	@Column(name="alarm_phone_number")
	private Set<String> alarmPhoneNumbers = new HashSet<String>();

	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof XCompany) {
			XCompany u = (XCompany) obj;
			Long uid = u.getId();
			if (uid != null && uid.equals(this.getId())) {
				return true;
			}
		}
		return false;
	}
	
	public Set<String> getAlarmPhoneNumbers() {
		return alarmPhoneNumbers;
	}

	public void setAlarmPhoneNumbers(Set<String> alarmPhoneNumbers) {
		this.alarmPhoneNumbers = alarmPhoneNumbers;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
