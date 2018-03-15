package com.tcsb.order.VO;

import java.util.Date;
import java.util.List;

import com.tcsb.distributionaddress.entity.TcsbDistributionAddressEntity;
import com.tcsb.printer.entity.TcsbPrinterEntity;


public class TcsbOrderPrintVO {
	private String orderId;
	private String note;
	private Double boxFeePrice;
	private Double distributionPrice;
	private Double shopDisPrice;
	private Double userDisPrice;
	private Double totalPrice;
	private Double toPayPrice;
	//下单时间
	private String createTime;
	//配送地址
	private TcsbDistributionAddressEntity tcsbDistributionAddressEntity;
	//订单号
	private String orderNo;
	private List<TcsbOrderItemPrintVO> tcsbOrderItemPrintVOs;
	private String shopName;
	//支付时间
	private String payTime;
	private List<TcsbPrinterEntity> tcsbPrinterEntities;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<TcsbPrinterEntity> getTcsbPrinterEntities() {
		return tcsbPrinterEntities;
	}

	public void setTcsbPrinterEntities(List<TcsbPrinterEntity> tcsbPrinterEntities) {
		this.tcsbPrinterEntities = tcsbPrinterEntities;
	}

	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Double getBoxFeePrice() {
		return boxFeePrice;
	}
	public void setBoxFeePrice(Double boxFeePrice) {
		this.boxFeePrice = boxFeePrice;
	}
	public Double getDistributionPrice() {
		return distributionPrice;
	}
	public void setDistributionPrice(Double distributionPrice) {
		this.distributionPrice = distributionPrice;
	}
	public Double getShopDisPrice() {
		return shopDisPrice;
	}
	public void setShopDisPrice(Double shopDisPrice) {
		this.shopDisPrice = shopDisPrice;
	}
	public Double getUserDisPrice() {
		return userDisPrice;
	}
	public void setUserDisPrice(Double userDisPrice) {
		this.userDisPrice = userDisPrice;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Double getToPayPrice() {
		return toPayPrice;
	}
	public void setToPayPrice(Double toPayPrice) {
		this.toPayPrice = toPayPrice;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public TcsbDistributionAddressEntity getTcsbDistributionAddressEntity() {
		return tcsbDistributionAddressEntity;
	}
	public void setTcsbDistributionAddressEntity(
			TcsbDistributionAddressEntity tcsbDistributionAddressEntity) {
		this.tcsbDistributionAddressEntity = tcsbDistributionAddressEntity;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public List<TcsbOrderItemPrintVO> getTcsbOrderItemPrintVOs() {
		return tcsbOrderItemPrintVOs;
	}
	public void setTcsbOrderItemPrintVOs(
			List<TcsbOrderItemPrintVO> tcsbOrderItemPrintVOs) {
		this.tcsbOrderItemPrintVOs = tcsbOrderItemPrintVOs;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
}
