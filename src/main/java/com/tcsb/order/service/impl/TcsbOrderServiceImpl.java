package com.tcsb.order.service.impl;
import com.tcsb.coupon.entity.TcsbCouponEntity;
import com.tcsb.discountactivity.entity.TcsbDiscountActivityEntity;
import com.tcsb.distributionaddress.entity.TcsbDistributionAddressEntity;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.foodstandard.entity.TcsbFoodStandardEntity;
import com.tcsb.foodstock.entity.TcsbFoodStockEntity;
import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import com.tcsb.order.service.TcsbOrderServiceI;

import org.apache.velocity.util.MapFactory;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

import com.tcsb.order.VO.TcsbOrderItemReturnVO;
import com.tcsb.order.VO.TcsbOrderItemVO;
import com.tcsb.order.VO.TcsbOrderReturnVO;
import com.tcsb.order.VO.TcsbOrderVO;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import com.tcsb.tcsbfoodtastefunction.entity.TcsbFoodTasteFunctionEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.OrderNumberGenerateUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.io.Serializable;


@Service("tcsbOrderService")
@Transactional
public class TcsbOrderServiceImpl extends CommonServiceImpl implements TcsbOrderServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TcsbOrderEntity)entity);
 	}
	
	public void addMain(TcsbOrderEntity tcsbOrder,
	        List<TcsbOrderItemEntity> tcsbOrderItemList){
			//保存主信息
			//设置默认支付状态
		    tcsbOrder.setPayStatus("0");
			this.save(tcsbOrder);
		
			/**保存-订单项管理*/
			for(TcsbOrderItemEntity tcsbOrderItem:tcsbOrderItemList){
				//外键设置
				tcsbOrderItem.setOrderId(tcsbOrder.getId());
				this.save(tcsbOrderItem);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tcsbOrder);
	}

	
	public void updateMain(TcsbOrderEntity tcsbOrder,
	        List<TcsbOrderItemEntity> tcsbOrderItemList) {
		//保存主表信息
		this.saveOrUpdate(tcsbOrder);
		//===================================================================================
		//获取参数
		Object id0 = tcsbOrder.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-订单项管理
	    String hql0 = "from TcsbOrderItemEntity where 1 = 1 AND oRDER_ID = ? ";
	    List<TcsbOrderItemEntity> tcsbOrderItemOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-订单项管理
		if(tcsbOrderItemList!=null&&tcsbOrderItemList.size()>0){
		for(TcsbOrderItemEntity oldE:tcsbOrderItemOldList){
			boolean isUpdate = false;
				for(TcsbOrderItemEntity sendE:tcsbOrderItemList){
					//需要更新的明细数据-订单项管理
					if(oldE.getId().equals(sendE.getId())){
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
							this.saveOrUpdate(oldE);
						} catch (Exception e) {
							e.printStackTrace();
							throw new BusinessException(e.getMessage());
						}
						isUpdate= true;
		    			break;
		    		}
		    	}
	    		if(!isUpdate){
		    		//如果数据库存在的明细，前台没有传递过来则是删除-订单项管理
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-订单项管理
			for(TcsbOrderItemEntity tcsbOrderItem:tcsbOrderItemList){
				if(oConvertUtils.isEmpty(tcsbOrderItem.getId())){
					//外键设置
					tcsbOrderItem.setOrderId(tcsbOrder.getId());
					this.save(tcsbOrderItem);
				}
			}
		}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tcsbOrder);
	}

	
	public void delMain(TcsbOrderEntity tcsbOrder) {
		//删除主表信息
		this.delete(tcsbOrder);
		//===================================================================================
		//获取参数
		Object id0 = tcsbOrder.getId();
		//===================================================================================
		//删除-订单项管理
	    String hql0 = "from TcsbOrderItemEntity where 1 = 1 AND oRDER_ID = ? ";
	    List<TcsbOrderItemEntity> tcsbOrderItemOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(tcsbOrderItemOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TcsbOrderEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TcsbOrderEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TcsbOrderEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TcsbOrderEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{order_no}",String.valueOf(t.getOrderNo()));
 		sql  = sql.replace("#{method}",String.valueOf(t.getMethod()));
 		sql  = sql.replace("#{shop_id}",String.valueOf(t.getShopId()));
 		sql  = sql.replace("#{create_time}",String.valueOf(t.getCreateTime()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{pay_status}",String.valueOf(t.getPayStatus()));
 		sql  = sql.replace("#{pay_method}",String.valueOf(t.getPayMethod()));
 		sql  = sql.replace("#{open_id}",String.valueOf(t.getOpenId()));
 		sql  = sql.replace("#{pay_time}",String.valueOf(t.getPayTime()));
 		sql  = sql.replace("#{note}",String.valueOf(t.getNote()));
 		sql  = sql.replace("#{order_istake}",String.valueOf(t.getOrderIstake()));
 		sql  = sql.replace("#{coupon_id}",String.valueOf(t.getCouponId()));
 		sql  = sql.replace("#{discount_activity_id}",String.valueOf(t.getDiscountActivityId()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	@Override
	public Map<String, Object> saveOrderReturnOrderId(TcsbOrderVO tcsbOrderVO) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtil.isEmpty(tcsbOrderVO)) {
			map.put("orderId", "-1");
		}else {
			TcsbOrderEntity tcsbOrderEntity = new TcsbOrderEntity();
			if (StringUtil.isNotEmpty(tcsbOrderVO.getCouponId())) {
				tcsbOrderEntity.setCouponId(tcsbOrderVO.getCouponId());
			}
			tcsbOrderEntity.setCreateTime(new Date());
			if (StringUtil.isNotEmpty(tcsbOrderVO.getDiscountActivityId())) {
				tcsbOrderEntity.setDiscountActivityId(tcsbOrderVO.getDiscountActivityId());
			}
			//下单只设置两个默认属性
			//订单状态     进行中 having 已完成 completed 已取消 canceled
			tcsbOrderEntity.setStatus("having");
			tcsbOrderEntity.setOrderIstake("N");
			//未支付状态
			tcsbOrderEntity.setPayStatus("0");
			tcsbOrderEntity.setNote(tcsbOrderVO.getNote());
			tcsbOrderEntity.setOpenId(tcsbOrderVO.getOpenId());
			//tcsbOrderEntity.setOrderNo(OrderNumberGenerateUtil.getOrderNumber());
			tcsbOrderEntity.setShopId(tcsbOrderVO.getShopId());
			//设置配送地址
			tcsbOrderEntity.setDistributionAddressId(tcsbOrderVO.getDistributionAddressId());
			if (StringUtil.isNotEmpty(tcsbOrderVO.getBoxFeePrice())) {
				tcsbOrderEntity.setBoxFeePrice(tcsbOrderVO.getBoxFeePrice());
			}
			if (StringUtil.isNotEmpty(tcsbOrderVO.getDistributionPrice())) {
				tcsbOrderEntity.setDistributionPrice(tcsbOrderVO.getDistributionPrice());
			}
			this.save(tcsbOrderEntity);
			//保存订单项信息
			List<TcsbOrderItemVO> tcsbOrderItemVOs = tcsbOrderVO.getTcsbOrderItemVOs();
			if (!tcsbOrderItemVOs.isEmpty()) {
				for (TcsbOrderItemVO tcsbOrderItemVO : tcsbOrderItemVOs) {
					TcsbOrderItemEntity tcsbOrderItemEntity = new TcsbOrderItemEntity();
					tcsbOrderItemEntity.setCount(tcsbOrderItemVO.getCount());
					tcsbOrderItemEntity.setFoodId(tcsbOrderItemVO.getFoodId());
					tcsbOrderItemEntity.setFoodTypeId(tcsbOrderItemVO.getFoodTypeId());
					tcsbOrderItemEntity.setOrderId(tcsbOrderEntity.getId());
					tcsbOrderItemEntity.setPrice(tcsbOrderItemVO.getPrice());
					tcsbOrderItemEntity.setStandardId(tcsbOrderItemVO.getStandardId());
					tcsbOrderItemEntity.setTasteId(tcsbOrderItemVO.getTasteId());
					this.save(tcsbOrderItemEntity);
					//更新库存信息
					TcsbFoodStockEntity tcsbFoodStockEntity = this.findUniqueByProperty(TcsbFoodStockEntity.class, "foodId", tcsbOrderItemVO.getFoodId());
					Integer stock = tcsbFoodStockEntity.getStock();
					tcsbFoodStockEntity.setStock(stock-tcsbOrderItemVO.getCount());
					this.saveOrUpdate(tcsbFoodStockEntity);
				}
			}
			map.put("orderId", tcsbOrderEntity.getId());
		}
		return map;
	}

	@Override
	public TcsbOrderReturnVO getOrderByOrderId(String orderId) {
		TcsbOrderReturnVO tcsbOrderReturnVO = new TcsbOrderReturnVO();
		//保存主订单信息
		TcsbOrderEntity tcsbOrderEntity = this.get(TcsbOrderEntity.class, orderId);
		Double totalPrice = 0.0;
		Double toPayPrice = 0.0;
		//获取店铺信息
		TcsbShopEntity tcsbShopEntity = this.get(TcsbShopEntity.class, tcsbOrderEntity.getShopId());
		tcsbOrderReturnVO.setShopName(tcsbShopEntity.getName());
		tcsbOrderReturnVO.setPhone(tcsbShopEntity.getPhone());
		tcsbOrderReturnVO.setPayStatus(tcsbOrderEntity.getPayStatus());
		if (StringUtil.isNotEmpty(tcsbOrderEntity.getBoxFeePrice())) {
			tcsbOrderReturnVO.setBoxFeePrice(tcsbOrderEntity.getBoxFeePrice());
		}else {
			tcsbOrderReturnVO.setBoxFeePrice(0.0);
		}
		tcsbOrderReturnVO.setCreateTime(tcsbOrderEntity.getCreateTime());
		tcsbOrderReturnVO.setDistributionPrice(tcsbOrderEntity.getDistributionPrice());
		tcsbOrderReturnVO.setNote(tcsbOrderEntity.getNote());
		tcsbOrderReturnVO.setOrderNo(tcsbOrderEntity.getOrderNo());
		if (StringUtil.isNotEmpty(tcsbOrderEntity.getDiscountActivityId())) {
			TcsbDiscountActivityEntity tcsbDiscountActivityEntity = this.get(TcsbDiscountActivityEntity.class, tcsbOrderEntity.getDiscountActivityId());
			TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplateEntity = this.get(TcsbShopFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
			tcsbOrderReturnVO.setShopDisPrice(Double.parseDouble(tcsbShopFullcutTemplateEntity.getDiscount()+""));
		}else {
			tcsbOrderReturnVO.setShopDisPrice(0.0);
		}
		if (StringUtil.isNotEmpty(tcsbOrderEntity.getCouponId())) {
			TcsbCouponEntity tcsbCouponEntity = this.get(TcsbCouponEntity.class, tcsbOrderEntity.getCouponId());
			TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = this.get(TcsbFullcutTemplateEntity.class, tcsbCouponEntity.getFullcutTemplateId());
			tcsbOrderReturnVO.setUserDisPrice(Double.parseDouble(tcsbFullcutTemplateEntity.getDiscount()+""));
		}else {
			tcsbOrderReturnVO.setUserDisPrice(0.0);
		}
		//设置配送地址
		TcsbDistributionAddressEntity tcsbDistributionAddressEntity = this.get(TcsbDistributionAddressEntity.class, tcsbOrderEntity.getDistributionAddressId());
		if (tcsbDistributionAddressEntity.getSex().equals("0")) {
			tcsbDistributionAddressEntity.setSexCh("女");
		}else {
			tcsbDistributionAddressEntity.setSexCh("男");
		}
		tcsbOrderReturnVO.setTcsbDistributionAddressEntity(tcsbDistributionAddressEntity);
		//设置订单项
		List<TcsbOrderItemEntity> tcsbOrderItemEntities = this.findHql("from TcsbOrderItemEntity where orderId = ? ", orderId);
		List<TcsbOrderItemReturnVO> tcsbOrderItemReturnVOs = new ArrayList<>();
		if (!tcsbOrderItemEntities.isEmpty()) {
			for (TcsbOrderItemEntity tcsbOrderItemEntity : tcsbOrderItemEntities) {
				//计算价格
				totalPrice = BigDecimalUtil.add(totalPrice, BigDecimalUtil.mul(tcsbOrderItemEntity.getCount(), tcsbOrderItemEntity.getPrice()));
				TcsbOrderItemReturnVO tcsbOrderItemReturnVO = new TcsbOrderItemReturnVO();
				tcsbOrderItemReturnVO.setCount(tcsbOrderItemEntity.getCount());
				tcsbOrderItemReturnVO.setFoodId(tcsbOrderItemEntity.getFoodId());
				TcsbFoodEntity tcsbFoodEntity = this.get(TcsbFoodEntity.class, tcsbOrderItemEntity.getFoodId());
				if ("N".equals(tcsbFoodEntity.getIsDis())) {
					tcsbOrderItemReturnVO.setIsNotDis(true);
				}else {
					tcsbOrderItemReturnVO.setIsNotDis(false);
				}
				tcsbOrderItemReturnVO.setFoodName(tcsbFoodEntity.getName());
				tcsbOrderItemReturnVO.setFoodTypeId(tcsbOrderItemEntity.getFoodTypeId());
				tcsbOrderItemReturnVO.setPrice(tcsbOrderItemEntity.getPrice());
				if (!StringUtil.isEmpty(tcsbOrderItemEntity.getStandardId())) {
					TcsbFoodStandardEntity tcsbFoodStandardEntity = this.get(TcsbFoodStandardEntity.class, tcsbOrderItemEntity.getStandardId());
					tcsbOrderItemReturnVO.setStandardName(tcsbFoodStandardEntity.getName());
				}
				if (!StringUtil.isEmpty(tcsbOrderItemEntity.getTasteId())) {
					String tasteid[] = tcsbOrderItemEntity.getTasteId().split(",");
					if (tasteid.length==1) {
						TcsbFoodTasteFunctionEntity tcsbFoodTasteFunctionEntity = this.get(TcsbFoodTasteFunctionEntity.class, tasteid[0]);
						tcsbOrderItemReturnVO.setTasteName(tcsbFoodTasteFunctionEntity.getStatename());
					}else {
						String tasteName ="";
						for (int i = 0; i < tasteid.length; i++) {
							TcsbFoodTasteFunctionEntity tcsbFoodTasteFunctionEntity = this.get(TcsbFoodTasteFunctionEntity.class, tasteid[i]);
							if (i==tasteid.length-1) {
								tasteName+=tcsbFoodTasteFunctionEntity.getStatename();
							}else {
								tasteName+=tcsbFoodTasteFunctionEntity.getStatename()+",";
							}
						}
						tcsbOrderItemReturnVO.setTasteName(tasteName);
					}
				}
				tcsbOrderItemReturnVOs.add(tcsbOrderItemReturnVO);
			}
		}
		tcsbOrderReturnVO.setTotalPrice(Double.parseDouble(BigDecimalUtil.numericRetentionDecimal(totalPrice, 2)));
		Double aextra = BigDecimalUtil.add(tcsbOrderReturnVO.getBoxFeePrice(), tcsbOrderReturnVO.getDistributionPrice()); 
		Double dExtra = BigDecimalUtil.add(tcsbOrderReturnVO.getShopDisPrice(), tcsbOrderReturnVO.getUserDisPrice());
		toPayPrice = BigDecimalUtil.sub(totalPrice, dExtra);
		toPayPrice = BigDecimalUtil.add(toPayPrice, aextra);
		tcsbOrderReturnVO.setToPayPrice(toPayPrice);
		tcsbOrderReturnVO.setTcsbOrderItemReturnVOs(tcsbOrderItemReturnVOs);
		return tcsbOrderReturnVO;
	}

	@Override
	public TcsbOrderEntity getOrderDetailByOrderId(String orderId) {
		TcsbOrderEntity tcsbOrderEntity = get(TcsbOrderEntity.class, orderId);
		//设置店铺优惠和优惠券
		if (StringUtil.isNotEmpty(tcsbOrderEntity.getDiscountActivityId())) {
			TcsbDiscountActivityEntity tcsbDiscountActivityEntity = this.get(TcsbDiscountActivityEntity.class, tcsbOrderEntity.getDiscountActivityId());
			TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplateEntity = this.get(TcsbShopFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
			tcsbOrderEntity.setShopDisPrice(Double.parseDouble(tcsbShopFullcutTemplateEntity.getDiscount()+""));
		}else {
			tcsbOrderEntity.setShopDisPrice(0.0);
		}
		if (StringUtil.isNotEmpty(tcsbOrderEntity.getCouponId())) {
			TcsbCouponEntity tcsbCouponEntity = this.get(TcsbCouponEntity.class, tcsbOrderEntity.getCouponId());
			TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = this.get(TcsbFullcutTemplateEntity.class, tcsbCouponEntity.getFullcutTemplateId());
			tcsbOrderEntity.setUserDisPrice(Double.parseDouble(tcsbFullcutTemplateEntity.getDiscount()+""));
		}else {
			tcsbOrderEntity.setUserDisPrice(0.0);
		}
		//根据订单号获取菜品价格
		Double totalMoney = getOrderItemMoney(orderId);
		//设置配送地址
		TcsbDistributionAddressEntity tcsbDistributionAddressEntity = this.get(TcsbDistributionAddressEntity.class, tcsbOrderEntity.getDistributionAddressId());
		tcsbOrderEntity.setTcsbDistributionAddressEntity(tcsbDistributionAddressEntity);
		Double aExtra = BigDecimalUtil.add(tcsbOrderEntity.getBoxFeePrice(), tcsbOrderEntity.getDistributionPrice());
		tcsbOrderEntity.setTotalPrice(BigDecimalUtil.add(aExtra, totalMoney));
		Double dExtra = BigDecimalUtil.add(tcsbOrderEntity.getShopDisPrice(), tcsbOrderEntity.getUserDisPrice());
		tcsbOrderEntity.setToPayPrice(BigDecimalUtil.sub(tcsbOrderEntity.getTotalPrice(), dExtra));
		return tcsbOrderEntity;
	}
	
	private Double getOrderItemMoney(String orderId) {
		Double totalMoney = 0.0;
		List<TcsbOrderItemEntity> tcsbOrderItemEntities = this.findHql("from TcsbOrderItemEntity where orderId = ?", orderId);
		for (TcsbOrderItemEntity tcsbOrderItemEntity : tcsbOrderItemEntities) {
			totalMoney = BigDecimalUtil.add(totalMoney, BigDecimalUtil.mul(tcsbOrderItemEntity.getCount(), tcsbOrderItemEntity.getPrice()));
		}
		return Double.parseDouble(BigDecimalUtil.numericRetentionDecimal(totalMoney, 2));
	}
}