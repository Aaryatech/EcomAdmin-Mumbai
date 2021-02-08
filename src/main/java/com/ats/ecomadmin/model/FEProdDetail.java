package com.ats.ecomadmin.model;

public class FEProdDetail {

	private String confDetailUuid;
	
	private int configDetailId;
	private int productId;
	private int flavorId;
	
	private int isVeg;
	
	private int rateSettingType;
	
	private float qty; //ie Weight
	
	private float displayRate;
	private float actualRate;
	
	private int shapeId;  //Sachin 05-11-2020
	
	
	private String vegNonvegName; // Sachin 06-11-2020

	public String getVegNonvegName() {
		return vegNonvegName;
	}

	public void setVegNonvegName(String vegNonvegName) {
		this.vegNonvegName = vegNonvegName;
	}
	public int getShapeId() {
		return shapeId;
	}
	public void setShapeId(int shapeId) {
		this.shapeId = shapeId;
	}
	
	
	public String getConfDetailUuid() {
		return confDetailUuid;
	}
	public void setConfDetailUuid(String confDetailUuid) {
		this.confDetailUuid = confDetailUuid;
	}

	
	
	public int getConfigDetailId() {
		return configDetailId;
	}
	public void setConfigDetailId(int configDetailId) {
		this.configDetailId = configDetailId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getFlavorId() {
		return flavorId;
	}
	public void setFlavorId(int flavorId) {
		this.flavorId = flavorId;
	}
	public int getIsVeg() {
		return isVeg;
	}
	public void setIsVeg(int isVeg) {
		this.isVeg = isVeg;
	}
	public int getRateSettingType() {
		return rateSettingType;
	}
	public void setRateSettingType(int rateSettingType) {
		this.rateSettingType = rateSettingType;
	}
	public float getQty() {
		return qty;
	}
	public void setQty(float qty) {
		this.qty = qty;
	}
	public float getDisplayRate() {
		return displayRate;
	}
	public void setDisplayRate(float displayRate) {
		this.displayRate = displayRate;
	}
	public float getActualRate() {
		return actualRate;
	}
	public void setActualRate(float actualRate) {
		this.actualRate = actualRate;
	}
	
	@Override
	public String toString() {
		return "FEProdDetail [confDetailUuid=" + confDetailUuid + ", configDetailId=" + configDetailId + ", productId="
				+ productId + ", flavorId=" + flavorId + ", isVeg=" + isVeg + ", rateSettingType=" + rateSettingType
				+ ", qty=" + qty + ", displayRate=" + displayRate + ", actualRate=" + actualRate + ", shapeId="
				+ shapeId + ", vegNonvegName=" + vegNonvegName + "]";
	}
	
}
