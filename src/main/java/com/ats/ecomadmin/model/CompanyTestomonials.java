package com.ats.ecomadmin.model;

public class CompanyTestomonials {

	private int id;
	
	
	private String name;
	
	private String designation;
	
	private String message;
	
	private String photo;
	
	private int isActive;
	
	private int delStatus;
	
	private int exInt1,exInt2;
	
	private String exVar1,exVar2;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDesignation() {
		return designation;
	}

	public String getMessage() {
		return message;
	}

	public String getPhoto() {
		return photo;
	}

	public int getIsActive() {
		return isActive;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public int getExInt1() {
		return exInt1;
	}

	public int getExInt2() {
		return exInt2;
	}

	public String getExVar1() {
		return exVar1;
	}

	public String getExVar2() {
		return exVar2;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}

	public void setExInt2(int exInt2) {
		this.exInt2 = exInt2;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}

	@Override
	public String toString() {
		return "CompanyTestomonials [id=" + id + ", name=" + name + ", designation=" + designation + ", message="
				+ message + ", photo=" + photo + ", isActive=" + isActive + ", delStatus=" + delStatus + ", exInt1="
				+ exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + "]";
	}
	
	
	
	
	
	
	
}
