package com.ats.ecomadmin.model;

import java.util.List;

//Author -Mahendra
//Created on -08-02-2021
//Desc - to show product master data to front end pages.
//Modified on -30-10-2020
//Modified By --08-02-2020

public class ProdListExl {

	private String prodUuid;

	private int productId;
	private String productCode;
	private String productName;
	private int prodCatId;
	private int prodSubCatId;
	private int taxId;

	private float minQty;

	private int uomId;
	private String shortName;
	private String shapeId; //changed to String from int on 27-10-2020

	private int allowSameDayDelivery;
	private String sameDayTimeAllowedSlot;
	private int prodTypeId;

	private String availInWeights;

	private String flavourIds;

	private int prodStatusId;
	private int bookBefore;

	private String eventsIds;

	private int isCharLimit;
	private int noOfCharsForAlphaCake;

	private int allowCoverPhotoUpload;

	private int allowBasePhotoUpload;

	private int allowSpecialInstruction;

	private int allowMsgOnCake;
	private int noOfCharsOnCake;

	private String productDesc;

	private String ingerdiants;

	private String applicableTags;

	private int companyId; 

	private String prodImagePrimary;

	private String productImages; 

	private String isVeg; 
	private int prepTime; 
	private int rateSettingType;

	private int maxWt;

	private String exVar1;
	private String exVar2;

	private float displayRate;
	private float actualRate;

	private int frachaseConfigId; 
	private int isHomePageProd; 

	private int configHeaderId; 
	private String taxName;
	private String hsnCode;
	
	private float cgstPer;
	private float sgstPer;
	private float igstPer;
	private float cessPer;
	private float totalTaxPer;
	
	private String subCatName;
	
	private int sortId;
	private int shelfLife;
	
	private int isReturnAllow;
	private float retPer;
	
	private int isSlotUsed;
	
	private int toppingCream;
	private int layeringCream;

	private int typeOfBread;
	private int typeOfCream;
	
	private String uomShowName;
	
	private String sameDayTimeSlotNames;
	
	private String eventNames;
	private String flavorNames;
	private String appliTagNames;
	private String shapeNames;
	private String prodTypeName;
	private String prodStatusName;
	private String toppingCreamNames;
	private String layeringCreamNames;
	
	private String creamTypeName;
	private String breadTypeName;
	
	private String vegNonvegName; 
	
	private int defaultFlavorId;
	private int defaultVegnonvegId;
	private int defaultShapeId;
	
	private float defaultPrice;
	
	private String defaultVegNonvegName;
	
	private String allFilterNames;
	
	private String defaultFlavorName;
	private String catName;
	private String defaultShapeName;

	public String getDefaultVegNonvegName() {
	return defaultVegNonvegName;
}

public void setDefaultVegNonvegName(String defaultVegNonvegName) {
	this.defaultVegNonvegName = defaultVegNonvegName;
}
	
	public float getDefaultPrice() {
		return defaultPrice;
	}

