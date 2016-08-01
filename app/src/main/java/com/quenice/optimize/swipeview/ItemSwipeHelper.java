package com.quenice.optimize.swipeview;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by qiubb on 2016/8/1.
 */
public class ItemSwipeHelper implements RecyclerView.OnItemTouchListener {
	public final static int SLIDE_STATUS_OFF = 0x01;
	public final static int SLIDE_STATUS_START_SCROLL = 0x02;
	public final static int SLIDE_STATUS_ON = 0x03;
	private final static int TAN = 6;
	private int mStatus;
	private RecyclerView mRecyclerView;
	private Context mContext;
	private int mTouchSlop;

	private SwipeView mCurrentSwipeView;
	/**
	 * 当前正在操作的item
	 */
	private SwipeView mActiveSwipeView;

	private float mInterceptLastX, mInterceptLastY, mTouchX, mTouchY;

	public ItemSwipeHelper(RecyclerView recyclerView, Context context) {
		this.mRecyclerView = recyclerView;
		this.mContext = context;
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	@Override
	public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent e) {
		int action = MotionEventCompat.getActionMasked(e);
		float x = e.getX();
		float y = e.getY();
		mActiveSwipeView = getActiveSwipeView(recyclerView, x, y);
		if (mActiveSwipeView == null) return false;
		boolean isintercept = false;
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				if (mStatus == SLIDE_STATUS_ON && mCurrentSwipeView != null) {
					mCurrentSwipeView.getContentView().scrollTo(0, 0);
					isintercept = true;
					mStatus = SLIDE_STATUS_OFF;
					mCurrentSwipeView = null;
					break;
				}
				break;
			case MotionEvent.ACTION_MOVE:
				int deltaX = (int) (x - mInterceptLastX);
				int deltaY = (int) (y - mInterceptLastY);
				//Y未滑动 && X滑动距离大于标准touchslop
				if (Math.abs(deltaY) == 0 && Math.abs(deltaX) > mTouchSlop) {
					mStatus = SLIDE_STATUS_START_SCROLL;
				}
				//Y滑动 && 滑动角度在可接受范围内
				if (Math.abs(deltaY) > 0 && Math.abs(deltaX) > Math.abs(deltaY) * TAN) {
					mStatus = SLIDE_STATUS_START_SCROLL;

				}
				if (mStatus == SLIDE_STATUS_START_SCROLL || mStatus == SLIDE_STATUS_ON) {
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

	public SwipeView getActiveSwipeView(RecyclerView recyclerView, float x, float y) {
		return (SwipeView) recyclerView.findChildViewUnder(x, y);
	}

	@Override
	public void onTouchEvent(RecyclerView recyclerView, MotionEvent e) {
		if (mActiveSwipeView == null) return;
		int action = MotionEventCompat.getActionMasked(e);
		float x = e.getX();
		float y = e.getY();
		int scrollX = mActiveSwipeView.getContentView().getScrollX();
		int swipeViewRightWidth = mActiveSwipeView.getRightView().getWidth();
		int newScrollX;
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
				int deltaX = (int) (x - mTouchX);
				newScrollX = scrollX - deltaX;
				if (deltaX != 0) {
					if (newScrollX < 0) {
						newScrollX = 0;
					} else if (newScrollX > swipeViewRightWidth) {
						newScrollX = swipeViewRightWidth;
					}
					mActiveSwipeView.getContentView().scrollTo(newScrollX, 0);
				}
				break;
			case MotionEvent.ACTION_UP:
				if (mStatus == SLIDE_STATUS_START_SCROLL) {
					newScrollX = 0;
					if (scrollX > swipeViewRightWidth * .5) {
						newScrollX = swipeViewRightWidth;
					}
					mActiveSwipeView.getContentView().scrollTo(newScrollX, 0);
					mStatus = newScrollX == 0 ? SLIDE_STATUS_OFF : SLIDE_STATUS_ON;
				}
				if (mStatus == SLIDE_STATUS_ON) mCurrentSwipeView = mActiveSwipeView;
				else mCurrentSwipeView = null;
				mActiveSwipeView = null;
				break;
		}

		mTouchX = x;
		mTouchY = y;
	}

	@Override
	public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

	}

	private boolean isClickInOperationArea(RecyclerView recyclerView, float x, float y) {

	}
}
