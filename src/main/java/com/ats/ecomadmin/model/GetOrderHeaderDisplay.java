package com.ats.ecomadmin.model;

import java.util.List;

public class GetOrderHeaderDisplay {

	private int orderId;
	private String orderNo;
	private String orderDate;
	private int frId;
	private int custId;
	private int status;
	private float taxableAmt;
	private float cgstAmt;
	private float sgstAmt;
	private float igstAmt;
	private float discAmt;
	private float itemDiscAmt;
	private float taxAmt;
	private float totalAmt;
	private int orderStatus;
	private int paidStatus;
	private int paymentMethod;
	private String paymentRemark;
	private int cityId;
	private int areaId;
	private int addressId;
	private String address;
	private String whatsappNo;
	private String landmark;
	private String deliveryDate;
	private String deliveryTime;
	private String insertDateTime;
	private int insertUserId;
	private int orderPlatform;
	private int delStatus;
	private int offerId;
	private String remark;
	private int orderDeliveredBy;
	private int exInt1;
	private int exInt2;
	private int exInt3;
	private int exInt4;
	private String exVar1;
	private String exVar2;
	private String exVar3;
	private String exVar4;
	private float exFloat1;
	private float exFloat2;
	private float exFloat3;
	private float exFloat4;
	private String exDate1;
	private String exDate2;
	private String billingName;
	private String billingAddress;
	private String customerGstnNo;
	private int deliveryType;
	private int deliveryInstId;
	private String deliveryInstText;
	private float deliveryKm;
	private float deliveryCharges;
	private int paymentSubMode;
	private int isAgent;

	private String orderDeliveredByName;
	private String custName;
	private String custMobile;
	private String emailId;
	private String frName;
	private String cityName;
	private String areaName;
	private String offerName;
	private String offerDesc;

	private int deliveryMonth;
	private int deliveryYear;
	private String deliveryDateDisplay;
	private String orderDateDisplay;
	private String deliveryTimeDisplay;
	private String monthName;

	List<GetOrderDetailDisplay> orderDetailList;
	List<GetOrderTrailDisplay> orderTrailList;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public int getFrId() {
		return frId;
	}

