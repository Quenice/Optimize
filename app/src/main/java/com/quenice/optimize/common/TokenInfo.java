package com.quenice.optimize.common;

public class TokenInfo implements Protection, Cloneable {
	private int type;
	private String userAccount;
	private String loginData;

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getLoginData() {
		return loginData;
	}

	public void setLoginData(String loginData) {
		this.loginData = loginData;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public TokenInfo clone() {
		try {
			return (TokenInfo) super.clone();
		} catch (Exception e) {
			e.printStackTrace();
		}
		TokenInfo cloneObj = new TokenInfo();
		cloneObj.setLoginData(loginData);
		cloneObj.setType(type);
		cloneObj.setUserAccount(userAccount);
		return cloneObj;
	}
}
