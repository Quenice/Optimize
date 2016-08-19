package com.quenice.optimize.viewpagerandindicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.quenice.optimize.R;
import com.quenice.optimize.viewpagerandindicator.circleIndicator.CircleIndicatorActivity;
import com.quenice.optimize.viewpagerandindicator.loopviewpager.LoopViewPagerActivity;

/**
 * Created by qiubb on 2016/8/19.
 */
public class ViewPagerAndIndicatorActivity extends AppCompatActivity implements View.OnClickListener {
	private View tv_circleindicator;
	private View tv_loopviewpager;

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
	}

	private void initListeners() {
		tv_circleindicator.setOnClickListener(this);
		tv_loopviewpager.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_circleindicator:
				startActivity(new Intent(this, CircleIndicatorActivity.class));
				break;
			case R.id.tv_loopviewpager:
				startActivity(new Intent(this, LoopViewPagerActivity.class));
				break;
		}
	}
}
