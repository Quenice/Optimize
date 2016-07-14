package com.quenice.optimize;

/**
 * Created by qiubb on 2016/5/25.
 */
public class Application extends android.app.Application {
	private static Application instance;
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		StaticDBInitializer.initialize(this);
	}

	public static synchronized Application getInstance() {
		return instance;
	}
}
