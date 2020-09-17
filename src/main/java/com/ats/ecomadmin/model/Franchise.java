package com.ats.ecomadmin.model;

public class Franchise {

	
	//Franchise Personal Details//////////////////////////////	
	private int frId;
	private String frCode;
	private String frName;
	private String frAddress;
	private String openingDate;
	private String frImage;
	private float frRating;
	private int frCity;
	private String frEmailId;
	private String frContactNo;	
	
	/****Franchise Other Details*****************************************/
	private String frPassword;
	private String fdaNumber;
	private String gstType;
	private String gstNumber;
	private String pincode;
	private String ownersBirthDay;
	private String fdaLicenseDateExp;
	private float shopsLatitude;
	private float shopsLogitude;
	private int isActive;
		
	/****Franchise Bank Details***************************************/
	private int delStatus;
	private int companyId;
	private String panNo;
	private String city;
	private String state;
	private String coBankName;
	private String coBankBranchName;
	private String coBankIfscCode;
	private String coBankAccNo;	
	private String paymentGetwayLinkSameAsParent;
	private String paymentGetwayLink;
	
	/******************************************/
	private float noOfKmAreaCover;
	private int userId;
	private String addDateTime;
	private String editDateTime;
	private String pincodeWeServed;
	private float exFloat1;
	private float exFloat2;
	private float exFloat3;
	private float exFloat4;
	private float exFloat5;
	private String exVar1;
	private String exVar2;
	private String exVar3;
	private String exVar4;
	private String exVar5;
	private String exVar6;
	private String exVar7;
	private String exDate1;
	private String exDate2;
	private int exInt1;
	private int exInt2;	
	private int exInt3;

	public int getFrId() {
		return frId;
	}

	public void setFrId(int frId) {
		this.frId = frId;
	}

	public String getFrCode() {
		return frCode;
	}

	public void setFrCode(String frCode) {
		this.frCode = frCode;
	}

	public String getFrName() {
		return frName;
	}

	public void setFrName(String frName) {
		this.frName = frName;
	}

	public String getFrAddress() {
		return frAddress;
	}

	public void setFrAddress(String frAddress) {
		this.frAddress = frAddress;
	} 
	
	public String getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(String openingDate) {
		this.openingDate = openingDate;
	}

	public String getFrImage() {
		return frImage;
	}

	public void setFrImage(String frImage) {
		this.frImage = frImage;
	}

	public float getFrRating() {
		return frRating;
	}

	public void setFrRating(float frRating) {
		this.frRating = frRating;
	}

	public int getFrCity() {
		return frCity;
	}

	public void setFrCity(int frCity) {
		this.frCity = frCity;
	}

	public String getFrEmailId() {
		return frEmailId;
	}

	public void setFrEmailId(String frEmailId) {
		this.frEmailId = frEmailId;
	}

	public String getFrContactNo() {
		return frContactNo;
	}

	public void setFrContactNo(String frContactNo) {
		this.frContactNo = frContactNo;
	}

	public String getFrPassword() {
		return frPassword;
	}

	public void setFrPassword(String frPassword) {
		this.frPassword = frPassword;
	}

	public String getFdaNumber() {
		return fdaNumber;
	}

	public void setFdaNumber(String fdaNumber) {
		this.fdaNumber = fdaNumber;
	}

	public String getGstType() {
		return gstType;
	}

	public void setGstType(String gstType) {
		this.gstType = gstType;
	}

	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getOwnersBirthDay() {
		return ownersBirthDay;
	}

	public void setOwnersBirthDay(String ownersBirthDay) {
		this.ownersBirthDay = ownersBirthDay;
	}

	public String getFdaLicenseDateExp() {
		return fdaLicenseDateExp;
	}

	public void setFdaLicenseDateExp(String fdaLicenseDateExp) {
		this.fdaLicenseDateExp = fdaLicenseDateExp;
	}

	public float getShopsLatitude() {
		return shopsLatitude;
	}

	public void setShopsLatitude(float shopsLatitude) {
		this.shopsLatitude = shopsLatitude;
	}

	public float getShopsLogitude() {
		return shopsLogitude;
	}

