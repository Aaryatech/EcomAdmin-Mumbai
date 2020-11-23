package com.ats.ecomadmin.model.offer;

import java.util.List;

import com.ats.ecomadmin.model.FilterTypes;

public class GetConfiguredCatAndFilter {

	List<FilterTypes> filterList;	
	CateFilterConfig catFilterConfig;
	public List<FilterTypes> getFilterList() {
		return filterList;
	}
	public void setFilterList(List<FilterTypes> filterList) {
		this.filterList = filterList;
	}
	public CateFilterConfig getCatFilterConfig() {
		return catFilterConfig;
	}
	public void setCatFilterConfig(CateFilterConfig catFilterConfig) {
		this.catFilterConfig = catFilterConfig;
	}
	@Override
	public String toString() {
		return "GetConfiguredCatAndFilter [filterList=" + filterList + ", catFilterConfig=" + catFilterConfig + "]";
	}
	
	
}
