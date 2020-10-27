package com.ats.ecomadmin.model.offer;

//Mahendra Singh 27-10-2020
public class TGrievanceTrail {
	
	private int trailId;
	private int grievanceId;
	private String remark;
	private int status;
	private int actionByUserId;
	private String actionDateTime;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;
	private String identifiedRootCause;
	private int grievanceResType;
	private String resolutionDetail;
	private String repayAmt;
	private String repayDetails;
	private String trailDate;
	private int grivActionText;
	private int grivActionValue;

	public int getTrailId() {
		return trailId;
	}

	public void setTrailId(int trailId) {
		this.trailId = trailId;
	}

	public int getGrievanceId() {
		return grievanceId;
	}

	public void setGrievanceId(int grievanceId) {
		this.grievanceId = grievanceId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getActionByUserId() {
		return actionByUserId;
	}

	public void setActionByUserId(int actionByUserId) {
		this.actionByUserId = actionByUserId;
	}

	public String getActionDateTime() {
		return actionDateTime;
	}

	public void setActionDateTime(String actionDateTime) {
		this.actionDateTime = actionDateTime;
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

	public String getIdentifiedRootCause() {
		return identifiedRootCause;
	}

	public void setIdentifiedRootCause(String identifiedRootCause) {
		this.identifiedRootCause = identifiedRootCause;
	}

	public int getGrievanceResType() {
		return grievanceResType;
	}

	public void setGrievanceResType(int grievanceResType) {
		this.grievanceResType = grievanceResType;
	}

	public String getResolutionDetail() {
		return resolutionDetail;
	}

	public void setResolutionDetail(String resolutionDetail) {
		this.resolutionDetail = resolutionDetail;
	}

	public String getRepayAmt() {
		return repayAmt;
	}

	public void setRepayAmt(String repayAmt) {
		this.repayAmt = repayAmt;
	}

	public String getRepayDetails() {
		return repayDetails;
	}

	public void setRepayDetails(String repayDetails) {
		this.repayDetails = repayDetails;
	}

	public String getTrailDate() {
		return trailDate;
	}

	public void setTrailDate(String trailDate) {
		this.trailDate = trailDate;
	}

	public int getGrivActionText() {
		return grivActionText;
	}

	public void setGrivActionText(int grivActionText) {
		this.grivActionText = grivActionText;
	}

	public int getGrivActionValue() {
		return grivActionValue;
	}

	public void setGrivActionValue(int grivActionValue) {
		this.grivActionValue = grivActionValue;
	}

	@Override
	public String toString() {
		return "TGrievanceTrail [trailId=" + trailId + ", grievanceId=" + grievanceId + ", remark=" + remark
				+ ", status=" + status + ", actionByUserId=" + actionByUserId + ", actionDateTime=" + actionDateTime
				+ ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2
				+ ", identifiedRootCause=" + identifiedRootCause + ", grievanceResType=" + grievanceResType
				+ ", resolutionDetail=" + resolutionDetail + ", repayAmt=" + repayAmt + ", repayDetails=" + repayDetails
				+ ", trailDate=" + trailDate + ", grivActionText=" + grivActionText + ", grivActionValue="
				+ grivActionValue + "]";
	}
	
	
}
