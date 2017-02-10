package com.quenice.optimize.common;

/**
 * raw response entity
 * @author qiubb
 *
 * @param <T>
 */
public class Resp<T> implements Protection {
	public final static String FIELD_NAME_CODE = "code";
	public final static String FIELD_NAME_MSG = "msg";
	public final static String FIELD_NAME_DATA = "data";
	private int code;
	private String msg;
	private T data;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
