package com.ats.ecomadmin.model;

public class MFilter {
	private int filterId;
	private String filterName;
	private String filterDesc;
	private int filterTypeId;
	private int usedForFilter;
	private int costAffect;
	private int usedForDescription;
	private int companyId;
	private int isParent;
	private int allowToCopy;
	private int sortNo;
	private int isActive;
	private int delStatus;
	private int exInt1;
	private int exInt2;
	private int exInt3;
	private String exVar1;
	private String exVar2;
	private String exVar3;

	public int getFilterId() {
		return filterId;
	}

	public void setFilterId(int filterId) {
		this.filterId = filterId;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public String getFilterDesc() {
		return filterDesc;
	}

	public void setFilterDesc(String filterDesc) {
		this.filterDesc = filterDesc;
	}

	public int getFilterTypeId() {
		return filterTypeId;
	}

	public void setFilterTypeId(int filterTypeId) {
		this.filterTypeId = filterTypeId;
	}

	public int getUsedForFilter() {
		return usedForFilter;
	}

	public void setUsedForFilter(int usedForFilter) {
		this.usedForFilter = usedForFilter;
	}

	public int getCostAffect() {
		return costAffect;
	}

	public void setCostAffect(int costAffect) {
		this.costAffect = costAffect;
	}

	public int getUsedForDescription() {
		return usedForDescription;
	}

	public void setUsedForDescription(int usedForDescription) {
		this.usedForDescription = usedForDescription;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getIsParent() {
		return isParent;
	}

	public void setIsParent(int isParent) {
		this.isParent = isParent;
	}

	public int getAllowToCopy() {
		return allowToCopy;
	}

	public void setAllowToCopy(int allowToCopy) {
		this.allowToCopy = allowToCopy;
	}

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
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
		return "MFilter [filterId=" + filterId + ", filterName=" + filterName + ", filterDesc=" + filterDesc
				+ ", filterTypeId=" + filterTypeId + ", usedForFilter=" + usedForFilter + ", costAffect=" + costAffect
				+ ", usedForDescription=" + usedForDescription + ", companyId=" + companyId + ", isParent=" + isParent
				+ ", allowToCopy=" + allowToCopy + ", sortNo=" + sortNo + ", isActive=" + isActive + ", delStatus="
				+ delStatus + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exInt3=" + exInt3 + ", exVar1=" + exVar1
				+ ", exVar2=" + exVar2 + ", exVar3=" + exVar3 + "]";
	}

}
