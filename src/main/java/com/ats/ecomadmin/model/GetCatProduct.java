package com.ats.ecomadmin.model;

import java.util.List;

public class GetCatProduct {
	List<Category> categoryList;
	List<ProductMaster> productList;
	public List<Category> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
	public List<ProductMaster> getProductList() {
		return productList;
	}
	public void setProductList(List<ProductMaster> productList) {
		this.productList = productList;
	}
	@Override
	public String toString() {
		return "GetCatProduct [categoryList=" + categoryList + ", productList=" + productList + "]";
	}
	
	
}
