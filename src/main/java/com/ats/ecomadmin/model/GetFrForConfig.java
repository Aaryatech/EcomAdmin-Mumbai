package com.ats.ecomadmin.model;
 
public class GetFrForConfig {

 private String uniqueId;
 
	private int frId;

	private String frCode;

	private String frName;

	private String frCity;

	private String route;
	
	
	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	

	public int getFrId() {
		return frId;
	}

	public void setFrId(int frId) {
		this.frId = frId;
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
		return "GetFrForConfig [uniqueId=" + uniqueId + ", frId=" + frId + ", frCode=" + frCode + ", frName=" + frName
				+ ", frCity=" + frCity + ", route=" + route + "]";
	}

}
