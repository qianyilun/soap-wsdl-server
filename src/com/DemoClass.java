package com;

import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

public class DemoClass {
	public String func(int a, int b) {
		return "heh" + String.valueOf(a + b);
	}
	
	public String getServerTime() {
		JSONObject jsonObject = new JSONObject();
		Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        
        long timeInMillis = date.getTime(); 
        jsonObject.put("timeInMillis", String.valueOf(timeInMillis));
        return jsonObject.toString();
	}
}
