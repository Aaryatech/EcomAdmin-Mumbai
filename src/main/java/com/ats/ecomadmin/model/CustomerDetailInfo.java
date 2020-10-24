package com.ats.ecomadmin.model;

public class CustomerDetailInfo {

	CustomerAddDetail custAdd;
	Customer cust;
	
	public CustomerAddDetail getCustAdd() {
		return custAdd;
	}
	public void setCustAdd(CustomerAddDetail custAdd) {
		this.custAdd = custAdd;
	}
	public Customer getCust() {
		return cust;
	}
	public void setCust(Customer cust) {
		this.cust = cust;
	}
	@Override
	public String toString() {
		return "CustomerDetailInfo [custAdd=" + custAdd + ", cust=" + cust + "]";
	}
	
}
