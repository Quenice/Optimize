package com.quenice.optimize.circleindicator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quenice.optimize.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by qiubb on 2016/7/14.
 */
public class CircleIndicatorActivity extends AppCompatActivity {
	private ViewPager mViewPager;
	private CirclePagerIndicator mIndicator;

	private TimerTask mTimerTask;
	private Timer mTimer;
	private int index;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circleindicator);

		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mIndicator = (CirclePagerIndicator) findViewById(R.id.indicator);
		mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
		mIndicator.setupWithViewPager(mViewPager);


		mTimerTask = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mIndicator.setCurrentItem(index++);
					}
				});
			}
		};
		mTimer = new Timer();
		mTimer.schedule(mTimerTask, 2000, 2000);

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
