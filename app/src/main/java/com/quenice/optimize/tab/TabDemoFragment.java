package com.quenice.optimize.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quenice.optimize.R;

/**
 * Created by qiubb on 2016/7/5.
 */
public class TabDemoFragment extends Fragment {
	private int mPosition;
	private View mView;
	private TextView tv;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_tabdemo, container, false);
		tv = (TextView) mView.findViewById(R.id.tv);
		Bundle bundle = getArguments();
		if (bundle != null) {
			mPosition = bundle.getInt("position");
			tv.setText("this is " + (mPosition + 1) + " page");
		}
		return mView;
	}
}
