package com.quenice.optimize.viewpagerandindicator.circleindicator.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quenice.optimize.R;
import com.quenice.optimize.viewpagerandindicator.circleindicator.widget.CirclePagerIndicator;

/**
 * Created by qiubb on 2016/7/14.
 */
public class CircleIndicatorActivity extends AppCompatActivity {
	private ViewPager mViewPager;
	private CirclePagerIndicator mIndicator;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circleindicator);

		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mIndicator = (CirclePagerIndicator) findViewById(R.id.indicator);
		mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
		mIndicator.setSmoothly(true);
		mIndicator.setupWithViewPager(mViewPager);
	}

	static class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return MyFragment.getInstance(position);
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			super.destroyItem(container, position, object);
			Log.e("destroyItem", "position=" + position);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			Log.e("instantiateItem", "position=" + position);
			return super.instantiateItem(container, position);
		}
	}

	public static class MyFragment extends Fragment {
		int position;
		View mView;

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
			mView = inflater.inflate(R.layout.fragment_circlepagerindicator, container, false);
			position = getArguments().getInt("position");
			((TextView) mView.findViewById(R.id.tv)).setText("this is " + position + " page");
			return mView;
		}

		public static MyFragment getInstance(int position) {
			Bundle bundle = new Bundle();
			bundle.putInt("position", position);
			MyFragment myFragment = new MyFragment();
			myFragment.setArguments(bundle);
			return myFragment;
		}
	}


}
