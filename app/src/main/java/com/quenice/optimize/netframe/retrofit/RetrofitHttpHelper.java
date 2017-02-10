package com.quenice.optimize.netframe.retrofit;

import android.content.Context;

import com.quenice.optimize.common.Callback;
import com.quenice.optimize.common.Resp;

import java.util.List;

import rx.Observable;

/**
 * Created by qiubb on 2016/11/2.
 */

public class RetrofitHttpHelper {
	private static RetrofitHttpHelper mHelper;

	private RetrofitHttpHelper() {
	}

	public static synchronized RetrofitHttpHelper getInstance() {
		return mHelper == null ? (mHelper = new RetrofitHttpHelper()) : mHelper;
	}

	public void rxJavaPost(Context context, Model model, Callback<List<Model>> callback) {
		Observable<Resp<List<Model>>> observable = RetrofitClient.getInstance().getHttpService().postRxJava(model);
		RetrofitClient.getInstance().doRxJavaPost(context, observable, callback);
	}

	public void rxJavaPost2(Context context, String model, Callback<List<Model>> callback) {
		Observable<Resp<List<Model>>> observable = RetrofitClient.getInstance().getHttpService().postRxJava2(model);
		RetrofitClient.getInstance().doRxJavaPost(context, observable, callback);
	}
}
