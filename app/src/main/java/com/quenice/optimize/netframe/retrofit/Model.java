package com.quenice.optimize.netframe.retrofit;

import java.util.List;

/**
 * Created by qiubb on 2016/10/28.
 */

public class Model {
	private String name;
	private String value;
	private List<Child> children;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<Child> getChildren() {
		return children;
	}

	public void setChildren(List<Child> children) {
		this.children = children;
	}

	public static class Child {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
