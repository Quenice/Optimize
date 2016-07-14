package com.quenice.optimize.production.cityselect;

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
public class CitySelectRecyclerViewAdapter extends RecyclerView.Adapter<CitySelectRecyclerViewAdapter.MyViewHolder> {
	private Context mContext;
	private List<String> mData;

	public CitySelectRecyclerViewAdapter(Context context, List<String> data) {
		this.mContext = context;
		this.mData = data;
	}

	@Override
	public CitySelectRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_cityselect_recyclerview, parent, false));
	}

	@Override
	public void onBindViewHolder(CitySelectRecyclerViewAdapter.MyViewHolder holder, int position) {
		holder.tv_city.setText(mData.get(position));
	}

	@Override
	public int getItemCount() {
		return mData.size();
	}

	static class MyViewHolder extends RecyclerView.ViewHolder {

		public MyViewHolder(View itemView) {
			super(itemView);
			tv_city = (TextView) itemView.findViewById(R.id.tv_content);
		}

		TextView tv_city;
	}
}
