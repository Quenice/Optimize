package com.quenice.optimize.viewpagerandindicator.loopviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.quenice.optimize.R;
import com.quenice.optimize.viewpagerandindicator.loopviewpager.widget.LoopableViewPager;

import java.util.Arrays;
import java.util.List;

/**
 * 循环viewpager
 * Created by qiubb on 2016/8/19.
 */
public class SampleLoopViewPagerActivity extends AppCompatActivity {
	private LoopableViewPager mViewPager;
	private List<String> data;
	private View btn;
	private int idx;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loop_viewpager);
		data = Arrays.asList("1", "2", "3", "4", "5");
		mViewPager = (LoopableViewPager) findViewById(R.id.viewPager);
		mViewPager.setAdapter(new SampleLoopablePagerAdapter<>(this, data));

		btn = findViewById(R.id.btn);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(4, true);
			}
		});
	}
}
