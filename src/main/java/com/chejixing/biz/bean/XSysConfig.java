package com.chejixing.biz.bean;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "XSYS_CONFIG")
public class XSysConfig extends XBean{
	
	private boolean showRevision = true;

	public boolean isShowRevision() {
		return showRevision;
	}

	public void setShowRevision(boolean showRevision) {
		this.showRevision = showRevision;
	}
	
}
