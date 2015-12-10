package com.idsmanager.rp.dao;

import java.util.List;

import com.idsmanager.oauth.domain.user.User;
import com.idsmanager.rp.domain.Department;
import com.idsmanager.rp.domain.EmpHR;

public interface HRMongoDao {
	/**
	 * 保存用户
	 * thinkpad dushaofei
	 * @param o2o
	 * @return
	 */
	public String saveDept(Department dept);

	public List<Department> getUserList();

	public void deleteUser(String string);
	
	public Department getUserByDeptID(String deptId);

	public String saveEmp(EmpHR emp);

	public List<EmpHR> getEmpList();
	
	public EmpHR getEmpByID(String id);

	public void deleteEmp(String string);

	public Department getDeptByLeaderCode(String leaderCode);
}
