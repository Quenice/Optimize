package com.quenice.optimize.download;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import java.util.List;

/**
 * 下载辅助类
 * Created by qiubb on 2016/9/1.
 */
public class ApkUpdateHelper {
	public final static long INVALID_DOWNLOAD_ID = -1;
	public final static int INVALID_STATE = -1;
	private DownloadManager mDownloadManager;
	private static ApkUpdateHelper instance;
	private Context mContext;

	private ApkUpdateHelper(Context context) {
		this.mContext = context;
		mDownloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
	}

	public synchronized DownloadManager getDownloadManager(Context context) {
		return mDownloadManager != null ? mDownloadManager : (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
	}

	public synchronized static ApkUpdateHelper getInstance(Context context) {
		return instance != null ? instance : new ApkUpdateHelper(context);
	}

	/**
	 * 下载服务是否可用
	 */
	public boolean isDownloadServiceEnable() {
		int state = mContext.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");
		return !(state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
				|| state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
				|| state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED);
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
		Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		intent.setData(Uri.parse("package:com.android.providers.downloads"));
		if (intentAvailable(intent)) {
			mContext.startActivity(intent);
		} else {
			//提示手动设置
		}
	}

	/**
	 * 安装app
	 *
	 * @param uri apk的本地地址
	 */
	public void installApk(Uri uri) {
		Intent install = new Intent(Intent.ACTION_VIEW);
		install.setDataAndType(uri, "application/vnd.android.package-archive");
		install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(install);
	}

	/**
	 * 获得apk信息
	 *
	 * @param apkPath
	 * @return
	 */
	private PackageInfo getApkInfo(String apkPath) {
		return mContext.getPackageManager().getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
	}


	private boolean compare(PackageInfo apkInfo) {
		if (apkInfo == null) {
			return false;
		}
		String localPackage = mContext.getPackageName();
		if (apkInfo.packageName.equals(localPackage)) {
			try {
				PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(localPackage, 0);
				if (apkInfo.versionCode > packageInfo.versionCode) {
					return true;
				}
			} catch (PackageManager.NameNotFoundException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 该intent是否有效
	 *
	 * @param intent
	 * @return
	 */
	private boolean intentAvailable(Intent intent) {
		List list = mContext.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	/**
	 * 当前下载状态
	 *
	 * @param id
	 */
	public ApkModel getState(long id) {
		ApkModel apkModel = null;
		Cursor cursor = null;
		try {
			DownloadManager.Query query = new DownloadManager.Query();
			query.setFilterById(id);
			cursor = getDownloadManager(mContext).query(query);
			if (cursor != null && cursor.getCount() > 0) {
				if (cursor.moveToFirst()) {
					apkModel = new ApkModel();
					apkModel.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_ID)));
					apkModel.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS)));
					apkModel.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TITLE)));
					apkModel.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_DESCRIPTION)));
					apkModel.setLocalUri(cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI)));
					apkModel.setServerUri(cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_URI)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) cursor.close();
		}
		return apkModel;
	}
}
