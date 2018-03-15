package com.tcsb.order.VO;

import java.util.Date;
import java.util.List;

import com.tcsb.distributionaddress.entity.TcsbDistributionAddressEntity;

public class TcsbOrderReturnVO {
	private String id;
	private String note;
	private Double boxFeePrice;
	private Double distributionPrice;
	private Double shopDisPrice;
	private Double userDisPrice;
	private Double totalPrice;
	private Double toPayPrice;
	//下单时间
	private Date createTime;
	//配送地址
	private TcsbDistributionAddressEntity tcsbDistributionAddressEntity;
	//订单号
	private String orderNo;
	private List<TcsbOrderItemReturnVO> tcsbOrderItemReturnVOs;
	private String shopName;
	private String phone;
	private String payStatus;
	private String status;
	private String orderIstake;
	
	
	public String getOrderIstake() {
		return orderIstake;
	}
	public void setOrderIstake(String orderIstake) {
		this.orderIstake = orderIstake;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public Double getBoxFeePrice() {
		return boxFeePrice;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public Double getDistributionPrice() {
		return distributionPrice;
	}
	public String getNote() {
		return note;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public String getPhone() {
		return phone;
	}
	public Double getShopDisPrice() {
		return shopDisPrice;
	}
	public String getShopName() {
		return shopName;
	}
	
	public TcsbDistributionAddressEntity getTcsbDistributionAddressEntity() {
		return tcsbDistributionAddressEntity;
	}
	public List<TcsbOrderItemReturnVO> getTcsbOrderItemReturnVOs() {
		return tcsbOrderItemReturnVOs;
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
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setDistributionPrice(Double distributionPrice) {
		this.distributionPrice = distributionPrice;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setShopDisPrice(Double shopDisPrice) {
		this.shopDisPrice = shopDisPrice;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public void setTcsbDistributionAddressEntity(
			TcsbDistributionAddressEntity tcsbDistributionAddressEntity) {
		this.tcsbDistributionAddressEntity = tcsbDistributionAddressEntity;
	}
	public void setTcsbOrderItemReturnVOs(
			List<TcsbOrderItemReturnVO> tcsbOrderItemReturnVOs) {
		this.tcsbOrderItemReturnVOs = tcsbOrderItemReturnVOs;
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
