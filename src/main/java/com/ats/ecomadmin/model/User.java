package com.ats.ecomadmin.model;


public class User {

	private int userId;
	private int isEnrolled;
	
	
	private int userTypeId;
	private String userName;
	
	private String userMobileNo;
	private String userAddress;
	private String userEmail;

	private String regDate;
	private String birthDate;
	
	private int deptId;
	
	private String profilePic;
	private String password;
	
	private int isActive;
	private int delStatus;
	
	private int companyId;
	
	private int makerUserId;
	private String updtDttime;
	private String insertDttime;
	
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
	
	
	
	public int getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(int userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserMobileNo() {
		return userMobileNo;
	}

	public void setUserMobileNo(String userMobileNo) {
		this.userMobileNo = userMobileNo;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getIsEnrolled() {
		return isEnrolled;
	}

	public void setIsEnrolled(int isEnrolled) {
		this.isEnrolled = isEnrolled;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", isEnrolled=" + isEnrolled + ", userTypeId=" + userTypeId + ", userName="
				+ userName + ", userMobileNo=" + userMobileNo + ", userAddress=" + userAddress + ", userEmail="
				+ userEmail + ", regDate=" + regDate + ", birthDate=" + birthDate + ", deptId=" + deptId
				+ ", profilePic=" + profilePic + ", password=" + password + ", isActive=" + isActive + ", delStatus="
				+ delStatus + ", companyId=" + companyId + ", makerUserId=" + makerUserId + ", updtDttime=" + updtDttime
				+ ", insertDttime=" + insertDttime + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exInt3=" + exInt3
				+ ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exVar3=" + exVar3 + ", exVar4=" + exVar4
				+ ", exFloat1=" + exFloat1 + ", exFloat2=" + exFloat2 + ", exFloat3=" + exFloat3 + ", exDate1="
				+ exDate1 + ", exDate2=" + exDate2 + "]";
	}
	
	
	
	
}
