package com.ats.ecomadmin.model.offer;

import java.util.List;

//Mahendra Singh 27-10-2020
public class TGrievance {
	
	private int grievanceId;
	private int orderId;
	private int grievanceTypeId;
	private int grievanceSubtypeId;
	private String grievanceNo;
	private String remark;
	private int currentStatus;
	private String insertDateTime;
	private int insertById;
	private String grievanceTypeName;
	private String grievenceSubtypeName;
	private String date;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;

	public int getGrievanceId() {
		return grievanceId;
	}

	public void setGrievanceId(int grievanceId) {
		this.grievanceId = grievanceId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getGrievanceTypeId() {
		return grievanceTypeId;
	}

	public void setGrievanceTypeId(int grievanceTypeId) {
		this.grievanceTypeId = grievanceTypeId;
	}

	public int getGrievanceSubtypeId() {
		return grievanceSubtypeId;
	}

	public void setGrievanceSubtypeId(int grievanceSubtypeId) {
		this.grievanceSubtypeId = grievanceSubtypeId;
	}

	public String getGrievanceNo() {
		return grievanceNo;
	}

	public void setGrievanceNo(String grievanceNo) {
		this.grievanceNo = grievanceNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(int currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getInsertDateTime() {
		return insertDateTime;
	}

	public void setInsertDateTime(String insertDateTime) {
		this.insertDateTime = insertDateTime;
	}

	public int getInsertById() {
		return insertById;
	}

	public void setInsertById(int insertById) {
		this.insertById = insertById;
	}

	public String getGrievanceTypeName() {
		return grievanceTypeName;
	}

	public void setGrievanceTypeName(String grievanceTypeName) {
		this.grievanceTypeName = grievanceTypeName;
	}

	public String getGrievenceSubtypeName() {
		return grievenceSubtypeName;
	}

	public void setGrievenceSubtypeName(String grievenceSubtypeName) {
		this.grievenceSubtypeName = grievenceSubtypeName;
	}
	 
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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
	
	List<TGrievanceTrail> grievanceTrais;

	public List<TGrievanceTrail> getGrievanceTrais() {
		return grievanceTrais;
	}

	public void setGrievanceTrais(List<TGrievanceTrail> grievanceTrais) {
		this.grievanceTrais = grievanceTrais;
	}

	@Override
	public String toString() {
		return "TGrievance [grievanceId=" + grievanceId + ", orderId=" + orderId + ", grievanceTypeId="
				+ grievanceTypeId + ", grievanceSubtypeId=" + grievanceSubtypeId + ", grievanceNo=" + grievanceNo
				+ ", remark=" + remark + ", currentStatus=" + currentStatus + ", insertDateTime=" + insertDateTime
				+ ", insertById=" + insertById + ", grievanceTypeName=" + grievanceTypeName + ", grievenceSubtypeName="
				+ grievenceSubtypeName + ", date=" + date + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1="
				+ exVar1 + ", exVar2=" + exVar2 + ", grievanceTrais=" + grievanceTrais + "]";
	}

	
}
