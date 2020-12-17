package com.ats.ecomadmin.model;

//Mahendra 21-09-2020
public class ConfigHomePageProduct {
	private int productId;
	
	private String productCode; //Based On sub Category prefix
	private String productName;
	
	private int prodCatId;	//FK For Category
	private int prodSubCatId; //FK For SubCategory
	
	private int taxId;		//FK Of Prduct_Tax 
	private int sortId;
	private int delStatus;
	
	private float minQty;
	
	private int shelfLife;
	
	private int isReturnAllow;
	private float retPer;
	
	private int isActive;
	
	private int uomId;  //FK Of UOM 
	
	private String shortName;
	private String shapeId; //FK Of Shape
	
	private int allowSameDayDelivery;
	
	private String sameDayTimeAllowedSlot; //FK Of Time_slot(multiple)
	
	private int prodTypeId; //FK Of Product_type
	
	private String availInWeights; 
	
	private String flavourIds;  //FK Of Flavourid(multiple)
	
	private int prodStatusId;	//FK Of Status
	
	private int bookBefore; //No of days 
	
	private String eventsIds; //FK Of Events id(multiple)
	
	private int isCharLimit;
	private int noOfCharsForAlphaCake;
	
	private int allowCoverPhotoUpload;
	
	private int allowBasePhotoUpload;
	
	private int allowSpecialInstruction;
	
	private int allowMsgOnCake;
	private int noOfCharsOnCake;
	
	private int isUsed;
	//private int delStatus;
	private int isSlotUsed; 
	
	
	private int typeOfBread;
	private int typeOfCream;
	
	private int layeringCream;
	private int toppingCream;
	
	private String productDesc;
	
	private String ingerdiants;
	
	private String applicableTags;
	
	private int companyId;	//FK(company_id)
	
	private String prodImagePrimary; //primary image of product
	
	private String productImages; //comma sep image names
	
	private String isVeg; //0 Veg 1 non Veg 2 Both 14-09-2020
	private int prepTime; //Cake Preparation time in minutes 14-09-2020
	private int rateSettingType; //0apply_rate_per_UOM/1apply_rate_per_KG/2apply_rate_as_per _filter		
	
	private int exInt1;
	private int exInt2;
	private int exInt3;
	
	private String exVar1;
	private String exVar2;
	private String exVar3;
	private String exVar4;
	
	private float exFloat1;
	private float exFloat2;
	private float exFloat3;
	
	private String exDate1;
	private String exDate2;
	
	private int makerUserId;
	private String updtDttime;
	
	private String insertDttime;
	private int copyItemId;
	
	private int maxWt;
	private int isChecked;
	private int hpStatusDetailId;
	private int sortNo;

