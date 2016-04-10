package com.chejixing.socket.message;

public class AuthReq {
	private String auth;
	
	public AuthReq(String auth) {
		super();
		this.auth = auth;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
	
	
}
