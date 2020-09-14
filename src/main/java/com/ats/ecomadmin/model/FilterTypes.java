package com.ats.ecomadmin.model;

public class FilterTypes {
	
	private int filterTypeId;
	private String filterTypeName;
	private String filterTypeDesc;
	private int isActive;
	private int delStatus;
	private int companyId;
	private int isCostAffect;
	private int isUsedFilter;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;

	public int getFilterTypeId() {
		return filterTypeId;
	}

	public void setFilterTypeId(int filterTypeId) {
		this.filterTypeId = filterTypeId;
	}

	public String getFilterTypeName() {
		return filterTypeName;
	}

	public void setFilterTypeName(String filterTypeName) {
		this.filterTypeName = filterTypeName;
	}
	
	public String getFilterTypeDesc() {
		return filterTypeDesc;
	}

	public void setFilterTypeDesc(String filterTypeDesc) {
		this.filterTypeDesc = filterTypeDesc;
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

	public int getIsCostAffect() {
		return isCostAffect;
	}

	public void setIsCostAffect(int isCostAffect) {
		this.isCostAffect = isCostAffect;
	}

	public int getIsUsedFilter() {
		return isUsedFilter;
	}

	public void setIsUsedFilter(int isUsedFilter) {
		this.isUsedFilter = isUsedFilter;
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

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "FilterTypes [filterTypeId=" + filterTypeId + ", filterTypeName=" + filterTypeName + ", filterTypeDesc="
				+ filterTypeDesc + ", isActive=" + isActive + ", delStatus=" + delStatus + ", companyId=" + companyId
				+ ", isCostAffect=" + isCostAffect + ", isUsedFilter=" + isUsedFilter + ", exInt1=" + exInt1
				+ ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + "]";
	}

	
}
