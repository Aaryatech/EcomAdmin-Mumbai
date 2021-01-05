package com.ats.ecomadmin.model;


public class DeliverySlots {
	
	private int deliSlotId;
	
	private String deliverySlotName;
	
	private String fromTime;
	
	private String toTime;
	
	private int companyId;
	
	private int makerUserId;
	
	private int delStatus;
	
	private int isActive;
	
	private String insertDttime;
	
	private String updtDttime;
	
	private int exInt1,exInt2;
	
	private String exVar1, exVar2;
	
	private float exFloat1;

	public int getDeliSlotId() {
		return deliSlotId;
	}



	public String getFromTime() {
		return fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public int getCompanyId() {
		return companyId;
	}

	public int getMakerUserId() {
		return makerUserId;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public int getIsActive() {
		return isActive;
	}

	public String getInsertDttime() {
		return insertDttime;
	}

	public String getUpdtDttime() {
		return updtDttime;
	}

	public int getExInt1() {
		return exInt1;
	}

	public int getExInt2() {
		return exInt2;
	}

	public String getExVar1() {
		return exVar1;
	}

	public String getExVar2() {
		return exVar2;
	}

	public float getExFloat1() {
		return exFloat1;
	}

	public void setDeliSlotId(int deliSlotId) {
		this.deliSlotId = deliSlotId;
	}

	
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public void setMakerUserId(int makerUserId) {
		this.makerUserId = makerUserId;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public void setInsertDttime(String insertDttime) {
		this.insertDttime = insertDttime;
	}

	public void setUpdtDttime(String updtDttime) {
		this.updtDttime = updtDttime;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}

	public void setExInt2(int exInt2) {
		this.exInt2 = exInt2;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}

	public void setExFloat1(float exFloat1) {
		this.exFloat1 = exFloat1;
	}



	public String getDeliverySlotName() {
		return deliverySlotName;
	}



	public void setDeliverySlotName(String deliverySlotName) {
		this.deliverySlotName = deliverySlotName;
	}



	@Override
	public String toString() {
		return "DeliverySlots [deliSlotId=" + deliSlotId + ", deliverySlotName=" + deliverySlotName + ", fromTime="
				+ fromTime + ", toTime=" + toTime + ", companyId=" + companyId + ", makerUserId=" + makerUserId
				+ ", delStatus=" + delStatus + ", isActive=" + isActive + ", insertDttime=" + insertDttime
				+ ", updtDttime=" + updtDttime + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1
				+ ", exVar2=" + exVar2 + ", exFloat1=" + exFloat1 + "]";
	}

	
	
	
	
	

}
