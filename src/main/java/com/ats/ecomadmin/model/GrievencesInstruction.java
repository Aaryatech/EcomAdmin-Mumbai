package com.ats.ecomadmin.model;

public class GrievencesInstruction {
	
	private int grievanceId;
	private String caption;
	private String description;
	private int delStatus;
	private int grievenceTypeId;
	private int isActive;
	private int companyId;
	private int allowToCopy;
	private int isParent;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;
	private String exVar3;

	public int getGrievanceId() {
		return grievanceId;
	}

	public void setGrievanceId(int grievanceId) {
		this.grievanceId = grievanceId;
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

	public int getGrievenceTypeId() {
		return grievenceTypeId;
	}

	public void setGrievenceTypeId(int grievenceTypeId) {
		this.grievenceTypeId = grievenceTypeId;
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

	public String getExVar3() {
		return exVar3;
	}

	public void setExVar3(String exVar3) {
		this.exVar3 = exVar3;
	}

	public int getAllowToCopy() {
		return allowToCopy;
	}

	public void setAllowToCopy(int allowToCopy) {
		this.allowToCopy = allowToCopy;
	}

	public int getIsParent() {
		return isParent;
	}

	public void setIsParent(int isParent) {
		this.isParent = isParent;
	}

	@Override
	public String toString() {
		return "GrievencesInstruction [grievanceId=" + grievanceId + ", caption=" + caption + ", description="
				+ description + ", delStatus=" + delStatus + ", grievenceTypeId=" + grievenceTypeId + ", isActive="
				+ isActive + ", companyId=" + companyId + ", allowToCopy=" + allowToCopy + ", isParent=" + isParent
				+ ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2
				+ ", exVar3=" + exVar3 + "]";
	}

}
