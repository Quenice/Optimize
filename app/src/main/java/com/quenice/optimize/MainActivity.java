package com.quenice.optimize;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.quenice.optimize.eventhandle.EventHandleActivity;
import com.quenice.optimize.horizontalview.HorizontalViewActivity;
import com.quenice.optimize.refreshloadview.demo.RefreshLoadActivity;
import com.quenice.optimize.official.coordinatorlayout.BottomSheetBehaviorActivity;
import com.quenice.optimize.official.coordinatorlayout.FloatingActionButtonActivity;
import com.quenice.optimize.official.recyclerview.RecyclerViewActivity;
import com.quenice.optimize.pickerview.PickerViewActivity;
import com.quenice.optimize.production.cityselect.CitySelectActivity;
import com.quenice.optimize.seekbar.SeekBarActivity;
import com.quenice.optimize.swipeview.SwipeViewActivity;
import com.quenice.optimize.tab.TabDemoActivity;
import com.quenice.optimize.viewpagerandindicator.ViewPagerAndIndicatorActivity;

/**
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
	private View tv_floatingactionbutton;
	private View tv_cityselect;
	private View tv_recyclerview;
	private View tv_bottomsheetbehavior;
	private View tv_pickerview;
	private View tv_listview;
	private View tv_tab;
	private View tv_viewpager_indicator;
	private View tv_seekbar;
	private View tv_swipeview;
	private View tv_horizontalview;
	private View tv_eventhandle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tv_floatingactionbutton = findViewById(R.id.tv_floatingaction);
		tv_cityselect = findViewById(R.id.tv_cityselect);
		tv_recyclerview = findViewById(R.id.tv_recyclerview);
		tv_bottomsheetbehavior = findViewById(R.id.tv_bottomsheetbehavior);
		tv_pickerview = findViewById(R.id.tv_pickerview);
		tv_listview = findViewById(R.id.tv_listview);
		tv_tab = findViewById(R.id.tv_tab);
		tv_seekbar = findViewById(R.id.tv_seekbar);
		tv_viewpager_indicator = findViewById(R.id.tv_viewpager_indicator);
		tv_swipeview = findViewById(R.id.tv_swipeview);
		tv_horizontalview = findViewById(R.id.tv_horizontalview);
		tv_eventhandle = findViewById(R.id.tv_eventhandle);

		tv_floatingactionbutton.setOnClickListener(this);
		tv_cityselect.setOnClickListener(this);
		tv_recyclerview.setOnClickListener(this);
		tv_bottomsheetbehavior.setOnClickListener(this);
		tv_pickerview.setOnClickListener(this);
		tv_listview.setOnClickListener(this);
		tv_tab.setOnClickListener(this);
		tv_seekbar.setOnClickListener(this);
		tv_viewpager_indicator.setOnClickListener(this);
		tv_swipeview.setOnClickListener(this);
		tv_horizontalview.setOnClickListener(this);
		tv_eventhandle.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_floatingaction:
				startActivity(new Intent(this, FloatingActionButtonActivity.class));
				break;
			case R.id.tv_cityselect:
				startActivity(new Intent(this, CitySelectActivity.class));
				break;
			case R.id.tv_recyclerview:
				startActivity(new Intent(this, RecyclerViewActivity.class));
				break;
			case R.id.tv_bottomsheetbehavior:
				startActivity(new Intent(this, BottomSheetBehaviorActivity.class));
				break;
			case R.id.tv_pickerview:
				startActivity(new Intent(this, PickerViewActivity.class));
				break;
			case R.id.tv_listview:
				startActivity(new Intent(this, RefreshLoadActivity.class));
				break;
			case R.id.tv_tab:
				startActivity(new Intent(this, TabDemoActivity.class));
				break;
			case R.id.tv_seekbar:
				startActivity(new Intent(this, SeekBarActivity.class));
				break;
			case R.id.tv_viewpager_indicator:
				startActivity(new Intent(this, ViewPagerAndIndicatorActivity.class));
				break;
			case R.id.tv_swipeview:
				startActivity(new Intent(this, SwipeViewActivity.class));
				break;
			case R.id.tv_horizontalview:
				startActivity(new Intent(this, HorizontalViewActivity.class));
				break;
			case R.id.tv_eventhandle:
				startActivity(new Intent(this, EventHandleActivity.class));
				break;
		}
	}
}
