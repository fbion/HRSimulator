package com.idsmanager.rp.util;

public class ErrorNumberUtils {
	/**
	 * 用户名不能为空
	 */
	public static final Integer USER_NAME_NULL=101;
	/**
	 * 用户已注册
	 */
	public static final Integer USER_REGISTERED = 102;
	/**
	 * 用户和challenge不存在
	 */
	public static final Integer USERCHALLENGE_DOES_NOT_EXIST = 103;
	/**
	 * 二次认证
	 */
	public static final Integer TWO_CERTIFICATION  = 104;
	/**
	 * 用户没有设备
	 */
	public static final Integer USER_NOT_DEVICE = 105;
	
	/**
	 * 注册响应匹配失败
	 */
	public static final Integer BAD_AUTHENTCATION_RESPONSE = 106;
	/**
	 * 用户不存在
	 */
	public static final Integer USER_NOT_EXSITS = 107;
	/**
	 * 验证appId异常
	 */
	public static final Integer APPID_EXCEPTION = 108;
	/**
	 * 注册失败,失败原因不明确才会用到
	 */
	public static final Integer REGISTER_FAILURE = 109;
	/**
	 * 登录用户名异常
	 */
	public static final Integer USER_NAME_ILLEGAL = 110;
	
	/**
	 * Success. Not used in errors but reserved
	 */
	public static final Integer OK = 0;
	
	/**
	 * An error otherwise not enumerated here
	 */
	public static final Integer OTHER_ERROR = 201;
	
	/**
	 * The request cannot be processed
	 */
	public static final Integer BAD_REQUEST=202;
	
	/**
	 * Client configuration is not supported
	 */
	public static final Integer CONFIGURATION_UNSUPPORTED = 203;
	
	/**
	 * The presented device is not eligible for this request. For a registration request this may mean that the token is already registered,
	 *  and for a sign request it may mean the token does not know the presented key handle.
	 */
	public static final Integer DEVICE_INELIGIBLE = 204;
	
	/**
	 * Timeout reached before request could be satisfied
	 */
	public static final Integer TIMEOUT = 205;
	/**
	 * 去QK获取token异常 
	 */
	public static final Integer GET_TOKENEXCEP = 301;
	/**
	 * 客户端协议异常
	 */
	public static final Integer CLIENT_PROTOCOL_EXCEP = 302;
	/**
	 * 验证请求异常
	 */
	public static final Integer AUTH_REQUEST_EXCEP = 303;
	/**
	 * 验证响应请求参数异常
	 */
	public static final Integer AUTH_RESPONSE_EXCEP = 304;
	/**
	 * 注册响应请求参数异常
	 */
	public static final Integer REG_RESPONSE_EXCEP = 305;
	/**
	 * RP没有该用户
	 */
	public static final Integer RP_NOT_USERS = 306;
	/**
	 * 已经注册该RP用户
	 */
	public static final Integer RP_USER_REGISTERED = 307;
	
	/**
	 * 没开启二次认证并且没有U2F设备
	 */
	public static final Integer USER_CAN_NOT_LOGIN_WITHOUT_PASSWORD = 308;
	/**
	 *注册请求参数异常
	 */
	public static final Integer REGI_REQUEST_EXCEP = 309;
	/**
	 * 调用FidoServer执行Http请求时异常
	 */
	public static final Integer EXEC_HTTP_EXCEP = 310;
	/**
	 * 获取RP access_token异常
	 */
	public static final Integer GET_RP_USER_TOKEN_EXCEP = 311;
	/**
	 * 查看用户是否有设备时异常
	 */
	public static final Integer GET_FIDO_DEVICE_EXCEP = 312;
	/**
	 * 关闭二次认证异常
	 */
	public static final Integer CLOSE_VERTIFY_EXCEP = 313;
	/**
	 * 交易异常
	 */
	public static final Integer TRANSACTION_EXCEP = 314;
	
	/**
	 * 用户没有设备不能交易
	 */
	public static final Integer TRANSACTION_DEVICE_EXCEP = 315;
	/**
	 * 交易金额不能大于余额
	 */
	public static final Integer TRANSACTION_MONEY_MAX_EXCEP = 316;
	
	/**
	 * 交易金额过大
	 */
	public static final Integer MONEY_LENGTH_MAX_EXCEP = 402;
	
	/**
	 * 一个设备只能在一个应用下注册一次
	 */
	public static final Integer DEVICE_REGIST_ONE_EXCEP = 405;
	/**
	 * 金额格式不正确
	 */
	public static final Integer MONEY_FORMAT_EXCEP = 406;
}
