package com.tcsb.memberlevelequity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 会员级别权益
 * @author onlineGenerator
 * @date 2017-11-06 15:11:17
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_member_level_equity", schema = "")
@SuppressWarnings("serial")
public class TcsbMemberLevelEquityEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**所属店铺*/
	@Excel(name="所属店铺")
	private java.lang.String shopId;
	/**会员级别ID*/
	@Excel(name="会员级别ID")
	private java.lang.String membershipLevelId;
	/**是否打折*/
	@Excel(name="是否打折")
	private java.lang.String isDiscount;
	/**打折*/
	@Excel(name="打折")
	private java.lang.Double discount;
	/**是否赠送卡卷*/
	@Excel(name="是否赠送卡卷")
	private java.lang.String isGiveCard;
	/**卡卷ID*/
	@Excel(name="卡卷ID")
	private java.lang.String cardId;
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
	 *@return: java.lang.String  会员级别ID
	 */
	@Column(name ="MEMBERSHIP_LEVEL_ID",nullable=true,length=32)
	public java.lang.String getMembershipLevelId(){
		return this.membershipLevelId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  会员级别ID
	 */
	public void setMembershipLevelId(java.lang.String membershipLevelId){
		this.membershipLevelId = membershipLevelId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否打折
	 */
	@Column(name ="IS_DISCOUNT",nullable=true,length=1)
	public java.lang.String getIsDiscount(){
		return this.isDiscount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否打折
	 */
	public void setIsDiscount(java.lang.String isDiscount){
		this.isDiscount = isDiscount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  打折
	 */
	@Column(name ="DISCOUNT",nullable=true,length=22)
	public java.lang.Double getDiscount(){
		return this.discount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  打折
	 */
	public void setDiscount(java.lang.Double discount){
		this.discount = discount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否赠送卡卷
	 */
	@Column(name ="IS_GIVE_CARD",nullable=true,length=1)
	public java.lang.String getIsGiveCard(){
		return this.isGiveCard;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否赠送卡卷
	 */
	public void setIsGiveCard(java.lang.String isGiveCard){
		this.isGiveCard = isGiveCard;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  卡卷ID
	 */
	@Column(name ="CARD_ID",nullable=true,length=32)
	public java.lang.String getCardId(){
		return this.cardId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  卡卷ID
	 */
	public void setCardId(java.lang.String cardId){
		this.cardId = cardId;
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
