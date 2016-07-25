package com.quenice.optimize.swipeview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by qiubb on 2016/7/25.
 */
public class SwipeView extends LinearLayout {
	public SwipeView(Context context) {
		super(context);
	}

	public SwipeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SwipeView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.e("SwipeView", "..." + getScrollX());
		return super.onTouchEvent(event);
	}
}
