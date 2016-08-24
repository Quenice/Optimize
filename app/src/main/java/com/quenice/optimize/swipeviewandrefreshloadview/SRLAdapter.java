package com.quenice.optimize.swipeviewandrefreshloadview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quenice.optimize.R;
import com.quenice.optimize.swipeviewandrefreshloadview.widget.SwipeViewAndRefreshLoadAdapter;

import java.util.List;

/**
 * Created by qiubb on 2016/8/23.
 */
public class SRLAdapter extends SwipeViewAndRefreshLoadAdapter<String> {
	public SRLAdapter(Context context, List<String> data) {
		super(context, data);
	}

	@Override
	protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
		return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_swipeview, parent, false));
	}

	@Override
	protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
		((MyViewHolder) holder).bind(this, mData.get(position).getData(), position);
	}


	static class MyViewHolder extends RecyclerView.ViewHolder {
		TextView tv_value, tv_delete;

		public MyViewHolder(View itemView) {
			super(itemView);
			tv_value = (TextView) itemView.findViewById(R.id.tv_value);
			tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
		}

		void bind(final SRLAdapter adapter, String d, final int position) {
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
					Log.e("Adapter", "tv_delete.onClick, position=" + position);
					adapter.removeItem(position);
				}
			});
		}
	}
}
