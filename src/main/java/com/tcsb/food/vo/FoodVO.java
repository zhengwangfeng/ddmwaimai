package com.tcsb.food.vo;

public class FoodVO {
	private String id;
	private String foodTypeId;
	private String name;
	private Double price;
	private String img;
	private Integer num;
	private Integer orders;
	private boolean canOrder;
	
	public boolean isCanOrder() {
		return canOrder;
	}
	public void setCanOrder(boolean canOrder) {
		this.canOrder = canOrder;
	}
	private boolean foodstandard;
	//就否参与优惠
	private Boolean isNotDis;
	private String unitName;
	//库存量
	private Integer stock;
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Boolean getIsNotDis() {
		return isNotDis;
	}
	public void setIsNotDis(Boolean isNotDis) {
		this.isNotDis = isNotDis;
	}
	private boolean foodTaste;
	public Integer getOrders() {
		return orders;
	}
	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	
	
	public String getId() {
		return id;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFoodTypeId() {
		return foodTypeId;
	}
	public void setFoodTypeId(String foodTypeId) {
		this.foodTypeId = foodTypeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public boolean isFoodTaste() {
		return foodTaste;
	}
	public void setFoodTaste(boolean foodTaste) {
		this.foodTaste = foodTaste;
	}
	public boolean isFoodstandard() {
		return foodstandard;
	}
	public void setFoodstandard(boolean foodstandard) {
		this.foodstandard = foodstandard;
	}

	
}
