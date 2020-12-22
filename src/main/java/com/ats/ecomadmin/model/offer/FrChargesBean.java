package com.ats.ecomadmin.model.offer;

import com.ats.ecomadmin.model.Franchise;

public class FrChargesBean {

	private FrCharges frCharges;
	private Franchise franchise;
	public FrCharges getFrCharges() {
		return frCharges;
	}
	public void setFrCharges(FrCharges frCharges) {
		this.frCharges = frCharges;
	}
	public Franchise getFranchise() {
		return franchise;
	}
	public void setFranchise(Franchise franchise) {
		this.franchise = franchise;
	}
	@Override
	public String toString() {
		return "FrChargesBean [frCharges=" + frCharges + ", franchise=" + franchise + "]";
	}
	
	
}
