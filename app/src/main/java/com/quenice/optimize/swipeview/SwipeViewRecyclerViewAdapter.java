package com.quenice.optimize.swipeview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quenice.optimize.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiubb on 2016/7/28.
 */
public class SwipeViewRecyclerViewAdapter extends RecyclerView.Adapter<SwipeViewRecyclerViewAdapter.MyViewHolder> {
	private Context mContext;
	private List<String> mData;
	private LayoutInflater mLayoutInflater;

	public SwipeViewRecyclerViewAdapter(Context context, List<String> data) {
		this.mContext = context;
		this.mData = data;
		if (mData == null) mData = new ArrayList<>();
		mLayoutInflater = LayoutInflater.from(context);
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
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		recyclerView.addOnItemTouchListener(new ItemSwipeHelper(recyclerView, mContext));
	}

	static class MyViewHolder extends RecyclerView.ViewHolder {
		TextView tv_value, tv_delete;

		public MyViewHolder(View itemView) {
			super(itemView);
			tv_value = (TextView) itemView.findViewById(R.id.tv_value);
			tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
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

		void bind(String d) {
			tv_value.setText(d);
		}
	}
}
