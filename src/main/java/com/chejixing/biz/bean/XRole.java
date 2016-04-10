package com.chejixing.biz.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="XRole")
public class XRole extends XBean{
	@ManyToMany(mappedBy="roles")
	private Set<XUser> users = new HashSet<XUser>();
	private String role;
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
