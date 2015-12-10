package com.idsmanager.rp.controller;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idsmanager.oauth.domain.oauth.AccessTokenRepository;
import com.idsmanager.oauth.domain.user.User;
import com.idsmanager.rp.domain.AccessTokenRequest;
import com.idsmanager.rp.domain.AuthResponseResult;
import com.idsmanager.rp.domain.AuthenticateRequest;
import com.idsmanager.rp.domain.AuthenticateRequestData;
import com.idsmanager.rp.domain.AuthenticateResponseData;
import com.idsmanager.rp.domain.RegisterRequest;
import com.idsmanager.rp.domain.RegisterRequestAndUser;
import com.idsmanager.rp.domain.RegisterRequestData;
import com.idsmanager.rp.domain.RegisterResponseData;
import com.idsmanager.rp.domain.TransactionRequest;
import com.idsmanager.rp.domain.UserAndAuthRequest;
import com.idsmanager.rp.domain.UserMoney;
import com.idsmanager.rp.domain.UserMoneyRequest;
import com.idsmanager.rp.service.UserMongoService;
import com.idsmanager.rp.util.ErrorNumberUtils;
import com.idsmanager.rp.util.HttpRequestMethodUtils;
import com.idsmanager.rp.util.RequestUrl;
import com.idsmanager.rp.util.bean.ProcessResult;
import com.sun.xml.messaging.saaj.util.Base64;

@Controller("RPController")
public class RPController {
	private static final String DETAIL = "detail";
	
	private static final String TYPE = "type";
	
	private static final String AUTHENTICATEREQUESTS = "authenticateRequests";
	
	private static final String REGISTERREQUESTS = "registerRequests";
	
	private static final String CHALLENGE ="challenge";
	
	private static final String ACCESS_TOKEN = "access_token";
	
	private static final String USER_INFO = "userinfo";
	
