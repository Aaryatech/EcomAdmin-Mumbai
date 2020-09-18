package com.ats.ecomadmin.model.offer;

 

 public class GetConfigureOfferList {
 	private int frId;
	private String frName;
	private int checked;
	public int getFrId() {
		return frId;
	}
	public void setFrId(int frId) {
		this.frId = frId;
	}
	public String getFrName() {
		return frName;
	}
	public void setFrName(String frName) {
		this.frName = frName;
	}
	public int getChecked() {
		return checked;
	}
	public void setChecked(int checked) {
		this.checked = checked;
	}
	@Override
	public String toString() {
		return "GetConfigureOfferList [frId=" + frId + ", frName=" + frName + ", checked=" + checked + "]";
	}
	
	
}
