package com.tcsb.distributionaddressrea.entity;

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
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 用户配送地址关联
 * @author onlineGenerator
 * @date 2018-01-12 16:17:35
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_distribution_address_rea", schema = "")
@SuppressWarnings("serial")
public class TcsbDistributionAddressReaEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**用户唯一标识*/
	@Excel(name="用户唯一标识")
	private java.lang.String openId;
	/**配送地址ID*/
	@Excel(name="配送地址ID")
	private java.lang.String distributionAddressId;
	/**创建时间*/
	private java.util.Date createTime;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=true,length=32)
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
	 *@return: java.lang.String  用户唯一标识
	 */
	@Column(name ="OPEN_ID",nullable=true,length=32)
	public java.lang.String getOpenId(){
		return this.openId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户唯一标识
	 */
	public void setOpenId(java.lang.String openId){
		this.openId = openId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  配送地址ID
	 */
	@Column(name ="DISTRIBUTION_ADDRESS_ID",nullable=true,length=32)
	public java.lang.String getDistributionAddressId(){
		return this.distributionAddressId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  配送地址ID
	 */
	public void setDistributionAddressId(java.lang.String distributionAddressId){
		this.distributionAddressId = distributionAddressId;
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
}
