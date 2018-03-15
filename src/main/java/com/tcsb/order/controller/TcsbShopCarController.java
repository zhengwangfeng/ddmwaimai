package com.tcsb.order.controller;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.ListSorter;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apiservice.common.utils.ReturnMessageEnum;
import com.tcsb.boxfee.entity.TcsbBoxFeeEntity;
import com.tcsb.boxfee.service.TcsbBoxFeeServiceI;
import com.tcsb.coupon.entity.TcsbCouponEntity;
import com.tcsb.coupon.service.TcsbCouponServiceI;
import com.tcsb.discountactivity.entity.TcsbDiscountActivityEntity;
import com.tcsb.discountactivity.service.TcsbDiscountActivityServiceI;
import com.tcsb.distributionscopeattr.entity.TcsbDistributionScopeAttrEntity;
import com.tcsb.distributionscopeattr.service.TcsbDistributionScopeAttrServiceI;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.food.service.TcsbFoodServiceI;
import com.tcsb.foodstandard.entity.TcsbFoodStandardEntity;
import com.tcsb.foodstandard.service.TcsbFoodStandardServiceI;
import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import com.tcsb.fullcuttemplate.service.TcsbFullcutTemplateServiceI;
import com.tcsb.order.VO.ShopCarItemVO;
import com.tcsb.order.VO.ShopCarVO;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import com.tcsb.shopfullcuttemplate.service.TcsbShopFullcutTemplateServiceI;

