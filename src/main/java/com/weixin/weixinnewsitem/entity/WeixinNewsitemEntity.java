package com.weixin.weixinnewsitem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import org.jeecgframework.poi.excel.annotation.Excel;

import com.weixin.weixinnewstemplate.entity.WeixinNewstemplateEntity;

/**   
 * @Title: Entity
 * @Description: 微信图文项
 * @author onlineGenerator
 * @date 2017-08-01 17:33:21
 * @version V1.0   
 *
 */
@Entity
@Table(name = "weixin_newsitem", schema = "")
@SuppressWarnings("serial")
public class WeixinNewsitemEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**类型 */
	@Excel(name="类型 ")
	private java.lang.String newType;
	/**作者 */
	@Excel(name="作者 ")
	private java.lang.String author;
	/**内容 */
	@Excel(name="内容 ")
	private java.lang.String content;
	/**描述 */
	@Excel(name="描述 ")
	private java.lang.String description;
	/**图片 */
	@Excel(name="图片 ")
	private java.lang.String image;
	/**顺序 */
	@Excel(name="顺序 ")
	private java.lang.String orders;
	/**标题*/
	@Excel(name="标题")
	private java.lang.String title;
	/**所属图文消*//*
	@Excel(name="所属图文消")
	private java.lang.String templateId;*/
	private WeixinNewstemplateEntity weixinNewstemplateEntity;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="template_id")
	public WeixinNewstemplateEntity getWeixinNewstemplateEntity() {
		return weixinNewstemplateEntity;
	}

	public void setWeixinNewstemplateEntity(
			WeixinNewstemplateEntity weixinNewstemplateEntity) {
		this.weixinNewstemplateEntity = weixinNewstemplateEntity;
	}

	/**外部url*/
	@Excel(name="外部url")
	private java.lang.String url;
	/**所属公众号 */
	private java.lang.String accountId;
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
	 *@return: java.lang.String  类型 
	 */
	@Column(name ="NEW_TYPE",nullable=true,length=255)
	public java.lang.String getNewType(){
		return this.newType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类型 
	 */
	public void setNewType(java.lang.String newType){
		this.newType = newType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  作者 
	 */
	@Column(name ="AUTHOR",nullable=true,length=255)
	public java.lang.String getAuthor(){
		return this.author;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  作者 
	 */
	public void setAuthor(java.lang.String author){
		this.author = author;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容 
	 */
	@Column(name ="CONTENT",nullable=true,length=2500)
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容 
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  描述 
	 */
	@Column(name ="DESCRIPTION",nullable=true,length=255)
	public java.lang.String getDescription(){
		return this.description;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  描述 
	 */
	public void setDescription(java.lang.String description){
		this.description = description;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图片 
	 */
	@Column(name ="IMAGE",nullable=true,length=255)
	public java.lang.String getImage(){
		return this.image;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图片 
	 */
	public void setImage(java.lang.String image){
		this.image = image;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  顺序 
	 */
	@Column(name ="ORDERS",nullable=true,length=255)
	public java.lang.String getOrders(){
		return this.orders;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  顺序 
	 */
	public void setOrders(java.lang.String orders){
		this.orders = orders;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标题
	 */
	@Column(name ="TITLE",nullable=true,length=255)
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标题
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属图文消
	 *//*
	@Column(name ="TEMPLATE_ID",nullable=true,length=32)
	public java.lang.String getTemplateId(){
		return this.templateId;
	}

	*//**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属图文消
	 *//*
	public void setTemplateId(java.lang.String templateId){
		this.templateId = templateId;
	}*/
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  外部url
	 */
	@Column(name ="URL",nullable=true,length=255)
	public java.lang.String getUrl(){
		return this.url;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  外部url
	 */
	public void setUrl(java.lang.String url){
		this.url = url;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属公众号 
	 */
	@Column(name ="ACCOUNT_ID",nullable=true,length=100)
	public java.lang.String getAccountId(){
		return this.accountId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属公众号 
	 */
	public void setAccountId(java.lang.String accountId){
		this.accountId = accountId;
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
