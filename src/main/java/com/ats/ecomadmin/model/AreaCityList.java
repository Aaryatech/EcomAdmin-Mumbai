package com.ats.ecomadmin.model;

public class AreaCityList {

	private int areaId;
	private String areaCode;
	private String areaName;
	private String pincode;
	private int isActive;
	private int cityId;
	private String cityName;
	private int exInt1;
	private String exVar1;
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public int getExInt1() {
		return exInt1;
	}
	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}
	public String getExVar1() {
		return exVar1;
	}
	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}
	@Override
	public String toString() {
		return "AreaCityList [areaId=" + areaId + ", areaCode=" + areaCode + ", areaName=" + areaName + ", pincode="
				+ pincode + ", isActive=" + isActive + ", cityId=" + cityId + ", cityName=" + cityName + ", exInt1="
				+ exInt1 + ", exVar1=" + exVar1 + "]";
	}
	
	
}
