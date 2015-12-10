package com.idsmanager.rp.domain;

public class AccessTokenResponse {
	//{"access_token":"38c4c77a-4320-42e0-86d4-eeb89184d4d5","token_type":"bearer","expires_in":41108,"scope":"read"}
	private String access_token;
	private String token_type;
	private String expires_in;
	private String scope;
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	
}
