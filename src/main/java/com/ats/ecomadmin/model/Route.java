package com.ats.ecomadmin.model;

 
public class Route {
	
	 
	private int routeId;
 	
	private String routeName ;
 	 
 	private int isPrimeRoute ;
 	
 	private int isDeliveryNo ;
 	
 	private String  frIds;
 	
 	private float routeKm;
 	
 	private float sortNo ;
 	
 	private String  routeCode ;
 	
 	
 	private int typeOfRoute ;
 	
 	
 	private int companyId ;
 	
	private int isActive;
	private int delStatus;
 
 	private int exInt1;
	private int exInt2;
	private int exInt3;
	private String exVar1;
	private String exVar2;
	private String exVar3;
	public int getRouteId() {
		return routeId;
	}
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public int getIsPrimeRoute() {
		return isPrimeRoute;
	}
	public void setIsPrimeRoute(int isPrimeRoute) {
		this.isPrimeRoute = isPrimeRoute;
	}
	public int getIsDeliveryNo() {
		return isDeliveryNo;
	}
	public void setIsDeliveryNo(int isDeliveryNo) {
		this.isDeliveryNo = isDeliveryNo;
	}
	public String getFrIds() {
		return frIds;
	}
	public void setFrIds(String frIds) {
		this.frIds = frIds;
	}
	public float getRouteKm() {
		return routeKm;
	}
	public void setRouteKm(float routeKm) {
		this.routeKm = routeKm;
	}
	public float getSortNo() {
		return sortNo;
	}
	public void setSortNo(float sortNo) {
		this.sortNo = sortNo;
	}
	public String getRouteCode() {
		return routeCode;
	}
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	public int getTypeOfRoute() {
		return typeOfRoute;
	}
	public void setTypeOfRoute(int typeOfRoute) {
		this.typeOfRoute = typeOfRoute;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
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
		return "Route [routeId=" + routeId + ", routeName=" + routeName + ", isPrimeRoute=" + isPrimeRoute
				+ ", isDeliveryNo=" + isDeliveryNo + ", frIds=" + frIds + ", routeKm=" + routeKm + ", sortNo=" + sortNo
				+ ", routeCode=" + routeCode + ", typeOfRoute=" + typeOfRoute + ", companyId=" + companyId
				+ ", isActive=" + isActive + ", delStatus=" + delStatus + ", exInt1=" + exInt1 + ", exInt2=" + exInt2
				+ ", exInt3=" + exInt3 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exVar3=" + exVar3 + "]";
	}
	
	
	

}
