package com.ats.ecomadmin.model;
 
public class RouteType {

	 
	private int routeTypeId;

	private String routeTypeName;

	private String routeRangeInKm;
	private int companyId;

	private int isCopy;
 	private float sortNo ;

	private int isActive;
	private int delStatus;

	private int exInt1;
	private int exInt2;
	private int exInt3;
	private String exVar1;
	private String exVar2;
	private String exVar3;
	public int getRouteTypeId() {
		return routeTypeId;
	}
	public void setRouteTypeId(int routeTypeId) {
		this.routeTypeId = routeTypeId;
	}
	public String getRouteTypeName() {
		return routeTypeName;
	}
	public void setRouteTypeName(String routeTypeName) {
		this.routeTypeName = routeTypeName;
	}
	public String getRouteRangeInKm() {
		return routeRangeInKm;
	}
	public void setRouteRangeInKm(String routeRangeInKm) {
		this.routeRangeInKm = routeRangeInKm;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getIsCopy() {
		return isCopy;
	}
	public void setIsCopy(int isCopy) {
		this.isCopy = isCopy;
	}
	public float getSortNo() {
		return sortNo;
	}
	public void setSortNo(float sortNo) {
		this.sortNo = sortNo;
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
		return "RouteType [routeTypeId=" + routeTypeId + ", routeTypeName=" + routeTypeName + ", routeRangeInKm="
				+ routeRangeInKm + ", companyId=" + companyId + ", isCopy=" + isCopy + ", sortNo=" + sortNo
				+ ", isActive=" + isActive + ", delStatus=" + delStatus + ", exInt1=" + exInt1 + ", exInt2=" + exInt2
				+ ", exInt3=" + exInt3 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exVar3=" + exVar3 + "]";
	}
	
	
	

}
