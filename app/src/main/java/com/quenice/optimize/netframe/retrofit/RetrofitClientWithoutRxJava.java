package com.quenice.optimize.netframe.retrofit;

import android.util.Log;

import com.quenice.optimize.common.Req;
import com.quenice.optimize.common.Resp;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by qiubb on 2016/11/1.
 */

public class RetrofitClientWithoutRxJava {
	private static RetrofitClientWithoutRxJava mRetrofitClient;
	private Retrofit mRetrofit;
	private HttpService mHttpService;

	private RetrofitClientWithoutRxJava() {
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.addInterceptor(new HeaderInterceptor())
				.build();
		mRetrofit = new Retrofit.Builder()
				.baseUrl("http://192.168.1.22:8080/androidWebDemo/")
				.client(okHttpClient)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		mHttpService = mRetrofit.create(HttpService.class);
	}

	public static synchronized RetrofitClientWithoutRxJava getInstance() {
		return mRetrofitClient == null ? (mRetrofitClient = new RetrofitClientWithoutRxJava()) : mRetrofitClient;
	}


	public void post() {
		Model m = new Model();
		m.setName("n1");
		m.setValue("v1");
		Call<Resp<List<Model>>> a = mHttpService.post(new Req<>(m));
		doRequest(a);
	}

	<DATA> void doRequest(Call<Resp<DATA>> call) {
		call.enqueue(new RetrofitClientWithoutRxJava.MyCallback<Resp<DATA>>());
	}

	private static class MyCallback<T extends Resp> implements Callback<T> {

		@Override
		public void onResponse(Call<T> call, Response<T> response) {
			Log.e("aaa", "onResponse");
		}

		@Override
		public void onFailure(Call<T> call, Throwable t) {
			Log.e("aaa", "error");
		}
	}
}
