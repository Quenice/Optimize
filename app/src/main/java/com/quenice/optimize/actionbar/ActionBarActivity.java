package com.quenice.optimize.actionbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.quenice.optimize.R;

/**
 * Created by qiubb on 2016/11/23.
 */

public class ActionBarActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actionbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("我的Header");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_search:
				Toast.makeText(this, "Clicked Search", Toast.LENGTH_SHORT).show();
				break;
		}
		Log.e("ActionBarActivity", "id=" + item.getItemId());
		return super.onOptionsItemSelected(item);
	}
}
