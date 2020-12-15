package com.ats.ecomadmin.model.report;

public class GetDateWiseBillReport {
	
	private String id;
	private String billDate;
	private float totalAmt;
	private int totalBills;
	private int cod;
	private int card;
	private int epay;
	private String monthName;
	private String orderYear;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	

	public float getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(float totalAmt) {
		this.totalAmt = totalAmt;
	}

	public int getTotalBills() {
		return totalBills;
	}

	public void setTotalBills(int totalBills) {
		this.totalBills = totalBills;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public int getCard() {
		return card;
	}

	public void setCard(int card) {
		this.card = card;
	}

	public int getEpay() {
		return epay;
	}

	public void setEpay(int epay) {
		this.epay = epay;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public String getOrderYear() {
		return orderYear;
	}

	public void setOrderYear(String orderYear) {
		this.orderYear = orderYear;
	}

	@Override
	public String toString() {
		return "GetDateWiseBillReport [id=" + id + ", billDate=" + billDate + ", totalAmt=" + totalAmt + ", totalBills="
				+ totalBills + ", cod=" + cod + ", card=" + card + ", epay=" + epay + ", monthName=" + monthName
				+ ", orderYear=" + orderYear + "]";
	}

}
