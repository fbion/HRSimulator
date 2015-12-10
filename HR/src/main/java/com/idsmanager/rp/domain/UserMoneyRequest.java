package com.idsmanager.rp.domain;

public class UserMoneyRequest {
	private String id;
	private String username;
	private String money;
	
	private AuthenticateRequest authenticateRequest;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	 
	public UserMoneyRequest(String username, String money,
			AuthenticateRequest authenticateRequest) {
		super();
		this.username = username;
		this.money = money;
		this.authenticateRequest = authenticateRequest;
	}
	public UserMoneyRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public AuthenticateRequest getAuthenticateRequest() {
		return authenticateRequest;
	}
	public void setAuthenticateRequest(AuthenticateRequest authenticateRequest) {
		this.authenticateRequest = authenticateRequest;
	}
 
	
}	
