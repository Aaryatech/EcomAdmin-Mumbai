package com.ats.ecomadmin.model;

//Author-Sachin 
//Created On 12-09-2020
//Modified On 12-09-2020
//Modified By Sachin
//Desc- used to show User list for assign Role
public class GetUser {

	private int userId;

	private String userName;

	private String userMobileNo;
	private String userEmail;

	private int roleId;
	private String roleName;
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	@Override
	public String toString() {
		return "GetUser [userId=" + userId + ", userName=" + userName + ", userMobileNo=" + userMobileNo
				+ ", userEmail=" + userEmail + ", roleId=" + roleId + ", roleName=" + roleName + "]";
	}
	
	
	
	
	
}
