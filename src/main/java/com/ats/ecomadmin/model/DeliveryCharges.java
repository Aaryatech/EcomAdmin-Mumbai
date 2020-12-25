package com.ats.ecomadmin.model;

public class DeliveryCharges {
	
	
	private int chId;
	private String groupName;
	private float minKm;
	private float maxKm;
	private float amt1;
	private float amt2;
	private int delStatus;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;
	private float exFloat1;
	private float exFloat2;
	public int getChId() {
		return chId;
	}
	public String getGroupName() {
		return groupName;
	}
	public float getMinKm() {
		return minKm;
	}
	public float getMaxKm() {
		return maxKm;
	}
	public float getAmt1() {
		return amt1;
	}
	public float getAmt2() {
		return amt2;
	}
	public int getDelStatus() {
		return delStatus;
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
	public float getExFloat2() {
		return exFloat2;
	}
	public void setChId(int chId) {
		this.chId = chId;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public void setMinKm(float minKm) {
		this.minKm = minKm;
	}
	public void setMaxKm(float maxKm) {
		this.maxKm = maxKm;
	}
	public void setAmt1(float amt1) {
		this.amt1 = amt1;
	}
	public void setAmt2(float amt2) {
		this.amt2 = amt2;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
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
	public void setExFloat2(float exFloat2) {
		this.exFloat2 = exFloat2;
	}
	@Override
	public String toString() {
		return "DeliveryCharges [chId=" + chId + ", groupName=" + groupName + ", minKm=" + minKm + ", maxKm=" + maxKm
				+ ", amt1=" + amt1 + ", amt2=" + amt2 + ", delStatus=" + delStatus + ", exInt1=" + exInt1 + ", exInt2="
				+ exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exFloat1=" + exFloat1 + ", exFloat2="
				+ exFloat2 + "]";
	}
	
	
	
	
	
	
	
	

}
