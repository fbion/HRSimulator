package com.idsmanager.rp.service;

import java.util.List;

import com.idsmanager.oauth.domain.user.User;
import com.idsmanager.rp.domain.RegisterRequestAndUser;
import com.idsmanager.rp.domain.UserAndAuthRequest;
import com.idsmanager.rp.domain.UserMoney;
import com.idsmanager.rp.domain.UserMoneyRequest;

	public interface UserMongoService {
	/**
	 * 保存用户
	 * thinkpad dushaofei
	 * @param o2o
	 * @return
	 */
	public String saveUserAndO2O(User o2o);
	/**
	 * 保存用户跟验证请求实体
	 * thinkpad dushaofei
	 * @param userAndAuthRequest
	 * @return
	 */
	public String saveUserAndAuth(UserAndAuthRequest userAndAuthRequest);
	/**
	 * 根据username修改用户登录次数
	 * thinkpad dushaofei
	 * @param username
	 * @return
	 */
	public Integer updateUserAndO2OByName(String  username);
	/**
	 * 根据username修改用户是否开启二次认证状态
	 * thinkpad dushaofei
	 * @param username
	 * @return
	 */
	public Boolean updateUserStateByName(String  username,Integer state);
	/**
	 * 根据username修改用户使用Fido协议状态
	 * thinkpad dushaofei
	 * @param username
	 * @return
	 */
	public Boolean updateUserSupportFido(String username);
	/**
	 * 根据username查询用户
	 * thinkpad dushaofei
	 * @param username
	 * @return
	 */
	public User getUserAndO2OByName(String username);
	/**
	 * 根据username和password查用户
	 * thinkpad dushaofei
	 * @param username
	 * @param password
	 * @return
	 */
	public User getUserByUserNameAndPassword(String username,String password);
	/**
	 * 根据challenge查询用户验证请求实体
	 * thinkpad dushaofei
	 * @param challger
	 * @return
	 */
	public UserAndAuthRequest getUserByChallger(String challger);
	/**
	 * 根据challenge删除用户验证请求实体
	 * thinkpad dushaofei
	 * @param challger
	 */
	public void deleteUserAndAuthRequest(String challger);
	/**
	 * 根据username查询用户验证实体
	 * thinkpad dushaofei
	 * @param username
	 * @return
	 */
	public List<UserAndAuthRequest> getUserAndAuthByName(String username);
	/**
	 * 根据username查询用户注册请求实体
	 * thinkpad dushaofei
	 * @param username
	 * @return
	 */
	public List<RegisterRequestAndUser> getRegistAndUserByName(String username);
	/**
	 * 保存用户注册请求实体
	 * thinkpad dushaofei
	 * @param requestAndUser
	 * @return
	 */
	public String saveRegisterRequestAndUser(RegisterRequestAndUser requestAndUser);
	/**
	 * 根据challenge查询用户注册请求实体
	 * thinkpad dushaofei
	 * @param challenge
	 * @return
	 */
	public RegisterRequestAndUser getUserAndRegisterByChallenge(String challenge);
	/**
	 * 根据challenge删除用户注册请求
	 * thinkpad dushaofei
	 * @param challenge
	 */
	public void deleteUserAndRegist(String challenge);
	/**
	 * 查询全部用户
	 * thinkpad dushaofei
	 * @return
	 */
	public List<User> getUserList();
	/**
	 * 删除用户
	 * thinkpad dushaofei
	 * @param userId
	 */
	public void deleteUser(String userId);
	/**
	 * 根据ID查用户
	 * thinkpad dushaofei
	 * @param userId
	 * @return
	 */
	public User getUserByUserID(String userId);
	/**
	 * 初始化用户金额
	 * thinkpad dushaofei
	 * @param money
	 */
	public void initUserMoney(UserMoney money);
	
	public UserMoney getMoneyByUserId(String guid);
	
	public Double updateUserMoneyByName(String name, Double money);
	public String saveUserMoneyRequest(UserMoneyRequest userMoneyRequest);
	public List<UserMoneyRequest> UserMoneyRequestByName(String name);
	public void deleteUserMoneyRequest(String username);
	public UserMoneyRequest getUserAndMoneyByChallenge(String challenge);
}
