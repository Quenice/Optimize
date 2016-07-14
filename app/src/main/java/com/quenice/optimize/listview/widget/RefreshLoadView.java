package com.quenice.optimize.listview.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * 刷新/加载列表控件
 * 需要加的功能：
 * 1 刷新时不能加载，加载是不能属性
 * Created by qiubb on 2016/7/1.
 */
public class RefreshLoadView<DATA> extends SwipeRefreshLayout {
	private final static String TAG = "RefreshLoadView";
	public final static int MODE_REFRESH = 0x01;
	public final static int MODE_LOAD = 0x02;
	public final static int MODE_BOTH = 0x03;
	public final static int MODE_NONE = 0x04;
	private RecyclerView mRecyclerView;
	//模式
	@MODE
	private int mMode = MODE_NONE;
	//正在加载
	private boolean loading;
	//已加载完所有数据
	private boolean noMoreData;
	//还剩几个不可见的item时执行加载动作
	private int invisibilityItemCountBeforeLoad = 1;
	private RLRecyclerAdapter<DATA> mAdapter;
	private List<RLDataModel<DATA>> mData;
	private RefreshLoadListener<DATA> mRefreshLoadListener;
	private RecyclerView.ItemDecoration mItemDecoration;
	private boolean mEnableItemDecoration = true;

	public RefreshLoadView(Context context) {
		this(context, null);
	}

