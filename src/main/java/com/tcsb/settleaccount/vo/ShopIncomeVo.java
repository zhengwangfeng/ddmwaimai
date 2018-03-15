/**
 * jeecgos
 * @author Mar_x
 * create on 2017 2017年6月8日 下午2:45:01
 */
package com.tcsb.settleaccount.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * jeecgos
 *
 *
 * @author jimmy
 * create on 2017年6月8日 下午2:45:01
 */
public class ShopIncomeVo {

		/** ID */
		private java.lang.String id;
		/** 名称 */
		private java.lang.String shopName;
		private String createTime;
		private Double boxFeePrice;
		private Double distributionPrice;
		private Double shopDisPrice;
		private Double userDisPrice;
		private Double totalPrice;
		private Double toPayPrice;
		public java.lang.String getId() {
			return id;
		}
		public void setId(java.lang.String id) {
			this.id = id;
		}
		public java.lang.String getShopName() {
			return shopName;
		}
		public void setShopName(java.lang.String shopName) {
			this.shopName = shopName;
		}
		public String getCreateTime() {
			return createTime;
		}
		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
		public Double getBoxFeePrice() {
			return boxFeePrice;
		}
		public void setBoxFeePrice(Double boxFeePrice) {
			this.boxFeePrice = boxFeePrice;
		}
		public Double getDistributionPrice() {
			return distributionPrice;
		}
		public void setDistributionPrice(Double distributionPrice) {
			this.distributionPrice = distributionPrice;
		}
		public Double getShopDisPrice() {
			return shopDisPrice;
		}
		public void setShopDisPrice(Double shopDisPrice) {
			this.shopDisPrice = shopDisPrice;
		}
		public Double getUserDisPrice() {
			return userDisPrice;
		}
		public void setUserDisPrice(Double userDisPrice) {
			this.userDisPrice = userDisPrice;
		}
		public Double getTotalPrice() {
			return totalPrice;
		}
		public void setTotalPrice(Double totalPrice) {
			this.totalPrice = totalPrice;
		}
		public Double getToPayPrice() {
			return toPayPrice;
		}
		public void setToPayPrice(Double toPayPrice) {
			this.toPayPrice = toPayPrice;
		}
		
}
