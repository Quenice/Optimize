package com.quenice.optimize.netframe.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.quenice.optimize.R;
import com.quenice.optimize.common.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiubb on 2016/9/7.
 */
public class RetrofitActivity extends AppCompatActivity {
	private TextView tv;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_netframe_retrofit);
		tv = (TextView) findViewById(R.id.tv);
//		RetrofitClientWithoutRxJava.getInstance().post();
		Model m = new Model();
		m.setName("n1");
		m.setValue("v1");
		List<Model.Child> list = new ArrayList<>();
		Model.Child child = new Model.Child();
		child.setName("i am child");
		list.add(child);
		child.setName("I am father");
		list.add(child);
		m.setChildren(list);
		RetrofitHttpHelper.getInstance().rxJavaPost2(this, "abc", new Callback<List<Model>>() {
			@Override
			public void onSuccess(List<Model> data, String msg) {
				Log.e("onSuccess", new Gson().toJson(data));
			}

			@Override
			public void onFailure(int code, String msg) {
				Log.e("onFailure", "code=" + code + ", msg=" + msg);
			}

			@Override
			public void onComplete() {
				super.onComplete();
				Log.e("onComplete", "complete!");
			}
		});
//		Log.e("RetrofitAction", "10_000=" + 10_000);
//		Observable<List<Object>> observable = RetrofitClient.getInstance().getHttpService().testUrl("http://www.baidu.com", "http://www.insurobot.com");
//		observable.observeOn(AndroidSchedulers.mainThread())
//				.subscribeOn(Schedulers.io())
//				.map(new Func1<List<Object>, Observable<Object>>() {
//					@Override
//					public Observable<Object> call(List<Object> objects) {
//						return Observable.from(objects);
//					}
//				});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		RetrofitClient.getInstance().cancelCalls(this);
	}
}
