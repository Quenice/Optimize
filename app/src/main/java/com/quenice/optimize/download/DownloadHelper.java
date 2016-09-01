package com.quenice.optimize.download;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

/**
 * 下载辅助类
 * Created by qiubb on 2016/9/1.
 */
public class DownloadHelper {
	public final static long INVALID_DOWNLOAD_ID = -1;
	public final static int INVALID_STATE = -1;
	private DownloadManager mDownloadManager;
	private static DownloadHelper instance;
	private Context mContext;

	private DownloadHelper(Context context) {
		this.mContext = context;
		mDownloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
	}

	public synchronized DownloadManager getDownloadManager(Context context) {
		return mDownloadManager != null ? mDownloadManager : (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
	}

	public synchronized static DownloadHelper getInstance(Context context) {
		return instance != null ? instance : new DownloadHelper(context);
	}

	/**
	 * 下载服务是否开启
	 */
	public void isDownloadServiceEnable() {

	}

	/**
	 * 开始下载，这里暂时只考虑下载apk
	 */
	public long download(String url, String version) {
		if (url == null || url.trim().equals("")) return INVALID_DOWNLOAD_ID;
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
		request.allowScanningByMediaScanner();
//		request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
		request.setMimeType("application/vnd.android.package-archive");
		request.setDestinationInExternalFilesDir(mContext, Environment.DIRECTORY_DOWNLOADS, "insurobot.autoupdate." + version + ".apk");
		request.setDescription("正在下载新版应用");
		request.setTitle("险萝卜");
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		request.setVisibleInDownloadsUi(true);
		return getDownloadManager(mContext).enqueue(request);
	}

	/**
	 * 显示开启下载服务页面
	 */
	public void showDownloadSetting() {

	}

	/**
	 * 当前下载状态
	 *
	 * @param id
	 */
	public int getState(long id) {
		Cursor cursor = null;
		try {
			DownloadManager.Query query = new DownloadManager.Query();
			query.setFilterById(id);
			cursor = getDownloadManager(mContext).query(query);
			if (cursor != null && cursor.getCount() > 0) {
				if (cursor.moveToFirst()) {
//					cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
					int t = cursor.getType(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
					Log.e("Download", "type = " + t);
//					cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) cursor.close();
		}
		return INVALID_STATE;
	}
}
