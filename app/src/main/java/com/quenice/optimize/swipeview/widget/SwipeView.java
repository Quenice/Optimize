package com.quenice.optimize.swipeview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 滑动显示菜单view
 * Created by qiubb on 2016/7/25.
 */
public class SwipeView extends RelativeLayout {
	/**
	 * 内容区域的view必须设置的tag
	 */
	private final static String TAG_CONTENT = "CONTENT";
	/**
	 * 右边操作区必须设置的tag
	 */
	private final static String TAG_RIGHT_ACTION = "RIGHT_ACTION";
	/**
	 * 内容区域View
	 */
	private View mContentView;
	/**
	 * 右边操作区域View
	 */
	private View mRightActionView;

	private Animator mAnimator;
	/**
	 * 动画执行最长时间（ms）
	 */
	private final static long ANIMATOR_DURATION = 200;

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
		mRightActionView = findViewWithTag(TAG_RIGHT_ACTION);
		mContentView = findViewWithTag(TAG_CONTENT);
	}

	public View getContentView() {
		return mContentView;
	}

	public View getRightActionView() {
		return mRightActionView;
	}

	/**
	 * 横向平缓滑动
	 *
	 * @param x destination x
	 */
	public void smoothScroll(int x) {
		smoothScroll(x, null);
	}

	/**
	 * 横向平缓滑动
	 *
	 * @param x
	 * @param callback
	 */
	public void smoothScroll(int x, final ItemSwipeHelper.SwipeCallback callback) {
		if (mContentView == null) return;

		//如果有动画还未结束，那么迅速结束。注意这边不能调用cancel()而要调用end()，因为调用end()会使scrollX值直接assign到指定的x
		if (mAnimator != null) {
			mAnimator.removeAllListeners();
			mAnimator.end();
		}

		long duration = ANIMATOR_DURATION;
		if (x > 0) {
			duration = duration * x / mRightActionView.getWidth();
		}
		mAnimator = ObjectAnimator.ofInt(mContentView, "scrollX", x);
		mAnimator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				mAnimator = null;
				if (callback != null)
					callback.onFinish();
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				super.onAnimationCancel(animation);
				mAnimator = null;
				if (callback != null)
					callback.onFinish();
			}
		});
		Log.e("SwipeView", "duration = " + duration);
		mAnimator.setDuration(duration);
		mAnimator.start();

	}

	/**
	 * 手动横向滑动
	 *
	 * @param x destination x
	 */
	public void manuallyScrollX(int x) {
		if (mContentView == null) return;
		mContentView.scrollTo(x, 0);
	}

	/**
	 * 是否在点击在操作区
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isHintInActionArea(float x, float y) {
		if (mRightActionView == null)
			return false;
		Rect rect = new Rect();
		mRightActionView.getHitRect(rect);
		//源x、y是针对RecyclerView的坐标，需要转成相对于SwipeView的坐标
		int realX = (int) x - getLeft();
		int realY = (int) y - getTop();
		return rect.contains(realX, realY);
	}
}
