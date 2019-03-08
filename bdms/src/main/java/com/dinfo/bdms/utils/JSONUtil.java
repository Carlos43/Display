package com.dinfo.bdms.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class JSONUtil {

	private static Gson gson = null;

	static {
		//		gson = new Gson();//todo yyyy-MM-dd HH:mm:ss
		gson = new GsonBuilder().setLenient().create();//todo yyyy-MM-dd HH:mm:ss
	}

	public static synchronized Gson newInstance() {
		if (gson == null) {
			gson = new Gson();
		}
		return gson;
	}

	public static String toJson(Object obj) {
		return gson.toJson(obj);
	}

	public static String toBeautyJson(Object obj){
		return new GsonBuilder().setLenient().setPrettyPrinting().create().toJson(obj);
	}

	public static <T> T toBean(String json, Class<T> clz) {
		String data;
		if (json.startsWith("\"") && !clz.equals(String.class)) {
			data = toBean(json, String.class);
		}
		else {
			data = json;
		}
		return gson.fromJson(data, clz);
	}

	public static List<Object> jsonToObjectList(String json) {
		List<Object> list = new ArrayList<Object>();
		return gson.fromJson(json, new TypeToken<List<Object>>() {}.getType());
	}
}
