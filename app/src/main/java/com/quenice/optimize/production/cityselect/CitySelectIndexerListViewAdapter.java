package com.quenice.optimize.production.cityselect;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quenice.optimize.City;
import com.quenice.optimize.R;
import com.quenice.optimize.userdefined.indexerlistview.IndexerListViewAdapter;
import com.quenice.optimize.userdefined.indexerlistview.IndexerListViewModel;

import java.util.List;

/**
 * Created by qiubb on 2016/5/26.
 */
public class CitySelectIndexerListViewAdapter extends IndexerListViewAdapter {
	public final static int VIEWTYPE_HEADER = 0;
	public final static int VIEWTYPE_GRIDVIEW = 1;
	public final static int VIEWTYPE_NORMAL = 2;
	private Context mContext;
	private List<IndexerListViewModel> mData;

	public CitySelectIndexerListViewAdapter(Context context, List<IndexerListViewModel> data) {
		this.mContext = context;
		this.mData = data;
	}

	@Override
	public List<IndexerListViewModel> getData() {
		return mData;
	}

	@Override
	public int getViewTypeCount() {
		return 3;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int viewType = getItemViewType(position);
		IndexerListViewModel model = getItem(position);
		switch (viewType) {
			case VIEWTYPE_HEADER:
				convertView = getHeaderView(convertView, model);
				break;
			case VIEWTYPE_GRIDVIEW:
				convertView = getGridView(convertView, model);
				break;
			case VIEWTYPE_NORMAL:
			default:
				convertView = getContentView(convertView, model);
				break;
		}
		return convertView;
	}

	@Override
	protected void refreshPinnedHeaderView(View pinnedHeaderView, int position) {
		if (position < 0) return;
		IndexerListViewModel m = mData.get(position);
		((TextView) pinnedHeaderView).setText(m.getSection());
	}


	private View getHeaderView(View convertView, IndexerListViewModel model) {
		HeaderViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_header, null);
			viewHolder = new HeaderViewHolder();
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (HeaderViewHolder) convertView.getTag();
		}
		viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
		viewHolder.tv_title.setText((String) model.getData());
		return convertView;
	}

	private View getGridView(View convertView, IndexerListViewModel model) {
		GridViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gridview, null);
			viewHolder = new GridViewHolder();
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (GridViewHolder) convertView.getTag();
		}
		viewHolder.recyclerview = (RecyclerView) convertView.findViewById(R.id.recyclerview);
		viewHolder.recyclerview.setLayoutManager(new GridLayoutManager(mContext, 3));
		viewHolder.recyclerview.setAdapter(new CitySelectRecyclerViewAdapter(mContext, (List<String>) model.getData()));
		return convertView;
	}

	private View getContentView(View convertView, IndexerListViewModel model) {
		ContentViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_content, null);
			viewHolder = new ContentViewHolder();
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ContentViewHolder) convertView.getTag();
		}
		viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
		viewHolder.tv_content.setText(((City) model.getData()).getCity());
		return convertView;
	}

	private static class HeaderViewHolder {
		TextView tv_title;
	}

	private static class GridViewHolder {
		RecyclerView recyclerview;
	}

	private static class ContentViewHolder {
		TextView tv_content;
	}
}
