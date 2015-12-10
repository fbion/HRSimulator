package com.idsmanager.rp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.idsmanager.oauth.domain.dto.UserDto;
import com.idsmanager.oauth.domain.oauth.AccessTokenRepository;
import com.idsmanager.oauth.service.UserService;
import com.idsmanager.rp.domain.AccessTokenRequest;
import com.idsmanager.rp.domain.AuthResponseResult;
import com.idsmanager.rp.domain.UserMoney;
import com.idsmanager.rp.service.UserMongoService;
import com.idsmanager.rp.util.ErrorNumberUtils;
import com.idsmanager.rp.util.HttpRequestMethodUtils;
import com.idsmanager.rp.util.RequestUrl;
import com.idsmanager.rp.util.ValidationUtils;
import com.idsmanager.rp.util.bean.ProcessResult;

@Controller("rpController")
public class RPLoginController {
	@Autowired
	private UserMongoService userService;
	@Autowired
	private UserService oauthUserService;
	
	@Autowired
	private AccessTokenRepository accessTokenRepositoryMongo;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private static final String ACCESS_TOKEN = "access_token";

	private static final String USER_INFO = "userinfo";

	/**
	 * 获取用户是否有设备 thinkpad dushaofei
	 * 
	 * @param username
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public Boolean getDevice(String username) throws ClientProtocolException,
			IOException {
		ProcessResult<Boolean> pr = new ProcessResult<Boolean>();
		// 获取token
		String access_token = HttpRequestMethodUtils.getAccesstoKen();
		AccessTokenRequest access = HttpRequestMethodUtils.getAccessEntity();
		RequestUrl url = HttpRequestMethodUtils.getRequestUrl();
		
		String requstUrl = url.getDeviceUrl()+"/"+access.getClient_id()+"/" + username;
		HttpGet get = new HttpGet(requstUrl);
		get.addHeader("Authorization", "bearer " + access_token);
		JSONObject obj = HttpRequestMethodUtils.getJsonObject(get);
		pr = (ProcessResult<Boolean>) obj.toBean(obj, pr.getClass());
		return pr.getDetail();
	}

	/**
	 * 无密登录 thinkpad dushaofei
	 * 
	 * @param user
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/public/user/nosecret/login", method = RequestMethod.POST)
	@ResponseBody
	public ProcessResult<Map<String, Object>> noPasswordLogin(
			@RequestBody UserDto user) {
		ProcessResult<Map<String, Object>> pr = new ProcessResult<Map<String, Object>>();
		if (StringUtils.isBlank(user.getUsername())) {
			pr.setSuccess(false);
			pr.setErrorNum(ErrorNumberUtils.USER_NAME_NULL);
			pr.setErrorMessage("user name is required.");
			return pr;
		}
		if (ValidationUtils.sqlPattern.matcher(user.getUsername().toLowerCase()).find()) {  
			pr.setErrorNum(ErrorNumberUtils.USER_NAME_ILLEGAL);
        	pr.setErrorMessage("name is Illegal");
            return pr;  
		} 
		
		 //如果有Html标记转换为实体
	     String username = ValidationUtils.filterHtml(user.getUsername());
     
		Map<String, Object> map = new HashMap<String, Object>();
		com.idsmanager.oauth.domain.user.User domain = userService
				.getUserAndO2OByName(username);
		//user.getUsername()
//		try {
//			
//		} catch (ClientProtocolException e) {
//			pr.setErrorMessage("Get UserInfo Exception.");
//			pr.setSuccess(false);
//			pr.setErrorNum(ErrorNumberUtils.GET_RP_USER_TOKEN_EXCEP);
//			return pr;
//		} catch (IOException e) {
//			pr.setErrorMessage("Get UserInfo Exception.");
//			pr.setSuccess(false);
//			pr.setErrorNum(ErrorNumberUtils.GET_RP_USER_TOKEN_EXCEP);
//			return pr;
//		}
		// 如果有设备并且用户状态是开启二次认证
		try {
//			String token = getAccess_tokenByUserName(domain.username());
			AuthResponseResult result = null;
			if (null == domain) {
				pr.setSuccess(false);
				pr.setErrorMessage("RP No this Users.");
				pr.setErrorNum(ErrorNumberUtils.RP_NOT_USERS);
				return pr;
			}
			
			Boolean device = getDevice(user.getUsername());
			// 更新登录次数
			if (device == true && domain.getState() == true) {
//				Integer num = userService
//						.updateUserAndO2OByName(user.getUsername());
//				result = new AuthResponseResult(user.getUsername(), num);
				pr.setSuccess(false);
				pr.setErrorNum(ErrorNumberUtils.TWO_CERTIFICATION);
				pr.setErrorMessage("users need two certification.");
//				map.put(USER_INFO, result);
				// map.put(ACCESS_TOKEN, token);
				pr.setDetail(map);
				return pr;
			} else {
				pr.setErrorNum(ErrorNumberUtils.USER_CAN_NOT_LOGIN_WITHOUT_PASSWORD);
				pr.setSuccess(false);
				return pr;
			}
		} catch (ClientProtocolException e) {
			pr.setErrorMessage("GET User Device Excep");
			pr.setSuccess(false);
			pr.setErrorNum(ErrorNumberUtils.GET_FIDO_DEVICE_EXCEP);
			return pr;
		} catch (IOException e) {
			pr.setErrorMessage("GET User Device Excep");
			pr.setSuccess(false);
			pr.setErrorNum(ErrorNumberUtils.GET_FIDO_DEVICE_EXCEP);
			return pr;
		}
	}

	/**
	 * 用户关闭二次认证 thinkpad dushaofei
	 * 
	 * @param username
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	@RequestMapping(value = "/v1/userVertify/vertify/{state}", method = RequestMethod.GET)
	@ResponseBody
	public ProcessResult<String> closeVertify(@PathVariable Integer state) {
		ProcessResult<String> pr = new ProcessResult<String>();
		String name = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		try {
			if (state == 0) {// 关闭
				pr.setErrorNum(ErrorNumberUtils.OK);
				pr.setDetail("close success.");
				// 关闭
				userService.updateUserStateByName(name, state);
				return pr;
			} else if (state == 1) {
				pr.setErrorNum(ErrorNumberUtils.OK);
				pr.setDetail("close success.");
				// 开启
				userService.updateUserStateByName(name, state);
				return pr;
			}
		} catch (Exception e) {
			pr.setErrorMessage("Close Vertify Excep");
			pr.setSuccess(false);
			pr.setErrorNum(ErrorNumberUtils.CLOSE_VERTIFY_EXCEP);
		}
		return pr;
	}
	/**
	 * 获取二次认证是否开启 thinkpad dushaofei
	 * 
	 * @param username
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	@RequestMapping(value = "/v1/userVertify/state", method = RequestMethod.GET)
	@ResponseBody
	public ProcessResult<Boolean> GetVertifyState() {
		ProcessResult<Boolean> pr = new ProcessResult<Boolean>();
		String name = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		com.idsmanager.oauth.domain.user.User user = userService
				.getUserAndO2OByName(name);
		if (null != user) {
			pr.setDetail(user.getState());
			return pr;
		}
		pr.setErrorNum(ErrorNumberUtils.RP_NOT_USERS);
		pr.setSuccess(false);
		return pr;
	}

	/**
	 * 查询全部用户
	 * 
	 * @return
	 */
	@RequestMapping(value = "/public/user/all")
	@ResponseBody
	public Map<String, Object> getAllApplication(HttpServletRequest req) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<com.idsmanager.oauth.domain.user.User> list = null;
		try {
			list = userService.getUserList();

		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("查询全部用户异常:" + e.getMessage());
		}
		map.put("aaData", list);
		return map;
	}

	/**
	 * 注册用户 thinkpad dushaofei
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/public/user/add", method = RequestMethod.POST)
	@ResponseBody
	public ProcessResult<String> addOauthUser(@RequestBody UserDto user) {
		ProcessResult<String> pr = new ProcessResult<String>();
		if (StringUtils.isBlank(user.getUsername())
				|| StringUtils.isBlank(user.getPassword())) {
			pr.setSuccess(false);
			pr.setErrorNum(ErrorNumberUtils.USER_NAME_NULL);
			pr.setErrorMessage("user name and password is required.");
			return pr;
		}
		boolean when = Validate(user.getUsername(),user.getPassword());
		if(!when){
			pr.setErrorNum(ErrorNumberUtils.USER_NAME_ILLEGAL);
        	pr.setErrorMessage("name or password is Illegal");
        	return pr;
		}
		List<String> strList = new ArrayList<String>();
		strList.add("API");
		user.setPrivileges(strList);
		com.idsmanager.oauth.domain.user.User loginUser = user.newDomain();
		com.idsmanager.oauth.domain.user.User userInfo = userService
				.getUserAndO2OByName(loginUser.username());
		if (null != userInfo) {
			pr.setSuccess(false);
			pr.setDetail("users already  exist.");
			pr.setErrorNum(ErrorNumberUtils.RP_USER_REGISTERED);
			logger.debug("该用户已在RP上注册.");
			return pr;
		} else {
			oauthUserService.createUser(user);
			//注册成功后初始化该用户金额
			userService.initUserMoney(new UserMoney(loginUser.getUsername()));
			pr.setDetail("register success");
			return pr;
		}
	}
	/**
	 * 验证账户密码是否合法
	 * thinkpad dushaofei
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean Validate(String username,String password){
		boolean when = true;
		//防止sql注入
		if (ValidationUtils.sqlPattern.matcher(username.toLowerCase()).find()) {  
        	when = false;
            return when;  
		} 
		//防止sql注入
		if (ValidationUtils.sqlPattern.matcher(password.toLowerCase()).find()) {  
        	when = false;
            return when;  
		} 
		//如果有Html标记就返回异常
	     boolean name = ValidationUtils.filterHtmlToIllegal(username);
	     if(!name){
        	when = false;
            return when; 
	     }
	     boolean pass = ValidationUtils.filterHtmlToIllegal(password);
	     if(!pass){
        	when = false;
            return when; 
	     }
	     return when;
	}
	/**
	 * 用户登录 thinkpad dushaofei
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/public/login/oauth")
	@ResponseBody
	public ProcessResult<Map<String, Object>> loginOauth(
			@RequestBody UserDto user) {
		ProcessResult<Map<String, Object>> pr = new ProcessResult<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		AuthResponseResult result = null;
		if (StringUtils.isBlank(user.getUsername())
				|| StringUtils.isBlank(user.getPassword())) {
			pr.setSuccess(false);
			pr.setErrorNum(ErrorNumberUtils.USER_NAME_NULL);
			pr.setErrorMessage("user name and password is required.");
			return pr;
		}
		boolean when = Validate(user.getUsername(),user.getPassword());
		if(!when){
			pr.setErrorNum(ErrorNumberUtils.USER_NAME_ILLEGAL);
        	pr.setErrorMessage("name or password is Illegal");
        	pr.setSuccess(false);
        	return pr;
		}
		try { 
			com.idsmanager.oauth.domain.user.User domain = userService.getUserByUserNameAndPassword(user.getUsername(),user.getPassword());
			JSONObject json = HttpRequestMethodUtils.getLoginInfo(
					user.getUsername(), user.getPassword());
			String token = (String) json.get(ACCESS_TOKEN);
			if(domain!=null&&domain.getState()==false&&domain.getSupportFido()==false){
				Integer num = userService.updateUserAndO2OByName(user
						.getUsername());
				result = new AuthResponseResult(user.getUsername(), num);
				map.put(ACCESS_TOKEN, token);
				map.put(USER_INFO, result);
				pr.setDetail(map);
				return pr;
			} 
			if ("" != token || null != token) {
				if(null == domain){
					pr.setErrorNum(ErrorNumberUtils.RP_NOT_USERS);
					pr.setSuccess(false);
					return pr;
				}
				Boolean device = getDevice(user.getUsername());
				if (device == true && domain.getState() == true) {
//					map.put(USER_INFO, result);
					pr.setSuccess(false);
					pr.setDetail(map);
					pr.setErrorNum(ErrorNumberUtils.TWO_CERTIFICATION);
					return pr;
				} else if (domain.getState() == false ||domain.getState() == true) {
					Integer num = userService.updateUserAndO2OByName(user
							.getUsername());
					result = new AuthResponseResult(user.getUsername(), num);
					map.put(ACCESS_TOKEN, token);
					map.put(USER_INFO, result);
					pr.setDetail(map);
					return pr;
				} else {
					pr.setErrorNum(ErrorNumberUtils.USER_CAN_NOT_LOGIN_WITHOUT_PASSWORD);
					pr.setSuccess(false);
					return pr;
				}
			} else {
				pr.setErrorNum(ErrorNumberUtils.RP_NOT_USERS);
				pr.setSuccess(false);
			}
		}catch (ClientProtocolException e) {
			pr.setErrorMessage("GET User Device Excep");
			pr.setSuccess(false);
			pr.setErrorNum(ErrorNumberUtils.GET_FIDO_DEVICE_EXCEP);
			return pr;
		}  catch (IOException e) {
			pr.setErrorNum(ErrorNumberUtils.GET_RP_USER_TOKEN_EXCEP);
			pr.setSuccess(false);
			return pr;
		}
		return pr;
	}

	/**
	 * 用户删除
	 * 
	 * @return
	 */
	@RequestMapping(value = "/public/user/delete/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public ProcessResult<Integer> userDelete(@PathVariable String[] userId) {
		ProcessResult<Integer> pr = new ProcessResult<Integer>();
		for (String string : userId) {
			//防止sql注入
			if (ValidationUtils.sqlPattern.matcher(string.toLowerCase()).find()) {  
	        	pr.setErrorMessage("参数异常");
	            return pr;  
			} 
			userService.deleteUser(string);
		}
		return pr;
	}

	/**
	 * 用户注册页面 thinkpad dushaofei
	 * 
	 * @return
	 */
	@RequestMapping("/public/user/regist")
	public ModelAndView registPage() {
		ModelAndView view = new ModelAndView();
		view.setViewName("userRegist.jsp");
		return view;
	}

	/**
	 * 用户列表页面 thinkpad dushaofei
	 * 
	 * @return
	 */
	@RequestMapping("/public/user/userList")
	public ModelAndView userList() {
		ModelAndView view = new ModelAndView();
		view.setViewName("userList.jsp");
		return view;
	}

	/**
	 * 用户登录页面 thinkpad dushaofei
	 * 
	 * @return
	 */
	@RequestMapping("/public/user/login")
	public ModelAndView userLogin() {
		ModelAndView view = new ModelAndView();
		view.setViewName("login.jsp");
		return view;
	}

	/**
	 * 获取用户名 thinkpad dushaofei
	 * 
	 * @return
	 */
	@RequestMapping("/v1/test/api")
	@ResponseBody
	public String OauthUserName() {
		String name = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		return name;
	}
	/**
	 * 根据用户名拿到用户token
	 * thinkpad dushaofei
	 * @param username
	 * @return
	 */
	public String getAccess_tokenByUserName(String username){
		return accessTokenRepositoryMongo.
				findATByUsername(username).
				token().getValue();
	}
}
