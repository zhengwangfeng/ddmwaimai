package com.tcsb.memberuser.entity;

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
 * @Description: 会员信息
 * @author onlineGenerator
 * @date 2017-11-03 18:14:59
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_member_user", schema = "")
@SuppressWarnings("serial")
public class TcsbMemberUserEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**预留会员卡号*/
	private java.lang.String cardNo;
	/**会员级别ID*/
	private java.lang.String membershipLevelId;
	/**账户余额*/
	private java.lang.Double balance;
	/**用户的唯一标识*/
	private java.lang.String openid;
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
	/**createTime*/
	private java.util.Date createTime;
	/**商家ID*/
	private java.lang.String shopId;
	/**mobile*/
	@Excel(name="mobile")
	private java.lang.String mobile;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String note;
	
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

	@Column(name ="CARD_NO",nullable=true,length=64)
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
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
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  账户余额
	 */
	@Column(name ="BALANCE",nullable=true,length=22)
	public java.lang.Double getBalance(){
		return this.balance;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  账户余额
	 */
	public void setBalance(java.lang.Double balance){
		this.balance = balance;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  mobile
	 */
	@Column(name ="MOBILE",nullable=true,length=11)
	public java.lang.String getMobile(){
		return this.mobile;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  mobile
	 */
	public void setMobile(java.lang.String mobile){
		this.mobile = mobile;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="NOTE",nullable=true,length=125)
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
}
