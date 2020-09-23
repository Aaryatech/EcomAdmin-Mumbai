package com.ats.ecomadmin.model;

import java.util.List;

public class TempConfTraveller {

	//Existing product detail bean
	List<TempProdConfig> prodConfDetailList ;//Sachin 22-09-2020
	
	//New product detail bean
	List<TempProdConfig> tempProdConfList   ;//Sachin 22-09-2020

	GetItemConfHead confHead; //Sachin 23-09-2020
	
	List<ItemConfDetail> confDetailList; //Sachin 23-09-2020
	
	public GetItemConfHead getConfHead() {
		return confHead;
	}
	public void setConfHead(GetItemConfHead confHead) {
		this.confHead = confHead;
	}
	
	public List<TempProdConfig> getProdConfDetailList() {
		return prodConfDetailList;
	}
	public void setProdConfDetailList(List<TempProdConfig> prodConfDetailList) {
		this.prodConfDetailList = prodConfDetailList;
	}
	
	public List<TempProdConfig> getTempProdConfList() {
		return tempProdConfList;
	}
	public void setTempProdConfList(List<TempProdConfig> tempProdConfList) {
		this.tempProdConfList = tempProdConfList;
	}
	
	public List<ItemConfDetail> getConfDetailList() {
		return confDetailList;
	}
	public void setConfDetailList(List<ItemConfDetail> confDetailList) {
		this.confDetailList = confDetailList;
	}
	
	@Override
	public String toString() {
		return "TempConfTraveller [prodConfDetailList=" + prodConfDetailList + ", tempProdConfList=" + tempProdConfList
				+ ", confHead=" + confHead + ", confDetailList=" + confDetailList + "]";
	}
	
}
