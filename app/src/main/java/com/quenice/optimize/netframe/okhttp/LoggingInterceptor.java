package com.quenice.optimize.netframe.okhttp;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 日志拦截器
 * Created by qiubb on 2016/9/9.
 */
public class LoggingInterceptor implements Interceptor {
	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		long t1 = System.nanoTime();
		Log.e("request-info", "url=" + request.url() + ", params=" + request.body().toString() + " ," + chain.connection());

		Response response = chain.proceed(request);

		long t2 = System.nanoTime();
		Log.e("Response-Info", "url=" + response.request().url() + ", time=" + ((t2 - t1) / 1e6d));
		return response;
	}
}
