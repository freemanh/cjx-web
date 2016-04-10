package com.chejixing.biz.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="XHouse")
public class XHouse extends XBean implements Serializable {
	private static final long serialVersionUID = 4750312458387742189L;

	public XHouse() {
		super();
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof XHouse) {
			XHouse u = (XHouse) obj;
			Long uid = u.getId();
			if (uid != null && uid.equals(this.getId())) {
				return true;
			}
		}
		return false;
	}

	@ManyToOne
	@JoinColumn(name="company_id")
	private XCompany company;

	public void setCompany(XCompany company) {
		this.company = company;
	}

	public XCompany getCompany() {
		return this.company;
	}

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	private String address;

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	private String manager;

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getManager() {
		return this.manager;
	}

	private String phone;

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return this.phone;
	}
}
