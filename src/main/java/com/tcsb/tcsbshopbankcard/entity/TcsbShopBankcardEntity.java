package com.tcsb.tcsbshopbankcard.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 店铺关联银行卡
 * @author onlineGenerator
 * @date 2017-07-15 15:38:58
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_shop_bankcard", schema = "")
@SuppressWarnings("serial")
public class TcsbShopBankcardEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**收款方行别代码*/
	@Excel(name="收款方行别代码")
	private java.lang.String beneficiaryBankcode;
	/**收款方客户账号*/
	@Excel(name="收款方客户账号")
	private java.lang.String beneficiaryAccount;
	/**收款方账户名称*/
	@Excel(name="收款方账户名称")
	private java.lang.String beneficiaryName;
	/**收款方开户行名称*/
	@Excel(name="收款方开户行名称")
	private java.lang.String beneficiaryOpeningBankName;
	/**收款方联行号*/
	@Excel(name="收款方联行号")
	private java.lang.String beneficiaryLineNumber;
	/**客户方流水号*/
	@Excel(name="客户方流水号")
	private java.lang.String clientDerialNumber;
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
	
	
	private String shopId;
	
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
	 *@return: java.lang.String  收款方行别代码
	 */
	@Column(name ="BENEFICIARY_BANKCODE",nullable=true,length=2)
	public java.lang.String getBeneficiaryBankcode(){
		return this.beneficiaryBankcode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收款方行别代码
	 */
	public void setBeneficiaryBankcode(java.lang.String beneficiaryBankcode){
		this.beneficiaryBankcode = beneficiaryBankcode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收款方客户账号
	 */
	@Column(name ="BENEFICIARY_ACCOUNT",nullable=true,length=32)
	public java.lang.String getBeneficiaryAccount(){
		return this.beneficiaryAccount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收款方客户账号
	 */
	public void setBeneficiaryAccount(java.lang.String beneficiaryAccount){
		this.beneficiaryAccount = beneficiaryAccount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收款方账户名称
	 */
	@Column(name ="BENEFICIARY_NAME",nullable=true,length=32)
	public java.lang.String getBeneficiaryName(){
		return this.beneficiaryName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收款方账户名称
	 */
	public void setBeneficiaryName(java.lang.String beneficiaryName){
		this.beneficiaryName = beneficiaryName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收款方开户行名称
	 */
	@Column(name ="BENEFICIARY_OPENING_BANK_NAME",nullable=true,length=128)
	public java.lang.String getBeneficiaryOpeningBankName(){
		return this.beneficiaryOpeningBankName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收款方开户行名称
	 */
	public void setBeneficiaryOpeningBankName(java.lang.String beneficiaryOpeningBankName){
		this.beneficiaryOpeningBankName = beneficiaryOpeningBankName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收款方联行号
	 */
	@Column(name ="BENEFICIARY_LINE_NUMBER",nullable=true,length=32)
	public java.lang.String getBeneficiaryLineNumber(){
		return this.beneficiaryLineNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收款方联行号
	 */
	public void setBeneficiaryLineNumber(java.lang.String beneficiaryLineNumber){
		this.beneficiaryLineNumber = beneficiaryLineNumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户方流水号
	 */
	@Column(name ="CLIENT_DERIAL_NUMBER",nullable=true,length=32)
	public java.lang.String getClientDerialNumber(){
		return this.clientDerialNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户方流水号
	 */
	public void setClientDerialNumber(java.lang.String clientDerialNumber){
		this.clientDerialNumber = clientDerialNumber;
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

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
}
