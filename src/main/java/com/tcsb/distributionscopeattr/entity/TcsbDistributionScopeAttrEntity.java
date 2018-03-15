package com.tcsb.distributionscopeattr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 店铺配送范围属性
 * @author onlineGenerator
 * @date 2018-01-09 15:56:45
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_distribution_scope_attr", schema = "")
@SuppressWarnings("serial")
public class TcsbDistributionScopeAttrEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**起送金额*/
	@Excel(name="起送金额")
	private java.lang.String beginSendMoney;
	/**类型*/
	private java.lang.String type;
	/**配送员*/
	private java.lang.String userId;
	/**计费类型*/
	private java.lang.String chargeType;
	/**消费未满*/
	private java.lang.String consumptionNotFull;
	/**配送费*/
	@Excel(name="配送费")
	private java.lang.String distributionFee;
	/**配送区域ID*/
	@Excel(name="配送区域ID")
	private java.lang.String distributionScopeId;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String note;
	/**更新人名字*/
	@Excel(name="更新人名字")
	private java.lang.String updateName;
	/**更新时间*/
	@Excel(name="更新时间",format = "yyyy-MM-dd")
	private java.util.Date updateDate;
	/**更新人*/
	@Excel(name="更新人")
	private java.lang.String updateBy;
	/**创建人名字*/
	@Excel(name="创建人名字")
	private java.lang.String createName;
	/**创建人*/
	@Excel(name="创建人")
	private java.lang.String createBy;
	/**创建时间*/
	@Excel(name="创建时间",format = "yyyy-MM-dd")
	private java.util.Date createDate;
	/**所属店铺*/
	private java.lang.String shopId;
	/**配送公里数*/
	@Excel(name="配送公里数")
	private java.lang.Double mileage;
	@Transient
	public java.lang.Double getMileage() {
		return mileage;
	}

	public void setMileage(java.lang.Double mileage) {
		this.mileage = mileage;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属店铺
	 */
	@Column(name ="SHOP_ID",nullable=true,length=32)
	public java.lang.String getShopId(){
		return this.shopId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属店铺
	 */
	public void setShopId(java.lang.String shopId){
		this.shopId = shopId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
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
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  起送金额
	 */
	@Column(name ="BEGIN_SEND_MONEY",nullable=true,scale=2,length=2)
	public java.lang.String getBeginSendMoney(){
		return this.beginSendMoney;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  起送金额
	 */
	public void setBeginSendMoney(java.lang.String beginSendMoney){
		this.beginSendMoney = beginSendMoney;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类型
	 */
	@Column(name ="TYPE",nullable=true,length=1)
	public java.lang.String getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类型
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  配送员
	 */
	@Column(name ="USER_ID",nullable=true,length=32)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  配送员
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  计费类型
	 */
	@Column(name ="CHARGE_TYPE",nullable=true,length=1)
	public java.lang.String getChargeType(){
		return this.chargeType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  计费类型
	 */
	public void setChargeType(java.lang.String chargeType){
		this.chargeType = chargeType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  消费未满
	 */
	@Column(name ="CONSUMPTION_NOT_FULL",nullable=true,scale=2,length=2)
	public java.lang.String getConsumptionNotFull(){
		return this.consumptionNotFull;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  消费未满
	 */
	public void setConsumptionNotFull(java.lang.String consumptionNotFull){
		this.consumptionNotFull = consumptionNotFull;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  配送费
	 */
	@Column(name ="DISTRIBUTION_FEE",nullable=true,scale=2,length=2)
	public java.lang.String getDistributionFee(){
		return this.distributionFee;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  配送费
	 */
	public void setDistributionFee(java.lang.String distributionFee){
		this.distributionFee = distributionFee;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  配送区域ID
	 */
	@Column(name ="DISTRIBUTION_SCOPE_ID",nullable=true,length=32)
	public java.lang.String getDistributionScopeId(){
		return this.distributionScopeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  配送区域ID
	 */
	public void setDistributionScopeId(java.lang.String distributionScopeId){
		this.distributionScopeId = distributionScopeId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="NOTE",nullable=true,length=255)
	public java.lang.String getNote(){
		return this.note;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setNote(java.lang.String note){
		this.note = note;
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
