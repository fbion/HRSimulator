package com.idsmanager.rp.domain;

import com.idsmanager.oauth.domain.user.User;

public class RegisterRequestAndUser {
	private String id;
	private RegisterRequest request;
	private User user;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public RegisterRequest getRequest() {
		return request;
	}
	public void setRequest(RegisterRequest request) {
		this.request = request;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public RegisterRequestAndUser(RegisterRequest request, User user) {
		super();
		this.request = request;
		this.user = user;
	}
	public RegisterRequestAndUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
