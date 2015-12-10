package com.idsmanager.rp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.idsmanager.oauth.domain.user.User;
import com.idsmanager.rp.dao.UserMongoDao;
import com.idsmanager.rp.domain.RegisterRequestAndUser;
import com.idsmanager.rp.domain.UserAndAuthRequest;
import com.idsmanager.rp.domain.UserMoney;
import com.idsmanager.rp.domain.UserMoneyRequest;
@Repository("userAndO2ODao")
public class UserMongoDaoImpl implements UserMongoDao {
	@Autowired
	private MongoOperations mongoTemplate;
	@Override
	public String saveUserAndO2O(User o2o) {
		mongoTemplate.save(o2o);
		return o2o.guid();
	}

	@Override
	public Integer updateUserAndO2OByName(String username) {
		 User user = getUserAndO2OByName(username);
		 if(null!=user){
			 Integer num = user.getLoginNum();
			 user.setLoginNum(num+1);
			 mongoTemplate.save(user);
		 }
		return user.getLoginNum();
	}

	@Override
	public User getUserAndO2OByName(String username) {
		Query query = new Query(Criteria.where("username").is(username));
		return mongoTemplate.findOne(query, User.class);
	}

	@Override
	public String saveUserAndAuth(UserAndAuthRequest userAndAuthRequest) {
		mongoTemplate.save(userAndAuthRequest);
		return userAndAuthRequest.getId();
	}

	@Override
	public UserAndAuthRequest getUserByChallger(String challger) {
		Query query = new Query(Criteria.where("request.challenge").is(challger));
		
		return mongoTemplate.findOne(query, UserAndAuthRequest.class);
	}

	@Override
	public void deleteUserAndAuthRequest(String challger) {
		UserAndAuthRequest request = getUserByChallger(challger);
		if(null!=request){
			mongoTemplate.remove(request);
		}
		
	}

	@Override
	public List<UserAndAuthRequest> getUserAndAuthByName(String username) {
		Query query = new Query(Criteria.where("user.username").is(username));
		return mongoTemplate.find(query, UserAndAuthRequest.class);
	}

	@Override
	public User getUserByUserNameAndPassword(String username, String password) {
		Query query = new Query(Criteria.where("username").is(username).andOperator(Criteria.where("password").is(password)));
		
		return mongoTemplate.findOne(query, User.class);
	}

	@Override
	public Boolean updateUserStateByName(String username,Integer state) {
		Boolean flag = true;
		 User user = getUserAndO2OByName(username);
		 if(null!=user){
			 if(state==1){//开启
				 user.setState(flag);
			 }else{//0关闭
				 flag = false;
				 user.setState(flag);
			 }
			 mongoTemplate.save(user);
		 }
		return flag;
	}

	@Override
	public Boolean updateUserSupportFido(String username) {
		Boolean flag = false;
		 User user = getUserAndO2OByName(username);
		 if(null!=user){
			 flag = true;
			 user.setSupportFido(flag);
			 mongoTemplate.save(user);
		 }
		return flag;
	}

	@Override
	public String saveRegisterRequestAndUser(
			RegisterRequestAndUser requestAndUser) {
		mongoTemplate.save(requestAndUser);
		return requestAndUser.getId();
	}

	@Override
	public RegisterRequestAndUser getUserAndRegisterByChallenge(String challenge) {
		Query query = new Query(Criteria.where("request.challenge").is(challenge));
		
		return mongoTemplate.findOne(query, RegisterRequestAndUser.class);
		
	}

	@Override
	public List<RegisterRequestAndUser> getUserAndRegisterByName(String username) {
		Query query = new Query(Criteria.where("user.username").is(username));
		return mongoTemplate.find(query, RegisterRequestAndUser.class);
	}

	@Override
	public void deleteUserAndRegist(String challenge) {
		RegisterRequestAndUser requestAndUser = getUserAndRegisterByChallenge(challenge);
		if(null!=requestAndUser){
			mongoTemplate.remove(requestAndUser);
		}
		
	}

	@Override
	public List<User> getUserList() {
		return mongoTemplate.findAll(User.class);
	}

	@Override
	public void deleteUser(String userId) {
		User user = getUserByUserID(userId);
		if(null!=user){
			mongoTemplate.remove(user);
		}
		
	}

	@Override
	public User getUserByUserID(String userid) {
		Query query = new Query(Criteria.where("guid").is(userid));
		return mongoTemplate.findOne(query, User.class);
	}

	@Override
	public void initUserMoney(UserMoney money) {
		mongoTemplate.save(money);
	}

	@Override
	public UserMoney getMoneyByUserId(String userid) {
		Query query = new Query(Criteria.where("userName").is(userid));
		return mongoTemplate.findOne(query, UserMoney.class);
	}

	@Override
	public Double updateUserMoneyByName(String name, Double money) {
		Double m = Double.valueOf(money);
		UserMoney user = getMoneyByUserId(name);
		if(null!=user){
			if(m > user.getMoney()){
				return null;
			}else{
				Double mon = user.getMoney();
				Double restMoney = (mon - m);
				user.setMoney(restMoney);
				mongoTemplate.save(user);
				return restMoney;
			}
		}
		return null;
	}

	@Override
	public String saveUserMoneyRequest(UserMoneyRequest userMoneyRequest) {
		mongoTemplate.save(userMoneyRequest);
		return userMoneyRequest.getMoney();
	}

	@Override
	public List<UserMoneyRequest> UserMoneyRequestByName(String name) {
		Query query = new Query(Criteria.where("username").is(name));
		return mongoTemplate.find(query, UserMoneyRequest.class);
	}

	@Override
	public void deleteUserMoneyRequest(String username) {
		List<UserMoneyRequest> request = UserMoneyRequestByName(username);
		if(null!=request){
			for (UserMoneyRequest userMoneyRequest : request) {
				mongoTemplate.remove(userMoneyRequest);
			}
		}
		
	}

	@Override
	public UserMoneyRequest getUserAndMoneyByChallenge(String challenge) {
			Query query = new Query(Criteria.where("authenticateRequest.challenge").is(challenge));
		
		return mongoTemplate.findOne(query, UserMoneyRequest.class);
	}
	
}
