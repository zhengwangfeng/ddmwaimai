package com.tcsb.order.VO;

public class TcsbOrderItemReturnVO {
	private String foodTypeId;
	private String foodId;
	private Integer count;
	private Double price;
	private String foodName;
	private String tasteName;
	private String standardName;
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
	public String getTasteName() {
		return tasteName;
	}
	public void setTasteName(String tasteName) {
		this.tasteName = tasteName;
	}
	public String getStandardName() {
		return standardName;
	}
	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}
	public String getFoodTypeId() {
		return foodTypeId;
	}
	public void setFoodTypeId(String foodTypeId) {
		this.foodTypeId = foodTypeId;
	}
	public String getFoodId() {
		return foodId;
	}
	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
}
