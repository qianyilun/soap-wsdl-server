package com;

import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

public class DemoClass {
	public String func(int a, int b) {
		return "server's " + String.valueOf(a + b);
	}
	
	public String getServerTime() {
		JSONObject jsonObject = new JSONObject();
		Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        
        long timeInMillis = date.getTime(); 
        System.out.println("Server is invoked");
        System.out.println("Server's system time is: " + date);
        
        jsonObject.put("timeInMillis", String.valueOf(timeInMillis));
        
        System.out.println("Server will return to client: " + jsonObject.toString());
        return jsonObject.toString();
	}
}
