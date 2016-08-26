package com.quenice.optimize.viewpagerandindicator.loopviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.quenice.optimize.R;
import com.quenice.optimize.viewpagerandindicator.loopviewpager.widget.LoopViewPager;

import java.util.Arrays;
import java.util.List;

/**
 * 循环viewpager
 * Created by qiubb on 2016/8/19.
 */
public class SampleLoopViewPagerActivity extends AppCompatActivity {
	private LoopViewPager mViewPager;
	private List<String> data;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loop_viewpager);
		data = Arrays.asList("1");
		mViewPager = (LoopViewPager) findViewById(R.id.viewPager);
		mViewPager.setAdapter(new SampleLoopPagerAdapter<>(data));
	}
}
