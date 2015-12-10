package com.idsmanager.rp.util;
/**
 * 请求地址工具类
 *类名称
 *创建人 dushaofei
 *创建时间：2015-12-2 上午11:02:39 
 *类描述：
 *@version
 */
public class RequestUrl {
	private String deviceUrl;
	private String registerReqUrl;
	private String registerRspUrl;
	private String authReqUrl;
	private String authRspUrl;
	private String startTransactionUrl;
	private String responseTransactionUrl;
	public String getDeviceUrl() {
		return deviceUrl;
	}
	public void setDeviceUrl(String deviceUrl) {
		this.deviceUrl = deviceUrl;
	}
	public String getRegisterReqUrl() {
		return registerReqUrl;
	}
	public void setRegisterReqUrl(String registerReqUrl) {
		this.registerReqUrl = registerReqUrl;
	}
	public String getRegisterRspUrl() {
		return registerRspUrl;
	}
	public void setRegisterRspUrl(String registerRspUrl) {
		this.registerRspUrl = registerRspUrl;
	}
	public String getAuthReqUrl() {
		return authReqUrl;
	}
	public void setAuthReqUrl(String authReqUrl) {
		this.authReqUrl = authReqUrl;
	}
	public String getAuthRspUrl() {
		return authRspUrl;
	}
	public void setAuthRspUrl(String authRspUrl) {
		this.authRspUrl = authRspUrl;
	}
	public String getStartTransactionUrl() {
		return startTransactionUrl;
	}
	public void setStartTransactionUrl(String startTransactionUrl) {
		this.startTransactionUrl = startTransactionUrl;
	}
	public String getResponseTransactionUrl() {
		return responseTransactionUrl;
	}
	public void setResponseTransactionUrl(String responseTransactionUrl) {
		this.responseTransactionUrl = responseTransactionUrl;
	}
	
}
