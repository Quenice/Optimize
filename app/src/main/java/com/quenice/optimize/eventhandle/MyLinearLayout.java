package com.quenice.optimize.eventhandle;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by qiubb on 2016/7/29.
 */
public class MyLinearLayout extends LinearLayout {
	public MyLinearLayout(Context context) {
		super(context);
	}

	public MyLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
//		Log.e("LinearLayout", "onInterceptTouchEvent");
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Log.e("LinearLayout", "onInterceptTouchEvent.down");
				break;
			case MotionEvent.ACTION_MOVE:
				Log.e("LinearLayout", "onInterceptTouchEvent.move");
				break;
			case MotionEvent.ACTION_UP:
				Log.e("LinearLayout", "onInterceptTouchEvent.up");
				break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
//		Log.e("LinearLayout", "onTouchEvent");
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Log.e("LinearLayout", "onTouchEvent.down");
				break;
			case MotionEvent.ACTION_MOVE:
				Log.e("LinearLayout", "onTouchEvent.move");
				break;
			case MotionEvent.ACTION_UP:
				Log.e("LinearLayout", "onTouchEvent.up");
				break;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
//		Log.e("LinearLayout", "dispatchTouchEvent=");
		return super.dispatchTouchEvent(ev);
	}
}
