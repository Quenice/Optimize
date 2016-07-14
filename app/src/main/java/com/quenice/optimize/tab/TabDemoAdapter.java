package com.quenice.optimize.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by qiubb on 2016/7/5.
 */
public class TabDemoAdapter extends FragmentPagerAdapter {

	private String[] titles = {"title1", "title2", "title3"};

	public TabDemoAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = new TabDemoFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("position", position);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public int getCount() {
		return titles.length;
	}
}
