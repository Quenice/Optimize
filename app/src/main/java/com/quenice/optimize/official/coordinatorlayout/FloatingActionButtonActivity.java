package com.quenice.optimize.official.coordinatorlayout;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.quenice.optimize.R;

/**
 * FloatingAction
 * Created by qiubb on 2016/4/21.
 */
public class FloatingActionButtonActivity extends AppCompatActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_floatingactionbutton);

		findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Snackbar.make(v, "FAB", Snackbar.LENGTH_LONG).setAction("cancel", new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								//这里的单击事件代表点击消除Action后的响应事件
							}
						}).show();
			}
		});
	}
}
