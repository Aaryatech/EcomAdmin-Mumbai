package com.ats.ecomadmin.model.offer;

public class GetOfferFrConfiguredList {

	private int offerConfigId;
	private int offerId;
	private String frName;
	private String offerName;
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
	public String getFrName() {
		return frName;
	}
	public void setFrName(String frName) {
		this.frName = frName;
	}
	public String getOfferName() {
		return offerName;
	}
	public void setOfferName(String offerName) {
		this.offerName = offerName;
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
		return "GetOfferFrConfiguredList [offerConfigId=" + offerConfigId + ", offerId=" + offerId + ", frName="
				+ frName + ", offerName=" + offerName + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + "]";
	}
	
	
	
	
}
