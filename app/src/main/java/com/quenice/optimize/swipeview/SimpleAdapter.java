package com.quenice.optimize.swipeview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quenice.optimize.R;
import com.quenice.optimize.swipeview.widget.ItemSwipeHelper;
import com.quenice.optimize.swipeview.widget.SwipeViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiubb on 2016/8/3.
 */
public class SimpleAdapter extends SwipeViewAdapter<SimpleAdapter.MyViewHolder> {
	private Context mContext;
	private List<String> mData;
	private LayoutInflater mLayoutInflater;
	private ItemSwipeHelper.TouchInterceptor mTouchInterceptor;

	public SimpleAdapter(Context context, List<String> data) {
		this.mContext = context;
		this.mData = data;
		if (mData == null) mData = new ArrayList<>();
		mLayoutInflater = LayoutInflater.from(context);
//		mTouchInterceptor = new ItemSwipeHelper.TouchInterceptor() {
//			@Override
//			public boolean dispatch(RecyclerView recyclerView, SwipeView swipeView) {
//				if (recyclerView == null || swipeView == null) return false;
//				RecyclerView.ViewHolder viewHolder = recyclerView.findContainingViewHolder(swipeView);
//				if (viewHolder == null) return false;
//				return ((MyViewHolder) viewHolder).tv_value.getText().toString().equals("6");
//			}
//		};
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new MyViewHolder(mLayoutInflater.inflate(R.layout.item_swipeview, parent, false));
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		holder.bind(mData.get(position));
	}

	@Override
	public int getItemCount() {
		return mData.size();
	}

	@Override
	public ItemSwipeHelper.TouchInterceptor getTouchInterceptor() {
		return mTouchInterceptor;
	}

	static class MyViewHolder extends RecyclerView.ViewHolder {
		TextView tv_value, tv_delete;

		public MyViewHolder(View itemView) {
			super(itemView);
			tv_value = (TextView) itemView.findViewById(R.id.tv_value);
			tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
		}

		void bind(String d) {
			tv_value.setText(d);
			tv_value.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.e("Adapter", "tv_value.onClick");
				}
			});
			tv_delete.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.e("Adapter", "tv_delete.onClick");
				}
			});
		}
	}
}
