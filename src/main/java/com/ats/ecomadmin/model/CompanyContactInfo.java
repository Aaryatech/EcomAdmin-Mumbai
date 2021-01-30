package com.ats.ecomadmin.model;

public class CompanyContactInfo {
	
	private int companyId;
	private String companyName;
	private String compAddress;
	private int compCity;
	private String compContactNo;
	private String compEmailAddress;
	private String compGstNo;
	private String compWebsite;
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompAddress() {
		return compAddress;
	}
	public void setCompAddress(String compAddress) {
		this.compAddress = compAddress;
	}
	public int getCompCity() {
		return compCity;
	}
	public void setCompCity(int compCity) {
		this.compCity = compCity;
	}
	public String getCompContactNo() {
		return compContactNo;
	}
	public void setCompContactNo(String compContactNo) {
		this.compContactNo = compContactNo;
	}
	@Override
	public String toString() {
		return "CompanyContactInfo [companyId=" + companyId + ", companyName=" + companyName + ", compAddress="
				+ compAddress + ", compCity=" + compCity + ", compContactNo=" + compContactNo + "]";
	}
	
}
