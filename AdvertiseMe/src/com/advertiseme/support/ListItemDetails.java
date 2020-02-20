package com.advertiseme.support;

public class ListItemDetails {

	public String getTextHeader() {
		return textHeader;
	}

	public void setTextHeader(String txtHeader) {
		this.textHeader = txtHeader;
	}

	public String getTextNormal() {
		return textNormal;
	}

	public void setTextNormal(String txtNormal) {
		this.textNormal = txtNormal;
	}

	public String getTextFooter() {
		return textFooter;
	}

	public void setTextFooter(String txtFooter) {
		this.textFooter = txtFooter;
	}

	public int getImageNumber() {
		return imageNumber;
	}

	public void setImageNumber(int imageNumber) {
		this.imageNumber = imageNumber;
	}

	public String getTextExtra() {
		return textExtra;
	}

	public void setTextExtra(String txtExtra) {
		this.textExtra = txtExtra;
	}
	public String getTextAdditional() {
		return textAdditional;
	}

	public void setTextAdditional(String textAdditional) {
		this.textAdditional = textAdditional;
	}
	public String getTextStatus() {
		return textStatus;
	}

	public void setTextStatus(String textStatus) {
		this.textStatus = textStatus;
	}
	public float getItemRating() {
		return itemRating;
	}

	public void setItemRating(float itemRating) {
		this.itemRating = itemRating;
	}
	
	public Boolean  getConditionStatus() {
		return conditionStatus;
	}

	public void setConditionStatus(Boolean conditionStatus) {
		this.conditionStatus = conditionStatus;
	}
	public int  getConditionOption() {
		return condistionOption;
	}

	public void setConditionOption(int condistionOption) {
		this.condistionOption = condistionOption;
	}
	
	public String  getItemId() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	
	public String getPreviewURL() {
		return previewURL;
	}

	public void setPreviewURL(String previewURL) {
		this.previewURL = previewURL;
	}
	public String [] getDataBundle() {
		return arrDataBundle;
	}

	public void setDataBundle(String [] arrDataBundle) {
		this.arrDataBundle = arrDataBundle;
	}
	
	private String textHeader;
	private String textNormal;
	private String textFooter;
	private String textStatus;
	
	private String textExtra;	
	private String textAdditional;
	
	
	private int imageNumber;
	private float itemRating;
	private Boolean conditionStatus;
	private int condistionOption;
	
	private String itemID;
	private String previewURL;
	
	private String [] arrDataBundle;
}
