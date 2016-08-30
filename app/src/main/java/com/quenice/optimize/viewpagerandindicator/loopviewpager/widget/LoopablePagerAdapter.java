package com.quenice.optimize.viewpagerandindicator.loopviewpager.widget;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 循环的viewpager adapter
 * Created by qiubb on 2016/8/19.
 */
public abstract class LoopablePagerAdapter<DATA> extends PagerAdapter implements ViewPager.OnPageChangeListener {
	protected List<DATA> mData;
	private View[] views;
	protected ViewPager mViewPager;
	private boolean loopable;

	public LoopablePagerAdapter(List<DATA> data) {
		if (data == null) data = new ArrayList<>();
		int len = data.size();
		if (len <= 1) {
			mData = data;
			views = new View[1];
			loopable = false;
		} else {
			mData = new ArrayList<>();
			mData.add(data.get(len - 1));
			mData.addAll(data);
			mData.add(data.get(0));
			views = new View[len + 2];
			loopable = true;
		}
	}

	public void setViewPager(ViewPager viewPager) {
		this.mViewPager = viewPager;
	}

	@Override
	public final int getCount() {
		return mData.size();
	}

	public abstract View createView(ViewGroup container, int position);

	@Override
	public final Object instantiateItem(ViewGroup container, int position) {
		if (views[position] == null) {
			views[position] = createView(container, position);
		}
		container.addView(views[position]);
		return views[position];
	}

	@Override
	public final void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public final boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public final void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		if (!loopable) return;
		//第一个页面，偏移量1到0（从隐藏到显示）
		if (position == 0) {
			if (positionOffset - 0.05 <= 0.0f) {
				mViewPager.setCurrentItem(mData.size() - 2, false);
				Log.e("onPageScrolled", "position=" + position + ", positionOffset=" + positionOffset);
			}
		}
		//倒数第二个页面（对实际数据来说，是最后一页）偏移量从0到1（从显示到隐藏）
		else if (position == mData.size() - 2) {
			if (positionOffset + 0.05 >= 1.0f) {
				mViewPager.setCurrentItem(1, false);
				Log.e("onPageScrolled", "position=" + position + ", positionOffset=" + positionOffset);
			}
		}
	}


	@Override
	public void onPageSelected(int position) {
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}

	public boolean isLoopable() {
		return loopable;
	}
}