	public void setFrId(int frId) {
		this.frId = frId;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public float getTaxableAmt() {
		return taxableAmt;
	}

	public void setTaxableAmt(float taxableAmt) {
		this.taxableAmt = taxableAmt;
	}

	public float getCgstAmt() {
		return cgstAmt;
	}

	public void setCgstAmt(float cgstAmt) {
		this.cgstAmt = cgstAmt;
	}

	public float getSgstAmt() {
		return sgstAmt;
	}

	public void setSgstAmt(float sgstAmt) {
		this.sgstAmt = sgstAmt;
	}

	public float getIgstAmt() {
		return igstAmt;
	}

	public void setIgstAmt(float igstAmt) {
		this.igstAmt = igstAmt;
	}

	public float getDiscAmt() {
		return discAmt;
	}

	public void setDiscAmt(float discAmt) {
		this.discAmt = discAmt;
	}

	public float getItemDiscAmt() {
		return itemDiscAmt;
	}

	public void setItemDiscAmt(float itemDiscAmt) {
		this.itemDiscAmt = itemDiscAmt;
	}

	public float getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(float taxAmt) {
		this.taxAmt = taxAmt;
	}

	public float getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(float totalAmt) {
		this.totalAmt = totalAmt;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getPaidStatus() {
		return paidStatus;
	}

	public void setPaidStatus(int paidStatus) {
		this.paidStatus = paidStatus;
	}

	public int getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(int paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentRemark() {
		return paymentRemark;
	}

	public void setPaymentRemark(String paymentRemark) {
		this.paymentRemark = paymentRemark;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWhatsappNo() {
		return whatsappNo;
	}

	public void setWhatsappNo(String whatsappNo) {
		this.whatsappNo = whatsappNo;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getInsertDateTime() {
		return insertDateTime;
	}

	public void setInsertDateTime(String insertDateTime) {
		this.insertDateTime = insertDateTime;
	}

	public int getInsertUserId() {
		return insertUserId;
	}

	public void setInsertUserId(int insertUserId) {
		this.insertUserId = insertUserId;
	}

	public int getOrderPlatform() {
		return orderPlatform;
	}

	public void setOrderPlatform(int orderPlatform) {
		this.orderPlatform = orderPlatform;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getOrderDeliveredBy() {
		return orderDeliveredBy;
	}

	public void setOrderDeliveredBy(int orderDeliveredBy) {
		this.orderDeliveredBy = orderDeliveredBy;
	}

	public int getExInt1() {
		return exInt1;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}

	public int getExInt2() {
		return exInt2;
	}

	public void setExInt2(int exInt2) {
		this.exInt2 = exInt2;
	}

	public int getExInt3() {
		return exInt3;
	}

	public void setExInt3(int exInt3) {
		this.exInt3 = exInt3;
	}

	public int getExInt4() {
		return exInt4;
	}

	public void setExInt4(int exInt4) {
		this.exInt4 = exInt4;
	}

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public String getExVar2() {
		return exVar2;
	}

	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}

	public String getExVar3() {
		return exVar3;
	}

	public void setExVar3(String exVar3) {
		this.exVar3 = exVar3;
	}

	public String getExVar4() {
		return exVar4;
	}

	public void setExVar4(String exVar4) {
		this.exVar4 = exVar4;
	}

	public float getExFloat1() {
		return exFloat1;
	}

	public void setExFloat1(float exFloat1) {
		this.exFloat1 = exFloat1;
	}

	public float getExFloat2() {
		return exFloat2;
	}

	public void setExFloat2(float exFloat2) {
		this.exFloat2 = exFloat2;
	}

	public float getExFloat3() {
		return exFloat3;
	}

	public void setExFloat3(float exFloat3) {
		this.exFloat3 = exFloat3;
	}

	public float getExFloat4() {
		return exFloat4;
	}

	public void setExFloat4(float exFloat4) {
		this.exFloat4 = exFloat4;
	}

	public String getExDate1() {
		return exDate1;
	}

	public void setExDate1(String exDate1) {
		this.exDate1 = exDate1;
	}

	public String getExDate2() {
		return exDate2;
	}

	public void setExDate2(String exDate2) {
		this.exDate2 = exDate2;
	}

	public String getBillingName() {
		return billingName;
	}

	public void setBillingName(String billingName) {
		this.billingName = billingName;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getCustomerGstnNo() {
		return customerGstnNo;
	}

	public void setCustomerGstnNo(String customerGstnNo) {
		this.customerGstnNo = customerGstnNo;
	}

	public int getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(int deliveryType) {
		this.deliveryType = deliveryType;
	}

	public int getDeliveryInstId() {
		return deliveryInstId;
	}

	public void setDeliveryInstId(int deliveryInstId) {
		this.deliveryInstId = deliveryInstId;
	}

	public String getDeliveryInstText() {
		return deliveryInstText;
	}

	public void setDeliveryInstText(String deliveryInstText) {
		this.deliveryInstText = deliveryInstText;
	}

	public float getDeliveryKm() {
		return deliveryKm;
	}

	public void setDeliveryKm(float deliveryKm) {
		this.deliveryKm = deliveryKm;
	}

	public float getDeliveryCharges() {
		return deliveryCharges;
	}

	public void setDeliveryCharges(float deliveryCharges) {
		this.deliveryCharges = deliveryCharges;
	}

	public int getPaymentSubMode() {
		return paymentSubMode;
	}

	public void setPaymentSubMode(int paymentSubMode) {
		this.paymentSubMode = paymentSubMode;
	}

	public int getIsAgent() {
		return isAgent;
	}

	public void setIsAgent(int isAgent) {
		this.isAgent = isAgent;
	}

	public String getOrderDeliveredByName() {
		return orderDeliveredByName;
	}

	public void setOrderDeliveredByName(String orderDeliveredByName) {
		this.orderDeliveredByName = orderDeliveredByName;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustMobile() {
		return custMobile;
	}

	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}
	public String getFrName() {
		return frName;
	}

	public void setFrName(String frName) {
		this.frName = frName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getOfferDesc() {
		return offerDesc;
	}

	public void setOfferDesc(String offerDesc) {
		this.offerDesc = offerDesc;
	}

	public int getDeliveryMonth() {
		return deliveryMonth;
	}

	public void setDeliveryMonth(int deliveryMonth) {
		this.deliveryMonth = deliveryMonth;
	}

	public int getDeliveryYear() {
		return deliveryYear;
	}

	public void setDeliveryYear(int deliveryYear) {
		this.deliveryYear = deliveryYear;
	}

	public String getDeliveryDateDisplay() {
		return deliveryDateDisplay;
	}

	public void setDeliveryDateDisplay(String deliveryDateDisplay) {
		this.deliveryDateDisplay = deliveryDateDisplay;
	}

	public String getDeliveryTimeDisplay() {
		return deliveryTimeDisplay;
	}

	public void setDeliveryTimeDisplay(String deliveryTimeDisplay) {
		this.deliveryTimeDisplay = deliveryTimeDisplay;
	}

	public List<GetOrderDetailDisplay> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<GetOrderDetailDisplay> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	public List<GetOrderTrailDisplay> getOrderTrailList() {
		return orderTrailList;
	}

	public void setOrderTrailList(List<GetOrderTrailDisplay> orderTrailList) {
		this.orderTrailList = orderTrailList;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getOrderDateDisplay() {
		return orderDateDisplay;
	}

	public void setOrderDateDisplay(String orderDateDisplay) {
		this.orderDateDisplay = orderDateDisplay;
	}

	@Override
	public String toString() {
		return "GetOrderHeaderDisplay [orderId=" + orderId + ", orderNo=" + orderNo + ", orderDate=" + orderDate
				+ ", frId=" + frId + ", custId=" + custId + ", status=" + status + ", taxableAmt=" + taxableAmt
				+ ", cgstAmt=" + cgstAmt + ", sgstAmt=" + sgstAmt + ", igstAmt=" + igstAmt + ", discAmt=" + discAmt
				+ ", itemDiscAmt=" + itemDiscAmt + ", taxAmt=" + taxAmt + ", totalAmt=" + totalAmt + ", orderStatus="
				+ orderStatus + ", paidStatus=" + paidStatus + ", paymentMethod=" + paymentMethod + ", paymentRemark="
				+ paymentRemark + ", cityId=" + cityId + ", areaId=" + areaId + ", addressId=" + addressId
				+ ", address=" + address + ", whatsappNo=" + whatsappNo + ", landmark=" + landmark + ", deliveryDate="
				+ deliveryDate + ", deliveryTime=" + deliveryTime + ", insertDateTime=" + insertDateTime
				+ ", insertUserId=" + insertUserId + ", orderPlatform=" + orderPlatform + ", delStatus=" + delStatus
				+ ", offerId=" + offerId + ", remark=" + remark + ", orderDeliveredBy=" + orderDeliveredBy + ", exInt1="
				+ exInt1 + ", exInt2=" + exInt2 + ", exInt3=" + exInt3 + ", exInt4=" + exInt4 + ", exVar1=" + exVar1
				+ ", exVar2=" + exVar2 + ", exVar3=" + exVar3 + ", exVar4=" + exVar4 + ", exFloat1=" + exFloat1
				+ ", exFloat2=" + exFloat2 + ", exFloat3=" + exFloat3 + ", exFloat4=" + exFloat4 + ", exDate1="
				+ exDate1 + ", exDate2=" + exDate2 + ", billingName=" + billingName + ", billingAddress="
				+ billingAddress + ", customerGstnNo=" + customerGstnNo + ", deliveryType=" + deliveryType
				+ ", deliveryInstId=" + deliveryInstId + ", deliveryInstText=" + deliveryInstText + ", deliveryKm="
				+ deliveryKm + ", deliveryCharges=" + deliveryCharges + ", paymentSubMode=" + paymentSubMode
				+ ", isAgent=" + isAgent + ", orderDeliveredByName=" + orderDeliveredByName + ", custName=" + custName
				+ ", custMobile=" + custMobile + ", emailId=" + emailId + ", frName=" + frName + ", cityName="
				+ cityName + ", areaName=" + areaName + ", offerName=" + offerName + ", offerDesc=" + offerDesc
				+ ", deliveryMonth=" + deliveryMonth + ", deliveryYear=" + deliveryYear + ", deliveryDateDisplay="
				+ deliveryDateDisplay + ", orderDateDisplay=" + orderDateDisplay + ", deliveryTimeDisplay="
				+ deliveryTimeDisplay + ", monthName=" + monthName + ", orderDetailList=" + orderDetailList
				+ ", orderTrailList=" + orderTrailList + "]";
	}

	
}
