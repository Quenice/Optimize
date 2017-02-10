package com.quenice.optimize.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * json工具类
 * @author qiubb
 *
 */
public final class JsonUtils {
	private JsonUtils() {
	}

	private static Gson gson;

	public final static synchronized Gson gson() {
		if (gson == null) {
			gson = new GsonBuilder().create();
		}
		return gson;
	}
}
