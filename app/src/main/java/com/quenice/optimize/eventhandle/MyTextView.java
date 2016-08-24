package com.quenice.optimize.eventhandle;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by qiubb on 2016/7/29.
 */
public class MyTextView extends TextView {
	public MyTextView(Context context) {
		super(context);
	}

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public MyTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
//		Log.e("TextView", "onTouchEvent");
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Log.e("TextView", "onTouchEvent.down");
				break;
			case MotionEvent.ACTION_MOVE:
				Log.e("TextView", "onTouchEvent.move");
				break;
			case MotionEvent.ACTION_UP:
				Log.e("TextView", "onTouchEvent.up");
				break;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
//		Log.e("TextView", "dispatchTouchEvent");
		return super.dispatchTouchEvent(ev);
	}
}
