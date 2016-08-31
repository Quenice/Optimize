package com.quenice.optimize.viewpagerandindicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.quenice.optimize.R;
import com.quenice.optimize.viewpagerandindicator.circleindicator.demo.CircleIndicatorActivity;
import com.quenice.optimize.viewpagerandindicator.circleindicator.demo.SampleAutoLoopableCircleIndicatorActivity;
import com.quenice.optimize.viewpagerandindicator.circleindicator.demo.SampleLoopCircleIndicatorActivity;
import com.quenice.optimize.viewpagerandindicator.loopviewpager.SampleLoopViewPagerActivity;

/**
 * Created by qiubb on 2016/8/19.
 */
public class ViewPagerAndIndicatorActivity extends AppCompatActivity implements View.OnClickListener {
	private View tv_circleindicator;
	private View tv_loopviewpager;
	private View tv_loopcircleindicatorviewpager;
	private View tv_autoloopcircleindicatorviewpager;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager_and_indicator_main);

		initVars();
		initListeners();
	}

	private void initVars() {
		tv_circleindicator = findViewById(R.id.tv_circleindicator);
		tv_loopviewpager = findViewById(R.id.tv_loopviewpager);
		tv_loopcircleindicatorviewpager = findViewById(R.id.tv_loopcircleindicatorviewpager);
		tv_autoloopcircleindicatorviewpager = findViewById(R.id.tv_autoloopcircleindicatorviewpager);
	}

	private void initListeners() {
		tv_circleindicator.setOnClickListener(this);
		tv_loopviewpager.setOnClickListener(this);
		tv_loopcircleindicatorviewpager.setOnClickListener(this);
		tv_autoloopcircleindicatorviewpager.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_circleindicator:
				startActivity(new Intent(this, CircleIndicatorActivity.class));
				break;
			case R.id.tv_loopviewpager:
				startActivity(new Intent(this, SampleLoopViewPagerActivity.class));
				break;
			case R.id.tv_loopcircleindicatorviewpager:
				startActivity(new Intent(this, SampleLoopCircleIndicatorActivity.class));
				break;
			case R.id.tv_autoloopcircleindicatorviewpager:
				startActivity(new Intent(this, SampleAutoLoopableCircleIndicatorActivity.class));
				break;
		}
	}
}
