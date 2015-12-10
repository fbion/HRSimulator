package com.idsmanager.rp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idsmanager.oauth.domain.user.User;
import com.idsmanager.oauth.infrastructure.PasswordHandler;
import com.idsmanager.rp.dao.UserMongoDao;
import com.idsmanager.rp.domain.RegisterRequestAndUser;
import com.idsmanager.rp.domain.UserAndAuthRequest;
import com.idsmanager.rp.domain.UserMoney;
import com.idsmanager.rp.domain.UserMoneyRequest;
import com.idsmanager.rp.service.UserMongoService;
@Service("userService")
public class UserMongoServiceImpl implements UserMongoService {
	@Autowired
	private UserMongoDao userAndO2ODao;
	@Override
	public String saveUserAndO2O(User o2o) {
		// TODO Auto-generated method stub
		return userAndO2ODao.saveUserAndO2O(o2o);
	}

	@Override
	public Integer updateUserAndO2OByName(String username){
		
		return userAndO2ODao.updateUserAndO2OByName(username);
	}

	@Override
	public User getUserAndO2OByName(String username) {
		
		return userAndO2ODao.getUserAndO2OByName(username);
	}

	@Override
	public String saveUserAndAuth(UserAndAuthRequest userAndAuthRequest) {
		 
		return userAndO2ODao.saveUserAndAuth(userAndAuthRequest);
	}

	@Override
	public UserAndAuthRequest getUserByChallger(String challger) {
		return userAndO2ODao.getUserByChallger(challger);
	}

	@Override
	public void deleteUserAndAuthRequest(String challger) {
		userAndO2ODao.deleteUserAndAuthRequest(challger);
		
	}

	@Override
	public List<UserAndAuthRequest> getUserAndAuthByName(String username) {
		
		return userAndO2ODao.getUserAndAuthByName(username);
	}

	@Override
	public User getUserByUserNameAndPassword(String username, String password) {
		String encryptPassword = PasswordHandler.encryptPassword(password);
		return userAndO2ODao.getUserByUserNameAndPassword(username, encryptPassword);
	}

	@Override
	public Boolean updateUserStateByName(String username,Integer state) {
		 
		return userAndO2ODao.updateUserStateByName(username,state);
	}

	@Override
	public Boolean updateUserSupportFido(String username) {
		 
		return userAndO2ODao.updateUserSupportFido(username);
	}

	@Override
	public String saveRegisterRequestAndUser(
			RegisterRequestAndUser requestAndUser) {
		 
		return userAndO2ODao.saveRegisterRequestAndUser(requestAndUser);
	}

	@Override
	public RegisterRequestAndUser getUserAndRegisterByChallenge(String challenge) {
		return userAndO2ODao.getUserAndRegisterByChallenge(challenge);
	}

	@Override
	public List<RegisterRequestAndUser> getRegistAndUserByName(String username) {
		
		return userAndO2ODao.getUserAndRegisterByName(username);
	}

	@Override
	public void deleteUserAndRegist(String challenge) {
		userAndO2ODao.deleteUserAndRegist(challenge);
	}

	@Override
	public List<User> getUserList() {
		return userAndO2ODao.getUserList();
	}

	@Override
	public void deleteUser(String userId) {
		userAndO2ODao.deleteUser(userId);
		
	}

	@Override
	public User getUserByUserID(String userId) {
		return userAndO2ODao.getUserByUserID(userId);
	}

	@Override
	public void initUserMoney(UserMoney money) {
		userAndO2ODao.initUserMoney(money);
		
	}

	@Override
	public UserMoney getMoneyByUserId(String guid) {
		return userAndO2ODao.getMoneyByUserId(guid) ;
	}

	@Override
	public Double updateUserMoneyByName(String name, Double money) {
		return userAndO2ODao.updateUserMoneyByName(name,money);
		
	}

	@Override
	public String saveUserMoneyRequest(UserMoneyRequest userMoneyRequest) {
		 
		return userAndO2ODao.saveUserMoneyRequest(userMoneyRequest);
	}

	@Override
	public List<UserMoneyRequest> UserMoneyRequestByName(String name) {
		return userAndO2ODao.UserMoneyRequestByName(name);
	}

	@Override
	public void deleteUserMoneyRequest(String username) {
		userAndO2ODao.deleteUserMoneyRequest(username);
		
	}

	@Override
	public UserMoneyRequest getUserAndMoneyByChallenge(String challenge) {
		// TODO Auto-generated method stub
		return userAndO2ODao.getUserAndMoneyByChallenge(challenge);
	}
}
