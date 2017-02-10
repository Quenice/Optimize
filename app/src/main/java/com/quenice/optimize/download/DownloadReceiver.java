package com.quenice.optimize.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * https://github.com/chiclaim/android-app-update
 * Created by qiubb on 2016/9/1.
 */
public class DownloadReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e("Download", "....");
		if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
			long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, ApkUpdateHelper.INVALID_DOWNLOAD_ID);
			if(downloadId == ApkUpdateHelper.INVALID_DOWNLOAD_ID) return;
			ApkUpdateHelper.getInstance(context).getState(downloadId);
//			ApkUpdateHelper.getInstance(context).
		}
	}
}
