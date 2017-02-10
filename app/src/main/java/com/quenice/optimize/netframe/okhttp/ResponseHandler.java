package com.quenice.optimize.netframe.okhttp;

/**
 * Created by qiubb on 2016/9/8.
 */
public interface ResponseHandler {
	void onSuccess(String rawData);
	void onFailure();
	void onNoneNet();
	void onCanceled();
}
