package com.quenice.optimize.refreshloadview.widget;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.ArrayList;
import java.util.List;

/**
 * 刷新/加载控件辅助类
 * Created by qiubb on 2016/7/1.
 */
public class RefreshLoadHelper {
	/**
	 * 包裹数据
	 *
	 * @param list
	 * @param <DATA>
	 * @return
	 */
	public static <DATA> List<RLDataModel<DATA>> wrapData(List<DATA> list) {
		List<RLDataModel<DATA>> finalList = new ArrayList<>();
		if (list == null || list.size() == 0) return finalList;
		for (DATA d : list) {
			RLDataModel<DATA> m = new RLDataModel<>();
			m.setViewType(RLDataModel.VIEWTYPE_NORMAL);
			m.setData(d);
			finalList.add(m);
		}
		return finalList;
	}

	/**
	 * 处理emptyview
	 *
	 * @param emptyView
	 * @param refreshLoadView
	 * @param itemView
	 */
	public static void addEmptyView(View emptyView, RefreshLoadView refreshLoadView, View itemView) {
		if (emptyView == null || refreshLoadView == null || itemView == null) return;
		//从原来的view tree中解除
		ViewParent parent = emptyView.getParent();
		if (parent != null && parent instanceof ViewGroup) {
			((ViewGroup) parent).removeView(emptyView);
		}

		ViewGroup.LayoutParams lp = emptyView.getLayoutParams();
		int width = refreshLoadView.getWidth() - refreshLoadView.getPaddingRight() - refreshLoadView.getPaddingLeft();
		int height = refreshLoadView.getHeight() - refreshLoadView.getPaddingTop() + refreshLoadView.getPaddingBottom();
		if (lp == null) {
			lp = new ViewGroup.LayoutParams(width, height);
		} else {
			lp.height = height;
			lp.width = width;
		}
		emptyView.setLayoutParams(lp);

		//加入现有view tree
		((ViewGroup) itemView).addView(emptyView);
	}
}