	public void setDefaultPrice(float defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

	List<FEProdDetail> prodDetailList;
	
	
	public String getProdUuid() {
		return prodUuid;
	}

	public void setProdUuid(String prodUuid) {
		this.prodUuid = prodUuid;
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

	public float getMinQty() {
		return minQty;
	}

	public void setMinQty(float minQty) {
		this.minQty = minQty;
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

	public int getMaxWt() {
		return maxWt;
	}

	public void setMaxWt(int maxWt) {
		this.maxWt = maxWt;
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

	public float getDisplayRate() {
		return displayRate;
	}

	public void setDisplayRate(float displayRate) {
		this.displayRate = displayRate;
	}

	public float getActualRate() {
		return actualRate;
	}

	public void setActualRate(float actualRate) {
		this.actualRate = actualRate;
	}

	

	public int getFrachaseConfigId() {
		return frachaseConfigId;
	}

	public void setFrachaseConfigId(int frachaseConfigId) {
		this.frachaseConfigId = frachaseConfigId;
	}

	public int getIsHomePageProd() {
		return isHomePageProd;
	}

	public void setIsHomePageProd(int isHomePageProd) {
		this.isHomePageProd = isHomePageProd;
	}

	public int getConfigHeaderId() {
		return configHeaderId;
	}

	public void setConfigHeaderId(int configHeaderId) {
		this.configHeaderId = configHeaderId;
	}


	public List<FEProdDetail> getProdDetailList() {
		return prodDetailList;
	}

	public void setProdDetailList(List<FEProdDetail> prodDetailList) {
		this.prodDetailList = prodDetailList;
	}
	
	
	
	
	
	public String getTaxName() {
		return taxName;
	}

	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public float getCgstPer() {
		return cgstPer;
	}

	public void setCgstPer(float cgstPer) {
		this.cgstPer = cgstPer;
	}

	public float getSgstPer() {
		return sgstPer;
	}

	public void setSgstPer(float sgstPer) {
		this.sgstPer = sgstPer;
	}

	public float getIgstPer() {
		return igstPer;
	}

	public void setIgstPer(float igstPer) {
		this.igstPer = igstPer;
	}

	public float getCessPer() {
		return cessPer;
	}

	public void setCessPer(float cessPer) {
		this.cessPer = cessPer;
	}

	public float getTotalTaxPer() {
		return totalTaxPer;
	}

	public void setTotalTaxPer(float totalTaxPer) {
		this.totalTaxPer = totalTaxPer;
	}

	public String getSubCatName() {
		return subCatName;
	}

	public void setSubCatName(String subCatName) {
		this.subCatName = subCatName;
	}

	public int getSortId() {
		return sortId;
	}

	public void setSortId(int sortId) {
		this.sortId = sortId;
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

	public int getIsSlotUsed() {
		return isSlotUsed;
	}

	public void setIsSlotUsed(int isSlotUsed) {
		this.isSlotUsed = isSlotUsed;
	}

	public int getToppingCream() {
		return toppingCream;
	}

	public void setToppingCream(int toppingCream) {
		this.toppingCream = toppingCream;
	}

	public int getLayeringCream() {
		return layeringCream;
	}

	public void setLayeringCream(int layeringCream) {
		this.layeringCream = layeringCream;
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

	public String getUomShowName() {
		return uomShowName;
	}

	public void setUomShowName(String uomShowName) {
		this.uomShowName = uomShowName;
	}

	public String getSameDayTimeSlotNames() {
		return sameDayTimeSlotNames;
	}

	public void setSameDayTimeSlotNames(String sameDayTimeSlotNames) {
		this.sameDayTimeSlotNames = sameDayTimeSlotNames;
	}

	public String getEventNames() {
		return eventNames;
	}

	public void setEventNames(String eventNames) {
		this.eventNames = eventNames;
	}

	public String getFlavorNames() {
		return flavorNames;
	}

	public void setFlavorNames(String flavorNames) {
		this.flavorNames = flavorNames;
	}

	public String getAppliTagNames() {
		return appliTagNames;
	}

	public void setAppliTagNames(String appliTagNames) {
		this.appliTagNames = appliTagNames;
	}

	public String getShapeNames() {
		return shapeNames;
	}

	public void setShapeNames(String shapeNames) {
		this.shapeNames = shapeNames;
	}

	public String getProdTypeName() {
		return prodTypeName;
	}

	public void setProdTypeName(String prodTypeName) {
		this.prodTypeName = prodTypeName;
	}

	public String getProdStatusName() {
		return prodStatusName;
	}

	public void setProdStatusName(String prodStatusName) {
		this.prodStatusName = prodStatusName;
	}

	public String getToppingCreamNames() {
		return toppingCreamNames;
	}

	public void setToppingCreamNames(String toppingCreamNames) {
		this.toppingCreamNames = toppingCreamNames;
	}

	public String getLayeringCreamNames() {
		return layeringCreamNames;
	}

	public void setLayeringCreamNames(String layeringCreamNames) {
		this.layeringCreamNames = layeringCreamNames;
	}

	public String getCreamTypeName() {
		return creamTypeName;
	}

	public void setCreamTypeName(String creamTypeName) {
		this.creamTypeName = creamTypeName;
	}

	public String getBreadTypeName() {
		return breadTypeName;
	}

	public void setBreadTypeName(String breadTypeName) {
		this.breadTypeName = breadTypeName;
	}

	
	
	
	public String getVegNonvegName() {
		return vegNonvegName;
	}

	public void setVegNonvegName(String vegNonvegName) {
		this.vegNonvegName = vegNonvegName;
	}

	
	
	
	
	public int getDefaultFlavorId() {
		return defaultFlavorId;
	}

	public void setDefaultFlavorId(int defaultFlavorId) {
		this.defaultFlavorId = defaultFlavorId;
	}

	public int getDefaultVegnonvegId() {
		return defaultVegnonvegId;
	}

	public void setDefaultVegnonvegId(int defaultVegnonvegId) {
		this.defaultVegnonvegId = defaultVegnonvegId;
	}

	public int getDefaultShapeId() {
		return defaultShapeId;
	}

	public void setDefaultShapeId(int defaultShapeId) {
		this.defaultShapeId = defaultShapeId;
	}

	public String getAllFilterNames() {
		return allFilterNames;
	}

	public void setAllFilterNames(String allFilterNames) {
		this.allFilterNames = allFilterNames;
	}

	public String getDefaultFlavorName() {
		return defaultFlavorName;
	}

	public void setDefaultFlavorName(String defaultFlavorName) {
		this.defaultFlavorName = defaultFlavorName;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getDefaultShapeName() {
		return defaultShapeName;
	}

	public void setDefaultShapeName(String defaultShapeName) {
		this.defaultShapeName = defaultShapeName;
	}

	@Override
	public String toString() {
		return "ProdListExl [prodUuid=" + prodUuid + ", productId=" + productId + ", productCode=" + productCode
				+ ", productName=" + productName + ", prodCatId=" + prodCatId + ", prodSubCatId=" + prodSubCatId
				+ ", taxId=" + taxId + ", minQty=" + minQty + ", uomId=" + uomId + ", shortName=" + shortName
				+ ", shapeId=" + shapeId + ", allowSameDayDelivery=" + allowSameDayDelivery
				+ ", sameDayTimeAllowedSlot=" + sameDayTimeAllowedSlot + ", prodTypeId=" + prodTypeId
				+ ", availInWeights=" + availInWeights + ", flavourIds=" + flavourIds + ", prodStatusId=" + prodStatusId
				+ ", bookBefore=" + bookBefore + ", eventsIds=" + eventsIds + ", isCharLimit=" + isCharLimit
				+ ", noOfCharsForAlphaCake=" + noOfCharsForAlphaCake + ", allowCoverPhotoUpload="
				+ allowCoverPhotoUpload + ", allowBasePhotoUpload=" + allowBasePhotoUpload
				+ ", allowSpecialInstruction=" + allowSpecialInstruction + ", allowMsgOnCake=" + allowMsgOnCake
				+ ", noOfCharsOnCake=" + noOfCharsOnCake + ", productDesc=" + productDesc + ", ingerdiants="
				+ ingerdiants + ", applicableTags=" + applicableTags + ", companyId=" + companyId
				+ ", prodImagePrimary=" + prodImagePrimary + ", productImages=" + productImages + ", isVeg=" + isVeg
				+ ", prepTime=" + prepTime + ", rateSettingType=" + rateSettingType + ", maxWt=" + maxWt + ", exVar1="
				+ exVar1 + ", exVar2=" + exVar2 + ", displayRate=" + displayRate + ", actualRate=" + actualRate
				+ ", frachaseConfigId=" + frachaseConfigId + ", isHomePageProd=" + isHomePageProd + ", configHeaderId="
				+ configHeaderId + ", taxName=" + taxName + ", hsnCode=" + hsnCode + ", cgstPer=" + cgstPer
				+ ", sgstPer=" + sgstPer + ", igstPer=" + igstPer + ", cessPer=" + cessPer + ", totalTaxPer="
				+ totalTaxPer + ", subCatName=" + subCatName + ", sortId=" + sortId + ", shelfLife=" + shelfLife
				+ ", isReturnAllow=" + isReturnAllow + ", retPer=" + retPer + ", isSlotUsed=" + isSlotUsed
				+ ", toppingCream=" + toppingCream + ", layeringCream=" + layeringCream + ", typeOfBread=" + typeOfBread
				+ ", typeOfCream=" + typeOfCream + ", uomShowName=" + uomShowName + ", sameDayTimeSlotNames="
				+ sameDayTimeSlotNames + ", eventNames=" + eventNames + ", flavorNames=" + flavorNames
				+ ", appliTagNames=" + appliTagNames + ", shapeNames=" + shapeNames + ", prodTypeName=" + prodTypeName
				+ ", prodStatusName=" + prodStatusName + ", toppingCreamNames=" + toppingCreamNames
				+ ", layeringCreamNames=" + layeringCreamNames + ", creamTypeName=" + creamTypeName + ", breadTypeName="
				+ breadTypeName + ", vegNonvegName=" + vegNonvegName + ", defaultFlavorId=" + defaultFlavorId
				+ ", defaultVegnonvegId=" + defaultVegnonvegId + ", defaultShapeId=" + defaultShapeId
				+ ", defaultPrice=" + defaultPrice + ", defaultVegNonvegName=" + defaultVegNonvegName
				+ ", allFilterNames=" + allFilterNames + ", defaultFlavorName=" + defaultFlavorName + ", catName="
				+ catName + ", defaultShapeName=" + defaultShapeName + ", prodDetailList=" + prodDetailList + "]";
	}

	
}
