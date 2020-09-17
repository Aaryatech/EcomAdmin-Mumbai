package com.ats.ecomadmin.model;

public class GetRequreProduct {
	
	
	
	private String id;
	
	
	private int productId;
	
	private int catId;
	
	private int configId;

 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public int getConfigId() {
		return configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	@Override
	public String toString() {
		return "GetRequreProduct [id=" + id + ", productId=" + productId + ", catId=" + catId + ", configId=" + configId
				+ "]";
	}

	 
	
	
	
	

}
