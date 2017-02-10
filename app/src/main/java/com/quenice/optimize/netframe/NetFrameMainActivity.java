package com.quenice.optimize.netframe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.quenice.optimize.R;
import com.quenice.optimize.netframe.okhttp.OkHttpActivity;
import com.quenice.optimize.netframe.retrofit.RetrofitActivity;

/**
 * Created by qiubb on 2016/9/7.
 */
public class NetFrameMainActivity extends AppCompatActivity implements View.OnClickListener {
	private View tv_okhttp;
	private View tv_retrofit;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_netframe_main);
		tv_okhttp = findViewById(R.id.tv_okhttp);
		tv_retrofit = findViewById(R.id.tv_retrofit);
		tv_okhttp.setOnClickListener(this);
		tv_retrofit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_okhttp:
				startActivity(new Intent(this, OkHttpActivity.class));
				break;
			case R.id.tv_retrofit:
				startActivity(new Intent(this, RetrofitActivity.class));
				break;
		}
	}
}
