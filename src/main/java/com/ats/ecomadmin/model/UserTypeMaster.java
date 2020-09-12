package com.ats.ecomadmin.model;

public class UserTypeMaster {
	
	private int userTypeId;
	private String userTypeName;
	private String userTypeDesc;
	
	
	private int isActive;
	private int delStatus;
	
	private int companyId;
	private int isParentCompany;  //Parent_company
	private int allowToCopy; //allow_to_copy_child_company

	private int makerUserId;
	private String updtDttime;
	private String insertDttime;

	public int getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(int userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public String getUserTypeDesc() {
		return userTypeDesc;
	}

	public void setUserTypeDesc(String userTypeDesc) {
		this.userTypeDesc = userTypeDesc;
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

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getIsParentCompany() {
		return isParentCompany;
	}

	public void setIsParentCompany(int isParentCompany) {
		this.isParentCompany = isParentCompany;
	}

	public int getAllowToCopy() {
		return allowToCopy;
	}

	public void setAllowToCopy(int allowToCopy) {
		this.allowToCopy = allowToCopy;
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

	@Override
	public String toString() {
		return "UserTypeMaster [userTypeId=" + userTypeId + ", userTypeName=" + userTypeName + ", userTypeDesc="
				+ userTypeDesc + ", isActive=" + isActive + ", delStatus=" + delStatus + ", companyId=" + companyId
				+ ", isParentCompany=" + isParentCompany + ", allowToCopy=" + allowToCopy + ", makerUserId="
				+ makerUserId + ", updtDttime=" + updtDttime + ", insertDttime=" + insertDttime + "]";
	}
	
}
