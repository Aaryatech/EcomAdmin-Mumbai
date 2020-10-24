package com.ats.ecomadmin.model;



//Harsha 15-09-2020 
 
public class CustomerAddDetail {

	private int custDetailId;

	private String caption;

	private String landmark;

	private String address;
	private int cityId;

	private int areaId;
	private String latitude;

	private String longitude;

	private int custId;

	private int isActive;
	private int delStatus;
	private int makerUserId;
	private String updtDttime;
	private String insertDttime;
	private int exInt1;
	private int exInt2;
	private int exInt3;
	private String exVar1;
	private String exVar2;
	private String exVar3;
	public int getCustDetailId() {
		return custDetailId;
	}
	public void setCustDetailId(int custDetailId) {
		this.custDetailId = custDetailId;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getLandmark() {
		return landmark;
	}
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	public int getMakerUserId() {
		return makerUserId;
	}
	public void setMakerUserId(int makerUserId) {
		this.makerUserId = makerUserId;
	}
	public String getUpdtDttime() {
		return updtDttime;
	}
	public void setUpdtDttime(String updtDttime) {
		this.updtDttime = updtDttime;
	}
	public String getInsertDttime() {
		return insertDttime;
	}
	public void setInsertDttime(String insertDttime) {
		this.insertDttime = insertDttime;
	}
	public int getExInt1() {
		return exInt1;
	}
	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}
	public int getExInt2() {
		return exInt2;
	}
	public void setExInt2(int exInt2) {
		this.exInt2 = exInt2;
	}
	public int getExInt3() {
		return exInt3;
	}
	public void setExInt3(int exInt3) {
		this.exInt3 = exInt3;
	}
	public String getExVar1() {
		return exVar1;
	}
	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}
	public String getExVar2() {
		return exVar2;
	}
	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}
	public String getExVar3() {
		return exVar3;
	}
	public void setExVar3(String exVar3) {
		this.exVar3 = exVar3;
	}
	@Override
	public String toString() {
		return "CustomerAddDetail [custDetailId=" + custDetailId + ", caption=" + caption + ", landmark=" + landmark
				+ ", address=" + address + ", cityId=" + cityId + ", areaId=" + areaId + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", custId=" + custId + ", isActive=" + isActive + ", delStatus="
				+ delStatus + ", makerUserId=" + makerUserId + ", updtDttime=" + updtDttime + ", insertDttime="
				+ insertDttime + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exInt3=" + exInt3 + ", exVar1="
				+ exVar1 + ", exVar2=" + exVar2 + ", exVar3=" + exVar3 + "]";
	}
	
	
	

}
