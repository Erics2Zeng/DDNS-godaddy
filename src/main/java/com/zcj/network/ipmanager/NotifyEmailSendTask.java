
package com.zcj.network.ipmanager;  
  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.TimerTask;

import org.apache.log4j.Logger;  
  

public class NotifyEmailSendTask extends  TimerTask  {  
  
    private String toMails;  
    private Logger logger = Logger.getLogger(this.getClass());  
    static Properties config = new  Properties();
    static String file =   Thread.currentThread().getContextClassLoader()
    		.getResource("ipconfig.properties").getFile();
    public NotifyEmailSendTask(String toMails) {  
        this.toMails = toMails;  
    }  
  
    public void run() {  
        String routerIp   = IpGet.getV4IP();
        try {  
            if (routerIp != null&&!routerIp.equals("0.0.0.0")) {    
                    if (!routerIp.equals(getPropetiesIp())){  
                    	OutputStream fos = new FileOutputStream(file);  
                    	config.setProperty("ip", routerIp);
	                    config.store(fos, "Update ip value");  
	                    logger.info("开始发送邮件" + toMails);  
	                    MailManager.send(toMails,"ip地址已更新:"+routerIp); 
	                    //更新ddns
//	                    DDNSManager.doPost(routerIp +":7081");
	                    HttpsRequest.sendRequest(routerIp + ":7081" );
	                } else {  
	                    logger.debug("发送邮件失败：" + routerIp );  
	                }  
            }
        } catch (Exception e) { 
        	System.out.println(e.getMessage());
            logger.warn(e.getMessage());  
        } 
  
    }  
    
    public static String getPropetiesIp(){
    	try {
    		System.out.println( file);
    	  config.load(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
			
      return  config.getProperty("ip");
    }

}  
