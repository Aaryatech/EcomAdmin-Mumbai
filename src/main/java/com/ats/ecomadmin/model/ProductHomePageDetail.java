//Mahendra
package com.ats.ecomadmin.model;

public class ProductHomePageDetail {
	private int hpStatusDetailId;
	private int homePageStatusId;
	private int productId;
	private int sortNo;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;

	public int getHpStatusDetailId() {
		return hpStatusDetailId;
	}

	public void setHpStatusDetailId(int hpStatusDetailId) {
		this.hpStatusDetailId = hpStatusDetailId;
	}

	public int getHomePageStatusId() {
		return homePageStatusId;
	}

	public void setHomePageStatusId(int homePageStatusId) {
		this.homePageStatusId = homePageStatusId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
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

	@Override
	public String toString() {
		return "ProductHomePageDetail [hpStatusDetailId=" + hpStatusDetailId + ", homePageStatusId=" + homePageStatusId
				+ ", productId=" + productId + ", sortNo=" + sortNo + ", exInt1=" + exInt1 + ", exInt2=" + exInt2
				+ ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + "]";
	}
	
	
}
