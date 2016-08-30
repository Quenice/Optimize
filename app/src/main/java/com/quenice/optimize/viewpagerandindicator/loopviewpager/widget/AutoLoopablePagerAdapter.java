package com.quenice.optimize.viewpagerandindicator.loopviewpager.widget;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 自动循环滑动的viewpager adapter
 * Created by qiubb on 2016/8/29.
 */
public abstract class AutoLoopablePagerAdapter<DATA> extends LoopablePagerAdapter<DATA> {
	private MyHandler mHandler;
	private Runnable mLoopTask;
	//默认的滑动间隔
	private final static long DEFAULT_SCROLL_INTERVAL = 3000;
	private long scrollInterval = DEFAULT_SCROLL_INTERVAL;

	public AutoLoopablePagerAdapter(List<DATA> data) {
		super(data);
		mHandler = new MyHandler(this);
		mLoopTask = new LoopTask(this);
		if (getCount() >= 3) startAutoLoop();
	}


	@Override
	public void onPageScrollStateChanged(int state) {
		super.onPageScrollStateChanged(state);
		switch (state) {
			//用户手动滑动page时触发
			case ViewPager.SCROLL_STATE_DRAGGING:
				mHandler.removeCallbacks(mLoopTask);
				break;
			//page滑动稳定之后触发
			case ViewPager.SCROLL_STATE_IDLE:
				if (getCount() <= 1) break;
				mHandler.postDelayed(mLoopTask, scrollInterval);
				break;
			case ViewPager.SCROLL_STATE_SETTLING:
				break;
		}
	}

	/**
	 * 设置滑动的时间间隔
	 *
	 * @param interval
	 */
	public void setScrollInterval(long interval) {
		this.scrollInterval = interval;
	}

	/**
	 * 滑动到下一个item
	 */
	private void smoothScrollNextItem() {
		mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
	}

	/**
	 * 开始循环
	 */
	public void startAutoLoop() {
		mHandler.removeCallbacks(mLoopTask);
		mHandler.postDelayed(mLoopTask, scrollInterval);
	}

	static class MyHandler extends Handler {
		WeakReference<AutoLoopablePagerAdapter> ref;

		public MyHandler(AutoLoopablePagerAdapter adapter) {
			ref = new WeakReference<>(adapter);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

		}
	}

	static class LoopTask implements Runnable {
		WeakReference<AutoLoopablePagerAdapter> ref;

		LoopTask(AutoLoopablePagerAdapter adapter) {
			ref = new WeakReference<>(adapter);
		}

		@Override
		public void run() {
			ref.get().smoothScrollNextItem();
		}
	}
}
