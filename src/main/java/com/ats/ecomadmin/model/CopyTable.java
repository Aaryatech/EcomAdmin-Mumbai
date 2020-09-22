package com.ats.ecomadmin.model;

 
public class CopyTable {

	 
	private int copyId;
	private String copyName;
	private String query;
	private String jpaQuery;
	private String fieldsToShow;
	private String beanName;

	private String makerDatetime;
	private int delStatus;

	private int isActive;

	private int exInt1;

	private int exInt2;

	private String exVar1;

	private String exVar2;

	public int getCopyId() {
		return copyId;
	}

	public void setCopyId(int copyId) {
		this.copyId = copyId;
	}

	public String getCopyName() {
		return copyName;
	}

	public void setCopyName(String copyName) {
		this.copyName = copyName;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getJpaQuery() {
		return jpaQuery;
	}

	public void setJpaQuery(String jpaQuery) {
		this.jpaQuery = jpaQuery;
	}

	public String getFieldsToShow() {
		return fieldsToShow;
	}

	public void setFieldsToShow(String fieldsToShow) {
		this.fieldsToShow = fieldsToShow;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getMakerDatetime() {
		return makerDatetime;
	}

	public void setMakerDatetime(String makerDatetime) {
		this.makerDatetime = makerDatetime;
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
		return "CopyTable [copyId=" + copyId + ", copyName=" + copyName + ", query=" + query + ", jpaQuery=" + jpaQuery
				+ ", fieldsToShow=" + fieldsToShow + ", beanName=" + beanName + ", makerDatetime=" + makerDatetime
				+ ", delStatus=" + delStatus + ", isActive=" + isActive + ", exInt1=" + exInt1 + ", exInt2=" + exInt2
				+ ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + "]";
	}

	
	
}
