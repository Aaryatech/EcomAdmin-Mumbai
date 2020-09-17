package com.ats.ecomadmin.model.offer;

public class Images {

	private int imagesId;
	private int docType;
	private int docId;
	private String imageName;
	private float seqNo;
	private int isActive;
	private int delStatus;
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

	public Images() {
		super();
	}

	public Images(int imagesId, int docType, int docId, String imageName, float seqNo, int isActive, int delStatus,
			int exInt1, int exInt2, int exInt3, String exVar1, String exVar2, String exVar3, String exVar4,
			float exFloat1, float exFloat2, float exFloat3) {
		super();
		this.imagesId = imagesId;
		this.docType = docType;
		this.docId = docId;
		this.imageName = imageName;
		this.seqNo = seqNo;
		this.isActive = isActive;
		this.delStatus = delStatus;
		this.exInt1 = exInt1;
		this.exInt2 = exInt2;
		this.exInt3 = exInt3;
		this.exVar1 = exVar1;
		this.exVar2 = exVar2;
		this.exVar3 = exVar3;
		this.exVar4 = exVar4;
		this.exFloat1 = exFloat1;
		this.exFloat2 = exFloat2;
		this.exFloat3 = exFloat3;
	}

	public int getImagesId() {
		return imagesId;
	}

	public void setImagesId(int imagesId) {
		this.imagesId = imagesId;
	}

	public int getDocType() {
		return docType;
	}

	public void setDocType(int docType) {
		this.docType = docType;
	}

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public float getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(float seqNo) {
		this.seqNo = seqNo;
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

	@Override
	public String toString() {
		return "Images [imagesId=" + imagesId + ", docType=" + docType + ", docId=" + docId + ", imageName=" + imageName
				+ ", seqNo=" + seqNo + ", isActive=" + isActive + ", delStatus=" + delStatus + ", exInt1=" + exInt1
				+ ", exInt2=" + exInt2 + ", exInt3=" + exInt3 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2
				+ ", exVar3=" + exVar3 + ", exVar4=" + exVar4 + ", exFloat1=" + exFloat1 + ", exFloat2=" + exFloat2
				+ ", exFloat3=" + exFloat3 + "]";
	}

}
