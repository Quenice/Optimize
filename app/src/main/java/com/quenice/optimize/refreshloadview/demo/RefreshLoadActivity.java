package com.quenice.optimize.refreshloadview.demo;

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
 * 上拉刷新，下拉加载更多listview
 * Created by qiubb on 2016/6/29.
 */
public class RefreshLoadActivity extends AppCompatActivity {
	private RefreshLoadView<String> refreshLoadView;
	private DemoAdapter mAdapter;
	private List<String> mData;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_refreshload);
		init();
	}

	private void init() {
		refreshLoadView = (RefreshLoadView<String>) findViewById(R.id.listview);
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			list.add(i + "");
		}
		mData = list;
		mAdapter = new DemoAdapter(this, mData);
		refreshLoadView.setMode(RefreshLoadView.MODE_BOTH);
		refreshLoadView.setRefreshLoadListener(new RefreshLoadView.RefreshLoadListener<String>() {

			@Override
			public void onRefresh(final RefreshLoadView.FinishDataCallback<String> callback) {
				new AsyncTask<Integer, Void, List<String>>() {
					@Override
					protected List<String> doInBackground(Integer... params) {
						try {
							Log.e("Activity", "onRefresh:" + Thread.currentThread().getId() + ", " + Thread.currentThread().getName());
							Thread.sleep(3000);
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
							Thread.sleep(10000);
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
				}.execute(mData.size() - 2);
			}
		});
		refreshLoadView.setAdapter(mAdapter);
		refreshLoadView.enableItemDecoration(true);
		refreshLoadView.init(false);
	}
}
