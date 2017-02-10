package com.quenice.optimize.netframe.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * OkHttp响应与封装的ResponseHandler之间嫁接
 * Created by qiubb on 2016/9/8.
 */
public class ResponseBridge {
	/**
	 * 请求成功
	 */
	public final static int MSG_SUCCESS = 0x01;
	/**
	 * 请求失败
	 */
	public final static int MSG_FAILURE = 0x02;
	/**
	 * 没有网络
	 */
	public final static int MSG_NONENET = 0x03;
	/**
	 * 请求被取消
	 */
	public final static int MSG_CANCELED = 0x04;
	private MainLooperHanlder mHandler;
	private ResponseHandler mResponseHandler;

	public ResponseBridge(ResponseHandler responseHandler) {
		mHandler = new MainLooperHanlder(this);
		this.mResponseHandler = responseHandler;
	}

	/**
	 * 请求成功
	 *
	 * @param rawData
	 */
	public void onSuccess(String rawData) {
		mHandler.obtainMessage(MSG_SUCCESS, rawData).sendToTarget();
	}

	/**
	 * 请求失败
	 */
	public void onFailure() {
		mHandler.obtainMessage(MSG_FAILURE).sendToTarget();
	}

	/**
	 * 没有网络
	 */
	public void onNoneNet() {
		mHandler.obtainMessage(MSG_NONENET).sendToTarget();
	}

	/**
	 * 请求被取消
	 */
	public void onCanceled() {
		mHandler.obtainMessage(MSG_CANCELED).sendToTarget();
	}

	private static class MainLooperHanlder extends Handler {
		private WeakReference<ResponseBridge> ref;

		public MainLooperHanlder(ResponseBridge bridge) {
			super(Looper.getMainLooper());
			ref = new WeakReference<>(bridge);
		}

		@Override
		public void handleMessage(Message msg) {
			if (ref.get().mResponseHandler != null) return;
			switch (msg.what) {
				case MSG_SUCCESS:
					ref.get().mResponseHandler.onSuccess(msg.obj == null ? null : msg.obj.toString());
					break;
				case MSG_FAILURE:
					ref.get().mResponseHandler.onFailure();
					break;
				case MSG_NONENET:
					ref.get().mResponseHandler.onNoneNet();
					break;
				case MSG_CANCELED:
					ref.get().mResponseHandler.onCanceled();
					break;
			}
		}
	}
}