	public void setShopsLogitude(float shopsLogitude) {
		this.shopsLogitude = shopsLogitude;
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

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCoBankName() {
		return coBankName;
	}

	public void setCoBankName(String coBankName) {
		this.coBankName = coBankName;
	}

	public String getCoBankBranchName() {
		return coBankBranchName;
	}

	public void setCoBankBranchName(String coBankBranchName) {
		this.coBankBranchName = coBankBranchName;
	}

	public String getCoBankIfscCode() {
		return coBankIfscCode;
	}

	public void setCoBankIfscCode(String coBankIfscCode) {
		this.coBankIfscCode = coBankIfscCode;
	}

	public String getCoBankAccNo() {
		return coBankAccNo;
	}

	public void setCoBankAccNo(String coBankAccNo) {
		this.coBankAccNo = coBankAccNo;
	}

	public String getPaymentGetwayLinkSameAsParent() {
		return paymentGetwayLinkSameAsParent;
	}

	public void setPaymentGetwayLinkSameAsParent(String paymentGetwayLinkSameAsParent) {
		this.paymentGetwayLinkSameAsParent = paymentGetwayLinkSameAsParent;
	}

	public String getPaymentGetwayLink() {
		return paymentGetwayLink;
	}

	public void setPaymentGetwayLink(String paymentGetwayLink) {
		this.paymentGetwayLink = paymentGetwayLink;
	}

	public float getNoOfKmAreaCover() {
		return noOfKmAreaCover;
	}

	public void setNoOfKmAreaCover(float noOfKmAreaCover) {
		this.noOfKmAreaCover = noOfKmAreaCover;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAddDateTime() {
		return addDateTime;
	}

	public void setAddDateTime(String addDateTime) {
		this.addDateTime = addDateTime;
	}

	public String getEditDateTime() {
		return editDateTime;
	}

	public void setEditDateTime(String editDateTime) {
		this.editDateTime = editDateTime;
	}

	public String getPincodeWeServed() {
		return pincodeWeServed;
	}

	public void setPincodeWeServed(String pincodeWeServed) {
		this.pincodeWeServed = pincodeWeServed;
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

	public float getExFloat4() {
		return exFloat4;
	}

	public void setExFloat4(float exFloat4) {
		this.exFloat4 = exFloat4;
	}

	public float getExFloat5() {
		return exFloat5;
	}

	public void setExFloat5(float exFloat5) {
		this.exFloat5 = exFloat5;
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

	public String getExVar5() {
		return exVar5;
	}

	public void setExVar5(String exVar5) {
		this.exVar5 = exVar5;
	}

	public String getExVar6() {
		return exVar6;
	}

	public void setExVar6(String exVar6) {
		this.exVar6 = exVar6;
	}

	public String getExVar7() {
		return exVar7;
	}

	public void setExVar7(String exVar7) {
		this.exVar7 = exVar7;
	}

	public String getExDate1() {
		return exDate1;
	}

	public void setExDate1(String exDate1) {
		this.exDate1 = exDate1;
	}

	public String getExDate2() {
		return exDate2;
	}

	public void setExDate2(String exDate2) {
		this.exDate2 = exDate2;
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

	@Override
	public String toString() {
		return "Franchise [frId=" + frId + ", frCode=" + frCode + ", frName=" + frName + ", frAddress=" + frAddress
				+ ", openingDate=" + openingDate + ", frImage=" + frImage + ", frRating=" + frRating + ", frCity="
				+ frCity + ", frEmailId=" + frEmailId + ", frContactNo=" + frContactNo + ", frPassword=" + frPassword
				+ ", fdaNumber=" + fdaNumber + ", gstType=" + gstType + ", gstNumber=" + gstNumber + ", pincode="
				+ pincode + ", ownersBirthDay=" + ownersBirthDay + ", fdaLicenseDateExp=" + fdaLicenseDateExp
				+ ", shopsLatitude=" + shopsLatitude + ", shopsLogitude=" + shopsLogitude + ", isActive=" + isActive
				+ ", delStatus=" + delStatus + ", companyId=" + companyId + ", panNo=" + panNo + ", city=" + city
				+ ", state=" + state + ", coBankName=" + coBankName + ", coBankBranchName=" + coBankBranchName
				+ ", coBankIfscCode=" + coBankIfscCode + ", coBankAccNo=" + coBankAccNo
				+ ", paymentGetwayLinkSameAsParent=" + paymentGetwayLinkSameAsParent + ", paymentGetwayLink="
				+ paymentGetwayLink + ", noOfKmAreaCover=" + noOfKmAreaCover + ", userId=" + userId + ", addDateTime="
				+ addDateTime + ", editDateTime=" + editDateTime + ", pincodeWeServed=" + pincodeWeServed
				+ ", exFloat1=" + exFloat1 + ", exFloat2=" + exFloat2 + ", exFloat3=" + exFloat3 + ", exFloat4="
				+ exFloat4 + ", exFloat5=" + exFloat5 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exVar3="
				+ exVar3 + ", exVar4=" + exVar4 + ", exVar5=" + exVar5 + ", exVar6=" + exVar6 + ", exVar7=" + exVar7
				+ ", exDate1=" + exDate1 + ", exDate2=" + exDate2 + ", exInt1=" + exInt1 + ", exInt2=" + exInt2
				+ ", exInt3=" + exInt3 + "]";
	}
	
	
	
}
