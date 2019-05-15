package com.zcj.network.ipmanager;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Map;

/**
 * Created by admin on 2017/11/14.
 */
public class HttpsRequest {
	
		//godday forward modify api url
	//https://api.godaddy.com/v2/customers/{customerId}/domains/forwards/{dns}
	private static String forwardModifyUrl = 
			"https://api.godaddy.com/v2/customers/b7c7e62f-68e4-4ead-a5f4-7262f3812312/domains/forwards/shengxuan.space";
	//Authorization:sso-key key:secrit
	
	// "{'type': 'REDIRECT_PERMANENT','url': 'http://10.131.129.90:9890'}";
	private static String params = "{\"type\": \"REDIRECT_PERMANENT\",\"url\": \"http://%s\"}";
    //post方式，以输出流的形式发送
    /**
     * 发送https请求
     *
     * @param requestUrl
     *            请求地址
     * @param requestMethod
     *            请求方式（GET、POST）
     * @param outStr
     *            提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static String sendRequest(String ip) throws Exception{
    	try{
//      传递的URL
	        URL url = new URL(forwardModifyUrl);
	        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
	        connection.setRequestMethod("PUT");
	        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			//key ,secrit for godaddy deverlop api  ,
	        connection.setRequestProperty("Authorization", "sso-key dLYeauX62LDN_4BP6FqFZsDTGEYX6PeSgaB:4BPAbNh6s3Qr5MGQNsS1Sf");
	//      请求方式
	        connection.setDoOutput(true);
	        connection.connect();
	
	        OutputStream outputStream = connection.getOutputStream();
	        outputStream.write(String.format(params,ip).getBytes("utf-8"));
	        outputStream.close();
	
	        System.out.println(connection.getResponseCode());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return "";
    }

}