package com.quenice.optimize.swipeview.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * 横向滑动item辅助类
 * Created by qiubb on 2016/8/1.
 */
public class ItemSwipeHelper extends RecyclerView.SimpleOnItemTouchListener {
	/**
	 * 滑动状态-未滑动
	 */
	public final static int SLIDE_STATUS_OFF = 0x01;
	/**
	 * 滑动状态-手动滑动
	 */
	public final static int SLIDE_STATUS_SCROLL_MANUALLY = 0x02;
	/**
	 * 滑动状态-自动滑动-至关闭
	 */
	public final static int SLIDE_STATUS_SCROLL_AUTO_ON = 0x03;
	/**
	 * 滑动状态-自动滑动-至打开
	 */
	public final static int SLIDE_STATUS_SCROLL_AUTO_OFF = 0x04;
	/**
	 * 滑动状态-已完成滑动
	 */
	public final static int SLIDE_STATUS_ON = 0x05;
	/**
	 * 正切，COT = 移动的x / 移动的y，根据这个值来调整滑动角度尽量接近左右横向滑动，COT值越大，滑动必须越接近横向滑动
	 */
	private final static int COT = 6;
	private int mStatus = SLIDE_STATUS_OFF;
	private int mTouchSlop;
	private float mInterceptLastX, mInterceptLastY, mTouchX, mTouchY;
	/**
	 * 最后操作的item
	 */
	private SwipeView mLatestSwipeView;
	/**
	 * 当前正在操作的item
	 */
	private SwipeView mActiveSwipeView;
	private TouchInterceptor mTouchInterceptor;

	public ItemSwipeHelper(Context context) {
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	@Override
	public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent e) {
		if (mStatus == SLIDE_STATUS_SCROLL_AUTO_OFF || mStatus == SLIDE_STATUS_SCROLL_AUTO_ON) {
			return true;
		}
		int action = MotionEventCompat.getActionMasked(e);
		float x = e.getX();
		float y = e.getY();
		mActiveSwipeView = getActiveSwipeView(recyclerView, x, y);
		if (mActiveSwipeView == null)
			return super.onInterceptTouchEvent(recyclerView, e);
		if (mTouchInterceptor != null && mTouchInterceptor.dispatch(recyclerView, mActiveSwipeView))
			return super.onInterceptTouchEvent(recyclerView, e);
		boolean isintercept = false;
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				if (mStatus == SLIDE_STATUS_ON && mLatestSwipeView != null) {
					//如果点击的是操作区域，则不能intercept，需要把事件传递给操作区域的View
					isintercept = !mLatestSwipeView.isHintInActionArea(x, y);
					mStatus = SLIDE_STATUS_SCROLL_AUTO_OFF;
					mLatestSwipeView.smoothScroll(0, new SwipeCallback() {
						@Override
						public void onFinish() {
							mStatus = SLIDE_STATUS_OFF;
							mLatestSwipeView = null;
						}
					});
					break;
				}
				break;
			case MotionEvent.ACTION_MOVE:
				int deltaX = (int) (x - mInterceptLastX);
				int deltaY = (int) (y - mInterceptLastY);
				//Y未滑动 && X滑动距离大于标准touchslop
				if (Math.abs(deltaY) == 0 && Math.abs(deltaX) > mTouchSlop) {
					mStatus = SLIDE_STATUS_SCROLL_MANUALLY;
				}
				//Y滑动 && 滑动角度在可接受范围内
				if (Math.abs(deltaY) > 0 && Math.abs(deltaX) > Math.abs(deltaY) * COT) {
					mStatus = SLIDE_STATUS_SCROLL_MANUALLY;

				}
				if (mStatus == SLIDE_STATUS_SCROLL_MANUALLY || mStatus == SLIDE_STATUS_ON) {
					isintercept = true;
				}
				break;
			case MotionEvent.ACTION_UP:
				break;
		}

