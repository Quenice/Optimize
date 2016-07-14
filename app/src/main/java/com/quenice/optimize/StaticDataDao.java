package com.quenice.optimize;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * 字典数据Dao
 *
 * @author qiubb
 */
public class StaticDataDao {
	private static StaticDataDao dao;
	private Context context;

	private StaticDataDao(Context context) {
		this.context = context;
	}

	public static synchronized StaticDataDao getInstance(Context context) {
		if (dao == null) {
			dao = new StaticDataDao(context);
		}
		return dao;
	}

	public synchronized ArrayList<City> getAllCity() {
		SQLiteDatabase db = getStaticDB(context);
		ArrayList<City> list = new ArrayList<City>();
		String sql = "select city, firstpy from city order by firstpy";
		Cursor c = db.rawQuery(sql, null);
		while (c.moveToNext()) {
			String city = c.getString(c.getColumnIndex("city"));
			String firstPY = c.getString(c.getColumnIndex("firstpy"));
			City item = new City();
			item.setCity(city);
			item.setFirstPY(firstPY);
			list.add(item);
		}
		c.close();
		close(db);
		return list;
	}

	/**
	 * 打开一个db连接
	 *
	 * @param context
	 * @return
	 */
	public final SQLiteDatabase getStaticDB(Context context) {
		return context.openOrCreateDatabase(StaticDBInitializer.getDBAbsoluthPath(context), Context.MODE_PRIVATE, null);
	}

	/**
	 * 关闭一个db连接
	 *
	 * @param db
	 */
	public final void close(SQLiteDatabase db) {
		if (db != null && db.isOpen()) db.close();
	}
}
