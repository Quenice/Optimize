package com.quenice.optimize.netframe.okhttp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;

import com.quenice.optimize.utils.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by qiubb on 2016/9/7.
 */
public class OkHttpClientWrapper {
	private final static MediaType MEDIATYPE_JSON;
	private static OkHttpClientWrapper instance;
	private ExecutorService mThreadPool;
	private OkHttpClient mOkHttpClient;
	private final Map<Context, List<Call>> callMap = new HashMap<>();

	static {
		MEDIATYPE_JSON = MediaType.parse("application/json; charset=utf-8");
	}

	public OkHttpClientWrapper() {
		mThreadPool = Executors.newCachedThreadPool();
		mOkHttpClient = new OkHttpClient.Builder()
				.addInterceptor(new LoggingInterceptor())
				.connectTimeout(10, TimeUnit.SECONDS)
				.writeTimeout(20, TimeUnit.SECONDS)
				.readTimeout(20, TimeUnit.SECONDS)
				.build();
	}

	public synchronized static OkHttpClientWrapper getInstance() {
		return instance == null ? instance = new OkHttpClientWrapper() : instance;
	}

	public <P> void postJson(Context context, String url, P params, final ResponseHandler responseHandler) {
		final ResponseBridge responseBridge = new ResponseBridge(responseHandler);
		//判断网络
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
		if (!networkInfo.isAvailable()) {
			responseBridge.onNoneNet();
			return;
		}
		//组装参数
		RequestBody requestBody = params == null ? null : RequestBody.create(MEDIATYPE_JSON, JsonUtils.gson().toJson(params));
		Request.Builder builder = new Request.Builder();
		builder.url(url);
		if (requestBody != null) {
			builder.post(requestBody);
		} else {
			builder.get();
		}
		Request request = builder.build();
		//发起请求
		Call call = mOkHttpClient.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				if (call.isCanceled()) {
					responseBridge.onCanceled();
				} else {
					responseBridge.onFailure();
				}
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if (call.isCanceled()) {
					responseBridge.onCanceled();
				} else if (response == null || !response.isSuccessful()) {
					responseBridge.onFailure();
				} else {
					String rawData;
					if (response.body() == null) {
						rawData = null;
					} else {
						rawData = response.body().string();
					}
					responseBridge.onSuccess(rawData);
				}
			}
		});

		addRunningCall(context, call);
	}

	/**
	 * 取消同一个Context下所有的请求。<br>
	 *
	 * @param context
	 */
	public void cancelCalls(final Context context) {
		if (context == null) return;
		if (callMap.size() == 0) return;
		final List<Call> calls = callMap.get(context);
		if (calls == null || calls.size() == 0) return;
		if (Looper.myLooper() == Looper.getMainLooper()) {
			mThreadPool.submit(new Runnable() {
				@Override
				public void run() {
					cancelCalls(context, calls);
				}
			});
		} else {
			cancelCalls(context, calls);
		}
	}

	/**
	 * 取消同一个Context下所有的请求
	 *
	 * @param context
	 * @param calls
	 */
	private void cancelCalls(Context context, List<Call> calls) {
		for (Call call : calls) {
			if (!call.isCanceled())
				call.cancel();
		}
		callMap.remove(context);
	}

	/**
	 * 加入当前context请求队列
	 *
	 * @param context
	 * @param call
	 */
	private void addRunningCall(Context context, Call call) {
		if (context == null || call == null) return;
		if (callMap.get(context) == null) callMap.put(context, new ArrayList<Call>());
		callMap.get(context).add(call);
	}
}
