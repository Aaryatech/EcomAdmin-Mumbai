package com.ats.ecomadmin.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SellBillHeader implements Serializable {

	private int sellBillNo;

	private String invoiceNo;

	private String billDate;

	private String timestamp;

	private int frId;

	private String frCode;

	private int discType; // new

	private float discAmtItem; // new

	private float taxableAmt;

	private float discountPer;

	private float discountAmt;

	private float payableAmt;

	private float totalTax;

	private float grandTotal;

	private float paidAmt;

	private float remainingAmt;

	private int paymentMode;

	private int isDairyMartBill;// Company Id

	private float advanceAmt; // new

	private String couponNo;// new

	private int custId;// new

	private float custLoyaltyPtRate;// new

	private float custLoyaltyPt;// new

	private String userName;

	private String userGstNo;

	private String userPhone;

	private int status;

	private char billType;

	private int delStatus;
	private int extInt1;
	private float extFloat1;
	private float extFloat2;
	private float extFloat3;

	private String extVar1;
	private int extInt2;
	private String extVar2;
	
	public String getExtVar1() {
		return extVar1;
	}

	public void setExtVar1(String extVar1) {
		this.extVar1 = extVar1;
	}

	public int getCustId() {
		return custId;
	}

	public int getExtInt1() {
		return extInt1;
	}

	public void setExtInt1(int extInt1) {
		this.extInt1 = extInt1;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public int getDiscType() {
		return discType;
	}

	public void setDiscType(int discType) {
		this.discType = discType;
	}

	public float getDiscAmtItem() {
		return discAmtItem;
	}

	public void setDiscAmtItem(float discAmtItem) {
		this.discAmtItem = discAmtItem;
	}

	public int getIsDairyMartBill() {
		return isDairyMartBill;
	}

	public void setIsDairyMartBill(int isDairyMartBill) {
		this.isDairyMartBill = isDairyMartBill;
	}

	public float getAdvanceAmt() {
		return advanceAmt;
	}

	public void setAdvanceAmt(float advanceAmt) {
		this.advanceAmt = advanceAmt;
	}

	public String getCouponNo() {
		return couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}

	public float getCustLoyaltyPtRate() {
		return custLoyaltyPtRate;
	}

	public void setCustLoyaltyPtRate(float custLoyaltyPtRate) {
		this.custLoyaltyPtRate = custLoyaltyPtRate;
	}

	public float getCustLoyaltyPt() {
		return custLoyaltyPt;
	}

	public void setCustLoyaltyPt(float custLoyaltyPt) {
		this.custLoyaltyPt = custLoyaltyPt;
	}

	public char getBillType() {
		return billType;
	}

	public void setBillType(char billType) {
		this.billType = billType;
	}

	List<SellBillDetail> sellBillDetailsList;

	public List<SellBillDetail> getSellBillDetailsList() {
		return sellBillDetailsList;
	}

	public void setSellBillDetailsList(List<SellBillDetail> sellBillDetailsList) {
		this.sellBillDetailsList = sellBillDetailsList;
	}

	public int getSellBillNo() {
		return sellBillNo;
	}

	public void setSellBillNo(int sellBillNo) {
		this.sellBillNo = sellBillNo;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
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

	public float getTaxableAmt() {
		return taxableAmt;
	}

	public void setTaxableAmt(float taxableAmt) {
		this.taxableAmt = taxableAmt;
	}

	public float getDiscountPer() {
		return discountPer;
	}

	public void setDiscountPer(float discountPer) {
		this.discountPer = discountPer;
	}

	public float getDiscountAmt() {
		return discountAmt;
	}

	public void setDiscountAmt(float discountAmt) {
		this.discountAmt = discountAmt;
	}

	public float getPayableAmt() {
		return payableAmt;
	}

	public void setPayableAmt(float payableAmt) {
		this.payableAmt = payableAmt;
	}

	public float getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(float totalTax) {
		this.totalTax = totalTax;
	}

	public float getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(float grandTotal) {
		this.grandTotal = grandTotal;
	}

	public float getPaidAmt() {
		return paidAmt;
	}

	public void setPaidAmt(float paidAmt) {
		this.paidAmt = paidAmt;
	}

	public float getRemainingAmt() {
		return remainingAmt;
	}

	public void setRemainingAmt(float remainingAmt) {
		this.remainingAmt = remainingAmt;
	}

	public int getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(int paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserGstNo() {
		return userGstNo;
	}

	public void setUserGstNo(String userGstNo) {
		this.userGstNo = userGstNo;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public float getExtFloat1() {
		return extFloat1;
	}

	public void setExtFloat1(float extFloat1) {
		this.extFloat1 = extFloat1;
	}

	public float getExtFloat2() {
		return extFloat2;
	}

	public void setExtFloat2(float extFloat2) {
		this.extFloat2 = extFloat2;
	}

	public float getExtFloat3() {
		return extFloat3;
	}

	public void setExtFloat3(float extFloat3) {
		this.extFloat3 = extFloat3;
	}

	public int getExtInt2() {
		return extInt2;
	}

	public void setExtInt2(int extInt2) {
		this.extInt2 = extInt2;
	}

	public String getExtVar2() {
		return extVar2;
	}

	public void setExtVar2(String extVar2) {
		this.extVar2 = extVar2;
	}

	@Override
	public String toString() {
		return "SellBillHeader [sellBillNo=" + sellBillNo + ", invoiceNo=" + invoiceNo + ", billDate=" + billDate
				+ ", timestamp=" + timestamp + ", frId=" + frId + ", frCode=" + frCode + ", discType=" + discType
				+ ", discAmtItem=" + discAmtItem + ", taxableAmt=" + taxableAmt + ", discountPer=" + discountPer
				+ ", discountAmt=" + discountAmt + ", payableAmt=" + payableAmt + ", totalTax=" + totalTax
				+ ", grandTotal=" + grandTotal + ", paidAmt=" + paidAmt + ", remainingAmt=" + remainingAmt
				+ ", paymentMode=" + paymentMode + ", isDairyMartBill=" + isDairyMartBill + ", advanceAmt=" + advanceAmt
				+ ", couponNo=" + couponNo + ", custId=" + custId + ", custLoyaltyPtRate=" + custLoyaltyPtRate
				+ ", custLoyaltyPt=" + custLoyaltyPt + ", userName=" + userName + ", userGstNo=" + userGstNo
				+ ", userPhone=" + userPhone + ", status=" + status + ", billType=" + billType + ", delStatus="
				+ delStatus + ", extInt1=" + extInt1 + ", extFloat1=" + extFloat1 + ", extFloat2=" + extFloat2
				+ ", extFloat3=" + extFloat3 + ", extVar1=" + extVar1 + ", extInt2=" + extInt2 + ", extVar2=" + extVar2
				+ ", sellBillDetailsList=" + sellBillDetailsList + "]";
	}

	
}
