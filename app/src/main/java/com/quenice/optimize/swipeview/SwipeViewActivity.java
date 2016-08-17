package com.quenice.optimize.swipeview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.quenice.optimize.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 侧滑view
 * Created by qiubb on 2016/7/25.
 */
public class SwipeViewActivity extends Activity {
	private RecyclerView recyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swipeview);

		List<String> data = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			data.add(i + "");
		}
		recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(new SimpleAdapter(this, data));
	}
}
