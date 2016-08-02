package com.quenice.optimize.swipeview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * 滑动显示菜单view
 * Created by qiubb on 2016/7/25.
 */
public class SwipeView extends RelativeLayout {
	/**
	 * 内容区域View
	 */
	private View mContentView;
	/**
	 * 右边操作区域View
	 */
	private View mRightActionView;

	private Scroller mScroller;

	public SwipeView(Context context) {
		super(context);
		init(context);
	}

	public SwipeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public SwipeView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public SwipeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context);
	}

	private void init(Context context) {
		mScroller = new Scroller(context);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (mRightActionView != null) {
			int height = mRightActionView.getMeasuredHeight();
			mRightActionView.layout(r - mRightActionView.getMeasuredWidth(), 0, r, height);
		}
		if (mContentView != null) {
			mContentView.layout(l, 0, r, mContentView.getMeasuredHeight());
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mRightActionView = getChildAt(0);
		mContentView = getChildAt(1);
	}

	public View getContentView() {
		return mContentView;
	}

	public View getRightActionView() {
		return mRightActionView;
	}
}
