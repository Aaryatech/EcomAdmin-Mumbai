package com.ats.ecomadmin.model;

import java.util.Date;

 
//Harsha 15-09-2020 
 
public class Customer {

	 
	private int custId;

	private String custName;

	private String custMobileNo;

	private String emailId;

	private String profilePic;

	private int cityId;

	private int companyId;

	private String dateOfBirth;

	private int custGender;

	private int ageRange;

	private int languageId;

	private int custAddPlatform;

	private int isPrimiunmCust;

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
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustMobileNo() {
		return custMobileNo;
	}
	public void setCustMobileNo(String custMobileNo) {
		this.custMobileNo = custMobileNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public int getCustGender() {
		return custGender;
	}
	public void setCustGender(int custGender) {
		this.custGender = custGender;
	}
	public int getAgeRange() {
		return ageRange;
	}
	public void setAgeRange(int ageRange) {
		this.ageRange = ageRange;
	}
	public int getLanguageId() {
		return languageId;
	}
	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}
	public int getCustAddPlatform() {
		return custAddPlatform;
	}
	public void setCustAddPlatform(int custAddPlatform) {
		this.custAddPlatform = custAddPlatform;
	}
	public int getIsPrimiunmCust() {
		return isPrimiunmCust;
	}
	public void setIsPrimiunmCust(int isPrimiunmCust) {
		this.isPrimiunmCust = isPrimiunmCust;
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
		return "Customer [custId=" + custId + ", custName=" + custName + ", custMobileNo=" + custMobileNo + ", emailId="
				+ emailId + ", profilePic=" + profilePic + ", cityId=" + cityId + ", companyId=" + companyId
				+ ", dateOfBirth=" + dateOfBirth + ", custGender=" + custGender + ", ageRange=" + ageRange
				+ ", languageId=" + languageId + ", custAddPlatform=" + custAddPlatform + ", isPrimiunmCust="
				+ isPrimiunmCust + ", isActive=" + isActive + ", delStatus=" + delStatus + ", makerUserId="
				+ makerUserId + ", updtDttime=" + updtDttime + ", insertDttime=" + insertDttime + ", exInt1=" + exInt1
				+ ", exInt2=" + exInt2 + ", exInt3=" + exInt3 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2
				+ ", exVar3=" + exVar3 + "]";
	}
	
	
	
}
