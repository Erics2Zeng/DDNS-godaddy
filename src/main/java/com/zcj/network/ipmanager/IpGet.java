package com.zcj.network.ipmanager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

public class IpGet {
	public static String getV4IP(){

		try {
			String ip= "";
			String result = post("http://tplogin.cn/","{\"method\":\"do\",\"login\":{\"password\":\"RzyLPvQdP3q0gwK\"}}".getBytes("UTF-8"));
			if (result != null) {
				JSONObject json = JSONObject.parseObject(result.toString());
				if (0 == json.getInteger("error_code")) {
					String token = json.getString("stok");
					token = token.replaceAll("\\+", "%20")
							.replaceAll("\\%21", "!")
							.replaceAll("\\%27", "'")
							.replaceAll("\\%28", "(")
							.replaceAll("\\%29", ")")
							.replaceAll("\\%7E", "~");
					String result1 = post("http://tplogin.cn/stok="+token+"/ds","{\"network\":{\"name\":[\"wan_status\"]},\"method\":\"get\"}".getBytes("UTF-8"));
					if ( null!= result) {
						JSONObject jsonOuter = JSONObject.parseObject(result1.toString());
						if (0 == jsonOuter.getInteger("error_code")) {
							ip = jsonOuter.getJSONObject("network").getJSONObject("wan_status").getString("ipaddr");
							System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())+"the outer ip is:" + ip);
							
						}
					}
//					System.out.println("*********************************release ip");
//					String realseIp = post("http://tplogin.cn/stok="+token+"/ds",
//							"{\"network\":{\"change_wan_status\":{\"proto\":\"pppoe\",\"operate\":\"disconnect\"}},\"method\":\"do\"}".getBytes("UTF-8"));
//				    System.out.println("************************************** grt ip");
//					String getIp = post("http://tplogin.cn/stok="+token+"/ds",
//							"{\"network\":{\"change_wan_status\":{\"proto\":\"pppoe\",\"operate\":\"connect\"}},\"method\":\"do\"}".getBytes("UTF-8"));
//					
					return  ip;
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static String post(String ur,byte[] body){
		
		StringBuilder inputLine = new StringBuilder();
		String read = "";
		URL url = null;
		HttpURLConnection urlConnection = null;
		BufferedReader in = null;
		try {
			url = new URL(ur);
		
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoOutput(true);
			urlConnection.getOutputStream().write(body);
			urlConnection.setConnectTimeout(100);
			in = new BufferedReader( new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
			while((read=in.readLine())!=null){
			inputLine.append(read+"\r\n");
			}
			//System.out.println(inputLine.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return inputLine.toString();
	}
}
