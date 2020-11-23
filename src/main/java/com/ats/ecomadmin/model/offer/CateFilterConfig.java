package com.ats.ecomadmin.model.offer;

public class CateFilterConfig {

	private int cateFilterConfigId;
	private int cateId;
	private String filterIds;
	private int delStatus;
	private int isActive;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;
	private int compId;
	private String makerDateTime;

	public int getCateFilterConfigId() {
		return cateFilterConfigId;
	}

	public void setCateFilterConfigId(int cateFilterConfigId) {
		this.cateFilterConfigId = cateFilterConfigId;
	}

	public int getCateId() {
		return cateId;
	}

	public void setCateId(int cateId) {
		this.cateId = cateId;
	}

	public String getFilterIds() {
		return filterIds;
	}

	public void setFilterIds(String filterIds) {
		this.filterIds = filterIds;
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

	public int getCompId() {
		return compId;
	}

	public void setCompId(int compId) {
		this.compId = compId;
	}

	public String getMakerDateTime() {
		return makerDateTime;
	}

	public void setMakerDateTime(String makerDateTime) {
		this.makerDateTime = makerDateTime;
	}

	@Override
	public String toString() {
		return "CateFilterConfig [cateFilterConfigId=" + cateFilterConfigId + ", cateId=" + cateId + ", filterIds="
				+ filterIds + ", delStatus=" + delStatus + ", isActive=" + isActive + ", exInt1=" + exInt1 + ", exInt2="
				+ exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", compId=" + compId + ", makerDateTime="
				+ makerDateTime + "]";
	}
}
