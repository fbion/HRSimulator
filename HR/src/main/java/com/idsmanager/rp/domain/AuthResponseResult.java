package com.idsmanager.rp.domain;

public class AuthResponseResult {
	private String username;
	
	private Integer loginNum = 0;
	
	private String rpName ="O2O";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}

	public String getRpName() {
		return rpName;
	}

	public void setRpName(String rpName) {
		this.rpName = rpName;
	}

	public AuthResponseResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AuthResponseResult(String username, Integer loginNum ) {
		super();
		this.username = username;
		this.loginNum = loginNum;
	}
	
}
