//Mahendra
package com.ats.ecomadmin.model;

import java.util.List;

public class ProductHomPage {
	
	private int homePageStatusId;
	private int companyId;
	private int statusId;
	private int productId;
	private int isActive;
	private int sortNo;
	private int delStatus;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;

	
	List<ProductHomePageDetail> prdctHomeList;
	
	public int getHomePageStatusId() {
		return homePageStatusId;
	}

	public void setHomePageStatusId(int homePageStatusId) {
		this.homePageStatusId = homePageStatusId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
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
	
	public List<ProductHomePageDetail> getPrdctHomeList() {
		return prdctHomeList;
	}

	public void setPrdctHomeList(List<ProductHomePageDetail> prdctHomeList) {
		this.prdctHomeList = prdctHomeList;
	}

	@Override
	public String toString() {
		return "ProductHomPage [homePageStatusId=" + homePageStatusId + ", companyId=" + companyId + ", statusId="
				+ statusId + ", productId=" + productId + ", isActive=" + isActive + ", sortNo=" + sortNo
				+ ", delStatus=" + delStatus + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1
				+ ", exVar2=" + exVar2 + ", prdctHomeList=" + prdctHomeList + "]";
	}

	
}
