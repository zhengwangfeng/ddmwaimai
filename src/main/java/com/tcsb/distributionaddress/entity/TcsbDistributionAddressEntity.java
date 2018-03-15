package com.tcsb.distributionaddress.entity;

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

/**   
 * @Title: Entity
 * @Description: 用户配送地址
 * @author onlineGenerator
 * @date 2018-01-12 16:18:30
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_distribution_address", schema = "")
@SuppressWarnings("serial")
public class TcsbDistributionAddressEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**配送地址*/
	@Excel(name="配送地址")
	private java.lang.String address;
	/**经度*/
	@Excel(name="经度")
	private java.lang.Double longitude;
	/**纬度*/
	@Excel(name="纬度")
	private java.lang.Double latitude;
	/**所属店铺*/
	private java.lang.String shopId;
	/**昵称*/
	@Excel(name="昵称")
	private String nickName;
	/**性别*/
	@Excel(name="性别")
	private String sex;
	/**手机号*/
	@Excel(name="手机号")
	private String mobile;
	
	private String sexCh;
	@Transient
	public String getSexCh() {
		return sexCh;
	}

	public void setSexCh(String sexCh) {
		this.sexCh = sexCh;
	}

	@Column(name ="nick_name",nullable=true,length=125)
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column(name ="detail_address",nullable=true,length=125)
	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	/**详细地址*/
	@Excel(name="详细地址")
	private String detailAddress;
	/**创建时间*/
	private java.util.Date createTime;
	/**更新时间*/
	private java.util.Date updateTime;
	
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
	 *@return: java.lang.String  配送地址
	 */
	@Column(name ="ADDRESS",nullable=true,length=125)
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  配送地址
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  经度
	 */
	@Column(name ="LONGITUDE",nullable=true,length=22)
	public java.lang.Double getLongitude(){
		return this.longitude;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  经度
	 */
	public void setLongitude(java.lang.Double longitude){
		this.longitude = longitude;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  纬度
	 */
	@Column(name ="LATITUDE",nullable=true,length=22)
	public java.lang.Double getLatitude(){
		return this.latitude;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  纬度
	 */
	public void setLatitude(java.lang.Double latitude){
		this.latitude = latitude;
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATE_TIME",nullable=true)
	public java.util.Date getCreateTime(){
		return this.createTime;
	}


	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	@Column(name ="UPDATE_TIME",nullable=true)
	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
