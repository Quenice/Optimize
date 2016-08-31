package com.quenice.optimize.download;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
		DownloadManager dManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
		DownloadManager.Request req = new DownloadManager.Request(Uri.parse("http://www.insurobot.com/download/insurobot.apk"));

		req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
		//req.setAllowedOverRoaming(false);

		req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

		//设置文件的保存的位置[三种方式]
		//第一种
		//file:///storage/emulated/0/Android/data/your-package/files/Download/update.apk
		req.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "update.apk");
		//第二种
		//file:///storage/emulated/0/Download/update.apk
		//req.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "update.apk");
		//第三种 自定义文件路径
		//req.setDestinationUri()


		// 设置一些基本显示信息
		req.setTitle("下载测试");
		req.setDescription("正在下载");
		req.setMimeType("application/vnd.android.package-archive");

		//加入下载队列
		dManager.enqueue(req);
	}
}
