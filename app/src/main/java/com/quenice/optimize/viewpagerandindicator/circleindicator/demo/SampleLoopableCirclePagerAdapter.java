package com.quenice.optimize.viewpagerandindicator.circleindicator.demo;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quenice.optimize.viewpagerandindicator.loopviewpager.widget.LoopablePagerAdapter;

import java.util.List;

/**
 * 循环的viewpager adapter
 * Created by qiubb on 2016/8/19.
 */
public class SampleLoopableCirclePagerAdapter<DATA> extends LoopablePagerAdapter<DATA> {
	public SampleLoopableCirclePagerAdapter(Context context, List<DATA> data) {
		super(context, data);
	}

	@Override
	public View createView(ViewGroup container, int position) {
		TextView textView = new TextView(container.getContext());
		textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		textView.setText("" + mData.get(position));
		textView.setGravity(Gravity.CENTER);
		textView.setTextSize(60);
		return textView;
	}
}
