package com.idsmanager.rp.domain;
/**
 * 
 *类名称 获取access_token类
 *创建人 dushaofei
 *创建时间：2015-11-9 下午3:00:04 
 *类描述：
 *@version
 */
public class GetAccessThisUserToken {
	private String client_id;
	private String client_secret;
	private String grant_type;
	private String client_credentials;
	private String scope;
	private String url;//获取access_token的地址
	private String username;
	private String password;
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getClient_secret() {
		return client_secret;
	}
	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}
	public String getGrant_type() {
		return grant_type;
	}
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}
	public String getClient_credentials() {
		return client_credentials;
	}
	public void setClient_credentials(String client_credentials) {
		this.client_credentials = client_credentials;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	@Override
	public String toString() {
		return "AccessTokenRequest [client_id=" + client_id
				+ ", client_secret=" + client_secret + "]";
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
