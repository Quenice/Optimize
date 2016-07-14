package com.quenice.optimize.official.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quenice.optimize.R;

import java.util.List;

/**
 * Created by qiubb on 2016/5/27.
 */
public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.MyViewHolder> {
	private List<String> mData;
	private Context mContext;

	public SampleAdapter(Context context, List<String> data) {
		this.mData = data;
		this.mContext = context;
	}

	@Override
	public SampleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview, parent, false));
	}

	@Override
	public void onBindViewHolder(SampleAdapter.MyViewHolder holder, int position) {
		holder.tv_title.setText(mData.get(position));
	}

	@Override
	public int getItemCount() {
		return mData == null ? 0 : mData.size();
	}

	static class MyViewHolder extends RecyclerView.ViewHolder {

		public MyViewHolder(View itemView) {
			super(itemView);
			tv_title = (TextView) itemView.findViewById(R.id.tv_title);
		}

		TextView tv_title;
	}
}
