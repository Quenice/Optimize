package com.quenice.optimize.seekbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.quenice.optimize.R;

/**
 * Created by qiubb on 2016/7/11.
 */
public class SeekBarActivity extends AppCompatActivity {
	private SeekBar seekbar;
	private View v_level;
	private TextView tv_value;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seekbar);

		seekbar = (SeekBar) findViewById(R.id.seekbar);
		v_level = findViewById(R.id.v_level);
		tv_value = (TextView) findViewById(R.id.tv_value);

		final int seekBarLeftBar = ((FrameLayout.LayoutParams)seekbar.getLayoutParams()).leftMargin;
		final int max = seekbar.getMax();
		Log.e("SeekBarActivity", "," + seekbar.getWidth());
		seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)tv_value.getLayoutParams();
				lp.leftMargin = (int)(seekBarLeftBar + progress * 1.0 / max * seekbar.getWidth() - tv_value.getWidth() / 2.0);
				tv_value.setLayoutParams(lp);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
	}


}
