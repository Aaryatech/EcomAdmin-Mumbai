package com.ats.ecomadmin.model;

import java.util.List;

public class GetFilterIds {
	List<MFilter> filterList;
	
	String filterIds;

	public List<MFilter> getFilterList() {
		return filterList;
	}

	public void setFilterList(List<MFilter> filterList) {
		this.filterList = filterList;
	}

	public String getFilterIds() {
		return filterIds;
	}

	public void setFilterIds(String filterIds) {
		this.filterIds = filterIds;
	}

	@Override
	public String toString() {
		return "GetFilterIds [filterList=" + filterList + ", filterIds=" + filterIds + "]";
	}
	
	
	
}
