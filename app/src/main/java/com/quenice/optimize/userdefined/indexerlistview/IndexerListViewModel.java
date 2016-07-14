package com.quenice.optimize.userdefined.indexerlistview;

/**
 * Created by qiubb on 2016/5/25.
 */
public class IndexerListViewModel {
	public final static int DEFAULT_VIEWTYPE = 0;
	private int viewType = DEFAULT_VIEWTYPE;
	private boolean isSection;
	private String section;
	private Object data;

	public IndexerListViewModel() {
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public boolean isSection() {
		return isSection;
	}

	public void setSection(boolean section) {
		isSection = section;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}
}
