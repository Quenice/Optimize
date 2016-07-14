package com.quenice.optimize.production.cityselect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.quenice.optimize.Application;
import com.quenice.optimize.City;
import com.quenice.optimize.R;
import com.quenice.optimize.StaticDataDao;
import com.quenice.optimize.userdefined.indexerlistview.IndexerListView;
import com.quenice.optimize.userdefined.indexerlistview.IndexerListViewModel;
import com.quenice.optimize.userdefined.indexerslideview.IndexerSlideView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qiubb on 2016/5/26.
 */
public class CitySelectActivity extends AppCompatActivity {
	private IndexerListView listview;
	private IndexerSlideView slideview;
	private TextView tv_show;
	private Map<String, Integer> map;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cityselect);

		listview = (IndexerListView) findViewById(R.id.listview);
		slideview = (IndexerSlideView) findViewById(R.id.slideview);
		tv_show = (TextView) findViewById(R.id.tv_show);

		ArrayList<City> list = StaticDataDao.getInstance(Application.getInstance()).getAllCity();
		ArrayList<String> indexList = new ArrayList<>();
		map = new HashMap<>();

		int idx = 0;
		ArrayList<IndexerListViewModel> data = new ArrayList<>();
		IndexerListViewModel mt = new IndexerListViewModel();
		mt.setSection(true);
		mt.setData("热门城市");
		mt.setViewType(CitySelectIndexerListViewAdapter.VIEWTYPE_HEADER);
		mt.setSection("热门城市");
		data.add(mt);
		indexList.add("#");
		map.put("#", idx);
		idx++;

		List<String> cList = new ArrayList<>();
		cList.add("深圳");
		cList.add("上海");
		cList.add("南京");
		cList.add("北京");
		mt = new IndexerListViewModel();
		mt.setSection(false);
		mt.setData(cList);
		mt.setViewType(CitySelectIndexerListViewAdapter.VIEWTYPE_GRIDVIEW);
		mt.setSection("热门城市");
		data.add(mt);
		idx++;

		String preIndex = null;

		for (City c : list) {
			if (!c.getFirstPY().equals(preIndex)) {
				IndexerListViewModel m = new IndexerListViewModel();
				m.setViewType(CitySelectIndexerListViewAdapter.VIEWTYPE_HEADER);
				m.setData(c.getFirstPY());
				m.setSection(true);
				m.setSection(c.getFirstPY());
				data.add(m);
				indexList.add(c.getFirstPY());
				map.put(c.getFirstPY(), idx);
				idx++;
			}
			IndexerListViewModel m = new IndexerListViewModel();
			m.setViewType(CitySelectIndexerListViewAdapter.VIEWTYPE_NORMAL);
			m.setData(c);
			m.setSection(false);
			m.setSection(c.getFirstPY());
			data.add(m);
			preIndex = c.getFirstPY();
			idx++;
		}

		listview.setPinnedHeaderView(this, R.layout.item_indexerlistview_pinnedheader);
		slideview.setAdatper(new CitySelectIndexerSlideViewAdapter(indexList));
		listview.setAdapter(new CitySelectIndexerListViewAdapter(this, data));

		slideview.setOnSlideListener(new IndexerSlideView.OnSlideListener() {
			@Override
			public void onSlideStart() {
				tv_show.setVisibility(View.VISIBLE);
			}

			@Override
			public void onSlide(int position, String text) {
				tv_show.setText(text);
				int selectionIdx = map.get(text);
				//先暂停之前的滑动
				listview.stopScrolling();
				listview.setSelection(selectionIdx);
			}

			@Override
			public void onSlideEnd() {
				tv_show.setVisibility(View.GONE);
			}
		});
	}
}
