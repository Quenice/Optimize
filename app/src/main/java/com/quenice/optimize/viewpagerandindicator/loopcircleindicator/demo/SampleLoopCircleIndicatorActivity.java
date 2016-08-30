package com.quenice.optimize.viewpagerandindicator.loopcircleindicator.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.quenice.optimize.R;
import com.quenice.optimize.viewpagerandindicator.loopcircleindicator.widget.LoopableCirclePagerIndicator;
import com.quenice.optimize.viewpagerandindicator.loopviewpager.widget.LoopableViewPager;

import java.util.Arrays;
import java.util.List;

/**
 * 循环viewpager
 * Created by qiubb on 2016/8/19.
 */
public class SampleLoopCircleIndicatorActivity extends AppCompatActivity {
	private LoopableViewPager mViewPager;
	private LoopableCirclePagerIndicator mIndicator;
	private List<String> data;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loop_circle_activity);
		data = Arrays.asList("1", "2");
		mViewPager = (LoopableViewPager) findViewById(R.id.viewPager);
		mIndicator = (LoopableCirclePagerIndicator) findViewById(R.id.indicator);
		mViewPager.setAdapter(new SampleLoopableCirclePagerAdapter<>(data));
		mIndicator.setupWithViewPager(mViewPager);
	}
}
