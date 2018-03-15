package com.tcsb.order.entity;
import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;

import javax.xml.soap.Text;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.SequenceGenerator;

import org.jeecgframework.poi.excel.annotation.Excel;

import com.tcsb.distributionaddress.entity.TcsbDistributionAddressEntity;

/**   
 * @Title: Entity
 * @Description: 订单
 * @author onlineGenerator
 * @date 2018-01-10 11:01:52
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_order", schema = "")
@SuppressWarnings("serial")
public class TcsbOrderEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**订单编号*/
    @Excel(name="订单编号")
	private java.lang.String orderNo;
	/**下单方式*/
    @Excel(name="下单方式")
	private java.lang.Integer method;
	/**所属店铺ID*/
	private java.lang.String shopId;
	/**下单时间*/
    @Excel(name="下单时间",format = "yyyy-MM-dd")
	private java.util.Date createTime;
	/**订单状态*/
    @Excel(name="订单状态")
	private java.lang.String status; //进行中 having 已完成 completed 已取消 canceled
	/**支付状态*/
    @Excel(name="支付状态")
	private java.lang.String payStatus;
	/**支付方式*/
    @Excel(name="支付方式")
	private java.lang.String payMethod;
	/**openId*/
	private java.lang.String openId;
	/**支付时间*/
    @Excel(name="支付时间",format = "yyyy-MM-dd")
	private java.util.Date payTime;
	/**特殊说明*/
    @Excel(name="特殊说明")
	private java.lang.String note;
	/**是否已接单*/
	private java.lang.String orderIstake;
	/**优惠券ID*/
    @Excel(name="优惠券ID")
	private java.lang.String couponId;
	/**优惠活动ID*/
    @Excel(name="优惠活动ID")
	private java.lang.String discountActivityId;
	/**更新人名字*/
	private java.lang.String updateName;
	/**更新时间*/
	private java.util.Date updateDate;
	/**更新人*/
	private java.lang.String updateBy;
	/**创建人名字*/
	private java.lang.String createName;
	/**创建人*/
	private java.lang.String createBy;
	/**创建时间*/
	private java.util.Date createDate;
	
	private Double boxFeePrice;
	
	private Double distributionPrice;
	
	private Double totalPrice;
	/**需支付金额*/
	private Double toPayPrice;
	
	/*配送地址*/
	private String distributionAddressId;
	
	
	
	private Double shopDisPrice;
	private Double userDisPrice;
	
	private TcsbDistributionAddressEntity tcsbDistributionAddressEntity;
	
	@Transient
	public TcsbDistributionAddressEntity getTcsbDistributionAddressEntity() {
		return tcsbDistributionAddressEntity;
	}

	public void setTcsbDistributionAddressEntity(
			TcsbDistributionAddressEntity tcsbDistributionAddressEntity) {
		this.tcsbDistributionAddressEntity = tcsbDistributionAddressEntity;
	}

	@Transient
	public Double getShopDisPrice() {
		return shopDisPrice;
	}

	public void setShopDisPrice(Double shopDisPrice) {
		this.shopDisPrice = shopDisPrice;
	}
	@Transient
	public Double getUserDisPrice() {
		return userDisPrice;
	}

	public void setUserDisPrice(Double userDisPrice) {
		this.userDisPrice = userDisPrice;
	}

	@Column(name ="distribution_address_id",nullable=false,length=32)
	public String getDistributionAddressId() {
		return distributionAddressId;
	}

	public void setDistributionAddressId(String distributionAddressId) {
		this.distributionAddressId = distributionAddressId;
	}

	@Column(name ="box_fee_price",nullable=false,length=32)
	public Double getBoxFeePrice() {
		return boxFeePrice;
	}

	public void setBoxFeePrice(Double boxFeePrice) {
		this.boxFeePrice = boxFeePrice;
	}
	@Column(name ="distribution_price",nullable=false,length=32)
	public Double getDistributionPrice() {
		return distributionPrice;
	}

	public void setDistributionPrice(Double distributionPrice) {
		this.distributionPrice = distributionPrice;
	}

	
	@Transient
	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	@Transient
	public Double getToPayPrice() {
		return toPayPrice;
	}

	public void setToPayPrice(Double toPayPrice) {
		this.toPayPrice = toPayPrice;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ID
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	
	@Column(name ="ID",nullable=false,length=32)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  ID
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单编号
	 */
	
	@Column(name ="ORDER_NO",nullable=true,length=32)
	public java.lang.String getOrderNo(){
		return this.orderNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单编号
	 */
	public void setOrderNo(java.lang.String orderNo){
		this.orderNo = orderNo;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  下单方式
	 */
	
	@Column(name ="METHOD",nullable=true,length=10)
	public java.lang.Integer getMethod(){
		return this.method;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  下单方式
	 */
	public void setMethod(java.lang.Integer method){
		this.method = method;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属店铺ID
	 */
	
	@Column(name ="SHOP_ID",nullable=true,length=32)
	public java.lang.String getShopId(){
		return this.shopId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属店铺ID
	 */
	public void setShopId(java.lang.String shopId){
		this.shopId = shopId;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  下单时间
	 */
	
	@Column(name ="CREATE_TIME",nullable=true)
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  下单时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  订单状态
	 */
	
	@Column(name ="STATUS",nullable=true,length=10)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  订单状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  支付状态
	 */
	
	@Column(name ="PAY_STATUS",nullable=true,length=1)
	public java.lang.String getPayStatus(){
		return this.payStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  支付状态
	 */
	public void setPayStatus(java.lang.String payStatus){
		this.payStatus = payStatus;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  支付方式
	 */
	
	@Column(name ="PAY_METHOD",nullable=true,length=1)
	public java.lang.String getPayMethod(){
		return this.payMethod;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  支付方式
	 */
	public void setPayMethod(java.lang.String payMethod){
		this.payMethod = payMethod;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  openId
	 */
	
	@Column(name ="OPEN_ID",nullable=true,length=32)
	public java.lang.String getOpenId(){
		return this.openId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  openId
	 */
	public void setOpenId(java.lang.String openId){
		this.openId = openId;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  支付时间
	 */
	
	@Column(name ="PAY_TIME",nullable=true)
	public java.util.Date getPayTime(){
		return this.payTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  支付时间
	 */
	public void setPayTime(java.util.Date payTime){
		this.payTime = payTime;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  特殊说明
	 */
	
	@Column(name ="NOTE",nullable=true,length=128)
	public java.lang.String getNote(){
		return this.note;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  特殊说明
	 */
	public void setNote(java.lang.String note){
		this.note = note;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否已接单
	 */
	
	@Column(name ="ORDER_ISTAKE",nullable=true,length=1)
	public java.lang.String getOrderIstake(){
		return this.orderIstake;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否已接单
	 */
	public void setOrderIstake(java.lang.String orderIstake){
		this.orderIstake = orderIstake;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  优惠券ID
	 */
	
	@Column(name ="COUPON_ID",nullable=true,length=32)
	public java.lang.String getCouponId(){
		return this.couponId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  优惠券ID
	 */
	public void setCouponId(java.lang.String couponId){
		this.couponId = couponId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  优惠活动ID
	 */
	
	@Column(name ="DISCOUNT_ACTIVITY_ID",nullable=true,length=32)
	public java.lang.String getDiscountActivityId(){
		return this.discountActivityId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  优惠活动ID
	 */
	public void setDiscountActivityId(java.lang.String discountActivityId){
		this.discountActivityId = discountActivityId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名字
	 */
	
	@Column(name ="UPDATE_NAME",nullable=true,length=32)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名字
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新时间
	 */
	
	@Column(name ="UPDATE_DATE",nullable=true)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新时间
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人
	 */
	
	@Column(name ="UPDATE_BY",nullable=true,length=32)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名字
	 */
	
	@Column(name ="CREATE_NAME",nullable=true,length=32)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名字
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人
	 */
	
	@Column(name ="CREATE_BY",nullable=true,length=32)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	
	@Column(name ="CREATE_DATE",nullable=true)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	
}
