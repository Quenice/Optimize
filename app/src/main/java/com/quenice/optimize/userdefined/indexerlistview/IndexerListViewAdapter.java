package com.quenice.optimize.userdefined.indexerlistview;

import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * listview数据适配器
 * Created by qiubb on 2016/5/25.
 */
public abstract class IndexerListViewAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
	public final static int PINNED_HEADER_GONE = 0;
	public final static int PINNED_HEADER_VISIBLE = 1;
	public final static int PINNED_HEADER_PUSHED_UP = 2;

	public abstract List<IndexerListViewModel> getData();

	@Override
	public final int getCount() {
		return getData() == null ? 0 : getData().size();
	}

	@Override
	public final IndexerListViewModel getItem(int position) {
		return getData() == null ? null : getData().get(position);
	}

	@Override
	public final long getItemId(int position) {
		return position;
	}

	@Override
	public abstract int getViewTypeCount();

	@Override
	public final int getItemViewType(int position) {
		IndexerListViewModel m = getItem(position);
		return m == null ? IndexerListViewModel.DEFAULT_VIEWTYPE : m.getViewType();
	}

	/**
	 * 滑动listview刷新pinned view
	 * @param pinnedHeaderView
	 * @param position
	 */
	protected abstract void refreshPinnedHeaderView(View pinnedHeaderView, int position);

	/**
	 * 根据当前位置获得pinned view应该处于什么状态
	 *
	 * @param position
	 * @return
	 */
	protected int getPinnedHeaderViewState(int position) {
		if (position < 0) return PINNED_HEADER_GONE;
		List<IndexerListViewModel> dataList = getData();
		if (dataList == null || dataList.size() == 0) return PINNED_HEADER_GONE;
		if (position >= dataList.size() - 1) return PINNED_HEADER_VISIBLE;
		IndexerListViewModel current = dataList.get(position);
		IndexerListViewModel next = dataList.get(position + 1);
		if (!current.isSection() && next.isSection()) {
			return PINNED_HEADER_PUSHED_UP;
		}
		return PINNED_HEADER_VISIBLE;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (view instanceof IndexerListView)
			((IndexerListView) view).refreshPinnedHeaderView(firstVisibleItem);
	}
}
