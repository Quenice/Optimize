package com.quenice.optimize.userdefined.indexerlistview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 带header索引+顶部悬浮的listview
 * Created by qiubb on 2016/5/26.
 */
public class IndexerListView extends ListView {
	private View mPinnedHeaderView;
	private int pinnedHeaderViewWidth;
	private int pinnedHeaderViewHeight;
	private boolean mPinnedHeaderViewVisible;
	private IndexerListViewAdapter mAdapter;

	public IndexerListView(Context context) {
		super(context);
	}

	public IndexerListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public IndexerListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public IndexerListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
		if (!(adapter instanceof IndexerListViewAdapter)) {
			throw new IllegalArgumentException("adapter need be instance of IndexerListViewAdapter");
		}
		mAdapter = (IndexerListViewAdapter) adapter;
		setOnScrollListener(mAdapter);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (mPinnedHeaderView == null) return;
		mPinnedHeaderView.layout(0, 0, pinnedHeaderViewWidth, pinnedHeaderViewHeight);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (mPinnedHeaderView == null) return;
		measureChild(mPinnedHeaderView, widthMeasureSpec, heightMeasureSpec);
		pinnedHeaderViewWidth = mPinnedHeaderView.getMeasuredWidth();
		pinnedHeaderViewHeight = mPinnedHeaderView.getMeasuredHeight();
	}

	public void setPinnedHeaderView(Context context, int layoutResId) {
		mPinnedHeaderView = LayoutInflater.from(context).inflate(layoutResId, this, false);
		requestLayout();
	}

	/**
	 * 根据firstVisibleItem来刷新pinned view
	 *
	 * @param firstVisibleItem
	 */
	public void refreshPinnedHeaderView(int firstVisibleItem) {
		if (mPinnedHeaderView == null || mAdapter == null) return;
		int state = mAdapter.getPinnedHeaderViewState(firstVisibleItem);
		switch (state) {
			case IndexerListViewAdapter.PINNED_HEADER_GONE:
				mPinnedHeaderViewVisible = false;
				break;
			case IndexerListViewAdapter.PINNED_HEADER_VISIBLE:
				if (mPinnedHeaderView.getTop() != 0) {
					mPinnedHeaderView.layout(0, 0, pinnedHeaderViewWidth, pinnedHeaderViewHeight);
				}
				mAdapter.refreshPinnedHeaderView(mPinnedHeaderView, firstVisibleItem);
				mPinnedHeaderViewVisible = true;
				break;
			case IndexerListViewAdapter.PINNED_HEADER_PUSHED_UP:
				View firstView = getChildAt(0);
				int bottom = firstView.getBottom();
				int y;
				if (bottom < pinnedHeaderViewHeight) {
					y = (bottom - pinnedHeaderViewHeight);
				} else {
					y = 0;
				}
				if (mPinnedHeaderView.getTop() != y) {
					mPinnedHeaderView.layout(0, y, pinnedHeaderViewWidth, pinnedHeaderViewHeight + y);
				}
				mAdapter.refreshPinnedHeaderView(mPinnedHeaderView, firstVisibleItem);
				mPinnedHeaderViewVisible = true;
				break;
		}
	}

	/**
	 * 停止滑动
	 */
	public void stopScrolling() {
		dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0, 0, 0));
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (mPinnedHeaderView != null && mPinnedHeaderViewVisible) {
			drawChild(canvas, mPinnedHeaderView, getDrawingTime());
		}
	}
}
