package com.tcsb.order.VO;

public class TcsbOrderItemVO {
	private String foodTypeId;
	private String foodId;
	private Integer count;
	private String standardId;
	private Double price;
	private String tasteId;
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
	public String getStandardId() {
		return standardId;
	}
	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getTasteId() {
		return tasteId;
	}
	public void setTasteId(String tasteId) {
		this.tasteId = tasteId;
	}
	
}
