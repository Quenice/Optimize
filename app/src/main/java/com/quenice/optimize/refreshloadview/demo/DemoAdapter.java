package com.quenice.optimize.refreshloadview.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quenice.optimize.R;
import com.quenice.optimize.refreshloadview.widget.RLDataModel;
import com.quenice.optimize.refreshloadview.widget.RLRecyclerAdapter;

import java.util.List;

/**
 * 数据适配器
 * Created by qiubb on 2016/6/29.
 */
public class DemoAdapter extends RLRecyclerAdapter<String> {


	public DemoAdapter(Context context, List<String> data) {
		super(context, data);
	}

	@Override
	protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
		return new NormalViewHolder(this, LayoutInflater.from(mContext).inflate(R.layout.item_refreshload_normal, parent, false));
	}

	@Override
	protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
		RLDataModel<String> model = mData.get(position);
		NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
		normalViewHolder.tv_title.setText(model.getData());
	}

	static class NormalViewHolder extends RecyclerView.ViewHolder {
		TextView tv_title;

		public NormalViewHolder(final DemoAdapter adapter, View itemView) {
			super(itemView);
			tv_title = (TextView) itemView.findViewById(R.id.tv_title);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					adapter.mData.get(1).setData("33");
					adapter.notifyItemChanged(1);
				}
			});
		}
	}
}
