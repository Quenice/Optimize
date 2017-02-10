package com.quenice.optimize.common;

/**
 * raw request entity
 *
 * @author qiubb
 */
public class Req<E> implements Protection {
	public Req() {
	}

	public Req(E data) {
		this.data = data;
	}

	private TokenInfo tokenInfo;
	private E data;

	public TokenInfo getTokenInfo() {
		return tokenInfo;
	}

	public void setTokenInfo(TokenInfo tokenInfo) {
		this.tokenInfo = tokenInfo == null ? null : tokenInfo.clone();
	}

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}
}
