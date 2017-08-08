package com.wifitz.utils;

import com.google.gson.Gson;


public class JsonUtil {
//	public static String creatJsonString(Object data) {
//		JSONObject json = JSONObject.fromObject(data);
//		return json.toString();
//	}
	public static String creatJsonString(Object data) {
//		JSONObject json = JSONObject.fromObject(data);
//		return json.toString();
		return new Gson().toJson(data);
	}

}
