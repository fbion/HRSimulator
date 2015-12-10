package com.idsmanager.rp.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.idsmanager.oauth.domain.dto.UserDto;
import com.idsmanager.oauth.domain.user.User;
import com.idsmanager.rp.domain.AccessTokenRequest;
import com.idsmanager.rp.domain.GetAccessThisUserToken;


public class HttpRequestMethodUtils {
	/**
	 * 执行GET请求
	 * thinkpad dushaofei
	 * @param get
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static JSONObject getJsonObject(HttpGet get) throws ClientProtocolException, IOException{
		get.setHeader("Content-Type","application/json;charset=UTF-8");
		HttpClient hc = HttpClientUtils.warpClient();
		HttpResponse resp =hc.execute(get);
		HttpEntity enty =resp.getEntity();
		InputStream is = enty.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String backMessage = br.readLine();
		System.out.println("Get请求返回数据:"+backMessage);
		JSONObject json = JSONObject.fromObject(backMessage);
		return json;
	}
	/**
	 * 执行POST请求
	 * thinkpad dushaofei
	 * @param request
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static JSONObject getJsonObject(HttpUriRequest post) throws ClientProtocolException, IOException{
		HttpClient hc = HttpClientUtils.warpClient();
		HttpResponse response = hc.execute(post);
		HttpEntity Respentity = response.getEntity();
		InputStream is = Respentity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String backMessage = br.readLine();
		System.out.println("Post请求返回数据:"+backMessage);
		JSONObject jsonObj = JSONObject.fromObject(backMessage);
		return jsonObj;
	}
	/**
	 * 向Q-keyServer发起请求,获取Access_token
	 * thinkpad dushaofei
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static String getAccesstoKen() throws ClientProtocolException, IOException{ 
		AccessTokenRequest token = getAccessEntity();
		System.out.println("去Q-KEY获取access_token:"+token);
		String url = token.getUrl();
		HttpUriRequest post = new HttpPost(url);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("client_id", token.getClient_id()));
		list.add(new BasicNameValuePair("client_secret", token.getClient_secret()));
		list.add(new BasicNameValuePair("grant_type", token.getGrant_type()));
		list.add(new BasicNameValuePair("scope", token.getScope()));
		HttpEntity params = null;
		try {
			params = new UrlEncodedFormEntity(list,"UTF-8");
			((HttpPost)post).setEntity(params);
			
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} 
		JSONObject obj = HttpRequestMethodUtils.getJsonObject(post);
		
		return obj.getString("access_token");
	}
	/**
	 * 获取配置文件中的请求QKEY获取Access_
	 * thinkpad dushaofei
	 * @return
	 */
	public static AccessTokenRequest getAccessEntity(){
		ApplicationContext context = new ClassPathXmlApplicationContext("rp/rp_qkey_get.xml");   
		AccessTokenRequest token = (AccessTokenRequest)context.getBean("access_token");   
		return token;
	} 
	/**
	 * 获取配置文件中的获取用户Access_token
	 * thinkpad dushaofei
	 * @return
	 */
	public static GetAccessThisUserToken getOauthAccess(){
		ApplicationContext context = new ClassPathXmlApplicationContext("rp/rp_oauth_get.xml");   
		GetAccessThisUserToken token = (GetAccessThisUserToken)context.getBean("access_token");   
		return token;
	} 
	/**
	 * 获取配置文件中的请求地址
	 * thinkpad dushaofei
	 * @return
	 */
	public static RequestUrl getRequestUrl(){
		ApplicationContext context = new ClassPathXmlApplicationContext("rp/rp_fido_get.xml");   
		RequestUrl url = (RequestUrl)context.getBean("request_url");   
		return url;
	} 
	/**
	 * 获取RP用户TOKEN
	 * thinkpad dushaofei
	 * @param user
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static JSONObject getLoginInfo(String username,String password) throws ClientProtocolException, IOException{
		//String url = "http://localhost:8080/RPServer/oauth/token";
		GetAccessThisUserToken token = getOauthAccess();
		HttpPost post = new HttpPost(token.getUrl());
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("client_id", token.getClient_id()));
		list.add(new BasicNameValuePair("client_secret", token.getClient_secret()));
		list.add(new BasicNameValuePair("grant_type",token.getGrant_type()));
		list.add(new BasicNameValuePair("scope", token.getScope()));
		list.add(new BasicNameValuePair("username", username));
		list.add(new BasicNameValuePair("password", password));
		HttpEntity params = null;
		params = new UrlEncodedFormEntity(list,"UTF-8");
		((HttpPost)post).setEntity(params);
		JSONObject json =HttpRequestMethodUtils.getJsonObject(post);
		return json;
	}
	

	
	  /** 
     * 字节数组转换成对象 
     * @param bytes  
     * @return Object 取得结果后强制转换成你存入的对象类型 
     */  
    public static Object ByteToObject(byte[] bytes){  
        java.lang.Object obj = null;  
        try {  
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);  
        ObjectInputStream oi = new ObjectInputStream(bi);  
  
        obj = oi.readObject();  
  
        bi.close();  
        oi.close();  
        }  
        catch(Exception e) {  
            System.out.println("translation"+e.getMessage());  
            e.printStackTrace();  
        }  
        return obj;  
    } 
}
