package com.ats.ecomadmin.model;

import java.util.List;

public class ItemConfHeader {

	private int configHeaderId;
	
	private int catId;
	private String configName;
	private String configDesc;
	
	private int companyId;
	
	private int isAllowToCopy;
	
	private int isActive;
	private int delStatus;
	
	private int makerUserId;
	private String updtDttime;
	private String insertDttime;
	
	private String applicableFor;
	
	
	private int exInt1;
	private int exInt2;
	private int exInt3;
	
	private String exVar1;
	private String exVar2;
	private String exVar3;
	private String exVar4;
	
	private float exFloat1;
	private float exFloat2;
	private float exFloat3;
	
	private String exDate1;
	private String exDate2;
	
	
	

	public int getConfigHeaderId() {
		return configHeaderId;
	}

	public void setConfigHeaderId(int configHeaderId) {
		this.configHeaderId = configHeaderId;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigDesc() {
		return configDesc;
	}

	public void setConfigDesc(String configDesc) {
		this.configDesc = configDesc;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getIsAllowToCopy() {
		return isAllowToCopy;
	}

	public void setIsAllowToCopy(int isAllowToCopy) {
		this.isAllowToCopy = isAllowToCopy;
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

	public String getUpdtDttime() {
		return updtDttime;
	}

	public void setUpdtDttime(String updtDttime) {
		this.updtDttime = updtDttime;
	}

	public String getInsertDttime() {
		return insertDttime;
	}

	public void setInsertDttime(String insertDttime) {
		this.insertDttime = insertDttime;
	}

	public String getApplicableFor() {
		return applicableFor;
	}

	public void setApplicableFor(String applicableFor) {
		this.applicableFor = applicableFor;
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

	public String getExVar4() {
		return exVar4;
	}

	public void setExVar4(String exVar4) {
		this.exVar4 = exVar4;
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

	List<ItemConfDetail> itemConfDetList;
	
	public List<ItemConfDetail> getItemConfDetList() {
		return itemConfDetList;
	}

	public void setItemConfDetList(List<ItemConfDetail> itemConfDetList) {
		this.itemConfDetList = itemConfDetList;
	}

	@Override
	public String toString() {
		return "ItemConfHeader [configHeaderId=" + configHeaderId + ", catId=" + catId + ", configName=" + configName
				+ ", configDesc=" + configDesc + ", companyId=" + companyId + ", isAllowToCopy=" + isAllowToCopy
				+ ", isActive=" + isActive + ", delStatus=" + delStatus + ", makerUserId=" + makerUserId
				+ ", updtDttime=" + updtDttime + ", insertDttime=" + insertDttime + ", applicableFor=" + applicableFor
				+ ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exInt3=" + exInt3 + ", exVar1=" + exVar1
				+ ", exVar2=" + exVar2 + ", exVar3=" + exVar3 + ", exVar4=" + exVar4 + ", exFloat1=" + exFloat1
				+ ", exFloat2=" + exFloat2 + ", exFloat3=" + exFloat3 + ", exDate1=" + exDate1 + ", exDate2=" + exDate2
				+ ", itemConfDetList=" + itemConfDetList + "]";
	}

}
