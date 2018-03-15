package com.tcsb.order.VO;

public class TcsbOrderItemPrintVO {
   private String foodName;
   private Integer count;
   private Double price;
   private String unitName;
   private String tasteName;
   private String foodId;
   private String standardName;
   
public String getStandardName() {
	return standardName;
}
public void setStandardName(String standardName) {
	this.standardName = standardName;
}
public String getFoodId() {
	return foodId;
}
public String getUnitName() {
	return unitName;
}
public void setUnitName(String unitName) {
	this.unitName = unitName;
}
public void setFoodId(String foodId) {
	this.foodId = foodId;
}
public String getFoodName() {
	return foodName;
}
public void setFoodName(String foodName) {
	this.foodName = foodName;
}
public Double getPrice() {
	return price;
}
public void setPrice(Double price) {
	this.price = price;
}
public Integer getCount() {
	return count;
}
public void setCount(Integer count) {
	this.count = count;
}
public String getTasteName() {
	return tasteName;
}
public void setTasteName(String tasteName) {
	this.tasteName = tasteName;
}

}
