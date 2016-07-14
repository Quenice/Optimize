package com.quenice.optimize.listview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.quenice.optimize.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 刷新/加载数据适配器
 * Created by qiubb on 2016/7/1.
 */
public abstract class RLRecyclerAdapter<DATA> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private final static String TAG = "RLRecyclerAdapter";
	protected Context mContext;
	protected List<RLDataModel<DATA>> mData = new ArrayList<>();
	private RefreshLoadView mRefreshLoadView;

	public RLRecyclerAdapter(Context context, List<RLDataModel<DATA>> data) {
		this.mContext = context;
		this.mData = data == null ? mData : data;
		addFooterData(mData.size() == 0 ? RLDataModel.FOOTERTYPE_NONEDATA : RLDataModel.FOOTERTYPE_NORMAL);
	}

	protected abstract RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType);

	protected abstract void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position);

	public List<RLDataModel<DATA>> getData() {
		return mData;
	}

	public final void addFooterData(@RLDataModel.FOOTERTYPE int footerType) {
		RLDataModel<DATA> footer = new RLDataModel<>();
		footer.setViewType(RLDataModel.VIEWTYPE_FOOTER);
		footer.setFooterType(footerType);
		mData.add(footer);
	}

	@Override
	public int getItemViewType(int position) {
		return mData.get(position).getViewType();
	}

	public RLDataModel<DATA> getItemData(int position) {
		return mData.get(position);
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		switch (viewType) {
			case RLDataModel.VIEWTYPE_FOOTER:
				Log.e(TAG, "onCreateViewHolder.footer");
				return new FooterViewHolder(this, mContext, LayoutInflater.from(mContext).inflate(R.layout.item_refreshload_footer, parent, false));
			default:
				return onCreateCustomViewHolder(parent, viewType);
		}
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		int viewtype = getItemViewType(position);
		RLDataModel model = getItemData(position);
		switch (viewtype) {
			case RLDataModel.VIEWTYPE_FOOTER:
				Log.e(TAG, "onBindViewHolder.footer");
				FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
				footerViewHolder.switchType(model.getFooterType());
				break;
			default:
				onBindCustomViewHolder(holder, position);
		}
	}

	@Override
	public int getItemCount() {
		return mData.size();
	}

	public void setRefreshLoadView(RefreshLoadView view) {
		this.mRefreshLoadView = view;
	}

	static class FooterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		private int[] colors = {
				0xFFFF0000, 0xFFFF7F00, 0xFFFFFF00, 0xFF00FF00
				, 0xFF00FFFF, 0xFF0000FF, 0xFF8B00FF};
		final static int MAX_ALPHA = 255;
		View mItemView;
		Context mContext;
		ImageView iv;
		TextView tv;
		MaterialProgressDrawable progressDrawable;
		ValueAnimator valueAnimator;
		RLRecyclerAdapter recyclerAdapter;

		public FooterViewHolder(RLRecyclerAdapter recyclerAdapter, Context context, View itemView) {
			super(itemView);
			mContext = context;
			this.recyclerAdapter = recyclerAdapter;
			this.mItemView = itemView;
			this.iv = (ImageView) itemView.findViewById(R.id.iv);
			this.tv = (TextView) itemView.findViewById(R.id.tv);
			initDrawable();
		}

		private void initDrawable() {
			progressDrawable = new MaterialProgressDrawable(mContext, iv);
			progressDrawable.setBackgroundColor(0xFFFAFAFA);
			progressDrawable.setColorSchemeColors(colors);
			progressDrawable.updateSizes(MaterialProgressDrawable.DEFAULT);
			iv.setBackground(progressDrawable);
			valueAnimator = ValueAnimator.ofFloat(0f, 1f);
			valueAnimator.setDuration(600);
			valueAnimator.setInterpolator(new DecelerateInterpolator());
			valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					float n = (float) animation.getAnimatedValue();
					//圈圈的旋转角度
					progressDrawable.setProgressRotation(n * 0.5f);
					//圈圈周长，0f-1F
					progressDrawable.setStartEndTrim(0f, n * 0.8f);
					//箭头大小，0f-1F
					progressDrawable.setArrowScale(n);
					//透明度，0-255
					progressDrawable.setAlpha((int) (MAX_ALPHA * n));
				}
			});
		}

		void switchType(int footerType) {
			Log.e("Adapter", "footerType = " + footerType);
			switch (footerType) {
				case RLDataModel.FOOTERTYPE_NORMAL:
					endAnim();
					mItemView.setVisibility(View.VISIBLE);
					tv.setText("点击加载更多");
					tv.setEnabled(true);
					tv.setOnClickListener(this);
					break;
				case RLDataModel.FOOTERTYPE_LOADING:
					startAnim();
					mItemView.setVisibility(View.VISIBLE);
					tv.setText("正在加载更多...");
					tv.setEnabled(false);
					break;
				case RLDataModel.FOOTERTYPE_NOMOREDATA:
					endAnim();
					mItemView.setVisibility(View.VISIBLE);
					tv.setText("已无更多数据");
					tv.setEnabled(false);
					break;
				case RLDataModel.FOOTERTYPE_NONEDATA:
					endAnim();
					mItemView.setVisibility(View.GONE);
					break;
			}
		}

		private void startAnim() {
			iv.setVisibility(View.VISIBLE);
			if (valueAnimator != null && !valueAnimator.isStarted() && !valueAnimator.isRunning()) {
				valueAnimator.start();
			}
			if (progressDrawable != null && !progressDrawable.isRunning()) {
				progressDrawable.setAlpha(MAX_ALPHA);
				progressDrawable.start();
			}
		}

		private void endAnim() {
			iv.setVisibility(View.GONE);
			if (valueAnimator != null && (valueAnimator.isStarted() || valueAnimator.isRunning()))
				valueAnimator.end();
			if (progressDrawable != null && progressDrawable.isRunning())
				progressDrawable.stop();
		}

		@Override
		public void onClick(View v) {
			recyclerAdapter.mRefreshLoadView.manualLoad();
		}
	}

	@Override
	public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
		super.onViewDetachedFromWindow(holder);
		if (holder instanceof FooterViewHolder) {
			int position = holder.getAdapterPosition();
			if (position == RecyclerView.NO_POSITION) return;
			RLDataModel<DATA> model = getItemData(position);
			if (model == null) return;
			if (model.getFooterType() == RLDataModel.FOOTERTYPE_LOADING) {
				((FooterViewHolder) holder).endAnim();
			}
		}
	}

	@Override
	public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
		super.onViewAttachedToWindow(holder);
		if (holder instanceof FooterViewHolder) {
			int position = holder.getAdapterPosition();
			if (position == RecyclerView.NO_POSITION) return;
			RLDataModel<DATA> model = getItemData(position);
			if (model == null) return;
			((FooterViewHolder) holder).switchType(model.getFooterType());
		}
	}
}
