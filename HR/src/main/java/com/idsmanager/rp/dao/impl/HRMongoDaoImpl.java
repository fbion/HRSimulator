package com.idsmanager.rp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.idsmanager.oauth.domain.user.User;
import com.idsmanager.rp.dao.HRMongoDao;
import com.idsmanager.rp.domain.Department;
import com.idsmanager.rp.domain.EmpHR;
@Repository("hrDao")
public class HRMongoDaoImpl implements HRMongoDao {
	@Autowired
	private MongoOperations mongoTemplate;
	@Override
	public String saveDept(Department dept) {
		mongoTemplate.save(dept);
		return dept.getId();
	}
	@Override
	public List<Department> getUserList() {
		return mongoTemplate.findAll(Department.class);
	}
	@Override
	public void deleteUser(String string) {
			Department user = getUserByDeptID(string);
			if(null!=user){
				mongoTemplate.remove(user);
			}
	}
	@Override
	public Department getUserByDeptID(String deptId) {
		Query query = new Query(Criteria.where("id").is(deptId));
		return mongoTemplate.findOne(query, Department.class);
	}
	@Override
	public String saveEmp(EmpHR emp) {
		mongoTemplate.save(emp);
		return emp.getId();
	}
	@Override
	public List<EmpHR> getEmpList() {
		Query query = new Query(Criteria.where("status").is(0));
		return mongoTemplate.find(query, EmpHR.class);
	}
	@Override
	public EmpHR getEmpByID(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, EmpHR.class);
	}
	@Override
	public void deleteEmp(String string) {
		EmpHR e = getEmpByID(string);
		if(e!=null){
			e.setStatus(1);
			mongoTemplate.save(e);
		}
		
	}
	@Override
	public Department getDeptByLeaderCode(String leaderCode) {
		Query query = new Query(Criteria.where("leaderCode").is(leaderCode));
		return mongoTemplate.findOne(query, Department.class);
	}
	
	
}