/**   
 * @Title: Controller  
 * @Description: 购物车
 * @author onlineGenerator
 * @date 2017-04-26 21:44:06
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbShopCarController")
public class TcsbShopCarController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbShopCarController.class);

	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbFoodStandardServiceI tcsbFoodStandardService;
	@Autowired
	private TcsbFoodServiceI tcsbFoodService;
	@Autowired
	private TcsbBoxFeeServiceI tcsbBoxFeeService;
	@Autowired
	private TcsbDistributionScopeAttrServiceI tcsbDistributionScopeAttrService;
	@Autowired
	private TcsbDiscountActivityServiceI tcsbDiscountActivityService;
	@Autowired
	private TcsbShopFullcutTemplateServiceI tcsbShopFullcutTemplateService;
	@Autowired
	private TcsbCouponServiceI tcsbCouponService;
	@Autowired
	private TcsbFullcutTemplateServiceI tcsbFullcutTemplateService;
	
	/**
	 * 点击下单获取购物车信息
	 * @param userId
	 * @param deskId
	 * @param foodId
	 * @param status
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getShopCarInfoByShopCarItem",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AjaxJsonApi getShopCarInfoByShopCarItem(String shopCar){
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
	    ShopCarVO shopCarVO = JSONHelper.fromJsonToObject(shopCar, ShopCarVO.class);
		List<ShopCarItemVO> shopCarItemVOs = shopCarVO.getShopCarItemVOs();
		//总价格
		Double totalPrice = 0.0;
		//需要参与的打折价
		Double disPrice = 0.0;
		//所需要的餐盒费
		Double boxFeePrice =0.0;
		Integer boxFeeNum = 0;
		//需支付
		Double toPayPrice = 0.0;
		//获取购物车的信息
		for (ShopCarItemVO shopCarItemVO : shopCarItemVOs) {
			Double tempPrice = 0.0;
			TcsbFoodEntity tcsbFoodEntity = tcsbFoodService.get(TcsbFoodEntity.class, shopCarItemVO.getFoodId());
			if ("N".equals(tcsbFoodEntity.getIsDis())) {
				shopCarItemVO.setIsNotDis(true);
			}else {
				shopCarItemVO.setIsNotDis(false);
			}
			if (StringUtil.isNotEmpty(shopCarItemVO.getStandardId())) {
				//查询规格具体的价格
				TcsbFoodStandardEntity tcsbFoodStandardEntity = tcsbFoodStandardService.get(TcsbFoodStandardEntity.class, shopCarItemVO.getStandardId());
				tempPrice = tcsbFoodStandardEntity.getPrice();
				shopCarItemVO.setPrice(tempPrice);
				shopCarItemVO.setStandardName(tcsbFoodStandardEntity.getName());
			}else {
				//食物的价格
				tempPrice = tcsbFoodEntity.getPrice();
				shopCarItemVO.setPrice(tempPrice);
			}
			shopCarItemVO.setFoodName(tcsbFoodEntity.getName());
			if ("Y".equals(tcsbFoodEntity.getIsDis())) {
				disPrice = BigDecimalUtil.add(disPrice, BigDecimalUtil.mul(tempPrice, shopCarItemVO.getCount()));
			}
			
			totalPrice = BigDecimalUtil.add(totalPrice, BigDecimalUtil.mul(tempPrice, shopCarItemVO.getCount()));
			//获取是否需要餐盒费
			if ("Y".equals(tcsbFoodEntity.getNeedBoxFee())) {
				boxFeeNum += shopCarItemVO.getCount();
			}
		}
		shopCarVO.setTotalPrice(totalPrice);
		//获取餐盒费
		List<TcsbBoxFeeEntity> tcsbBoxFeeEntities = tcsbBoxFeeService.findHql("from TcsbBoxFeeEntity where shopId = ? and status ='Y'", shopCarVO.getShopId());
		if (!tcsbBoxFeeEntities.isEmpty()) {
			TcsbBoxFeeEntity tcsbBoxFeeEntity = tcsbBoxFeeEntities.get(0);
			boxFeePrice = BigDecimalUtil.mul(boxFeeNum, tcsbBoxFeeEntity.getPrice());
		}
		shopCarVO.setBoxFeePrice(boxFeePrice);
		//设置配送费
		//获取店铺的起送和配送费
		TcsbDistributionScopeAttrEntity  tcsbDistributionScopeAttrEntity = tcsbDistributionScopeAttrService.findUniqueByProperty(TcsbDistributionScopeAttrEntity.class, "shopId", shopCarVO.getShopId());
		if (StringUtil.isNotEmpty(tcsbDistributionScopeAttrEntity)) {
			shopCarVO.setDistributionPrice(Double.parseDouble(tcsbDistributionScopeAttrEntity.getDistributionFee()));
		}else {
			shopCarVO.setDistributionPrice(0.0);
		}
		toPayPrice = totalPrice;
		//设置店铺活动和优惠券价格
		//获取优惠活动
		List<TcsbShopFullcutTemplateEntity> tcsbShopFullcutTemplateEntities = getDiscountActivity(shopCarVO.getShopId());
		
		boolean isShopFullCut = false;
		for (TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplateEntity : tcsbShopFullcutTemplateEntities) {
			if (disPrice>=tcsbShopFullcutTemplateEntity.getTotal()) {
				toPayPrice = BigDecimalUtil.sub(toPayPrice, tcsbShopFullcutTemplateEntity.getDiscount());
				shopCarVO.setShopDisPrice(Double.parseDouble(tcsbShopFullcutTemplateEntity.getDiscount()+""));
				isShopFullCut = true;
				disPrice = BigDecimalUtil.sub(disPrice, Double.parseDouble(tcsbShopFullcutTemplateEntity.getDiscount()+""));
				//设置用了哪个店铺活动
				shopCarVO.setDiscountActivityId(tcsbShopFullcutTemplateEntity.getDiscountActivityId());
				break;
			}
		}
		//没满足店铺活动情况
		if (!isShopFullCut) {
			shopCarVO.setShopDisPrice(0.0);
		}
		//获取优惠卷
		List<TcsbFullcutTemplateEntity> tcsbFullcutTemplateEntities = getUserCoupon(shopCarVO.getShopId(),shopCarVO.getOpenId());
		boolean userCut = false;
		if (!tcsbFullcutTemplateEntities.isEmpty()) {
			for (TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity : tcsbFullcutTemplateEntities) {
				if (disPrice>=tcsbFullcutTemplateEntity.getTotal()) {
					shopCarVO.setUserDisPrice(Double.parseDouble(tcsbFullcutTemplateEntity.getDiscount()+""));
					toPayPrice = BigDecimalUtil.sub(toPayPrice, tcsbFullcutTemplateEntity.getDiscount());
					//设置用了哪个优惠券
					shopCarVO.setCouponId(tcsbFullcutTemplateEntity.getCouponId());
					userCut = true;
					break;
				}
			}
			if (!userCut) {
				shopCarVO.setUserDisPrice(0.0);
			}
		}else {
			shopCarVO.setUserDisPrice(0.0);
		}
		//打完折在加上配送费和餐盒费
		Double extr = BigDecimalUtil.add(shopCarVO.getBoxFeePrice(), shopCarVO.getDistributionPrice());
		toPayPrice = BigDecimalUtil.add(toPayPrice, extr);
		shopCarVO.setToPayPrice(toPayPrice);
		ajaxJsonApi.setMsg(ReturnMessageEnum.QUERYTRUE.getMsg());
		ajaxJsonApi.setSuccess(true);
		ajaxJsonApi.setObj(shopCarVO);
		return ajaxJsonApi;
	}


	private List<TcsbFullcutTemplateEntity> getUserCoupon(String shopId,String userId){
		String hql = "from TcsbCouponEntity where shopId='"+shopId+"' and userId='"+userId+"' and useStatus='0'";
		List<TcsbCouponEntity> tcsbCouponEntities = tcsbCouponService.findByQueryString(hql);
		List<TcsbFullcutTemplateEntity> tcsbFullcutTemplateEntities = new ArrayList<>();
		if (!tcsbCouponEntities.isEmpty()) {
			for (TcsbCouponEntity tcsbCouponEntity : tcsbCouponEntities) {
				TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = tcsbFullcutTemplateService.get(TcsbFullcutTemplateEntity.class, tcsbCouponEntity.getFullcutTemplateId());
				Integer userPeriod = tcsbFullcutTemplateEntity.getUsePeriod();//使用期限
				String dateUnit = tcsbFullcutTemplateEntity.getDateUnit(); //日期单位
				int days = 0;
				//todo
				if (dateUnit.equals("year")) {
					days = userPeriod*DateUtils.getCurrentYearDays();
				}else if (dateUnit.equals("month")) {
					days = userPeriod*DateUtils.getCurrentMonthDay();
				}else {
					days = userPeriod;
				}
				//专用券中有效的满减活动
				Calendar calDes;
				try {
					calDes = DateUtils.parseCalendar(DateUtils.date2Str(tcsbCouponEntity.getExpiryDate(),DateUtils.datetimeFormat),"yyyy-MM-dd HH:mm:ss");
					Calendar calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat),"yyyy-MM-dd HH:mm:ss");
					if (DateUtils.dateDiff('s', calSrc, calDes)<0) {
						TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity2 = new TcsbFullcutTemplateEntity();
						MyBeanUtils.copyBeanNotNull2Bean(tcsbFullcutTemplateEntity, tcsbFullcutTemplateEntity2);
						tcsbFullcutTemplateEntity2.setCouponId(tcsbCouponEntity.getId());
						tcsbFullcutTemplateEntity2.setUseRange(tcsbCouponEntity.getUseRange());
						tcsbFullcutTemplateEntities.add(tcsbFullcutTemplateEntity2);
					};
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		}
		ListSorter.sortDesc(tcsbFullcutTemplateEntities, "total","discount");
		return tcsbFullcutTemplateEntities;
	}

	private List<TcsbShopFullcutTemplateEntity> getDiscountActivity(String shopId) {
		List<TcsbDiscountActivityEntity> tcsbDiscountActivityEntities = tcsbDiscountActivityService.findByProperty(TcsbDiscountActivityEntity.class, "shopId", shopId);
		List<TcsbShopFullcutTemplateEntity> tcsbShopFullcutTemplateEntities = new ArrayList<>();
		if (!tcsbDiscountActivityEntities.isEmpty()) {
			for (TcsbDiscountActivityEntity tcsbDiscountActivityEntity : tcsbDiscountActivityEntities) {
				TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplateEntity = tcsbShopFullcutTemplateService.get(TcsbShopFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
				tcsbShopFullcutTemplateEntity.setDiscountActivityId(tcsbDiscountActivityEntity.getId());
				tcsbShopFullcutTemplateEntities.add(tcsbShopFullcutTemplateEntity);
			}
		}
		ListSorter.sortDesc(tcsbShopFullcutTemplateEntities, "total","discount");
		return tcsbShopFullcutTemplateEntities;
	}
	
	/**
	 * 获取我的购物车
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 *//*
	@RequestMapping(value = "/getMyShopCar",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject getMyShopCar(@RequestParam String userId,HttpServletRequest request,HttpServletResponse response){
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		List<TcsbShopCarVO> maps = new ArrayList<>();
		try {
			maps = tcsbShopCarService.getMyShopCar(userId);
			for (TcsbShopCarVO tcsbShopCarVO : maps) {
				tcsbShopCarVO.setShopImg(getCkPath()+tcsbShopCarVO.getShopImg());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		ajaxJsonApi.setMsg(ReturnMessageEnum.QUERYTRUE.getMsg());
		ajaxJsonApi.setSuccess(true);
		ajaxJsonApi.setObj(maps);
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}*/
	
}
