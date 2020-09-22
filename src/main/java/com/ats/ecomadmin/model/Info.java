package com.ats.ecomadmin.model;

import java.util.List;


public class Info {
	
	private boolean isError;  //true means Error present, false means no Error
	private String msg;
	
	private int statusCode;
	private String statusText;
	
	private int isSessionAlive;
	
	private String responseObject1;
	
	private String responseObject2;
	
	List<TempProdConfig> prodConfDetailList ;//Sachin 22-09-2020
	List<TempProdConfig> tempProdConfList   ;//Sachin 22-09-2020

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
	
	
	public boolean isError() {
		return isError;
	}
	public void setError(boolean isError) {
		this.isError = isError;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	public int getIsSessionAlive() {
		return isSessionAlive;
	}
	public void setIsSessionAlive(int isSessionAlive) {
		this.isSessionAlive = isSessionAlive;
	}
	public String getResponseObject1() {
		return responseObject1;
	}
	public void setResponseObject1(String responseObject1) {
		this.responseObject1 = responseObject1;
	}
	public String getResponseObject2() {
		return responseObject2;
	}
	public void setResponseObject2(String responseObject2) {
		this.responseObject2 = responseObject2;
	}
	
	@Override
	public String toString() {
		return "Info [isError=" + isError + ", msg=" + msg + ", statusCode=" + statusCode + ", statusText=" + statusText
				+ ", isSessionAlive=" + isSessionAlive + ", responseObject1=" + responseObject1 + ", responseObject2="
				+ responseObject2 + ", prodConfDetailList=" + prodConfDetailList + ", tempProdConfList="
				+ tempProdConfList + "]";
	}

	
	
	
}
