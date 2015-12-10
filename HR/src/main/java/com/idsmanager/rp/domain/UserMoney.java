package com.idsmanager.rp.domain;

public class UserMoney {
	
	private String id;
	
	private String userName;
	
	private Double money = 2000.00 ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public UserMoney() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserMoney(String userName) {
		super();
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
