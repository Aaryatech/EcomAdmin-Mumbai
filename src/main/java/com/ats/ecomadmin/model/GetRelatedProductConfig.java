package com.ats.ecomadmin.model;

 
public class GetRelatedProductConfig {

	 
	private int relatedProductId;
	
	
	private int primaryItemId;
	
	private String secondaryItemId;
	
	private int isActive;
	
	private String exVar1;
	
	private String primaryItem;
	
	private String prodList;
	private String exVar2;
 

	public String getExVar2() {
		return exVar2;
	}

	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
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

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public String getPrimaryItem() {
		return primaryItem;
	}

	public void setPrimaryItem(String primaryItem) {
		this.primaryItem = primaryItem;
	}

	public String getProdList() {
		return prodList;
	}

	public void setProdList(String prodList) {
		this.prodList = prodList;
	}

	public int getRelatedProductId() {
		return relatedProductId;
	}

	public void setRelatedProductId(int relatedProductId) {
		this.relatedProductId = relatedProductId;
	}

	@Override
	public String toString() {
		return "GetRelatedProductConfig [relatedProductId=" + relatedProductId + ", primaryItemId=" + primaryItemId
				+ ", secondaryItemId=" + secondaryItemId + ", isActive=" + isActive + ", exVar1=" + exVar1
				+ ", primaryItem=" + primaryItem + ", prodList=" + prodList + "]";
	}
 
	
	
	
}
