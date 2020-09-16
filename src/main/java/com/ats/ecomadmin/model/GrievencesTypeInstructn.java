package com.ats.ecomadmin.model;

public class GrievencesTypeInstructn {
	
	private int grevTypeId;
	private String caption;
	private String description;
	private int delStatus;
	private int isActive;
	private int companyId;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;

	public int getGrevTypeId() {
		return grevTypeId;
	}

	public void setGrevTypeId(int grevTypeId) {
		this.grevTypeId = grevTypeId;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
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
		return "GrievencesTypeInstructn [grevTypeId=" + grevTypeId + ", caption=" + caption + ", description="
				+ description + ", delStatus=" + delStatus + ", isActive=" + isActive + ", companyId=" + companyId
				+ ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + "]";
	}

	
}
