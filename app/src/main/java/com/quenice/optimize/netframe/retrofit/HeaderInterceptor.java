package com.quenice.optimize.netframe.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by qiubb on 2016/10/31.
 */

public class HeaderInterceptor implements Interceptor {
	@Override
	public Response intercept(Chain chain) throws IOException {
//		Request request = chain.request().newBuilder()
//				.header("Content-Type", "application/json;charset=utf-8")
//				.build();
		Request request = chain.request();
//		RequestBody requestBody = request.body();
//		MediaType contentType = requestBody.contentType();
//		Charset charset = contentType.charset(Charset.forName("UTF-8"));
//		Buffer buffer = new Buffer();
//		requestBody.writeTo(buffer);
//		String str = buffer.readString(charset);
//		Log.e("HeaderInterceptor", str);
//		Log.e("HeaderInterceptor", "Need-Token:" + request.header("Need-Token"));
//		Object o = new Gson().fromJson(str, Object.class);
//		Req<Object> req = new Req<>(o);
//		TokenInfo tokenInfo = new TokenInfo();
//		tokenInfo.setType(1);
//		tokenInfo.setUserAccount("12341234");
//		req.setTokenInfo(tokenInfo);
//		String p = new Gson().toJson(req);
//		request = request.newBuilder().post(RequestBody.create(MediaType.parse("application/json;charset=utf-8"), p)).build();
		return chain.proceed(request);
	}
}
