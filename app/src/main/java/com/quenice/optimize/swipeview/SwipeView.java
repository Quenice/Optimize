package com.quenice.optimize.swipeview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by qiubb on 2016/7/25.
 */
public class SwipeView extends RelativeLayout {
	private View mContentView;
	private View mRightView;
	private int mRightWidth;

	public SwipeView(Context context) {
		super(context);
	}

	public SwipeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SwipeView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public SwipeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (mRightView != null) {
			mRightWidth = mRightView.getMeasuredWidth();
			int height = mRightView.getHeight();
			mRightView.layout(r - mRightWidth, 0, r, height);
		}
		if (mContentView != null) {
			int height = mContentView.getMeasuredHeight();
			mContentView.layout(l, 0, r, height);
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mRightView = getChildAt(0);
		mContentView = getChildAt(1);
	}

	public View getContentView() {
		return mContentView;
	}

	public View getRightView() {
		return mRightView;
	}
}
