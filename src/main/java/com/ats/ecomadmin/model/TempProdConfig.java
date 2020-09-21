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

	//Sachin 21-09-2020
	private int configDetailId;
	private int configHeaderId;
	
	private float rateAmt;
	private float mrpAmt;
	
	private float spRateAmt1;
	private float spRateAmt2;
	private float spRateAmt3;
	private float spRateAmt4;
	
	public int getConfigDetailId() {
		return configDetailId;
	}

	public void setConfigDetailId(int configDetailId) {
		this.configDetailId = configDetailId;
	}

	public int getConfigHeaderId() {
		return configHeaderId;
	}

	public void setConfigHeaderId(int configHeaderId) {
		this.configHeaderId = configHeaderId;
	}

	public float getRateAmt() {
		return rateAmt;
	}

	public void setRateAmt(float rateAmt) {
		this.rateAmt = rateAmt;
	}

	public float getMrpAmt() {
		return mrpAmt;
	}

	public void setMrpAmt(float mrpAmt) {
		this.mrpAmt = mrpAmt;
	}

	public float getSpRateAmt1() {
		return spRateAmt1;
	}

	public void setSpRateAmt1(float spRateAmt1) {
		this.spRateAmt1 = spRateAmt1;
	}

	public float getSpRateAmt2() {
		return spRateAmt2;
	}

	public void setSpRateAmt2(float spRateAmt2) {
		this.spRateAmt2 = spRateAmt2;
	}

	public float getSpRateAmt3() {
		return spRateAmt3;
	}

	public void setSpRateAmt3(float spRateAmt3) {
		this.spRateAmt3 = spRateAmt3;
	}

	public float getSpRateAmt4() {
		return spRateAmt4;
	}

	public void setSpRateAmt4(float spRateAmt4) {
		this.spRateAmt4 = spRateAmt4;
	}

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
				+ ", configDetailId=" + configDetailId + ", configHeaderId=" + configHeaderId + ", rateAmt=" + rateAmt
				+ ", mrpAmt=" + mrpAmt + ", spRateAmt1=" + spRateAmt1 + ", spRateAmt2=" + spRateAmt2 + ", spRateAmt3="
				+ spRateAmt3 + ", spRateAmt4=" + spRateAmt4 + "]";
	}
	
}
