package com.ats.ecomadmin.model;

import java.util.List;


 
public class CategoryProduct {
	
	Category cat;
	
	List<ProductMaster> productList;

	public Category getCat() {
		return cat;
	}

	public void setCat(Category cat) {
		this.cat = cat;
	}

	public List<ProductMaster> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductMaster> productList) {
		this.productList = productList;
	}
	
	
	
	@Override
	public String toString() {
		return "CategoryProduct [cat=" + cat + ", productList=" + productList + "]";
	}
	
}
