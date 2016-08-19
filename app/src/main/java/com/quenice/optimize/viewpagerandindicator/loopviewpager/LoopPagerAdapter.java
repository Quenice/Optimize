package com.quenice.optimize.viewpagerandindicator.loopviewpager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 循环的viewpager adapter
 * Created by qiubb on 2016/8/19.
 */
public class LoopPagerAdapter<DATA> extends PagerAdapter implements ViewPager.OnPageChangeListener {
	private List<DATA> mData;
	private View[] views;
	private ViewPager mViewPager;

	LoopPagerAdapter(List<DATA> data) {
		int len = data.size();
		mData = new ArrayList<>();
		mData.add(data.get(len - 1));
		mData.addAll(data);
		mData.add(data.get(0));
		views = new View[len + 2];
	}

	public void setViewPager(ViewPager viewPager) {
		this.mViewPager = viewPager;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		if (views[position] == null) {
			ImageView imageView = new ImageView(container.getContext());
			imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			imageView.setImageResource(container.getResources().getIdentifier("ic_horizontalview" + mData.get(position), "drawable", container.getContext().getPackageName()));
			views[position] = imageView;
		}
		container.addView(views[position]);
		return views[position];
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//		if (position == 0) {
//			if (positionOffset - 0.1 <= 0.0f) {
//				mViewPager.setCurrentItem(mData.size() - 2, false);
//				Log.e("onPageScrolled", "position=" + position + ", positionOffset=" + positionOffset);
//			}
//		} else if (position == mData.size() - 1) {
//			if (positionOffset - 0.1 <= 0.0f)
//				mViewPager.setCurrentItem(1, false);
//			Log.e("onPageScrolled", "position=" + position + ", positionOffset=" + positionOffset);
//		}
	}


	@Override
	public void onPageSelected(int position) {
		if (position == 0) {
			mViewPager.setCurrentItem(mData.size() - 2, false);
//			Log.e("onPageSelected", "position=" + position);
		} else if (position == mData.size() - 1) {
			mViewPager.setCurrentItem(1, false);
//			Log.e("onPageSelected", "position=" + position);
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}
}
