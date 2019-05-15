package com.zcj.network.ipmanager;

import java.util.Timer;

public class App {
	public static void main(String[] args) {
		 Timer timer = new Timer(); 
		 long delay2 = 2 * 1000; 
		 long period2 =60 * 1000; 
		 // 从现在开始 2 秒钟之后，每隔 2 秒钟执行一次 job2 
		 timer.schedule(new NotifyEmailSendTask("13408051342@139.com"), delay2, period2); 
	}
}