	public RefreshLoadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		mRecyclerView = new RecyclerView(context);
		RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mRecyclerView.setLayoutParams(layoutParams);
		addView(mRecyclerView);
	}

	/**
	 * 加载数据之前
	 */
	private void preLoad() {
		loading = true;
		this.setEnabled(false);
		final int position = mData.size() - 1;
		RLDataModel<DATA> model = mData.get(position);
		model.setFooterType(RLDataModel.FOOTERTYPE_LOADING);
		post(new Runnable() {
			@Override
			public void run() {
				mAdapter.notifyItemChanged(position);
			}
		});
	}

	/**
	 * 刷新数据之前
	 */
	private void preRefresh() {
		loading = true;
	}

	/**
	 * 加载数据之后
	 *
	 * @param newData
	 */
	private void afterLoad(final List<RLDataModel<DATA>> newData, final boolean noMoreData) {
		if (getContext() == null || (getContext() instanceof Activity && ((Activity) getContext()).isDestroyed())) {
			Log.e(TAG, "abort afterLoad");
			return;
		} else {
			Log.e(TAG, "execute afterLoad");
		}
		this.noMoreData = noMoreData;
		final int lastPosition = mData.size() - 1;
		mData.remove(lastPosition);
		mData.addAll(newData);
		loading = false;
		this.setEnabled(true);
		post(new Runnable() {
			@Override
			public void run() {
				mAdapter.addFooterData(noMoreData ? RLDataModel.FOOTERTYPE_NOMOREDATA : RLDataModel.FOOTERTYPE_NORMAL);
				mAdapter.notifyItemRangeInserted(lastPosition, newData.size());
				mAdapter.notifyItemChanged(mData.size() - 1);
			}
		});
	}

	/**
	 * 刷新数据之后
	 *
	 * @param newData
	 */
	private void afterRefresh(List<RLDataModel<DATA>> newData, final boolean noMoreData) {
		if (getContext() == null || (getContext() instanceof Activity && ((Activity) getContext()).isDestroyed())) {
			Log.e(TAG, "abort afterRefresh");
			return;
		} else {
			Log.e(TAG, "execute afterRefresh");
		}
		this.setRefreshing(false);
		this.noMoreData = noMoreData;
		mData.clear();
		mData.addAll(newData);
		loading = false;
		post(new Runnable() {
			@Override
			public void run() {
				mAdapter.addFooterData(noMoreData ? RLDataModel.FOOTERTYPE_NOMOREDATA : RLDataModel.FOOTERTYPE_NORMAL);
				mAdapter.notifyDataSetChanged();
			}
		});
	}

	/**
	 * 是否已经滑到底
	 *
	 * @param recyclerView
	 * @return
	 */
	private boolean isBottom(RecyclerView recyclerView) {
		LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
		int totalItemCount = layoutManager.getItemCount();
		//无数据
		if (totalItemCount == 0) return false;
		int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
		return totalItemCount <= (lastVisibleItem + invisibilityItemCountBeforeLoad);
	}

	/**
	 * 初始化RecyclerView
	 */
	private void initRecyclerView() {
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		if (mEnableItemDecoration) {
			if (mItemDecoration == null)
				mItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
			mRecyclerView.addItemDecoration(mItemDecoration);
		}
		if (mMode == MODE_BOTH || mMode == MODE_LOAD) {
			mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
				@Override
				public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
					super.onScrolled(recyclerView, dx, dy);
					//如果已无更多数据，或者正在加载，则直接返回
					if (noMoreData || loading) return;
					//无初始化无数据，直接返回。注意：如果size=1，那么这条数据是footer的，也算作空
					if (mData == null || mData.size() <= 1) return;
					if (isBottom(recyclerView)) {
						manualLoad();
					}
				}
			});
		}
	}

	/**
	 * 初始化SwipeRefreshLayout
	 */
	private void initSwipeRefreshLayout() {
		if (mMode == MODE_BOTH || mMode == MODE_REFRESH) {
			this.setOnRefreshListener(new OnRefreshListener() {
				@Override
				public void onRefresh() {
					preRefresh();
					if (mRefreshLoadListener != null) {
						Log.e(TAG, "onRefresh");
						mRefreshLoadListener.onRefresh(new FinishDataCallback<DATA>() {
							@Override
							public void onFinish(List<RLDataModel<DATA>> data, boolean noMoreData) {
								afterRefresh(data, noMoreData);
							}
						});
					} else {
						afterRefresh(new ArrayList<RLDataModel<DATA>>(), noMoreData);
					}
				}
			});
		} else {
			this.setEnabled(false);
		}
	}


	public RecyclerView getRecyclerView() {
		return mRecyclerView;
	}

	/**
	 * 设置刷新/加载模式
	 *
	 * @param mode
	 */
	public void setMode(@MODE int mode) {
		this.mMode = mode;
	}

	/**
	 * 设置还剩几个不可见的item时执行加载动作
	 *
	 * @param count
	 */
	public void setInvisibilityItemCountBeforeLoad(int count) {
		this.invisibilityItemCountBeforeLoad = count;
	}

	/**
	 * 设置数据适配器
	 *
	 * @param adapter
	 */
	public void setAdapter(RLRecyclerAdapter<DATA> adapter) {
		this.mAdapter = adapter;
	}

	/**
	 * 手动加载
	 */
	public void manualLoad() {
		if (loading) {
			Log.e(TAG, "View is running, can't load more");
			return;
		}
		preLoad();
		Log.e(TAG, "onLoad");
		if (mRefreshLoadListener != null)
			mRefreshLoadListener.onLoad(new FinishDataCallback<DATA>() {
				@Override
				public void onFinish(List<RLDataModel<DATA>> data, boolean noMoreData) {
					afterLoad(data, noMoreData);
				}
			});
		else afterLoad(new ArrayList<RLDataModel<DATA>>(), noMoreData);
	}

	/**
	 * 设置数据适配器
	 */
	public void init() {
		if (mAdapter == null) {
			Log.e(TAG, "请先调用setAdapter来设置RLRecyclerView");
			return;
		}
		this.mData = mAdapter.getData();
		mAdapter.setRefreshLoadView(this);
		initRecyclerView();
		mRecyclerView.setAdapter(mAdapter);
		initSwipeRefreshLayout();
	}

	/**
	 * 增加分割线
	 *
	 * @param itemDecoration
	 */
	public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
		mItemDecoration = itemDecoration;
	}

	/**
	 * 是否启用分割线
	 *
	 * @param enable
	 */
	public void enableItemDecoration(boolean enable) {
		mEnableItemDecoration = enable;
	}

	public void setRefreshLoadListener(RefreshLoadListener<DATA> listener) {
		this.mRefreshLoadListener = listener;
	}


	@IntDef({MODE_REFRESH, MODE_LOAD, MODE_BOTH, MODE_NONE})
	@Retention(RetentionPolicy.SOURCE)
	public @interface MODE {

	}

	public interface RefreshLoadListener<DATA> {
		/**
		 * 刷新数据
		 *
		 * @param callback 数据加载完之后的回调
		 */
		void onRefresh(FinishDataCallback<DATA> callback);

		/**
		 * 当完成数据加载的时候，并且已经拿到数据结果的时候，调用callback.onFinish(data)来传递数据。这个要放到UI主线程调用
		 *
		 * @param callback 数据加载完之后的回调
		 */
		void onLoad(FinishDataCallback<DATA> callback);
	}

	public interface FinishDataCallback<DATA> {
		/**
		 * @param data       刷新/加载的新/增量数据
		 * @param noMoreData 是否全部加载完数据
		 */
		void onFinish(List<RLDataModel<DATA>> data, boolean noMoreData);
	}
}
