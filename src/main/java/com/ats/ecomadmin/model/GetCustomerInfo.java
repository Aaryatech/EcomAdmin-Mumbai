package com.ats.ecomadmin.model;

import java.util.Date;

 
 public class GetCustomerInfo {
	
	

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

	 

	private int isActive;
	private int delStatus;
	
	private String exVar1;
	
	private String companyName;

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

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String toString() {
		return "GetCustomerInfo [custId=" + custId + ", custName=" + custName + ", custMobileNo=" + custMobileNo
				+ ", emailId=" + emailId + ", profilePic=" + profilePic + ", cityId=" + cityId + ", companyId="
				+ companyId + ", dateOfBirth=" + dateOfBirth + ", custGender=" + custGender + ", ageRange=" + ageRange
				+ ", languageId=" + languageId + ", isActive=" + isActive + ", delStatus=" + delStatus + ", exVar1="
				+ exVar1 + ", companyName=" + companyName + "]";
	}

 


}
