package com.quenice.optimize;

public class City {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String province; // 省
	private String city; // 城市名
	private String number; //
	private String firstPY; // 首字拼音首字母
	private String allPY; // 全拼音
	private String allFirstPY; // 拼音首字母
	private String cityno; //
	private String shortName; // 简称

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getFirstPY() {
		return firstPY;
	}

	public void setFirstPY(String firstPY) {
		this.firstPY = firstPY;
	}

	public String getAllPY() {
		return allPY;
	}

	public void setAllPY(String allPY) {
		this.allPY = allPY;
	}

	public String getAllFirstPY() {
		return allFirstPY;
	}

	public void setAllFirstPY(String allFirstPY) {
		this.allFirstPY = allFirstPY;
	}

	public String getCityno() {
		return cityno;
	}

	public void setCityno(String cityno) {
		this.cityno = cityno;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
}