	@Autowired
	private UserMongoService userService;
	@Autowired
	private AccessTokenRepository accessTokenRepositoryMongo;
	/**
	 * 注册请求
	 */
	@RequestMapping(value="/v1/RegisterRequest")
	@ResponseBody
	public ProcessResult<RegisterRequestData> registRequest() throws ClientProtocolException, IOException{

			ProcessResult<RegisterRequestData> pr = new ProcessResult<RegisterRequestData>();
			List<AuthenticateRequest> Auth_list = new ArrayList<AuthenticateRequest>();
			List<RegisterRequest> reg_list = new ArrayList<RegisterRequest>();
			String type ="";
			//获取client_id
		  	String username =SecurityContextHolder.getContext().getAuthentication().getName();
		  	User checkUser =userService.getUserAndO2OByName(username);
		  	//获取token
		  	String access_token = null;
			try {
				access_token = HttpRequestMethodUtils.getAccesstoKen();
			} catch ( IOException e) {
				pr.setSuccess(false);
				pr.setErrorNum(ErrorNumberUtils.GET_TOKENEXCEP);
				pr.setErrorMessage("Get for Accesstoken Exception.");
				System.out.println("去Q-KEY获取token异常:"+e.getMessage());
				return pr;
			}
		  	
		  	if(null==checkUser){
		  		pr.setErrorNum(ErrorNumberUtils.USER_NOT_EXSITS);
		  		pr.setSuccess(false);
		  		return pr;
		  	}
		  	
		try {   
			AccessTokenRequest access = HttpRequestMethodUtils.getAccessEntity();
			RequestUrl url = HttpRequestMethodUtils.getRequestUrl();
			String requestUrl =url.getRegisterReqUrl()+"/"+access.getClient_id()+"/"+username;
			HttpGet get = new HttpGet(requestUrl);
			get.addHeader("Authorization", "bearer "+access_token);
			JSONObject json = HttpRequestMethodUtils.getJsonObject(get);
			JSONObject detail = (JSONObject) json.get(DETAIL);
			JSONObject regists_detail = JSONObject.fromObject(detail);
			System.out.println("注册请求:"+regists_detail.toString());
			//循环验证集合
			JSONArray array = regists_detail.getJSONArray(AUTHENTICATEREQUESTS);
			if(array.size()>0){
				for (Object object : array) {
					AuthenticateRequest request = new AuthenticateRequest();
					JSONObject json_req = JSONObject.fromObject(object);
					request = (AuthenticateRequest) json_req.toBean(json_req,AuthenticateRequest.class);
					Auth_list.add(request);
				}
			}
			//循环注册集合
			JSONArray reg_array = regists_detail.getJSONArray(REGISTERREQUESTS);
				if(reg_array.size()>0){
					for (Object object : reg_array) {
						RegisterRequest request = new RegisterRequest();
						JSONObject json_reg = JSONObject.fromObject(object);
						request = (RegisterRequest) json_reg.toBean(json_reg,RegisterRequest.class);
						reg_list.add(request);
						//根据注册实体和用户添加到数据库,验证响应后循环删除,最后留下注册成功的
						com.idsmanager.oauth.domain.user.User user = userService.getUserAndO2OByName(username);
						if(null!=user){
							userService.saveRegisterRequestAndUser(new RegisterRequestAndUser(request, user));
						}
					}
				}
				type = regists_detail.getString(TYPE);
			} catch (UnsupportedEncodingException m) {
				pr.setErrorNum(ErrorNumberUtils.REGI_REQUEST_EXCEP);
				pr.setErrorMessage("Register Register Parameter Exception.");
				pr.setSuccess(false);
				System.out.println("注册请求参数异常:"+m.getMessage());
				return pr;
			}catch (ClientProtocolException e1) {
				pr.setSuccess(false);
				pr.setErrorNum(ErrorNumberUtils.CLIENT_PROTOCOL_EXCEP);
				pr.setErrorMessage("Client Protocol Exception.");
				System.out.println("客户端协议异常:"+e1.getMessage());
				return pr;
			}catch (IOException e1) {
				pr.setSuccess(false);
				pr.setErrorNum(ErrorNumberUtils.EXEC_HTTP_EXCEP);
				pr.setErrorMessage("Exec Get Method Exception.");
				System.out.println("执行GET请求异常:"+e1.getMessage());
				return pr;
			}catch(Exception e1){
				pr.setSuccess(false);
				System.out.println("注册请求异常:"+e1.getMessage());
				pr.setErrorNum(ErrorNumberUtils.REGISTER_FAILURE);
				return pr;
			}
			//最后封装数据
			RegisterRequestData data = new RegisterRequestData();
			data.setAuthenticateRequests(Auth_list);
			data.setRegisterRequests(reg_list);
			data.setType(type);
			pr.setDetail(data);
		return pr;
	}
	/**
	 * 注册响应
	 * thinkpad dushaofei
	 * @param clientData
	 * @param registData
	 * @param address
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping(value="/v1/RegisterResponse")
	@ResponseBody
	public ProcessResult<Integer> RegistResponse(@RequestBody RegisterResponseData responseData,HttpServletRequest address) throws ClientProtocolException, IOException {
		ProcessResult<Integer> pr = new ProcessResult<Integer>();
		RequestUrl url = HttpRequestMethodUtils.getRequestUrl();
		String access_token;
		try {
			access_token = HttpRequestMethodUtils.getAccesstoKen();
		} catch ( IOException e) {
			pr.setErrorNum(ErrorNumberUtils.GET_TOKENEXCEP);
			pr.setErrorMessage("Get for Accesstoken Exception.");
			pr.setSuccess(false);
			System.out.println("去Q-KEY获取token异常:"+e.getMessage());
			return pr;
		}
		try { 
			HttpUriRequest request = new HttpPost(url.getRegisterRspUrl());
			JSONObject obj = new JSONObject();
			obj.accumulate("clientData", responseData.getClientData().replaceAll("\n", "").replaceAll("=", ""));
			obj.accumulate("registrationData", responseData.getRegistrationData().replaceAll("\n", "").replaceAll("=", ""));
			StringEntity entity = new StringEntity(obj.toString(),HTTP.UTF_8);
			((HttpPost)request).setEntity(entity);
			request.addHeader("Authorization", "bearer "+access_token);
			request.addHeader("content-type","application/json;charset=utf-8");
			JSONObject jsonObj = HttpRequestMethodUtils.getJsonObject(request);
			System.out.println("注册响应:"+jsonObj.toString());
			pr = (ProcessResult<Integer>) jsonObj.toBean(jsonObj,pr.getClass());
			//如果注册成功,就修改该用户的支持Fido协议的状态
			if(pr.getSuccess() ==true){
				String challger = responseData.getClientData();
				System.out.println("开始改变用户Fido状态");
				UpdateUserSupportState(challger);
			}
		} catch (UnsupportedEncodingException m) {
			pr.setErrorNum(ErrorNumberUtils.AUTH_RESPONSE_EXCEP);
			pr.setErrorMessage("Register Rresponse Parameter Exception.");
			pr.setSuccess(false);
			System.out.println("注册响应请求参数异常:"+m.getMessage());
			return pr;
		} catch (ClientProtocolException e1) {
			pr.setErrorNum(ErrorNumberUtils.CLIENT_PROTOCOL_EXCEP);
			pr.setErrorMessage("Client Protocol Exception.");
			pr.setSuccess(false);
			System.out.println("客户端协议异常:"+e1.getMessage());
			return pr;
		}catch (IOException e1) {
			pr.setErrorNum(ErrorNumberUtils.EXEC_HTTP_EXCEP);
			pr.setErrorMessage("Exec Post Method Exception.");
			pr.setSuccess(false);
			System.out.println("执行Post请求异常:"+e1.getMessage());
			return pr;
		}catch(Exception e1){
			System.out.println("注册响应异常109:"+e1.getMessage());
			pr.setErrorNum(ErrorNumberUtils.REGISTER_FAILURE);
			pr.setSuccess(false);
			return pr;
		}
		return pr;
	}
	/**
	 * 验证请求
	 * thinkpad dushaofei
	 * @param username
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value="/rp/AuthenticateRequest/{username}")
	@ResponseBody
	public ProcessResult<AuthenticateRequestData>AuthRequest(@PathVariable String username ) throws ClientProtocolException, IOException{ 
     //  获取token
		String access_token = null;
		AccessTokenRequest access = HttpRequestMethodUtils.getAccessEntity();
		ProcessResult<AuthenticateRequestData> pr = new ProcessResult<AuthenticateRequestData>();
		try {
			access_token = HttpRequestMethodUtils.getAccesstoKen();
		} catch (IOException e) {
			pr.setErrorNum(ErrorNumberUtils.GET_TOKENEXCEP);
			pr.setSuccess(false);
			pr.setErrorMessage("Get for Accesstoken Exception.");
			System.out.println("去Q-KEY获取token异常:"+e.getMessage());
			return pr;
		} 
		try {
			RequestUrl url = HttpRequestMethodUtils.getRequestUrl();
			String requestUrl = url.getAuthReqUrl()+"/"+access.getClient_id()+"/"+username;
			HttpGet get = new HttpGet(requestUrl);
			List<AuthenticateRequest>Auth_list = null;
			//参数token.
			get.addHeader("Authorization", "bearer "+access_token);
			JSONObject jsonObj = HttpRequestMethodUtils.getJsonObject(get);
			System.out.println("验证请求:"+jsonObj.toString());
			//获取detail.
			JSONObject auth =(JSONObject) jsonObj.get(DETAIL);
			JSONObject json_detail = auth.fromObject(auth);
			//获取detail里的请求集合.
			JSONArray requests =	(JSONArray) json_detail.get(AUTHENTICATEREQUESTS);
			//循环requests数组.
			if(requests.size()>0){
				Auth_list = new ArrayList<AuthenticateRequest>();
				for (Object object : requests) {
					AuthenticateRequest request = new AuthenticateRequest();
					//把循环出来的每一个元素转换成对象添加到集合中.
					JSONObject json = JSONObject.fromObject(object);
					request = (AuthenticateRequest) json.toBean(json, AuthenticateRequest.class);
					Auth_list.add(request);
					//添加用户跟验证请求的实体
					com.idsmanager.oauth.domain.user.User user = userService.getUserAndO2OByName(username);
					if(null!=user){
						userService.saveUserAndAuth(new UserAndAuthRequest(user, request));
					}
				}
			}
			//封装数据
			String type = (String) json_detail.get(TYPE);
			AuthenticateRequestData authData = new AuthenticateRequestData();
			authData.setAuthenticateRequests(Auth_list);
			authData.setType(type);
			pr.setDetail(authData);
		}catch (ClientProtocolException e1) {
			pr.setErrorNum(ErrorNumberUtils.CLIENT_PROTOCOL_EXCEP);
			pr.setSuccess(false);
			pr.setErrorMessage("Client Protocol Exception.");
			System.out.println("客户端协议异常:"+e1.getMessage());
			return pr;
		}catch (IOException e1) {
			pr.setErrorNum(ErrorNumberUtils.EXEC_HTTP_EXCEP);
			pr.setSuccess(false);
			pr.setErrorMessage("Exec Get Method Exception.");
			System.out.println("执行GET请求异常:"+e1.getMessage());
			return pr;
		}catch(Exception e1){
			System.out.println("验证请求异常:"+e1.getMessage());
			pr.setErrorNum(ErrorNumberUtils.AUTH_REQUEST_EXCEP);
			pr.setSuccess(false);
			return pr;
		}
		return pr;
	}
	/**
	 * 验证响应
	 * thinkpad dushaofei
	 * @param clientData
	 * @param signatureData
	 * @param keyHandle
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@RequestMapping(value="/rp/AuthenticateResponse")
	@ResponseBody
	public ProcessResult<Map<String, Object>>AuthResponse(@RequestBody AuthenticateResponseData responseData) throws ClientProtocolException, IOException{
		ProcessResult<Map<String, Object>> mapPr = new ProcessResult<Map<String, Object>>();
		RequestUrl url = HttpRequestMethodUtils.getRequestUrl();
		String access_token;
		try {
			access_token = HttpRequestMethodUtils.getAccesstoKen();
		} catch ( IOException e) {
			mapPr.setErrorNum(ErrorNumberUtils.GET_TOKENEXCEP);
			mapPr.setErrorMessage("Get AccessToken  Exception.");
			mapPr.setSuccess(false);
			System.out.println("去Q-KEY获取token异常:"+e.getMessage());
			return mapPr;
		}
		try {
			HttpUriRequest post = new HttpPost(url.getAuthRspUrl());
			JSONObject jsonP = new JSONObject();
			jsonP.accumulate("clientData", responseData.getClientData().replaceAll("\n", "").replaceAll("=", ""));
			jsonP.accumulate("signatureData", responseData.getSignatureData().replaceAll("\n", "").replaceAll("=", ""));
			jsonP.accumulate("keyHandle", responseData.getKeyHandle());
			StringEntity entity;
			entity = new StringEntity(jsonP.toString(),HTTP.UTF_8);
			((HttpPost)post).setEntity(entity);
			post.addHeader("content-type","application/json;charset=utf-8");
			post.addHeader("Authorization", "bearer "+access_token);
			JSONObject jsonObj = HttpRequestMethodUtils.getJsonObject(post);
			System.out.println("验证响应:"+jsonObj.toString());
			mapPr = (ProcessResult<Map<String, Object>>) jsonObj.toBean(jsonObj, mapPr.getClass());
			if(mapPr.getSuccess()==true){
				String challger = responseData.getClientData();
				System.out.println("开始返回用户登录次数和应用名字");
				mapPr = ReturnUserAndAppName(challger);
			}else{
				mapPr.setErrorNum(ErrorNumberUtils.BAD_REQUEST);
				mapPr.setSuccess(false);
			}
		} catch (UnsupportedEncodingException m) {
			mapPr.setErrorNum(ErrorNumberUtils.AUTH_RESPONSE_EXCEP);
			mapPr.setErrorMessage("Verification Rresponse Parameter Exception.");
			mapPr.setSuccess(false);
			System.out.println("验证响应请求参数异常:"+m.getMessage());
			return mapPr;
		}catch (ClientProtocolException e1) {
			mapPr.setErrorNum(ErrorNumberUtils.CLIENT_PROTOCOL_EXCEP);
			mapPr.setSuccess(false);
			mapPr.setErrorMessage("Client Protocol Exception.");
			System.out.println("客户端协议异常:"+e1.getMessage());
			return mapPr;
		}catch (IOException e1) {
			mapPr.setErrorNum(ErrorNumberUtils.EXEC_HTTP_EXCEP);
			mapPr.setSuccess(false);
			mapPr.setErrorMessage("Exec Post Method Exception.");
			System.out.println("执行POST请求异常:"+e1.getMessage());
			return mapPr;
		}catch(Exception e1){
			System.out.println("验证响应异常:"+e1.getMessage());
			mapPr.setErrorNum(ErrorNumberUtils.BAD_AUTHENTCATION_RESPONSE);
			mapPr.setSuccess(false);
			return mapPr;
		}
		return mapPr;
	}
	/**
	 * 验证响应
	 * thinkpad dushaofei
	 * @param challger
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	private ProcessResult<Map<String, Object>> ReturnUserAndAppName(String challger) throws ClientProtocolException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		ProcessResult<Map<String, Object>> pr = new ProcessResult<Map<String, Object>>();
		String code = Base64.base64Decode(challger);
		JSONObject chal_code = JSONObject.fromObject(code);
		String challenge = (String) chal_code.get(CHALLENGE);
		//根据challenge查询出验证请求和用户对于实体
		UserAndAuthRequest user_auth = userService.getUserByChallger(challenge);
		if(null!=user_auth){
			com.idsmanager.oauth.domain.user.User user = user_auth.getUser();
			Integer num = userService.updateUserAndO2OByName(user.username());
			AuthResponseResult result = new AuthResponseResult(user.username(), num);
			
			String token =getAccess_tokenByUserName(user.username());
			map.put(USER_INFO, result);
			map.put(ACCESS_TOKEN, token);
			pr.setDetail(map);
			//删除数据库中challenge不一样的数据
			List<UserAndAuthRequest> requeList = userService.getUserAndAuthByName(user.username());
			if(requeList.size()>0){
				for (UserAndAuthRequest userAndAuthRequest : requeList) {
					AuthenticateRequest auth_req = userAndAuthRequest.getRequest();
					if(!auth_req.getChallenge().equals(challenge)){
						userService.deleteUserAndAuthRequest(auth_req.getChallenge());
					}
				}
			}
		}
		return pr;
	}
	/**
	 * 获取用户金额
	 * thinkpad dushaofei
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/v1/user/money", method = RequestMethod.POST)
	@ResponseBody
	public ProcessResult<Double> getUserByMoney(){
		String username = OauthUserName();
		ProcessResult<Double> pr = new ProcessResult<Double>();
		if (StringUtils.isBlank(username)) {
			pr.setSuccess(false);
			pr.setErrorNum(ErrorNumberUtils.USER_NAME_NULL);
			pr.setErrorMessage("user name is required.");
			return pr;
		}
		UserMoney money = userService.getMoneyByUserId(username);
		if(null!=money){
			pr.setDetail(money.getMoney());
			return pr;
		}
		pr.setSuccess(false);
		pr.setErrorNum(ErrorNumberUtils.RP_NOT_USERS);
		return pr;
	}
	/**
	 * 用户请求交易
	 * thinkpad dushaofei
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/v1/user/startTransaction",method=RequestMethod.POST)
	@ResponseBody
	public ProcessResult<AuthenticateRequestData> startTeanSaction( @RequestBody TransactionRequest request){
		ProcessResult<AuthenticateRequestData> pr = new ProcessResult<AuthenticateRequestData>();
		RequestUrl url = HttpRequestMethodUtils.getRequestUrl();
		String access_token;
		AccessTokenRequest access_rp = HttpRequestMethodUtils.getAccessEntity();
		try {
			access_token = HttpRequestMethodUtils.getAccesstoKen();
		} catch (Exception e) {
			pr.setErrorNum(ErrorNumberUtils.GET_TOKENEXCEP);
			pr.setErrorMessage("Get AccessToken  Exception.");
			pr.setSuccess(false);
			System.out.println("去Q-KEY获取token异常:"+e.getMessage());
			return pr;
		}
		UserMoney userMoney = userService.getMoneyByUserId(request.getUsername());
		Double money = userMoney.getMoney();
		if(Double.parseDouble(request.getMoney())>money){
			pr.setSuccess(false);
			pr.setErrorNum(ErrorNumberUtils.TRANSACTION_MONEY_MAX_EXCEP);
			return pr;
		}
		String str = request.getMoney();
		boolean when1 = str.contains(".");
		if(when1){
			int index = request.getMoney().lastIndexOf(".");
			String sindex = request.getMoney().substring(index + 1, request.getMoney().length());
			if(sindex.length()>2){
				pr.setSuccess(false);
				pr.setErrorNum(ErrorNumberUtils.MONEY_FORMAT_EXCEP);
				System.out.println("格式不正确");
				return pr;
			}
		}
		
		HttpUriRequest post = new HttpPost(url.getStartTransactionUrl());
		JSONObject jsonP = new JSONObject();
		jsonP.accumulate("appId", access_rp.getClient_id());
		jsonP.accumulate("username", request.getUsername());
		jsonP.accumulate("money", request.getMoney());
		StringEntity entity;
		try {
			entity = new StringEntity(jsonP.toString(),HTTP.UTF_8);
			((HttpPost)post).setEntity(entity);
			post.addHeader("content-type","application/json;charset=utf-8");
			post.addHeader("Authorization", "bearer "+access_token);
			JSONObject jsonObj = HttpRequestMethodUtils.getJsonObject(post);
			System.out.println("返回结果:"+jsonObj);
			int when = 0;//(int) jsonObj.get("errorNum");
			if(when==0){
				JSONObject auth =(JSONObject) jsonObj.get(DETAIL);
				@SuppressWarnings("static-access")
				JSONObject json_detail = auth.fromObject(auth);
				//循环requests数组.
				ArrayList<AuthenticateRequest> Auth_list = new ArrayList<AuthenticateRequest>();
				//获取detail里的请求集合.
				JSONArray requests = (JSONArray) json_detail.get(AUTHENTICATEREQUESTS);
				
				String m = request.getMoney();
				String username = OauthUserName();
				
				for (Object object : requests) {
					//把循环出来的每一个元素转换成对象添加到集合中.
					JSONObject json = JSONObject.fromObject(object);
					@SuppressWarnings("static-access")
					AuthenticateRequest requestAuth = (AuthenticateRequest) json.toBean(json, AuthenticateRequest.class);
					Auth_list.add(requestAuth);
					if(username!=null && requestAuth!=null){
						userService.saveUserMoneyRequest(new UserMoneyRequest(username,m,requestAuth));
					}
				}
				AuthenticateRequestData data = new AuthenticateRequestData();
				String type = (String) json_detail.get(TYPE);
				data.setType(type);
				data.setAuthenticateRequests(Auth_list);
				pr.setDetail(data);
				return pr;
			}
			pr.setErrorNum(ErrorNumberUtils.TRANSACTION_DEVICE_EXCEP);
		} catch (UnsupportedEncodingException e) {
			 
			e.printStackTrace();
		}catch (ClientProtocolException e) {
			 
			e.printStackTrace();
		}catch (IOException e) {
			 
			e.printStackTrace();
		}catch(Exception e){
			//删除临时表
			String username = OauthUserName();
			userService.deleteUserMoneyRequest(username);
		}
		return pr;
	}
	/**
	 * 交易确认响应
	 * thinkpad dushaofei
	 * @param clientData
	 * @param signatureData
	 * @param keyHandle
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value="/v1/user/finishTransaction",method=RequestMethod.POST)
	@ResponseBody
	public ProcessResult<String>finishTransResponse(HttpServletRequest requestSession,HttpServletResponse resp, @RequestBody AuthenticateResponseData responseData) throws ClientProtocolException, IOException{
		ProcessResult<String> mapPr = new ProcessResult<String>();
		RequestUrl url = HttpRequestMethodUtils.getRequestUrl();
		String access_token;
		String name = OauthUserName();
		try {
			access_token = HttpRequestMethodUtils.getAccesstoKen();
		} catch ( Exception e) {
			mapPr.setErrorNum(ErrorNumberUtils.GET_TOKENEXCEP);
			mapPr.setErrorMessage("Get AccessToken  Exception.");
			mapPr.setSuccess(false);
			System.out.println("去Q-KEY获取token异常:"+e.getMessage());
			userService.deleteUserMoneyRequest(name);
			return mapPr;
		}
		try {
			HttpUriRequest post = new HttpPost(url.getResponseTransactionUrl());
			JSONObject jsonP = new JSONObject();
			jsonP.accumulate("clientData", responseData.getClientData().replaceAll("\n", "").replaceAll("=", ""));
			jsonP.accumulate("signatureData", responseData.getSignatureData().replaceAll("\n", "").replaceAll("=", ""));
			jsonP.accumulate("keyHandle", responseData.getKeyHandle());
			StringEntity entity;
			entity = new StringEntity(jsonP.toString(),HTTP.UTF_8);
			((HttpPost)post).setEntity(entity);
			post.addHeader("content-type","application/json;charset=utf-8");
			post.addHeader("Authorization", "bearer "+access_token);
			JSONObject jsonObj = HttpRequestMethodUtils.getJsonObject(post);
			mapPr = (ProcessResult<String>) jsonObj.toBean(jsonObj, mapPr.getClass());
			if(mapPr.getErrorNum() == 0){
				//修改用户金额
				List<UserMoneyRequest> money = userService.UserMoneyRequestByName(name);
				if(money!=null && money.size()>0){
					String challger = responseData.getClientData();
					System.out.println("开始减去用户金额");
					Double restMoney = UpdateUserMoney(challger);
					if(null!=restMoney){
						String str=String.format("%7.2f", restMoney);
						mapPr.setDetail(String.valueOf(str));
						return mapPr;
					}
					if(mapPr.getErrorNum() == 402){
						System.out.println("长度过长");
						mapPr.setErrorNum(ErrorNumberUtils.MONEY_LENGTH_MAX_EXCEP);
						userService.deleteUserMoneyRequest(name);
						return mapPr;
					}else if(mapPr.getErrorNum() == 403){
						System.out.println("交易异常");
						mapPr.setErrorNum(ErrorNumberUtils.TRANSACTION_EXCEP);
						userService.deleteUserMoneyRequest(name);
						return mapPr;
					}else{
						System.out.println("交易金额不能大于余额");
						mapPr.setErrorNum(ErrorNumberUtils.TRANSACTION_MONEY_MAX_EXCEP);
						userService.deleteUserMoneyRequest(name);
						return mapPr;
					}
				}
			}else{
				mapPr.setErrorNum(ErrorNumberUtils.BAD_REQUEST);
				mapPr.setSuccess(false);
				userService.deleteUserMoneyRequest(name);
			}
		} catch (UnsupportedEncodingException m) {
			mapPr.setErrorNum(ErrorNumberUtils.TRANSACTION_EXCEP);
			mapPr.setErrorMessage("Verification Rresponse Parameter Exception.");
			mapPr.setSuccess(false);
			System.out.println("交易响应请求参数异常:"+m.getMessage());
			userService.deleteUserMoneyRequest(name);
			return mapPr;
		}catch (ClientProtocolException e1) {
			mapPr.setErrorNum(ErrorNumberUtils.CLIENT_PROTOCOL_EXCEP);
			mapPr.setSuccess(false);
			mapPr.setErrorMessage("Client Protocol Exception.");
			System.out.println("客户端协议异常:"+e1.getMessage());
			userService.deleteUserMoneyRequest(name);
			return mapPr;
		}catch (IOException e1) {
			mapPr.setErrorNum(ErrorNumberUtils.EXEC_HTTP_EXCEP);
			mapPr.setSuccess(false);
			mapPr.setErrorMessage("Exec Post Method Exception.");
			System.out.println("执行POST请求异常:"+e1.getMessage());
			userService.deleteUserMoneyRequest(name);
			return mapPr;
		}catch(Exception e1){
			System.out.println("交易响应异常:"+e1.getMessage());
			mapPr.setErrorNum(ErrorNumberUtils.TRANSACTION_EXCEP);
			mapPr.setSuccess(false);
			userService.deleteUserMoneyRequest(name);
			return mapPr;
		}
		return mapPr;
	}
	/**
	 * 修改用户金额
	 * thinkpad dushaofei
	 * @param challger
	 */
	public Double UpdateUserMoney(String challger){
		String code = Base64.base64Decode(challger);
		JSONObject chal_code = JSONObject.fromObject(code);
		String challenge = (String) chal_code.get(CHALLENGE);
		//根据challenge查询出用户跟注册请求实体类
		UserMoneyRequest reg_user = userService.getUserAndMoneyByChallenge(challenge);
		Double restMoney = null;
		if(reg_user!=null){
			restMoney = userService.updateUserMoneyByName(reg_user.getUsername(), Double.parseDouble(reg_user.getMoney()));
			//删除数据库中challenge不一样的数据
			userService.deleteUserMoneyRequest(reg_user.getUsername());
			return restMoney;
		}
		return restMoney;
	}
	/**
	 * 修改用户支持Fido协议状态
	 * thinkpad dushaofei
	 * @param challger
	 */
	public void UpdateUserSupportState(String challger){
		String code = Base64.base64Decode(challger);
		JSONObject chal_code = JSONObject.fromObject(code);
		String challenge = (String) chal_code.get(CHALLENGE);
		//根据challenge查询出用户跟注册请求实体类
		RegisterRequestAndUser reg_user = userService.getUserAndRegisterByChallenge(challenge);
		if(reg_user!=null){
			com.idsmanager.oauth.domain.user.User user = reg_user.getUser();
			userService.updateUserSupportFido(user.username());
			//删除数据库中challenge不一样的数据
			List<RegisterRequestAndUser> requeList = userService.getRegistAndUserByName(user.username());
			if(requeList.size()>0){
				for (RegisterRequestAndUser requestAndUser : requeList) {
						RegisterRequest auth_req = requestAndUser.getRequest();
						userService.deleteUserAndRegist(auth_req.getChallenge());
				}
			}
		}
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
	/**
	 * 获取用户名
	 * thinkpad dushaofei
	 * @return
	 */
	public String OauthUserName() {
		String name = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		return name;
	}
}
