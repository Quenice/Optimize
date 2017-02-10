package com.quenice.optimize.netframe.retrofit;

import android.content.Context;

import com.google.gson.JsonSyntaxException;
import com.quenice.optimize.common.Callback;
import com.quenice.optimize.common.Resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Retrofit客户端
 * Created by qiubb on 2016/10/31.
 */
public class RetrofitClient {
	private static RetrofitClient mRetrofitClient;
	private Retrofit mRetrofit;
	private HttpService mHttpService;
	private final Map<Context, List<Subscription>> callMap = new HashMap<>();

	private RetrofitClient() {
		HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
		httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.addInterceptor(new HeaderInterceptor())
				.addInterceptor(httpLoggingInterceptor)
				.build();
		mRetrofit = new Retrofit.Builder()
				.baseUrl("http://192.168.1.22:8080/androidWebDemo/")
				.client(okHttpClient)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build();
		mHttpService = mRetrofit.create(HttpService.class);
	}

	public HttpService getHttpService() {
		return mHttpService;
	}

	public static synchronized RetrofitClient getInstance() {
		return mRetrofitClient == null ? (mRetrofitClient = new RetrofitClient()) : mRetrofitClient;
	}

	public <T> void doRxJavaPost(Context context, Observable<Resp<T>> observable, final Callback<T> callback) {
		Subscription subscription = observable.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(new Subscriber<Resp<T>>() {
					@Override
					public void onNext(Resp<T> resp) {
						if (resp == null) {
							callback.onFailure(-1, "response is null");
							return;
						}

						int code = resp.getCode();
						String msg = resp.getMsg();
						T data = resp.getData();

						if (code == 1) {
							callback.onSuccess(data, msg);
						} else {
							callback.onFailure(code, msg);
						}
					}

					@Override
					public void onError(Throwable e) {
						if (e instanceof JsonSyntaxException) {
							callback.onFailure(-2, "解析异常");
						} else {
							callback.onFailure(-1, "网络异常");
						}
						callback.onComplete();
						e.printStackTrace();
					}

					@Override
					public void onCompleted() {
						callback.onComplete();
					}
				});
		addRunningCall(context, subscription);
	}

	/**
	 * 取消同一个Context下所有的请求。<br>
	 *
	 * @param context
	 */
	public void cancelCalls(final Context context) {
		if (context == null) return;
		if (callMap.size() == 0) return;
		final List<Subscription> calls = callMap.get(context);
		if (calls == null || calls.size() == 0) return;

		for (Subscription call : calls) {
			if (!call.isUnsubscribed()) {
				call.unsubscribe();
			}
		}
		callMap.remove(context);
	}

	/**
	 * 加入当前context请求队列
	 *
	 * @param context
	 * @param call
	 */
	private void addRunningCall(Context context, Subscription call) {
		if (context == null || call == null) return;
		if (callMap.get(context) == null) callMap.put(context, new ArrayList<Subscription>());
		callMap.get(context).add(call);
	}
}
