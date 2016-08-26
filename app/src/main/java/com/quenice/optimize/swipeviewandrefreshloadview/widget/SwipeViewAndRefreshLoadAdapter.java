package com.quenice.optimize.swipeviewandrefreshloadview.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.quenice.optimize.refreshloadview.widget.RLDataModel;
import com.quenice.optimize.refreshloadview.widget.RLRecyclerAdapter;
import com.quenice.optimize.swipeview.widget.ItemSwipeHelper;
import com.quenice.optimize.swipeview.widget.SwipeView;

import java.util.List;

/**
 * 支持item左滑 + 上拉下拉刷新 recyclerview adapter
 * Created by qiubb on 2016/7/28.
 */
public abstract class SwipeViewAndRefreshLoadAdapter<DATA> extends RLRecyclerAdapter<DATA> implements ItemSwipeHelper.TouchInterceptor {

	private boolean canSwipe = true;
	private ItemSwipeHelper mItemSwipeHelper;

	/**
	 * 是否允许item能够滑动。
	 *
	 * @param canSwipe false：任何情况下都不能滑动。true：还需要结合其他逻辑(比如viewType是否等于VIEWTYPE_NORMAL)继续判断
	 */
	public void setCanSwipe(boolean canSwipe) {
		this.canSwipe = canSwipe;
	}

	@Override
	public boolean dispatch(RecyclerView recyclerView, SwipeView swipeView) {
		RecyclerView.ViewHolder viewHolder = recyclerView.findContainingViewHolder(swipeView);
		if (!canSwipe) return true;
		return !(viewHolder != null && viewHolder.getItemViewType() == RLDataModel.VIEWTYPE_NORMAL);
	}

	public SwipeViewAndRefreshLoadAdapter(Context context, List<DATA> data) {
		super(context, data);
	}

	public SwipeViewAndRefreshLoadAdapter(List<RLDataModel<DATA>> data, Context context) {
		super(data, context);
	}

	/**
	 * 获得item滑动辅助实例。
	 *
	 * @param context
	 * @return
	 */
	public final synchronized ItemSwipeHelper getItemSwipeHelper(Context context) {
		if (mItemSwipeHelper == null) {
			mItemSwipeHelper = new ItemSwipeHelper(context);
			mItemSwipeHelper.setTouchInterceptor(this);
		}
		return mItemSwipeHelper;
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		ItemSwipeHelper itemSwipeHelper = getItemSwipeHelper(recyclerView.getContext());
		if (itemSwipeHelper != null)
			recyclerView.addOnItemTouchListener(itemSwipeHelper);
	}
}
