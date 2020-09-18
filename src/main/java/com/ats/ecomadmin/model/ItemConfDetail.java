package com.ats.ecomadmin.model;


public class ItemConfDetail {

	private int configDetailId;
	
	private int configHeaderId;
	
	private int productId;
	private int flavorId;
	
	private int isVeg;
	private int rateSettingType;
	
	private float qty;
	private float rateAmt;
	private float mrpAmt;
	
	private float spRateAmt1;
	private float spRateAmt2;
	private float spRateAmt3;
	private float spRateAmt4;
	
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
	private String exVar4;
	
	private float exFloat1;
	private float exFloat2;
	private float exFloat3;
	
	private String exDate1;
	private String exDate2;
	
	
	
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
	public String getExVar4() {
		return exVar4;
	}
	public void setExVar4(String exVar4) {
		this.exVar4 = exVar4;
	}
	public float getExFloat1() {
		return exFloat1;
	}
	public void setExFloat1(float exFloat1) {
		this.exFloat1 = exFloat1;
	}
	public float getExFloat2() {
		return exFloat2;
	}
	public void setExFloat2(float exFloat2) {
		this.exFloat2 = exFloat2;
	}
	public float getExFloat3() {
		return exFloat3;
	}
	public void setExFloat3(float exFloat3) {
		this.exFloat3 = exFloat3;
	}
	public String getExDate1() {
		return exDate1;
	}
	public void setExDate1(String exDate1) {
		this.exDate1 = exDate1;
	}
	public String getExDate2() {
		return exDate2;
	}
	public void setExDate2(String exDate2) {
		this.exDate2 = exDate2;
	}
	
	@Override
	public String toString() {
		return "ItemConfDetail [configDetailId=" + configDetailId + ", configHeaderId=" + configHeaderId
				+ ", productId=" + productId + ", flavorId=" + flavorId + ", isVeg=" + isVeg + ", rateSettingType="
				+ rateSettingType + ", qty=" + qty + ", rateAmt=" + rateAmt + ", mrpAmt=" + mrpAmt + ", spRateAmt1="
				+ spRateAmt1 + ", spRateAmt2=" + spRateAmt2 + ", spRateAmt3=" + spRateAmt3 + ", spRateAmt4="
				+ spRateAmt4 + ", isActive=" + isActive + ", delStatus=" + delStatus + ", makerUserId=" + makerUserId
				+ ", updtDttime=" + updtDttime + ", insertDttime=" + insertDttime + ", exInt1=" + exInt1 + ", exInt2="
				+ exInt2 + ", exInt3=" + exInt3 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exVar3=" + exVar3
				+ ", exVar4=" + exVar4 + ", exFloat1=" + exFloat1 + ", exFloat2=" + exFloat2 + ", exFloat3=" + exFloat3
				+ ", exDate1=" + exDate1 + ", exDate2=" + exDate2 + "]";
	}
	
}
