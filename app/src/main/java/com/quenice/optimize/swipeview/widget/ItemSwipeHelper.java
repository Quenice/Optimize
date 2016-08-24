package com.quenice.optimize.swipeview.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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
	 * touch事件down的x坐标
	 */
	private float mDownX;
	//是否需要响应action区域事件
	private boolean needResponseAction;
	/**
	 * 最后操作的item
	 */
	private SwipeView mLatestSwipeView;
	/**
	 * 当前正在操作的item
	 */
	private SwipeView mActiveSwipeView;
	/**
	 * 有一些item可能不需要响应左右滑动事件，所以通过注册滑动拦截器来过滤
	 */
	private TouchInterceptor mTouchInterceptor;

	public ItemSwipeHelper(Context context) {
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	@Override
	public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent e) {
		//当touch区域为action区域的时候，需要响应action区域事件
		if (mStatus == SLIDE_STATUS_SCROLL_AUTO_OFF && needResponseAction) {
			needResponseAction = false;
			return super.onInterceptTouchEvent(recyclerView, e);
		}
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
		boolean isIntercept = false;
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				mDownX = x;
				if (mStatus == SLIDE_STATUS_ON && mLatestSwipeView != null) {
					//如果点击的是操作区域，则不能intercept，需要把事件传递给操作区域的View
					isIntercept = !mLatestSwipeView.isHintInActionArea(x, y);
					if (!isIntercept) needResponseAction = true;
					Log.e("ItemSwipeHelper", "needResponseAction=" + needResponseAction);
					mStatus = SLIDE_STATUS_SCROLL_AUTO_OFF;
					mLatestSwipeView.smoothScroll(0, new SwipeCallback() {
						@Override
						public void onFinish() {
							mStatus = SLIDE_STATUS_OFF;
							needResponseAction = false;
							mLatestSwipeView = null;
						}
					});
				}
				break;
			case MotionEvent.ACTION_MOVE:
				//如果是反向滑动，不作处理，直接传递事件
				if (mDownX != -1 && (x - mDownX) > 0) {
					return super.onInterceptTouchEvent(recyclerView, e);
				}
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
					//防止在item滑动的时候被外围scroll类的view阻断（比如与SwipeRefreshLayout下拉冲突）
					recyclerView.requestDisallowInterceptTouchEvent(true);
					isIntercept = true;
				}
				break;
			case MotionEvent.ACTION_UP:
				mDownX = -1;
				break;
		}

		mInterceptLastX = x;
		mInterceptLastY = y;
		return isIntercept;
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
		View view = recyclerView.findChildViewUnder(x, y);
		if (!(view instanceof SwipeView)) return null;
		return (SwipeView) view;
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
