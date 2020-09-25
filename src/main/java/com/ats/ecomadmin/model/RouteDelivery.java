package com.ats.ecomadmin.model;

 
public class RouteDelivery {
	
	 
	private int rouidDelveryId;
	
	
	private String  deliveryName;
	
	private String  timeSlots ;
	
	private int companyId ;
 	
	private float sortNo;
 	
 	private int isCopy;
 	
 	
 	private int isActive;
	private int delStatus;
 
 	private int exInt1;
	private int exInt2;
	private int exInt3;
	private String exVar1;
	private String exVar2;
	private String exVar3;
	public int getRouidDelveryId() {
		return rouidDelveryId;
	}
	public void setRouidDelveryId(int rouidDelveryId) {
		this.rouidDelveryId = rouidDelveryId;
	}
	public String getDeliveryName() {
		return deliveryName;
	}
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}
	public String getTimeSlots() {
		return timeSlots;
	}
	public void setTimeSlots(String timeSlots) {
		this.timeSlots = timeSlots;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public float getSortNo() {
		return sortNo;
	}
	public void setSortNo(float sortNo) {
		this.sortNo = sortNo;
	}
	public int getIsCopy() {
		return isCopy;
	}
	public void setIsCopy(int isCopy) {
		this.isCopy = isCopy;
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
		return "RouteDelivery [rouidDelveryId=" + rouidDelveryId + ", deliveryName=" + deliveryName + ", timeSlots="
				+ timeSlots + ", companyId=" + companyId + ", sortNo=" + sortNo + ", isCopy=" + isCopy + ", isActive="
				+ isActive + ", delStatus=" + delStatus + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exInt3="
				+ exInt3 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exVar3=" + exVar3 + "]";
	}
	
	
	

}
