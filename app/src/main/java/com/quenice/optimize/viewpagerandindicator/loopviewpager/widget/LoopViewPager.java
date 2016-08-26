package com.quenice.optimize.viewpagerandindicator.loopviewpager.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * 循环ViewPager
 * Created by qiubb on 2016/8/19.
 */
public class LoopViewPager extends ViewPager {
	public LoopViewPager(Context context) {
		super(context);
	}

	public LoopViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setAdapter(LoopPagerAdapter adapter) {
		adapter.setViewPager(this);
		addOnPageChangeListener(adapter);
		super.setAdapter(adapter);
		if (adapter.isLoopable())
			setCurrentItem(1, false);
	}
}
