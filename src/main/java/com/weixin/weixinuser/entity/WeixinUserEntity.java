package com.weixin.weixinuser.entity;

import java.lang.String;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 微信用户
 * @author onlineGenerator
 * @date 2017-04-11 15:59:51
 * @version V1.0   
 *
 */
@Entity
@Table(name = "weixin_user", schema = "")
@SuppressWarnings("serial")
public class WeixinUserEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**用户的唯一标识*/
	@Excel(name="用户的唯一标识")
	private java.lang.String openid;
	/**商家ID*/
	@Excel(name="商家ID")
	private java.lang.String shopId;
	/**用户是否消费（1已消费，0未消费）*/
	private java.lang.String isSale;
	
	@Excel(name="备注")
	private String note;
	/**createTime*/
	@Excel(name="createTime",format = "yyyy-MM-dd")
	private java.util.Date createTime;
	
	
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
	 *@return: java.lang.String  用户的唯一标识
	 */
	@Column(name ="OPENID",nullable=true,length=32)
	public java.lang.String getOpenid(){
		return this.openid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户的唯一标识
	 */
	public void setOpenid(java.lang.String openid){
		this.openid = openid;
	}
	
	@Column(name ="issale",nullable=true)
	public java.lang.String getIsSale() {
		return isSale;
	}

	public void setIsSale(java.lang.String isSale) {
		this.isSale = isSale;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商家ID
	 */
	@Column(name ="SHOP_ID",nullable=true,length=32)
	public java.lang.String getShopId(){
		return this.shopId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商家ID
	 */
	public void setShopId(java.lang.String shopId){
		this.shopId = shopId;
	}
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  createTime
	 */
	@Column(name ="CREATE_TIME",nullable=true)
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  createTime
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	
	
	/**用户昵称*/
	@Excel(name="用户昵称")
	private java.lang.String nickname;
	/**用户性别*/
	@Excel(name="用户性别")
	private java.lang.String sex;
	/**省份*/
	@Excel(name="省份")
	private java.lang.String province;
	/**城市*/
	@Excel(name="城市")
	private java.lang.String city;
	/**国家*/
	@Excel(name="国家")
	private java.lang.String country;
	/**用户头像*/
	@Excel(name="用户头像")
	private java.lang.String headimgurl;
	/**用户特权信息*/
	@Excel(name="用户特权信息")
	private java.lang.String privilege;
	
	
	
	@Excel(name="手机绑定")
	private java.lang.String mobile;
	
	@Excel(name="手机验证码")
	private java.lang.String smsCode;
	
    @Excel(name="发送时间",format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date sendTime;

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户昵称
	 */
	@Column(name ="NICKNAME",nullable=true,length=64)
	public java.lang.String getNickname(){
		return this.nickname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户昵称
	 */
	public void setNickname(java.lang.String nickname){
		this.nickname = nickname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户性别
	 */
	@Column(name ="SEX",nullable=true,length=1)
	public java.lang.String getSex(){
		return this.sex;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户性别
	 */
	public void setSex(java.lang.String sex){
		this.sex = sex;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  省份
	 */
	@Column(name ="PROVINCE",nullable=true,length=64)
	public java.lang.String getProvince(){
		return this.province;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  省份
	 */
	public void setProvince(java.lang.String province){
		this.province = province;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  城市
	 */
	@Column(name ="CITY",nullable=true,length=64)
	public java.lang.String getCity(){
		return this.city;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  城市
	 */
	public void setCity(java.lang.String city){
		this.city = city;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  国家
	 */
	@Column(name ="COUNTRY",nullable=true,length=32)
	public java.lang.String getCountry(){
		return this.country;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  国家
	 */
	public void setCountry(java.lang.String country){
		this.country = country;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户头像
	 */
	@Column(name ="HEADIMGURL",nullable=true,length=500)
	public java.lang.String getHeadimgurl(){
		return this.headimgurl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户头像
	 */
	public void setHeadimgurl(java.lang.String headimgurl){
		this.headimgurl = headimgurl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户特权信息
	 */
	@Column(name ="PRIVILEGE",nullable=true,length=125)
	public java.lang.String getPrivilege(){
		return this.privilege;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户特权信息
	 */
	public void setPrivilege(java.lang.String privilege){
		this.privilege = privilege;
	}
	

	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户绑定手机
	 */
	@Column(name ="MOBILE",nullable=true,length=32)
	public java.lang.String getMobile(){
		return this.mobile;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户绑定手机
	 */
	public void setMobile(java.lang.String mobile){
		this.mobile = mobile;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机验证码
	 */
	@Column(name ="SMSCODE",nullable=true,length=10)
	public java.lang.String getSmscode(){
		return this.smsCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机验证码
	 */
	public void setSmscode(java.lang.String smsCode){
		this.smsCode = smsCode;
	}
	
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发送时间
	 */
	@Column(name ="SENDTIME",nullable=true)
	public java.util.Date getSendTime(){
		return this.sendTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发送时间
	 */
	public void setSendTime(java.util.Date sendTime){
		this.sendTime = sendTime;
	}

	

	
}
