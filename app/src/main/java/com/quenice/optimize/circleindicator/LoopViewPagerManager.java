package com.quenice.optimize.circleindicator;

import android.support.v4.view.ViewPager;

/**
 * 轮播管理器
 * Created by qiubb on 2016/7/17.
 */
public class LoopViewPagerManager {
	private ViewPager mViewPager;
	private CirclePagerIndicator mPagerIndicator;
	private String dataUrl;
//	private

	public void setViewPager(ViewPager viewPager) {
		this.mViewPager = viewPager;
	}

	public void CirclePagerIndicator(CirclePagerIndicator circlePagerIndicator) {
		this.mPagerIndicator = circlePagerIndicator;
	}

	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}


	/**
	 * 开始初始化
	 */
	public void init() {
		if (mViewPager == null) return;
		if (mPagerIndicator == null) return;
		mViewPager.setAdapter(null);
		mPagerIndicator.setupWithViewPager(mViewPager);
	}
}
