package com.quenice.optimize.horizontalview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by qiubb on 2016/7/28.
 */
public class MyHorizontalView extends HorizontalScrollView {
	public MyHorizontalView(Context context) {
		super(context);
	}

	public MyHorizontalView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyHorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public MyHorizontalView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		Log.e("HorizontalView", "scrollX = " + getScrollX());
		return super.onTouchEvent(ev);
	}
}
