package com.ats.ecomadmin.model;
 
 
public class RelatedProductConfig {
 
	private int relatedProductId;

	private int primaryItemId;

	private String secondaryItemId;

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
	public int getRelatedProductId() {
		return relatedProductId;
	}
	public void setRelatedProductId(int relatedProductId) {
		this.relatedProductId = relatedProductId;
	}
	public int getPrimaryItemId() {
		return primaryItemId;
	}
	public void setPrimaryItemId(int primaryItemId) {
		this.primaryItemId = primaryItemId;
	}
	public String getSecondaryItemId() {
		return secondaryItemId;
	}
	public void setSecondaryItemId(String secondaryItemId) {
		this.secondaryItemId = secondaryItemId;
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
		return "RelatedProductConfig [relatedProductId=" + relatedProductId + ", primaryItemId=" + primaryItemId
				+ ", secondaryItemId=" + secondaryItemId + ", isActive=" + isActive + ", delStatus=" + delStatus
				+ ", makerUserId=" + makerUserId + ", updtDttime=" + updtDttime + ", insertDttime=" + insertDttime
				+ ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exInt3=" + exInt3 + ", exVar1=" + exVar1
				+ ", exVar2=" + exVar2 + ", exVar3=" + exVar3 + "]";
	}
	
	
	

}
