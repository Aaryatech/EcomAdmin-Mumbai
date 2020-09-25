package com.ats.ecomadmin.model;


public class GetFrConfigList {
	
	 
	private int frachaseConfigId;

	private int configHeaderId;

	private int frId;
	
	private String configName;
 
	private String frCode;
	
	private String frName;
	
	private String frCity;
	
	
	private String route;


	public int getFrachaseConfigId() {
		return frachaseConfigId;
	}


	public void setFrachaseConfigId(int frachaseConfigId) {
		this.frachaseConfigId = frachaseConfigId;
	}


	public int getConfigHeaderId() {
		return configHeaderId;
	}


	public void setConfigHeaderId(int configHeaderId) {
		this.configHeaderId = configHeaderId;
	}


	public int getFrId() {
		return frId;
	}


	public void setFrId(int frId) {
		this.frId = frId;
	}


	public String getConfigName() {
		return configName;
	}


	public void setConfigName(String configName) {
		this.configName = configName;
	}


	public String getFrCode() {
		return frCode;
	}


	public void setFrCode(String frCode) {
		this.frCode = frCode;
	}


	public String getFrName() {
		return frName;
	}


	public void setFrName(String frName) {
		this.frName = frName;
	}


	public String getFrCity() {
		return frCity;
	}


	public void setFrCity(String frCity) {
		this.frCity = frCity;
	}


	public String getRoute() {
		return route;
	}


	public void setRoute(String route) {
		this.route = route;
	}


	@Override
	public String toString() {
		return "GetFrConfigList [frachaseConfigId=" + frachaseConfigId + ", configHeaderId=" + configHeaderId
				+ ", frId=" + frId + ", configName=" + configName + ", frCode=" + frCode + ", frName=" + frName
				+ ", frCity=" + frCity + ", route=" + route + "]";
	}

	

}
