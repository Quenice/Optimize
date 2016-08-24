package com.quenice.optimize.eventhandle;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by qiubb on 2016/7/29.
 */
public class MyRelativeLayout extends RelativeLayout {
	public MyRelativeLayout(Context context) {
		super(context);
	}

	public MyRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public MyRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}


	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
//		Log.e("RelativeLayout", "onInterceptTouchEvent");
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Log.e("RelativeLayout", "onInterceptTouchEvent.down");
//				return true;
				break;
			case MotionEvent.ACTION_MOVE:
				Log.e("RelativeLayout", "onInterceptTouchEvent.move");
				break;
			case MotionEvent.ACTION_UP:
				Log.e("RelativeLayout", "onInterceptTouchEvent.up");
				break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
//		Log.e("RelativeLayout", "onTouchEvent");
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Log.e("RelativeLayout", "onTouchEvent.down");
				break;
			case MotionEvent.ACTION_MOVE:
				Log.e("RelativeLayout", "onTouchEvent.move");
				break;
			case MotionEvent.ACTION_UP:
				Log.e("RelativeLayout", "onTouchEvent.up");
				break;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
//		Log.e("RelativeLayout", "dispatchTouchEvent");
		return super.dispatchTouchEvent(ev);
	}
}
