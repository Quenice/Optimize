package com.quenice.optimize.common;

/**
 * 回调接口
 * Created by qiubb on 2015/10/22.
 */
public abstract class Callback<T> {
	public abstract void onSuccess(T data, String msg);

	public abstract void onFailure(int code, String msg);

	public void onComplete() {
	}
}
