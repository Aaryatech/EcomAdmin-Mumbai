package com.ats.ecomadmin.model;

import java.util.List;

public class GetFranchiesAndFrIds {

	List<Franchise> frList;	
	String frStrIds;
	public List<Franchise> getFrList() {
		return frList;
	}
	public void setFrList(List<Franchise> frList) {
		this.frList = frList;
	}
	
	public String getFrStrIds() {
		return frStrIds;
	}
	public void setFrStrIds(String frStrIds) {
		this.frStrIds = frStrIds;
	}
	@Override
	public String toString() {
		return "GetFranchiesAndFrIds [frList=" + frList + ", frStrIds=" + frStrIds + "]";
	}
	
	
}
