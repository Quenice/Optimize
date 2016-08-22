package com.quenice.optimize.viewpagerandindicator.loopviewpager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
//			ImageView imageView = new ImageView(container.getContext());
//			imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//			imageView.setImageResource(container.getResources().getIdentifier("ic_horizontalview" + mData.get(position), "drawable", container.getContext().getPackageName()));
			TextView imageView = new TextView(container.getContext());
			imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
			imageView.setText("" + mData.get(position));
			imageView.setGravity(Gravity.CENTER);
			imageView.setTextSize(60);
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
}
