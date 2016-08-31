package com.quenice.optimize.viewpagerandindicator.circleindicator.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quenice.optimize.R;
import com.quenice.optimize.viewpagerandindicator.circleindicator.widget.LoopableCirclePagerIndicator;
import com.quenice.optimize.viewpagerandindicator.loopviewpager.widget.AutoLoopablePagerAdapter;
import com.quenice.optimize.viewpagerandindicator.loopviewpager.widget.LoopableViewPager;

import java.util.Arrays;
import java.util.List;

/**
 * Created by qiubb on 2016/8/29.
 */
public class SampleAutoLoopableCircleIndicatorActivity extends AppCompatActivity {
	private LoopableViewPager mViewPager;
	private MyAdapter mPagerAdapter;
	private LoopableCirclePagerIndicator indicator;
	private List<String> data;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_autoloopable_circleindicator_activity);
		data = Arrays.asList("1", "2", "3");
		mViewPager = (LoopableViewPager) findViewById(R.id.viewPager);
		indicator = (LoopableCirclePagerIndicator) findViewById(R.id.indicator);
		mPagerAdapter = new MyAdapter(this, data);
		mViewPager.setAdapter(mPagerAdapter);
		indicator.setupWithViewPager(mViewPager);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mPagerAdapter.startAutoLoop();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mPagerAdapter.stopAutoLoop();
	}

	static class MyAdapter extends AutoLoopablePagerAdapter<String> {

		public MyAdapter(Context context, List<String> data) {
			super(context, data);
		}

		@Override
		public View createView(ViewGroup container, int position) {
			TextView textView = new TextView(container.getContext());
			textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
			textView.setText("" + mData.get(position));
			textView.setGravity(Gravity.CENTER);
			textView.setTextSize(60);
			return textView;
		}
	}
}
