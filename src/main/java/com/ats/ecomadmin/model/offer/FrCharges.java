package com.ats.ecomadmin.model.offer;

public class FrCharges {
	
	private int chargeId;
	private int frId;
	private String fromDate;
	private String toDate;
	private float surchargeFee;
	private float packingChg;
	private float handlingChg;
	private float extraChg;
	private float roundOffAmt;
	private int exInt1;
	private int exInt2;
	private int exInt3;
	private float exFloat1;
	private float exFloat2;
	private float exFloat3;
	private String exVar1;
	private String exVar2;
	private String exVar3;

	public int getChargeId() {
		return chargeId;
	}

	public void setChargeId(int chargeId) {
		this.chargeId = chargeId;
	}

	public int getFrId() {
		return frId;
	}

	public void setFrId(int frId) {
		this.frId = frId;
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

	public float getSurchargeFee() {
		return surchargeFee;
	}

	public void setSurchargeFee(float surchargeFee) {
		this.surchargeFee = surchargeFee;
	}

	public float getPackingChg() {
		return packingChg;
	}

	public void setPackingChg(float packingChg) {
		this.packingChg = packingChg;
	}

	public float getHandlingChg() {
		return handlingChg;
	}

	public void setHandlingChg(float handlingChg) {
		this.handlingChg = handlingChg;
	}

	public float getExtraChg() {
		return extraChg;
	}

	public void setExtraChg(float extraChg) {
		this.extraChg = extraChg;
	}

	public float getRoundOffAmt() {
		return roundOffAmt;
	}

	public void setRoundOffAmt(float roundOffAmt) {
		this.roundOffAmt = roundOffAmt;
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
		return "FrCharges [chargeId=" + chargeId + ", frId=" + frId + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", surchargeFee=" + surchargeFee + ", packingChg=" + packingChg + ", handlingChg=" + handlingChg
				+ ", extraChg=" + extraChg + ", roundOffAmt=" + roundOffAmt + ", exInt1=" + exInt1 + ", exInt2="
				+ exInt2 + ", exInt3=" + exInt3 + ", exFloat1=" + exFloat1 + ", exFloat2=" + exFloat2 + ", exFloat3="
				+ exFloat3 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exVar3=" + exVar3 + "]";
	}
	
	
	
}
