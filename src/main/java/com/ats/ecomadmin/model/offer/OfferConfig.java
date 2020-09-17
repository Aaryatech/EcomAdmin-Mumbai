package com.ats.ecomadmin.model.offer;

public class OfferConfig {

	private int offerConfigId;
	
	private int offerId;
	private String frId;
	private int isActive;
	private int delStatus;
	private int makerUserId;
	private String makerDatetime;
	private String updatedDateTime;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;

	public int getOfferConfigId() {
		return offerConfigId;
	}

	public void setOfferConfigId(int offerConfigId) {
		this.offerConfigId = offerConfigId;
	}

	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}

	public String getFrId() {
		return frId;
	}

	public void setFrId(String frId) {
		this.frId = frId;
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

	public String getMakerDatetime() {
		return makerDatetime;
	}

	public void setMakerDatetime(String makerDatetime) {
		this.makerDatetime = makerDatetime;
	}

	public String getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(String updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
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

	@Override
	public String toString() {
		return "OfferConfig [offerConfigId=" + offerConfigId + ", offerId=" + offerId + ", frId=" + frId + ", isActive="
				+ isActive + ", delStatus=" + delStatus + ", makerUserId=" + makerUserId + ", makerDatetime="
				+ makerDatetime + ", updatedDateTime=" + updatedDateTime + ", exInt1=" + exInt1 + ", exInt2=" + exInt2
				+ ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + "]";
	}
	
}
