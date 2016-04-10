package com.chejixing.biz.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="XArea")
public class XArea extends XBean implements Serializable {
	private static final long serialVersionUID = 7920556131364530968L;

	public XArea() {
		super();
	}

	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof XArea) {
			XArea u = (XArea) obj;
			Long uid = u.getId();
			if (uid != null && uid.equals(this.getId())) {
				return true;
			}
		}
		return false;
	}

	@ManyToOne
	@JoinColumn(name="house_id")
	private XHouse house;

	public void setHouse(XHouse house) {
		this.house = house;
	}

	public XHouse getHouse() {
		return this.house;
	}

	private Integer x;

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getX() {
		return this.x;
	}

	private Integer y;

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getY() {
		return this.y;
	}
}
