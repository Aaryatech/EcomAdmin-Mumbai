package com.ats.ecomadmin.model.report;

import java.util.List;

import com.ats.ecomadmin.model.SellBillDetail;

public class GetSellBillHeader {	
	
	private int sellBillNo;
	private String invoiceNo;
	private String billDate;
	private int frId;
	private String frCode;
	private float taxableAmt;
	private int discType;
	private float discountPer;
	private float discountAmt;
	private float payableAmt;
	private float totalTax;
	private float grandTotal;
	private float paidAmt;
	private float remainingAmt;
	private float discAmtItem;
	private float advanceAmt;
	private int paymentMode;
	private int custId;
	private String userName;
	private String userGstNo;
	private String userPhone;
	private int status;
	private int isDairyMartBill;// Company Id
	private String couponNo;
	private float custLoyaltyPtRate;
	private float custLoyaltyPt;
	private int delStatus;
	private char billType;
	private int extInt1;
	private int extInt2;
	private int extInt3;
	private int extInt4;
	private int extFloat1;
	private int extFloat2;
	private int extFloat3;
	private int extFloat4;
	private String extVar1;
	private String extVar2;
	private String extVar3;
	private String extVar4;
	
	private  int delBoyId;
	private  String delvrBoyName;
	private  String delvrBoyMobNo;
	
	public String getExtVar1() {
		return extVar1;
	}

	public void setExtVar1(String extVar1) {
		this.extVar1 = extVar1;
	}

	public int getExtFloat1() {
		return extFloat1;
	}

	public void setExtFloat1(int extFloat1) {
		this.extFloat1 = extFloat1;
	}

	public int getExtInt1() {
		return extInt1;
	}

	public void setExtInt1(int extInt1) {
		this.extInt1 = extInt1;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
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

	public float getAdvanceAmt() {
		return advanceAmt;
	}

	public void setAdvanceAmt(float advanceAmt) {
		this.advanceAmt = advanceAmt;
	}

	public int getIsDairyMartBill() {
		return isDairyMartBill;
	}

	public void setIsDairyMartBill(int isDairyMartBill) {
		this.isDairyMartBill = isDairyMartBill;
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

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public void setUserGstNo(String userGstNo) {
		this.userGstNo = userGstNo;
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

	public int getExtFloat2() {
		return extFloat2;
	}

	public void setExtFloat2(int extFloat2) {
		this.extFloat2 = extFloat2;
	}

	public int getExtFloat3() {
		return extFloat3;
	}

	public void setExtFloat3(int extFloat3) {
		this.extFloat3 = extFloat3;
	}

	public int getExtInt3() {
		return extInt3;
	}

	public void setExtInt3(int extInt3) {
		this.extInt3 = extInt3;
	}

	public int getExtInt4() {
		return extInt4;
	}

	public void setExtInt4(int extInt4) {
		this.extInt4 = extInt4;
	}

	public int getExtFloat4() {
		return extFloat4;
	}

	public void setExtFloat4(int extFloat4) {
		this.extFloat4 = extFloat4;
	}

	public String getExtVar3() {
		return extVar3;
	}

	public void setExtVar3(String extVar3) {
		this.extVar3 = extVar3;
	}

	public String getExtVar4() {
		return extVar4;
	}

	public void setExtVar4(String extVar4) {
		this.extVar4 = extVar4;
	}

	public int getDelBoyId() {
		return delBoyId;
	}

	public void setDelBoyId(int delBoyId) {
		this.delBoyId = delBoyId;
	}

	public String getDelvrBoyName() {
		return delvrBoyName;
	}

	public void setDelvrBoyName(String delvrBoyName) {
		this.delvrBoyName = delvrBoyName;
	}

	public String getDelvrBoyMobNo() {
		return delvrBoyMobNo;
	}

	public void setDelvrBoyMobNo(String delvrBoyMobNo) {
		this.delvrBoyMobNo = delvrBoyMobNo;
	}

	@Override
	public String toString() {
		return "GetSellBillHeader [sellBillNo=" + sellBillNo + ", invoiceNo=" + invoiceNo + ", billDate=" + billDate
				+ ", frId=" + frId + ", frCode=" + frCode + ", taxableAmt=" + taxableAmt + ", discType=" + discType
				+ ", discountPer=" + discountPer + ", discountAmt=" + discountAmt + ", payableAmt=" + payableAmt
				+ ", totalTax=" + totalTax + ", grandTotal=" + grandTotal + ", paidAmt=" + paidAmt + ", remainingAmt="
				+ remainingAmt + ", discAmtItem=" + discAmtItem + ", advanceAmt=" + advanceAmt + ", paymentMode="
				+ paymentMode + ", custId=" + custId + ", userName=" + userName + ", userGstNo=" + userGstNo
				+ ", userPhone=" + userPhone + ", status=" + status + ", isDairyMartBill=" + isDairyMartBill
				+ ", couponNo=" + couponNo + ", custLoyaltyPtRate=" + custLoyaltyPtRate + ", custLoyaltyPt="
				+ custLoyaltyPt + ", delStatus=" + delStatus + ", billType=" + billType + ", extInt1=" + extInt1
				+ ", extInt2=" + extInt2 + ", extInt3=" + extInt3 + ", extInt4=" + extInt4 + ", extFloat1=" + extFloat1
				+ ", extFloat2=" + extFloat2 + ", extFloat3=" + extFloat3 + ", extFloat4=" + extFloat4 + ", extVar1="
				+ extVar1 + ", extVar2=" + extVar2 + ", extVar3=" + extVar3 + ", extVar4=" + extVar4 + ", delBoyId="
				+ delBoyId + ", delvrBoyName=" + delvrBoyName + ", delvrBoyMobNo=" + delvrBoyMobNo
				+ ", sellBillDetailsList=" + sellBillDetailsList + "]";
	}

}
