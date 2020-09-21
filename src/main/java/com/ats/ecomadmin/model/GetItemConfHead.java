package com.ats.ecomadmin.model;

/*****************************
//Created Date: 21-09-2020
//UpdateDate:21-09-2020
//Description: Product Confi List Page Bean
//Devloped By(Devloper Name): Sachin
//Updated By(Devloper Name): Sachin
******************************/

public class GetItemConfHead {

	private int configHeaderId;

	private int catId;
	private String configName;

	private int companyId;

	private int isAllowToCopy;

	private int isActive;

	private String catName;

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

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	@Override
	public String toString() {
		return "GetItemConfHead [configHeaderId=" + configHeaderId + ", catId=" + catId + ", configName=" + configName
				+ ", companyId=" + companyId + ", isAllowToCopy=" + isAllowToCopy + ", isActive=" + isActive
				+ ", catName=" + catName + "]";
	}
	
}
