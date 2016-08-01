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
		Log.e("LinearLayout", "onInterceptTouchEvent");
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.e("LinearLayout", "onTouchEvent");
		return super.onTouchEvent(event);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.e("LinearLayout", "dispatchTouchEvent=");
		return  super.dispatchTouchEvent(ev);
	}
}
