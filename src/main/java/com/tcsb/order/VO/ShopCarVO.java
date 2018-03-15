package com.tcsb.order.VO;

import java.util.List;

public class ShopCarVO {
	private String shopId;
	private String openId;
	private Double boxFeePrice;
	private Double distributionPrice;
	private Double shopDisPrice;
	private Double userDisPrice;
	private Double totalPrice;
	private String discountActivityId;
	private String couponId;
	private Double toPayPrice;
	List<ShopCarItemVO> shopCarItemVOs;
	public Double getBoxFeePrice() {
		return boxFeePrice;
	}
	public String getCouponId() {
		return couponId;
	}
	public String getDiscountActivityId() {
		return discountActivityId;
	}
	public Double getDistributionPrice() {
		return distributionPrice;
	}
	public String getOpenId() {
		return openId;
	}
	public List<ShopCarItemVO> getShopCarItemVOs() {
		return shopCarItemVOs;
	}
	public Double getShopDisPrice() {
		return shopDisPrice;
	}
	public String getShopId() {
		return shopId;
	}
	public Double getToPayPrice() {
		return toPayPrice;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public Double getUserDisPrice() {
		return userDisPrice;
	}
	public void setBoxFeePrice(Double boxFeePrice) {
		this.boxFeePrice = boxFeePrice;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public void setDiscountActivityId(String discountActivityId) {
		this.discountActivityId = discountActivityId;
	}
	public void setDistributionPrice(Double distributionPrice) {
		this.distributionPrice = distributionPrice;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public void setShopCarItemVOs(List<ShopCarItemVO> shopCarItemVOs) {
		this.shopCarItemVOs = shopCarItemVOs;
	}
	public void setShopDisPrice(Double shopDisPrice) {
		this.shopDisPrice = shopDisPrice;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public void setToPayPrice(Double toPayPrice) {
		this.toPayPrice = toPayPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public void setUserDisPrice(Double userDisPrice) {
		this.userDisPrice = userDisPrice;
	}
}
