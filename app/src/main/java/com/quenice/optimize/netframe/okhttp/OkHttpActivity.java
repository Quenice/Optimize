package com.quenice.optimize.netframe.okhttp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.quenice.optimize.R;

/**
 * Created by qiubb on 2016/9/7.
 */
public class OkHttpActivity extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_netframe_okhttp);

//		OkHttpClientWrapper.getInstance().postJson(this, "http://httpbin.org/delay/2", );
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		OkHttpClientWrapper.getInstance().cancelCalls(this);
	}
}
