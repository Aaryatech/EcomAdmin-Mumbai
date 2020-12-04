package com.ats.ecomadmin.model.report;

public class GetCustomerWisReport {

	private String id;
	private int custId;
	private String custName;
	private String custMobileNo;
	private float grandTotal;
	private String dateOfBirth;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustMobileNo() {
		return custMobileNo;
	}
	public void setCustMobileNo(String custMobileNo) {
		this.custMobileNo = custMobileNo;
	}
	public float getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(float grandTotal) {
		this.grandTotal = grandTotal;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	@Override
	public String toString() {
		return "GetCustomerWisReport [id=" + id + ", custName=" + custName + ", custMobileNo=" + custMobileNo
				+ ", grandTotal=" + grandTotal + ", dateOfBirth=" + dateOfBirth + "]";
	}
	
	
}
