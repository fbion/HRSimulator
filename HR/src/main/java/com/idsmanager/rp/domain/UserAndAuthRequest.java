package com.idsmanager.rp.domain;

import com.idsmanager.oauth.domain.user.User;

public class UserAndAuthRequest {
	private String id;

	private User user;
	
	private AuthenticateRequest request;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AuthenticateRequest getRequest() {
		return request;
	}

	public void setRequest(AuthenticateRequest request) {
		this.request = request;
	}

	public UserAndAuthRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserAndAuthRequest(User user, AuthenticateRequest request) {
		super();
		this.user = user;
		this.request = request;
	}

	
}
