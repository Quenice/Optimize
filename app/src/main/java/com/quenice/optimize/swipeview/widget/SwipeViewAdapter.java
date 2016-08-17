package com.quenice.optimize.swipeview.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * 支持item左滑的recyclerview adapter
 * Created by qiubb on 2016/7/28.
 */
public abstract class SwipeViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

	public abstract ItemSwipeHelper.TouchInterceptor getTouchInterceptor();

	public final ItemSwipeHelper getItemSwipeHelper(Context context) {
		ItemSwipeHelper itemSwipeHelper = new ItemSwipeHelper(context);
		itemSwipeHelper.setTouchInterceptor(getTouchInterceptor());
		return itemSwipeHelper;
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		ItemSwipeHelper itemSwipeHelper = getItemSwipeHelper(recyclerView.getContext());
		if (itemSwipeHelper != null)
			recyclerView.addOnItemTouchListener(itemSwipeHelper);
	}
}
