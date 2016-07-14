package com.quenice.optimize.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.quenice.optimize.R;

/**
 * Created by qiubb on 2016/7/5.
 */
public class TabDemoActivity extends AppCompatActivity {
	private TabLayout tablayout;
	private ViewPager viewpager;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabdemo);

		tablayout = (TabLayout) findViewById(R.id.tablayout);
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		PagerAdapter pagerAdapter = new TabDemoAdapter(getSupportFragmentManager());
		viewpager.setAdapter(pagerAdapter);
		tablayout.setupWithViewPager(viewpager);
	}
}
