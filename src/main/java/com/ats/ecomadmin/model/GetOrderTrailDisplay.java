package com.ats.ecomadmin.model;

import java.util.Date;

public class GetOrderTrailDisplay {

	private int trailId;
	private int orderId;
	private int actionByUserId;
	private Date actionDateTime;
	private int status;
	private int exInt1;
	private int exInt2;
	private int exInt3;
	private String exVar1;
	private String exVar2;
	private String exVar3;

	private String userName;
	private String trailDate;

	public int getTrailId() {
		return trailId;
	}

	public void setTrailId(int trailId) {
		this.trailId = trailId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getActionByUserId() {
		return actionByUserId;
	}

	public void setActionByUserId(int actionByUserId) {
		this.actionByUserId = actionByUserId;
	}

	public Date getActionDateTime() {
		return actionDateTime;
	}

	public void setActionDateTime(Date actionDateTime) {
		this.actionDateTime = actionDateTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTrailDate() {
		return trailDate;
	}

	public void setTrailDate(String trailDate) {
		this.trailDate = trailDate;
	}

	@Override
	public String toString() {
		return "GetOrderTrailDisplay [trailId=" + trailId + ", orderId=" + orderId + ", actionByUserId="
				+ actionByUserId + ", actionDateTime=" + actionDateTime + ", status=" + status + ", exInt1=" + exInt1
				+ ", exInt2=" + exInt2 + ", exInt3=" + exInt3 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2
				+ ", exVar3=" + exVar3 + ", userName=" + userName + ", trailDate=" + trailDate + "]";
	}

}