		mInterceptLastX = x;
		mInterceptLastY = y;
		return isintercept;
	}

	@Override
	public void onTouchEvent(RecyclerView recyclerView, MotionEvent e) {
		if (mActiveSwipeView == null) return;
		if (mTouchInterceptor != null && mTouchInterceptor.dispatch(recyclerView, mActiveSwipeView))
			return;

		//正在 自动收缩，直接不处理touch事件
		if (mStatus == SLIDE_STATUS_SCROLL_AUTO_OFF) {
			return;
		}

		//正在 自动展开，需要马上结束自动展开，随后启动自动收缩
		if (mStatus == SLIDE_STATUS_SCROLL_AUTO_ON) {
			mStatus = SLIDE_STATUS_SCROLL_AUTO_OFF;
			mActiveSwipeView.smoothScroll(0, new SwipeCallback() {
				@Override
				public void onFinish() {
					mStatus = SLIDE_STATUS_OFF;
					mLatestSwipeView = null;
					mActiveSwipeView = null;
				}
			});
			return;
		}

		Log.e("onTouchEvent", "5=" + mStatus);
		int action = MotionEventCompat.getActionMasked(e);
		float x = e.getX();
		float y = e.getY();
		int scrollX = mActiveSwipeView.getContentView().getScrollX();
		int swipeViewRightWidth = mActiveSwipeView.getRightActionView().getWidth();
		int newScrollX;
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
				//只能手工滑动
				if (mStatus == SLIDE_STATUS_SCROLL_MANUALLY) {
					int deltaX = (int) (x - mTouchX);
					newScrollX = scrollX - deltaX;
					if (deltaX != 0) {
						if (newScrollX < 0) {
							newScrollX = 0;
						} else if (newScrollX > swipeViewRightWidth) {
							newScrollX = swipeViewRightWidth;
						}
						mActiveSwipeView.manuallyScrollX(newScrollX);
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				if (mStatus == SLIDE_STATUS_SCROLL_MANUALLY) {
					newScrollX = 0;
					if (scrollX > swipeViewRightWidth * .5) {
						newScrollX = swipeViewRightWidth;
					}
					final int _sx = newScrollX;
					mStatus = newScrollX == 0 ? SLIDE_STATUS_SCROLL_AUTO_OFF : SLIDE_STATUS_SCROLL_AUTO_ON;
					mActiveSwipeView.smoothScroll(newScrollX, new SwipeCallback() {
						@Override
						public void onFinish() {
							mStatus = _sx == 0 ? SLIDE_STATUS_OFF : SLIDE_STATUS_ON;
							if (mStatus == SLIDE_STATUS_ON) mLatestSwipeView = mActiveSwipeView;
							else mLatestSwipeView = null;
							mActiveSwipeView = null;
						}
					});
				}
				break;
		}

		mTouchX = x;
		mTouchY = y;
	}

	/**
	 * 根据x、y坐标获得对应item
	 *
	 * @param recyclerView
	 * @param x
	 * @param y
	 * @return
	 */
	public SwipeView getActiveSwipeView(RecyclerView recyclerView, float x, float y) {
		return (SwipeView) recyclerView.findChildViewUnder(x, y);
	}

	/**
	 * 设置touch事件拦截器
	 *
	 * @param touchInterceptor
	 */
	public void setTouchInterceptor(TouchInterceptor touchInterceptor) {
		this.mTouchInterceptor = touchInterceptor;
	}

	/**
	 * 获得touch事件拦截器
	 *
	 * @return
	 */
	public TouchInterceptor getTouchInterceptor() {
		return this.mTouchInterceptor;
	}

	/**
	 * touch事件拦截器
	 */
	public interface TouchInterceptor {
		/**
		 * 用在RecyclerView.OnItemTouchListener.onInterceptTouchEvent中。是否需要拦截或者放行
		 *
		 * @param recyclerView
		 * @param swipeView    当前操作的item
		 * @return true:分发事件给子view，直接调用super.onInterceptTouchEvent。false:不分发，继续其他逻辑，可能会消耗事件
		 */
		boolean dispatch(RecyclerView recyclerView, SwipeView swipeView);
	}

	/**
	 * 滑动回调
	 */
	public interface SwipeCallback {
		void onFinish();
	}
}
