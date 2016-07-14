package com.quenice.optimize;


import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.Executors;

/**
 * 静态数据库的初始化。支持数据库的版本更新
 * Created by qiubb on 2015/11/16.
 */
public class StaticDBInitializer {

	/**
	 * 初始化DB
	 *
	 * @param context
	 */
	public static void initialize(final Context context) {
		Executors.newCachedThreadPool().execute(new Runnable() {
			@Override
			public void run() {
				initDB(context);
			}
		});
	}

	/**
	 * 初始化DB file
	 *
	 * @param context
	 * @return
	 */
	private static void initDB(final Context context) {
		String dbPath = getDBAbsoluthPath(context);
		File dbFile = new File(dbPath);
		if (dbFile.isFile() && dbFile.exists()) {
			dbFile.delete();
			createDBFile(context);
		} else {
			createDBFile(context);
		}
	}


	/**
	 * 更新db文件
	 *
	 * @param context
	 */
	private static void createDBFile(Context context) {
		String dbName = "demo.db";
		String dbPath = getDBAbsoluthPath(context);
		File dbFile = new File(dbPath);
		try {
			InputStream is = context.getAssets().open(dbName);
			FileOutputStream fos = new FileOutputStream(dbFile);
			int len;
			byte[] buffer = new byte[1024];
			while ((len = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				fos.flush();
			}
			fos.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得db文件所在绝对路径
	 *
	 * @param context
	 * @return
	 */
	public static String getDBAbsoluthPath(Context context) {
		return "/data" + Environment.getDataDirectory().getAbsolutePath() + File.separator + context.getPackageName() + File.separator + "demo.db";
	}

}
