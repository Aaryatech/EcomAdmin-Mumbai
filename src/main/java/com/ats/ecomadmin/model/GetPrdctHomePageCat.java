package com.ats.ecomadmin.model;

import java.util.List;

public class GetPrdctHomePageCat {
	List<Category> categoryList;
	List<ConfigHomePageProduct> productList;
	public List<Category> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
	public List<ConfigHomePageProduct> getProductList() {
		return productList;
	}
	public void setProductList(List<ConfigHomePageProduct> productList) {
		this.productList = productList;
	}
	@Override
	public String toString() {
		return "GetCatProduct [categoryList=" + categoryList + ", productList=" + productList + "]";
	}
	
	
}
