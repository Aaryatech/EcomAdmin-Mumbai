package com.ats.ecomadmin.model;

import java.util.Date;

public class FrEmpMaster {
	
	private int frEmpId;
	private String frEmpName;
	private String frEmpContact;
	private String frEmpAddress;
	private String frEmpJoiningDate;
	private int frId;
	private int delStatus;
	private int isActive;
	private float totalLimit;
	private String fromDate;
	private String toDate;
	private float currentBillAmt;
	private String updateDatetime;
	private String password;
	private String empCode;
	private int designation;
	private int exInt1;
	private int exInt2;
	private int exInt3;
	private String exVar1;
	private String exVar2;
	private String exVar3;
	public int getFrEmpId() {
		return frEmpId;
	}
	public void setFrEmpId(int frEmpId) {
		this.frEmpId = frEmpId;
	}
	public String getFrEmpName() {
		return frEmpName;
	}
	public void setFrEmpName(String frEmpName) {
		this.frEmpName = frEmpName;
	}
	public String getFrEmpContact() {
		return frEmpContact;
	}
	public void setFrEmpContact(String frEmpContact) {
		this.frEmpContact = frEmpContact;
	}
	public String getFrEmpAddress() {
		return frEmpAddress;
	}
	public void setFrEmpAddress(String frEmpAddress) {
		this.frEmpAddress = frEmpAddress;
	}
	public String getFrEmpJoiningDate() {
		return frEmpJoiningDate;
	}
	public void setFrEmpJoiningDate(String frEmpJoiningDate) {
		this.frEmpJoiningDate = frEmpJoiningDate;
	}
	public int getFrId() {
		return frId;
	}
	public void setFrId(int frId) {
		this.frId = frId;
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
	public float getTotalLimit() {
		return totalLimit;
	}
	public void setTotalLimit(float totalLimit) {
		this.totalLimit = totalLimit;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public float getCurrentBillAmt() {
		return currentBillAmt;
	}
	public void setCurrentBillAmt(float currentBillAmt) {
		this.currentBillAmt = currentBillAmt;
	}
	public String getUpdateDatetime() {
		return updateDatetime;
	}
	public void setUpdateDatetime(String updateDatetime) {
		this.updateDatetime = updateDatetime;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public int getDesignation() {
		return designation;
	}
	public void setDesignation(int designation) {
		this.designation = designation;
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
		return "FrEmpMaster [frEmpId=" + frEmpId + ", frEmpName=" + frEmpName + ", frEmpContact=" + frEmpContact
				+ ", frEmpAddress=" + frEmpAddress + ", frEmpJoiningDate=" + frEmpJoiningDate + ", frId=" + frId
				+ ", delStatus=" + delStatus + ", isActive=" + isActive + ", totalLimit=" + totalLimit + ", fromDate="
				+ fromDate + ", toDate=" + toDate + ", currentBillAmt=" + currentBillAmt + ", updateDatetime="
				+ updateDatetime + ", password=" + password + ", empCode=" + empCode + ", designation=" + designation
				+ ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exInt3=" + exInt3 + ", exVar1=" + exVar1
				+ ", exVar2=" + exVar2 + ", exVar3=" + exVar3 + "]";
	}

}
