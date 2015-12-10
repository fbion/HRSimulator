package com.idsmanager.rp.domain;


public class EmpHR {
	private String id;
	
	private String username;
	
	//所属机构
	private String dept;
	//编号
	private String enumber;
	//1离职(逻辑删除)
	private Integer status = 0;
	//用户状态   0启动 1禁止 
	private Integer state;
	//是否推到UD 0：是    1：否
	private Integer ispush;
	//入职日期
	private String edate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getEnumber() {
		return enumber;
	}
	public void setEnumber(String enumber) {
		this.enumber = enumber;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getIspush() {
		return ispush;
	}
	public void setIspush(Integer ispush) {
		this.ispush = ispush;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	
	
	
	
}
