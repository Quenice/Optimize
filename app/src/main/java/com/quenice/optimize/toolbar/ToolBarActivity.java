package com.quenice.optimize.toolbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.quenice.optimize.R;

/**
 * toobar
 * Created by qiubb on 2016/11/28.
 */
public class ToolBarActivity extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.NoActionBar);
		setContentView(R.layout.activity_toolbar);
		Toolbar toolbar = (Toolbar) findViewById(R.id.v_toolbar);
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
	}
}
