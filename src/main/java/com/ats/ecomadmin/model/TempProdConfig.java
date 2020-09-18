package com.ats.ecomadmin.model;

public class TempProdConfig {

	private String uuid;
	
	private int productId;
	private String productName;
	
	private String flavorName;
	private int flavorId;
	
	private float weight;
	
	private int vegType;
	private int catId;
	private String curTimeStamp;
	
	private int rateSetingType;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getFlavorName() {
		return flavorName;
	}

	public void setFlavorName(String flavorName) {
		this.flavorName = flavorName;
	}

	public int getFlavorId() {
		return flavorId;
	}

	public void setFlavorId(int flavorId) {
		this.flavorId = flavorId;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public int getVegType() {
		return vegType;
	}

	public void setVegType(int vegType) {
		this.vegType = vegType;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public String getCurTimeStamp() {
		return curTimeStamp;
	}

	public void setCurTimeStamp(String curTimeStamp) {
		this.curTimeStamp = curTimeStamp;
	}

	public int getRateSetingType() {
		return rateSetingType;
	}

	public void setRateSetingType(int rateSetingType) {
		this.rateSetingType = rateSetingType;
	}

	@Override
	public String toString() {
		return "TempProdConfig [uuid=" + uuid + ", productId=" + productId + ", productName=" + productName
				+ ", flavorName=" + flavorName + ", flavorId=" + flavorId + ", weight=" + weight + ", vegType="
				+ vegType + ", catId=" + catId + ", curTimeStamp=" + curTimeStamp + ", rateSetingType=" + rateSetingType
				+ "]";
	}
	
}