	public String getIsVeg() {
		return isVeg;
	}
	public void setIsVeg(String isVeg) {
		this.isVeg = isVeg;
	}
	public int getPrepTime() {
		return prepTime;
	}
	public void setPrepTime(int prepTime) {
		this.prepTime = prepTime;
	}
	public int getRateSettingType() {
		return rateSettingType;
	}
	public void setRateSettingType(int rateSettingType) {
		this.rateSettingType = rateSettingType;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getProdCatId() {
		return prodCatId;
	}
	public void setProdCatId(int prodCatId) {
		this.prodCatId = prodCatId;
	}
	public int getProdSubCatId() {
		return prodSubCatId;
	}
	public void setProdSubCatId(int prodSubCatId) {
		this.prodSubCatId = prodSubCatId;
	}
	public int getTaxId() {
		return taxId;
	}
	public void setTaxId(int taxId) {
		this.taxId = taxId;
	}
	public int getSortId() {
		return sortId;
	}
	public void setSortId(int sortId) {
		this.sortId = sortId;
	}
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	public float getMinQty() {
		return minQty;
	}
	public void setMinQty(float minQty) {
		this.minQty = minQty;
	}
	public int getShelfLife() {
		return shelfLife;
	}
	public void setShelfLife(int shelfLife) {
		this.shelfLife = shelfLife;
	}
	public int getIsReturnAllow() {
		return isReturnAllow;
	}
	public void setIsReturnAllow(int isReturnAllow) {
		this.isReturnAllow = isReturnAllow;
	}
	public float getRetPer() {
		return retPer;
	}
	public void setRetPer(float retPer) {
		this.retPer = retPer;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public int getUomId() {
		return uomId;
	}
	public void setUomId(int uomId) {
		this.uomId = uomId;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getShapeId() {
		return shapeId;
	}
	public void setShapeId(String shapeId) {
		this.shapeId = shapeId;
	}
	public int getAllowSameDayDelivery() {
		return allowSameDayDelivery;
	}
	public void setAllowSameDayDelivery(int allowSameDayDelivery) {
		this.allowSameDayDelivery = allowSameDayDelivery;
	}
	public String getSameDayTimeAllowedSlot() {
		return sameDayTimeAllowedSlot;
	}
	public void setSameDayTimeAllowedSlot(String sameDayTimeAllowedSlot) {
		this.sameDayTimeAllowedSlot = sameDayTimeAllowedSlot;
	}
	public int getProdTypeId() {
		return prodTypeId;
	}
	public void setProdTypeId(int prodTypeId) {
		this.prodTypeId = prodTypeId;
	}
	public String getAvailInWeights() {
		return availInWeights;
	}
	public void setAvailInWeights(String availInWeights) {
		this.availInWeights = availInWeights;
	}
	public String getFlavourIds() {
		return flavourIds;
	}
	public void setFlavourIds(String flavourIds) {
		this.flavourIds = flavourIds;
	}
	public int getProdStatusId() {
		return prodStatusId;
	}
	public void setProdStatusId(int prodStatusId) {
		this.prodStatusId = prodStatusId;
	}
	public int getBookBefore() {
		return bookBefore;
	}
	public void setBookBefore(int bookBefore) {
		this.bookBefore = bookBefore;
	}
	public String getEventsIds() {
		return eventsIds;
	}
	public void setEventsIds(String eventsIds) {
		this.eventsIds = eventsIds;
	}
	public int getIsCharLimit() {
		return isCharLimit;
	}
	public void setIsCharLimit(int isCharLimit) {
		this.isCharLimit = isCharLimit;
	}
	public int getNoOfCharsForAlphaCake() {
		return noOfCharsForAlphaCake;
	}
	public void setNoOfCharsForAlphaCake(int noOfCharsForAlphaCake) {
		this.noOfCharsForAlphaCake = noOfCharsForAlphaCake;
	}
	public int getAllowCoverPhotoUpload() {
		return allowCoverPhotoUpload;
	}
	public void setAllowCoverPhotoUpload(int allowCoverPhotoUpload) {
		this.allowCoverPhotoUpload = allowCoverPhotoUpload;
	}
	public int getAllowBasePhotoUpload() {
		return allowBasePhotoUpload;
	}
	public void setAllowBasePhotoUpload(int allowBasePhotoUpload) {
		this.allowBasePhotoUpload = allowBasePhotoUpload;
	}
	public int getAllowSpecialInstruction() {
		return allowSpecialInstruction;
	}
	public void setAllowSpecialInstruction(int allowSpecialInstruction) {
		this.allowSpecialInstruction = allowSpecialInstruction;
	}
	public int getAllowMsgOnCake() {
		return allowMsgOnCake;
	}
	public void setAllowMsgOnCake(int allowMsgOnCake) {
		this.allowMsgOnCake = allowMsgOnCake;
	}
	public int getNoOfCharsOnCake() {
		return noOfCharsOnCake;
	}
	public void setNoOfCharsOnCake(int noOfCharsOnCake) {
		this.noOfCharsOnCake = noOfCharsOnCake;
	}
	public int getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}
	public int getIsSlotUsed() {
		return isSlotUsed;
	}
	public void setIsSlotUsed(int isSlotUsed) {
		this.isSlotUsed = isSlotUsed;
	}
	public int getTypeOfBread() {
		return typeOfBread;
	}
	public void setTypeOfBread(int typeOfBread) {
		this.typeOfBread = typeOfBread;
	}
	public int getTypeOfCream() {
		return typeOfCream;
	}
	public void setTypeOfCream(int typeOfCream) {
		this.typeOfCream = typeOfCream;
	}
	public int getLayeringCream() {
		return layeringCream;
	}
	public void setLayeringCream(int layeringCream) {
		this.layeringCream = layeringCream;
	}
	public int getToppingCream() {
		return toppingCream;
	}
	public void setToppingCream(int toppingCream) {
		this.toppingCream = toppingCream;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getIngerdiants() {
		return ingerdiants;
	}
	public void setIngerdiants(String ingerdiants) {
		this.ingerdiants = ingerdiants;
	}
	public String getApplicableTags() {
		return applicableTags;
	}
	public void setApplicableTags(String applicableTags) {
		this.applicableTags = applicableTags;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getProdImagePrimary() {
		return prodImagePrimary;
	}
	public void setProdImagePrimary(String prodImagePrimary) {
		this.prodImagePrimary = prodImagePrimary;
	}
	public String getProductImages() {
		return productImages;
	}
	public void setProductImages(String productImages) {
		this.productImages = productImages;
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
	
	public int getMakerUserId() {
		return makerUserId;
	}
	public void setMakerUserId(int makerUserId) {
		this.makerUserId = makerUserId;
	}
	public String getUpdtDttime() {
		return updtDttime;
	}
	public void setUpdtDttime(String updtDttime) {
		this.updtDttime = updtDttime;
	}
	
	public String getInsertDttime() {
		return insertDttime;
	}
	public void setInsertDttime(String insertDttime) {
		this.insertDttime = insertDttime;
	}
	public int getCopyItemId() {
		return copyItemId;
	}
	public void setCopyItemId(int copyItemId) {
		this.copyItemId = copyItemId;
	}
	public int getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(int isChecked) {
		this.isChecked = isChecked;
	}
	public int getMaxWt() {
		return maxWt;
	}
	public void setMaxWt(int maxWt) {
		this.maxWt = maxWt;
	}
	public int getHpStatusDetailId() {
		return hpStatusDetailId;
	}
	public void setHpStatusDetailId(int hpStatusDetailId) {
		this.hpStatusDetailId = hpStatusDetailId;
	}
	public int getSortNo() {
		return sortNo;
	}
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	@Override
	public String toString() {
		return "ConfigHomePageProduct [productId=" + productId + ", productCode=" + productCode + ", productName="
				+ productName + ", prodCatId=" + prodCatId + ", prodSubCatId=" + prodSubCatId + ", taxId=" + taxId
				+ ", sortId=" + sortId + ", delStatus=" + delStatus + ", minQty=" + minQty + ", shelfLife=" + shelfLife
				+ ", isReturnAllow=" + isReturnAllow + ", retPer=" + retPer + ", isActive=" + isActive + ", uomId="
				+ uomId + ", shortName=" + shortName + ", shapeId=" + shapeId + ", allowSameDayDelivery="
				+ allowSameDayDelivery + ", sameDayTimeAllowedSlot=" + sameDayTimeAllowedSlot + ", prodTypeId="
				+ prodTypeId + ", availInWeights=" + availInWeights + ", flavourIds=" + flavourIds + ", prodStatusId="
				+ prodStatusId + ", bookBefore=" + bookBefore + ", eventsIds=" + eventsIds + ", isCharLimit="
				+ isCharLimit + ", noOfCharsForAlphaCake=" + noOfCharsForAlphaCake + ", allowCoverPhotoUpload="
				+ allowCoverPhotoUpload + ", allowBasePhotoUpload=" + allowBasePhotoUpload
				+ ", allowSpecialInstruction=" + allowSpecialInstruction + ", allowMsgOnCake=" + allowMsgOnCake
				+ ", noOfCharsOnCake=" + noOfCharsOnCake + ", isUsed=" + isUsed + ", isSlotUsed=" + isSlotUsed
				+ ", typeOfBread=" + typeOfBread + ", typeOfCream=" + typeOfCream + ", layeringCream=" + layeringCream
				+ ", toppingCream=" + toppingCream + ", productDesc=" + productDesc + ", ingerdiants=" + ingerdiants
				+ ", applicableTags=" + applicableTags + ", companyId=" + companyId + ", prodImagePrimary="
				+ prodImagePrimary + ", productImages=" + productImages + ", isVeg=" + isVeg + ", prepTime=" + prepTime
				+ ", rateSettingType=" + rateSettingType + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exInt3="
				+ exInt3 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exVar3=" + exVar3 + ", exVar4=" + exVar4
				+ ", exFloat1=" + exFloat1 + ", exFloat2=" + exFloat2 + ", exFloat3=" + exFloat3 + ", exDate1="
				+ exDate1 + ", exDate2=" + exDate2 + ", makerUserId=" + makerUserId + ", updtDttime=" + updtDttime
				+ ", insertDttime=" + insertDttime + ", copyItemId=" + copyItemId + ", maxWt=" + maxWt + ", isChecked="
				+ isChecked + ", hpStatusDetailId=" + hpStatusDetailId + ", sortNo=" + sortNo + "]";
	}
	
}
