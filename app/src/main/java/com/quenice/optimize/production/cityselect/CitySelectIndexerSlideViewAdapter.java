package com.quenice.optimize.production.cityselect;

import com.quenice.optimize.userdefined.indexerslideview.IndexerSlideViewAdapter;

import java.util.ArrayList;

/**
 * Created by qiubb on 2016/5/26.
 */
public class CitySelectIndexerSlideViewAdapter extends IndexerSlideViewAdapter {
	private ArrayList<String> mData;
	public CitySelectIndexerSlideViewAdapter(ArrayList<String> data) {
		this.mData = data;
	}
	@Override
	public ArrayList<String> getData() {
		return mData;
	}
}
