package com.ats.ecomadmin.model;

 
public class GetRouteList {
 
	private int routeId;

	private String routeName;

	private String frName;

	private float routeKm;

	private float sortNo;

	private String routeCode;

	private String routeTypeName;
	
	
	private String  deliveryName;
	
	private String  exVar1;



	public String getExVar1() {
		return exVar1;
	}


	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}


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


	public String getFrName() {
		return frName;
	}


	public void setFrName(String frName) {
		this.frName = frName;
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


	public String getRouteTypeName() {
		return routeTypeName;
	}


	public void setRouteTypeName(String routeTypeName) {
		this.routeTypeName = routeTypeName;
	}


	public String getDeliveryName() {
		return deliveryName;
	}


	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}


	@Override
	public String toString() {
		return "GetRouteList [routeId=" + routeId + ", routeName=" + routeName + ", frName=" + frName + ", routeKm="
				+ routeKm + ", sortNo=" + sortNo + ", routeCode=" + routeCode + ", routeTypeName=" + routeTypeName
				+ ", deliveryName=" + deliveryName + ", exVar1=" + exVar1 + "]";
	}


	 
	
	

}
