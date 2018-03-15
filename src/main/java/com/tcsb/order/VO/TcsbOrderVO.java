package com.tcsb.order.VO;

import java.util.List;

public class TcsbOrderVO {
	private String openId;
	private String shopId;
	private String note;
	private Double boxFeePrice;
	private Double distributionPrice;
	private String discountActivityId;
	private String couponId;
	private String distributionAddressId;
	private List<TcsbOrderItemVO> tcsbOrderItemVOs;
	
	public String getDistributionAddressId() {
		return distributionAddressId;
	}
	public void setDistributionAddressId(String distributionAddressId) {
		this.distributionAddressId = distributionAddressId;
	}
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
	public String getNote() {
		return note;
	}
	public String getOpenId() {
		return openId;
	}
	public String getShopId() {
		return shopId;
	}
	public List<TcsbOrderItemVO> getTcsbOrderItemVOs() {
		return tcsbOrderItemVOs;
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
	public void setNote(String note) {
		this.note = note;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public void setTcsbOrderItemVOs(List<TcsbOrderItemVO> tcsbOrderItemVOs) {
		this.tcsbOrderItemVOs = tcsbOrderItemVOs;
	}
}
