package com.quenice.optimize.swipeviewandrefreshloadview;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.quenice.optimize.R;
import com.quenice.optimize.refreshloadview.widget.RefreshLoadView;

import java.util.ArrayList;
import java.util.List;

/**
 * SwipView and RefreshLoadView
 * Created by qiubb on 2016/8/23.
 */
public class SRLActivity extends AppCompatActivity {
	private RefreshLoadView<String> refreshLoadView;
	private SRLAdapter srlAdapter;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swipeviewandrefreshloadview);
		final List<String> data = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			data.add(i + "");
		}
		refreshLoadView = (RefreshLoadView<String>) findViewById(R.id.refreshLoadView);
		srlAdapter = new SRLAdapter(this, data);
		refreshLoadView.setMode(RefreshLoadView.MODE_BOTH);
		refreshLoadView.enableItemDecoration(true);
		refreshLoadView.setRefreshLoadListener(new RefreshLoadView.SimpleRefreshLoadListener<String>() {

			@Override
			public void preLoad() {
				super.preLoad();
				srlAdapter.setCanSwipe(false);
			}

			@Override
			public void preRefresh() {
				super.preRefresh();
				srlAdapter.setCanSwipe(false);
			}

			@Override
			public void afterLoad() {
				super.afterLoad();
				srlAdapter.setCanSwipe(true);
			}

			@Override
			public void afterRefresh() {
				super.afterRefresh();
				srlAdapter.setCanSwipe(true);
			}

			@Override
			public void onRefresh(final RefreshLoadView.FinishDataCallback<String> callback) {
				new AsyncTask<Integer, Void, List<String>>() {
					@Override
					protected List<String> doInBackground(Integer... params) {
						try {
							Log.e("Activity", "onRefresh:" + Thread.currentThread().getId() + ", " + Thread.currentThread().getName());
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						List<String> list = new ArrayList<>();
						int start = 0;
						int end = start + 15;
						for (int i = start; i < end; i++) {
							list.add(i + "");
						}
						return list;
					}

					@Override
					protected void onPostExecute(List<String> list) {
						super.onPostExecute(list);
						callback.onFinish(list, false);
					}
				}.execute(-0);
			}

			@Override
			public void onLoad(final RefreshLoadView.FinishDataCallback<String> callback) {
				new AsyncTask<Integer, Void, List<String>>() {
					@Override
					protected List<String> doInBackground(Integer... params) {
						try {
							Log.e("Activity", "onLoad:" + Thread.currentThread().getId() + ", " + Thread.currentThread().getName());
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						List<String> list = new ArrayList<>();
						int start = params[0] + 1;
						int end = start + 20;
						for (int i = start; i < end; i++) {
							list.add(i + "");
						}
						return list;
					}

					@Override
					protected void onPostExecute(List<String> list) {
						super.onPostExecute(list);
						callback.onFinish(list, false);
					}
				}.execute(data.size() - 2);
			}
		});
		refreshLoadView.setAdapter(srlAdapter);
		refreshLoadView.init(false);
	}
}
