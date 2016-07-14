package com.quenice.optimize.listview.widget;

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
}
