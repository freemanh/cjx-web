package com.chejixing.biz.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="XUser")
public class XUser extends XBean{
	private String username;
	private String pwd;
	private String name;
	@ManyToOne
	@JoinColumn(name="company_id")
	private XCompany company;
	@ManyToMany
	@JoinTable(name="USER_ROLE"
		, joinColumns={@JoinColumn(columnDefinition="user_id", referencedColumnName="id")}
		, inverseJoinColumns={@JoinColumn(columnDefinition="role_id", referencedColumnName="id")})
	private Set<XRole> roles = new HashSet<XRole>();
	private boolean enabled = true;
	@Column(name="is_show_revision")
	private boolean showRevision = true;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public XCompany getCompany() {
		return company;
	}
	public void setCompany(XCompany company) {
		this.company = company;
	}
	public Set<XRole> getRoles() {
		return roles;
	}
	public void setRoles(Set<XRole> roles) {
		this.roles = roles;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isShowRevision() {
		return showRevision;
	}
	public void setShowRevision(boolean showRevision) {
		this.showRevision = showRevision;
	}
	
	
}
