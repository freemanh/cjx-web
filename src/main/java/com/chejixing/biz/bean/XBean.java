package com.chejixing.biz.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class XBean {
	public XBean() {
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof XBean) {
			XBean u = (XBean) obj;
			Long uid = u.getId();
			if (uid != null && uid.equals(this.getId())) {
				return true;
			}
		}
		return false;
	}
}
