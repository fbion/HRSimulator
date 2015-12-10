package com.idsmanager.rp.util.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.idsmanager.rp.util.HttpClientUtils;

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
	
}
