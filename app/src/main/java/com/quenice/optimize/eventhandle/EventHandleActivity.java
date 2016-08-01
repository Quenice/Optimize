package com.quenice.optimize.eventhandle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.quenice.optimize.R;

/**
 * Created by qiubb on 2016/7/29.
 */
public class EventHandleActivity extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eventhandle);

//		findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Log.e("aaa", "aaa");
//				Toast.makeText(EventHandleActivity.this, "Click", Toast.LENGTH_SHORT).show();
//			}
//		});
	}
}
