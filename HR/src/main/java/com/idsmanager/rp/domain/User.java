package com.idsmanager.rp.domain;

public class User {
	private String id;
	private String username;
	private String password;
	private Boolean state = true;//是否开启二次认证
	private Boolean supportFido = false;//是否支持Fido协议
	private Integer loginNum = 0;//登录次数
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getState() {
		return state;
	}
	public void setState(Boolean state) {
		this.state = state;
	}
	public Integer getLoginNum() {
		return loginNum;
	}
	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}
	public Boolean getSupportFido() {
		return supportFido;
	}
	public void setSupportFido(Boolean supportFido) {
		this.supportFido = supportFido;
	}
	
}
