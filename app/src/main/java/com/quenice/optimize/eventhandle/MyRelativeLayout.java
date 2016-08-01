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
		Log.e("RelativeLayout", "onInterceptTouchEvent");
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.e("RelativeLayout", "onTouchEvent");
		return super.onTouchEvent(event);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.e("RelativeLayout", "dispatchTouchEvent");
		return super.dispatchTouchEvent(ev);
	}
}
