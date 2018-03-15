/**
 * jeecgos
 * @author Mar_x
 * create on 2017 2017年5月23日 上午11:28:59
 */
package com.tcsb.discountactivity.vo;

import com.tcsb.discountactivity.entity.TcsbDiscountActivityEntity;

/**
 * jeecgos
 *
 *
 * @author Mar_x
 * create on 2017年5月23日 上午11:28:59
 */
public class TcsbDiscountActivityEntityVo extends TcsbDiscountActivityEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6188807366314259780L;
	
	
	private String shopName;
	
	private String fullcutTemplateIdName;

	@Override
	public String getShopName() {
		return shopName;
	}

	@Override
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@Override
	public String getFullcutTemplateIdName() {
		return fullcutTemplateIdName;
	}

	@Override
	public void setFullcutTemplateIdName(String fullcutTemplateIdName) {
		this.fullcutTemplateIdName = fullcutTemplateIdName;
	}

}
