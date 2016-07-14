package com.quenice.optimize.official.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.quenice.optimize.R;

import java.util.ArrayList;
import java.util.List;

/**
 * recyclerview
 * Created by qiubb on 2016/5/27.
 */
public class RecyclerViewActivity extends AppCompatActivity {
	private RecyclerView recyclerview;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recyclerview);
		recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
		recyclerview.setLayoutManager(new GridLayoutManager(this, 3));

		List<String> data = new ArrayList<>();
		for(int i = 0; i < 2; i++) {
			data.add(i + "");
		}
		recyclerview.setAdapter(new SampleAdapter(this, data));
	}
}
