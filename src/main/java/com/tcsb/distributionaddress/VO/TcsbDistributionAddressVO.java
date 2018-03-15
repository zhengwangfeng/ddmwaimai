package com.tcsb.distributionaddress.VO;

/**
 * @Title: VO
 * @Description: 用户配送地址
 * @author onlineGenerator
 * @date 2018-01-12 16:18:30
 * @version V1.0
 * 
 */
public class TcsbDistributionAddressVO {
	private String distributionAddressId;
	private String address;
	private String longitude;
	private String latitude;
	private java.lang.String shopId;
	private String nickName;
	private String sex;//0 woman 1 man
	private String mobile;
	private String userId;
	private String detailAddress;
	public java.lang.String getAddress() {
		return address;
	}
	public String getDetailAddress() {
		return detailAddress;
	}

	public String getDistributionAddressId() {
		return distributionAddressId;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getMobile() {
		return mobile;
	}

	public String getNickName() {
		return nickName;
	}

	public String getSex() {
		return sex;
	}

	public java.lang.String getShopId() {
		return shopId;
	}

	public String getUserId() {
		return userId;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public void setDistributionAddressId(String distributionAddressId) {
		this.distributionAddressId = distributionAddressId;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setShopId(java.lang.String shopId) {
		this.shopId = shopId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
