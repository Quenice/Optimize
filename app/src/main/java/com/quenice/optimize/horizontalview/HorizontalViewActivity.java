package com.quenice.optimize.horizontalview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quenice.optimize.R;

/**
 * 横向滚动view
 * Created by qiubb on 2016/7/28.
 */
public class HorizontalViewActivity extends AppCompatActivity {
	private LinearLayout v_content;
	private LayoutInflater mLayoutInflater;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_horizontalview);
		mLayoutInflater = LayoutInflater.from(this);
		v_content = (LinearLayout) findViewById(R.id.v_content);
		loadImages();
	}


	private void loadImages() {
		for (int i = 1; i <= 7; i++) {
			View item = mLayoutInflater.inflate(R.layout.item_horizontalview_image, v_content, false);
			ImageView iv_img = (ImageView) item.findViewById(R.id.iv_img);
			TextView tv_desc = (TextView) item.findViewById(R.id.tv_desc);
			iv_img.setImageResource(getResources().getIdentifier("ic_horizontalview" + i, "drawable", getPackageName()));
			tv_desc.setText("图片" + i);

			v_content.addView(item);
		}
	}
}
