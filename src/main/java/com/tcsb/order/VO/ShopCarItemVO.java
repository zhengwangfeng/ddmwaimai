package com.tcsb.order.VO;

public class ShopCarItemVO {
	private String foodId;
	private String foodTypeId;
	private Integer count;
	private String standardId;
	private String tasteName;
	private Double price;
	private String foodName;
	private String standardName;
	private String tasteId;
	private Boolean isNotDis;
	
	public Boolean getIsNotDis() {
		return isNotDis;
	}
	public void setIsNotDis(Boolean isNotDis) {
		this.isNotDis = isNotDis;
	}
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public Integer getCount() {
		return count;
	}
	//是否有规格
	public String getFoodId() {
		return foodId;
	}

	public String getFoodTypeId() {
		return foodTypeId;
	}
	public Double getPrice() {
		return price;
	}
	
	public String getStandardId() {
		return standardId;
	}
	public String getStandardName() {
		return standardName;
	}
	public String getTasteId() {
		return tasteId;
	}
	public String getTasteName() {
		return tasteName;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}
	public void setFoodTypeId(String foodTypeId) {
		this.foodTypeId = foodTypeId;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}
	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}
	public void setTasteId(String tasteId) {
		this.tasteId = tasteId;
	}
	public void setTasteName(String tasteName) {
		this.tasteName = tasteName;
	}
}
