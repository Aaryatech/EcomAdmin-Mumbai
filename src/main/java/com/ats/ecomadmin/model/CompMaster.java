package com.ats.ecomadmin.model;

import java.util.Date;

 

//Harsha 14-09-2020
 
public class CompMaster {
	 
	private int companyId;
	private String companyName;
	private int companyType;
	private int parentCompId;
	private String compAddress;
	private int compCity;
	private String compState;
	private String compContactNo;
	private String compEmailAddress;
	private String compGstNo;
	private String compStateGstCode;
	private String compWebsite;
	private String compBankName;
	private String compBankBranchName;
	private String compBankIfsc;
	private String compBankAccNo;
	private String compCinNo;
	private String compFdaNo;
	private String compFdaDeclarText;
	private String compPanNo;
	private String compOpeningDate;
	
	private String paymentGatewayLink ;
	
	
	private int  paymentGatewayApplicable;
	
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
	private String exVar4;
	private float exFloat1;
	private float exFloat2;
	private float exFloat3;
	private Date exDate1;
	private Date exDate2;
	
	
	private String companyLogo ;
	
	private String companyPrefix ;
	
	
	private int compGstType;
	
	
	public String getPaymentGatewayLink() {
		return paymentGatewayLink;
	}
	public void setPaymentGatewayLink(String paymentGatewayLink) {
		this.paymentGatewayLink = paymentGatewayLink;
	}
	public int getPaymentGatewayApplicable() {
		return paymentGatewayApplicable;
	}
	public void setPaymentGatewayApplicable(int paymentGatewayApplicable) {
		this.paymentGatewayApplicable = paymentGatewayApplicable;
	}
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
	public Integer getCompanyType() {
		return companyType;
	}
	public void setCompanyType(Integer companyType) {
		this.companyType = companyType;
	}
	public int getParentCompId() {
		return parentCompId;
	}
	public void setParentCompId(int parentCompId) {
		this.parentCompId = parentCompId;
	}
	public String getCompAddress() {
		return compAddress;
	}
	public void setCompAddress(String compAddress) {
		this.compAddress = compAddress;
	}
	public Integer getCompCity() {
		return compCity;
	}
	public void setCompCity(Integer compCity) {
		this.compCity = compCity;
	}
	public String getCompState() {
		return compState;
	}
	public void setCompState(String compState) {
		this.compState = compState;
	}
	public String getCompContactNo() {
		return compContactNo;
	}
	public void setCompContactNo(String compContactNo) {
		this.compContactNo = compContactNo;
	}
	public String getCompEmailAddress() {
		return compEmailAddress;
	}
	public void setCompEmailAddress(String compEmailAddress) {
		this.compEmailAddress = compEmailAddress;
	}
	public String getCompGstNo() {
		return compGstNo;
	}
	public void setCompGstNo(String compGstNo) {
		this.compGstNo = compGstNo;
	}
	public String getCompStateGstCode() {
		return compStateGstCode;
	}
	public void setCompStateGstCode(String compStateGstCode) {
		this.compStateGstCode = compStateGstCode;
	}
	public String getCompWebsite() {
		return compWebsite;
	}
	public void setCompWebsite(String compWebsite) {
		this.compWebsite = compWebsite;
	}
	public String getCompBankName() {
		return compBankName;
	}
	public void setCompBankName(String compBankName) {
		this.compBankName = compBankName;
	}
	public String getCompBankBranchName() {
		return compBankBranchName;
	}
	public void setCompBankBranchName(String compBankBranchName) {
		this.compBankBranchName = compBankBranchName;
	}
	public String getCompBankIfsc() {
		return compBankIfsc;
	}
	public void setCompBankIfsc(String compBankIfsc) {
		this.compBankIfsc = compBankIfsc;
	}
	public String getCompBankAccNo() {
		return compBankAccNo;
	}
	public void setCompBankAccNo(String compBankAccNo) {
		this.compBankAccNo = compBankAccNo;
	}
	public String getCompCinNo() {
		return compCinNo;
	}
	public void setCompCinNo(String compCinNo) {
		this.compCinNo = compCinNo;
	}
	public String getCompFdaNo() {
		return compFdaNo;
	}
	public void setCompFdaNo(String compFdaNo) {
		this.compFdaNo = compFdaNo;
	}
	public String getCompFdaDeclarText() {
		return compFdaDeclarText;
	}
	public void setCompFdaDeclarText(String compFdaDeclarText) {
		this.compFdaDeclarText = compFdaDeclarText;
	}
	public String getCompPanNo() {
		return compPanNo;
	}
	public void setCompPanNo(String compPanNo) {
		this.compPanNo = compPanNo;
	}
	public String getCompOpeningDate() {
		return compOpeningDate;
	}
	public void setCompOpeningDate(String compOpeningDate) {
		this.compOpeningDate = compOpeningDate;
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
	public String getExVar4() {
		return exVar4;
	}
	public void setExVar4(String exVar4) {
		this.exVar4 = exVar4;
	}
	public float getExFloat1() {
		return exFloat1;
	}
	public void setExFloat1(float exFloat1) {
		this.exFloat1 = exFloat1;
	}
	public float getExFloat2() {
		return exFloat2;
	}
	public void setExFloat2(float exFloat2) {
		this.exFloat2 = exFloat2;
	}
	public float getExFloat3() {
		return exFloat3;
	}
	public void setExFloat3(float exFloat3) {
		this.exFloat3 = exFloat3;
	}
	public Date getExDate1() {
		return exDate1;
	}
	public void setExDate1(Date exDate1) {
		this.exDate1 = exDate1;
	}
	public Date getExDate2() {
		return exDate2;
	}
	public void setExDate2(Date exDate2) {
		this.exDate2 = exDate2;
	}
	
	public String getCompanyLogo() {
		return companyLogo;
	}
	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}
	public String getCompanyPrefix() {
		return companyPrefix;
	}
	public void setCompanyPrefix(String companyPrefix) {
		this.companyPrefix = companyPrefix;
	}
	public int getCompGstType() {
		return compGstType;
	}
	public void setCompGstType(int compGstType) {
		this.compGstType = compGstType;
	}
	@Override
	public String toString() {
		return "CompMaster [companyId=" + companyId + ", companyName=" + companyName + ", companyType=" + companyType
				+ ", parentCompId=" + parentCompId + ", compAddress=" + compAddress + ", compCity=" + compCity
				+ ", compState=" + compState + ", compContactNo=" + compContactNo + ", compEmailAddress="
				+ compEmailAddress + ", compGstNo=" + compGstNo + ", compStateGstCode=" + compStateGstCode
				+ ", compWebsite=" + compWebsite + ", compBankName=" + compBankName + ", compBankBranchName="
				+ compBankBranchName + ", compBankIfsc=" + compBankIfsc + ", compBankAccNo=" + compBankAccNo
				+ ", compCinNo=" + compCinNo + ", compFdaNo=" + compFdaNo + ", compFdaDeclarText=" + compFdaDeclarText
				+ ", compPanNo=" + compPanNo + ", compOpeningDate=" + compOpeningDate + ", paymentGatewayLink="
				+ paymentGatewayLink + ", paymentGatewayApplicable=" + paymentGatewayApplicable + ", isActive="
				+ isActive + ", delStatus=" + delStatus + ", makerUserId=" + makerUserId + ", updtDttime=" + updtDttime
				+ ", insertDttime=" + insertDttime + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exInt3=" + exInt3
				+ ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exVar3=" + exVar3 + ", exVar4=" + exVar4
				+ ", exFloat1=" + exFloat1 + ", exFloat2=" + exFloat2 + ", exFloat3=" + exFloat3 + ", exDate1="
				+ exDate1 + ", exDate2=" + exDate2 + ", companyLogo=" + companyLogo + ", companyPrefix=" + companyPrefix
				+ ", compGstType=" + compGstType + "]";
	}
	 
	
}
