package com.quenice.optimize.download;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.quenice.optimize.R;

/**
 * 文件下载
 * Created by qiubb on 2016/8/31.
 */
public class DownLoadActivity extends AppCompatActivity implements View.OnClickListener {
	private View tv_download;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);

		tv_download = findViewById(R.id.tv_download);
		tv_download.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		download();
	}

	private void download() {
//		long id = ApkUpdateHelper.getInstance(this).download("http://www.insurobot.com/download/insurobot.apk", "1.1.1");
//		Log.e("Activity", "id = " + id);
		ApkUpdateHelper.getInstance(this).getState(16);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
