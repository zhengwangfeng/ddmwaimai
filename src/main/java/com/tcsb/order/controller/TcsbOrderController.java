package com.tcsb.order.controller;
import com.tcsb.coupon.entity.TcsbCouponEntity;
import com.tcsb.coupon.service.TcsbCouponServiceI;
import com.tcsb.discountactivity.entity.TcsbDiscountActivityEntity;
import com.tcsb.discountactivity.service.TcsbDiscountActivityServiceI;
import com.tcsb.distributionaddress.entity.TcsbDistributionAddressEntity;
import com.tcsb.distributionaddress.service.TcsbDistributionAddressServiceI;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.food.service.TcsbFoodServiceI;
import com.tcsb.foodtype.entity.TcsbFoodTypeEntity;
import com.tcsb.foodtype.service.TcsbFoodTypeServiceI;
import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import com.tcsb.fullcuttemplate.service.TcsbFullcutTemplateServiceI;
import com.tcsb.order.VO.TcsbOrderItemPrintVO;
import com.tcsb.order.VO.TcsbOrderPrintVO;
import com.tcsb.order.VO.TcsbOrderReturnVO;
import com.tcsb.order.VO.TcsbOrderVO;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.order.service.TcsbOrderServiceI;
import com.tcsb.order.page.TcsbOrderPage;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.printer.entity.TcsbPrinterEntity;
import com.tcsb.printerfoodfun.entity.TcsbPrinterFoodFunEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import com.tcsb.shopfullcuttemplate.service.TcsbShopFullcutTemplateServiceI;
import com.tcsb.tcsbfoodtastefunction.entity.TcsbFoodTasteFunctionEntity;
import com.tcsb.tcsbfoodunit.entity.TcsbFoodUnitEntity;
import com.tcsb.tcsbfoodunit.service.TcsbFoodUnitServiceI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.OrderNumberGenerateUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.p3.core.common.utils.DateUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

/**   
 * @Title: Controller
 * @Description: 订单
 * @author onlineGenerator
 * @date 2018-01-10 11:01:52
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbOrderController")
public class TcsbOrderController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbOrderController.class);

	@Autowired
	private TcsbOrderServiceI tcsbOrderService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbFoodTypeServiceI tcsbFoodTypeService;
	@Autowired
	private TcsbFoodServiceI tcsbFoodService;
	@Autowired
	private TcsbFoodUnitServiceI tcsbFoodUnitService;
	@Autowired
	private TcsbDiscountActivityServiceI tcsbDiscountActivityService;
	@Autowired
	private TcsbCouponServiceI tcsbCouponService;
	@Autowired
	private TcsbShopFullcutTemplateServiceI tcsbShopFullcutTemplateService;
	@Autowired
	private TcsbFullcutTemplateServiceI tcsbFullcutTemplateService;
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	@Autowired
	private TcsbDistributionAddressServiceI tcsbDistributionAddressService;
	/**
	 * 订单列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/order/tcsbOrderList");
	}

	/**
	 * 订单管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "listAccept")
	public ModelAndView listAccept(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/order/tcsbOrderAcceptList");
	}

	/**
	 * 订单日志列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "listLog")
	public ModelAndView listLog(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/order/tcsbOrderListLog");
	}
	/**
	 * 接单更改订单接收状态
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "receiveOrder")
	@ResponseBody
	public AjaxJson receiveOrder(TcsbOrderEntity tcsbOrderEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "恭喜你接单成功";
		try{
			if (StringUtil.isNotEmpty(tcsbOrderEntity.getId())) {
				tcsbOrderEntity = tcsbOrderService.getEntity(TcsbOrderEntity.class, tcsbOrderEntity.getId());
			}
			tcsbOrderEntity.setOrderIstake("Y");
			tcsbOrderService.saveOrUpdate(tcsbOrderEntity);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新订单管理失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TcsbOrderEntity tcsbOrder,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbOrderEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbOrder);
		try{
		//自定义追加查询条件
			//只查询出已支付状态的订单
			cq.add(Restrictions.eq("payStatus", "1"));
			cq.addOrder("createTime", SortDirection.desc);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbOrderService.getDataGridReturn(cq, true);
		List<TcsbOrderReturnVO> tcsbOrderReturnVOs = new ArrayList<>();
		if (!dataGrid.getResults().isEmpty()) {
			List<TcsbOrderEntity> tcsbOrderEntities = dataGrid.getResults();
			for (TcsbOrderEntity tcsbOrderEntity : tcsbOrderEntities) {
				TcsbOrderReturnVO tcsbOrderReturnVO = new TcsbOrderReturnVO();
				try {
					MyBeanUtils.copyBeanNotNull2Bean(tcsbOrderEntity, tcsbOrderReturnVO);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//设置店铺优惠和优惠券
				if (StringUtil.isNotEmpty(tcsbOrderEntity.getDiscountActivityId())) {
					TcsbDiscountActivityEntity tcsbDiscountActivityEntity = tcsbDiscountActivityService.get(TcsbDiscountActivityEntity.class, tcsbOrderEntity.getDiscountActivityId());
					TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplateEntity = tcsbShopFullcutTemplateService.get(TcsbShopFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
					tcsbOrderReturnVO.setShopDisPrice(Double.parseDouble(tcsbShopFullcutTemplateEntity.getDiscount()+""));
				}else {
					tcsbOrderReturnVO.setShopDisPrice(0.0);
				}
				if (StringUtil.isNotEmpty(tcsbOrderEntity.getCouponId())) {
					TcsbCouponEntity tcsbCouponEntity = tcsbCouponService.get(TcsbCouponEntity.class, tcsbOrderEntity.getCouponId());
					TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = tcsbFullcutTemplateService.get(TcsbFullcutTemplateEntity.class, tcsbCouponEntity.getFullcutTemplateId());
					tcsbOrderReturnVO.setUserDisPrice(Double.parseDouble(tcsbFullcutTemplateEntity.getDiscount()+""));
				}else {
					tcsbOrderReturnVO.setUserDisPrice(0.0);
				}
				//根据订单号获取菜品价格
				Double totalMoney = getOrderItemMoney(tcsbOrderEntity.getId());
				//设置配送地址
				TcsbDistributionAddressEntity tcsbDistributionAddressEntity = tcsbDistributionAddressService.get(TcsbDistributionAddressEntity.class, tcsbOrderEntity.getDistributionAddressId());
				tcsbOrderReturnVO.setTcsbDistributionAddressEntity(tcsbDistributionAddressEntity);
				Double aExtra = BigDecimalUtil.add(tcsbOrderReturnVO.getBoxFeePrice(), tcsbOrderReturnVO.getDistributionPrice());
				tcsbOrderReturnVO.setTotalPrice(BigDecimalUtil.add(aExtra, totalMoney));
				Double dExtra = BigDecimalUtil.add(tcsbOrderReturnVO.getShopDisPrice(), tcsbOrderReturnVO.getUserDisPrice());
				tcsbOrderReturnVO.setToPayPrice(BigDecimalUtil.sub(tcsbOrderReturnVO.getTotalPrice(), dExtra));
				tcsbOrderReturnVOs.add(tcsbOrderReturnVO);
			}
		}
		dataGrid.setResults(tcsbOrderReturnVOs);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagridLog")
	public void datagridLog(TcsbOrderEntity tcsbOrder,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbOrderEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbOrder);
		try{
		//自定义追加查询条件
			cq.addOrder("createTime", SortDirection.desc);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbOrderService.getDataGridReturn(cq, true);
		List<TcsbOrderReturnVO> tcsbOrderReturnVOs = new ArrayList<>();
		if (!dataGrid.getResults().isEmpty()) {
			List<TcsbOrderEntity> tcsbOrderEntities = dataGrid.getResults();
			for (TcsbOrderEntity tcsbOrderEntity : tcsbOrderEntities) {
				TcsbOrderReturnVO tcsbOrderReturnVO = new TcsbOrderReturnVO();
				try {
					MyBeanUtils.copyBeanNotNull2Bean(tcsbOrderEntity, tcsbOrderReturnVO);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//设置店铺优惠和优惠券
				if (StringUtil.isNotEmpty(tcsbOrderEntity.getDiscountActivityId())) {
					TcsbDiscountActivityEntity tcsbDiscountActivityEntity = tcsbDiscountActivityService.get(TcsbDiscountActivityEntity.class, tcsbOrderEntity.getDiscountActivityId());
					TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplateEntity = tcsbShopFullcutTemplateService.get(TcsbShopFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
					tcsbOrderReturnVO.setShopDisPrice(Double.parseDouble(tcsbShopFullcutTemplateEntity.getDiscount()+""));
				}else {
					tcsbOrderReturnVO.setShopDisPrice(0.0);
				}
				if (StringUtil.isNotEmpty(tcsbOrderEntity.getCouponId())) {
					TcsbCouponEntity tcsbCouponEntity = tcsbCouponService.get(TcsbCouponEntity.class, tcsbOrderEntity.getCouponId());
					TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = tcsbFullcutTemplateService.get(TcsbFullcutTemplateEntity.class, tcsbCouponEntity.getFullcutTemplateId());
					tcsbOrderReturnVO.setUserDisPrice(Double.parseDouble(tcsbFullcutTemplateEntity.getDiscount()+""));
				}else {
					tcsbOrderReturnVO.setUserDisPrice(0.0);
				}
				//根据订单号获取菜品价格
				Double totalMoney = getOrderItemMoney(tcsbOrderEntity.getId());
				//设置配送地址
				TcsbDistributionAddressEntity tcsbDistributionAddressEntity = tcsbDistributionAddressService.get(TcsbDistributionAddressEntity.class, tcsbOrderEntity.getDistributionAddressId());
				tcsbOrderReturnVO.setTcsbDistributionAddressEntity(tcsbDistributionAddressEntity);
				Double aExtra = BigDecimalUtil.add(tcsbOrderReturnVO.getBoxFeePrice(), tcsbOrderReturnVO.getDistributionPrice());
				tcsbOrderReturnVO.setTotalPrice(BigDecimalUtil.add(aExtra, totalMoney));
				Double dExtra = BigDecimalUtil.add(tcsbOrderReturnVO.getShopDisPrice(), tcsbOrderReturnVO.getUserDisPrice());
				tcsbOrderReturnVO.setToPayPrice(BigDecimalUtil.sub(tcsbOrderReturnVO.getTotalPrice(), dExtra));
				tcsbOrderReturnVOs.add(tcsbOrderReturnVO);
			}
		}
		dataGrid.setResults(tcsbOrderReturnVOs);
		TagUtil.datagrid(response, dataGrid);
	}
	
	private Double getOrderItemMoney(String id) {
		Double totalMoney = 0.0;
		List<TcsbOrderItemEntity> tcsbOrderItemEntities = tcsbOrderService.findHql("from TcsbOrderItemEntity where orderId = ?", id);
		for (TcsbOrderItemEntity tcsbOrderItemEntity : tcsbOrderItemEntities) {
			totalMoney = BigDecimalUtil.add(totalMoney, BigDecimalUtil.mul(tcsbOrderItemEntity.getCount(), tcsbOrderItemEntity.getPrice()));
		}
		return Double.parseDouble(BigDecimalUtil.numericRetentionDecimal(totalMoney, 2));
	}

	/**
	 * 删除订单
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbOrderEntity tcsbOrder, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tcsbOrder = systemService.getEntity(TcsbOrderEntity.class, tcsbOrder.getId());
		String message = "订单删除成功";
		try{
			tcsbOrderService.delMain(tcsbOrder);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "订单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除订单
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "订单删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbOrderEntity tcsbOrder = systemService.getEntity(TcsbOrderEntity.class,
				id
				);
				tcsbOrderService.delMain(tcsbOrder);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "订单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加订单
	 * 
	 * @param ids
	 * @return
	 */
  	@ResponseBody
	public AjaxJson doAdd(TcsbOrderEntity tcsbOrder,TcsbOrderPage tcsbOrderPage, HttpServletRequest request) {
		List<TcsbOrderItemEntity> tcsbOrderItemList =  tcsbOrderPage.getTcsbOrderItemList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			 //生成订单号
            String orderNoGenerate = OrderNumberGenerateUtil.getOrderNumber();
            tcsbOrder.setOrderNo(orderNoGenerate);
		    TSUser user = getCurrentUser();
            tcsbOrder.setShopId(user.getShopId());
			tcsbOrderService.addMain(tcsbOrder, tcsbOrderItemList);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "订单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新订单
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbOrderEntity tcsbOrder,TcsbOrderPage tcsbOrderPage, HttpServletRequest request) {
		List<TcsbOrderItemEntity> tcsbOrderItemList =  tcsbOrderPage.getTcsbOrderItemList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			tcsbOrderService.updateMain(tcsbOrder, tcsbOrderItemList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新订单失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 订单新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbOrderEntity tcsbOrder, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbOrder.getId())) {
			tcsbOrder = tcsbOrderService.getEntity(TcsbOrderEntity.class, tcsbOrder.getId());
			req.setAttribute("tcsbOrderPage", tcsbOrder);
		}
		 //根据 shopId获取所有食物
        TSUser user = getCurrentUser();
        if (!checkAdmin()) {

            List<TcsbFoodTypeEntity> tcsbFoodTypeEntity = tcsbFoodTypeService.findHql("from TcsbFoodTypeEntity where shopId = ?", user.getShopId());
            req.setAttribute("tcsbFoodTypeEntity", tcsbFoodTypeEntity);

            List<TcsbFoodEntity> tcsbFoodEntities = tcsbFoodService.findHql("from TcsbFoodEntity where shopId = ?", user.getShopId());
            req.setAttribute("tcsbFoodEntities", tcsbFoodEntities);
        }
		return new ModelAndView("com/tcsb/order/tcsbOrder-add");
	}
	
    @RequestMapping(params = "getfoodByfoodType")
    @ResponseBody
    public List<TcsbFoodEntity> getfoodByfoodType(String typeid) {
        List<TcsbFoodEntity> tcsbFoodEntity = tcsbFoodService.findHql("from TcsbFoodEntity where foodTypeId = ?", typeid);
        return tcsbFoodEntity;
    }
	/**
	 * 订单编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbOrderEntity tcsbOrder, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbOrder.getId())) {
			tcsbOrder = tcsbOrderService.getEntity(TcsbOrderEntity.class, tcsbOrder.getId());
			req.setAttribute("tcsbOrderPage", tcsbOrder);
			//设置优惠券和优惠活动
			if (StringUtil.isNotEmpty(tcsbOrder.getDiscountActivityId())) {
				//
				TcsbDiscountActivityEntity tcsbDiscountActivityEntity = tcsbDiscountActivityService.get(TcsbDiscountActivityEntity.class, tcsbOrder.getDiscountActivityId());
				TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplateEntity = tcsbShopFullcutTemplateService.get(TcsbShopFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
				tcsbOrder.setShopDisPrice(Double.parseDouble(tcsbShopFullcutTemplateEntity.getDiscount()+""));
			}else {
				tcsbOrder.setShopDisPrice(0.0);
			}
			if (StringUtil.isNotEmpty(tcsbOrder.getCouponId())) {
				//
				TcsbCouponEntity tcsbCouponEntity = tcsbCouponService.get(TcsbCouponEntity.class, tcsbOrder.getCouponId());
				TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = tcsbFullcutTemplateService.get(TcsbFullcutTemplateEntity.class, tcsbCouponEntity.getFullcutTemplateId());
				tcsbOrder.setUserDisPrice(Double.parseDouble(tcsbFullcutTemplateEntity.getDiscount()+""));
			}else {
				tcsbOrder.setUserDisPrice(0.0);
			}
			//根据订单号获取菜品价格
			Double totalMoney = getOrderItemMoney(tcsbOrder.getId());
			//设置配送地址
			TcsbDistributionAddressEntity tcsbDistributionAddressEntity = tcsbDistributionAddressService.get(TcsbDistributionAddressEntity.class, tcsbOrder.getDistributionAddressId());
			tcsbOrder.setTcsbDistributionAddressEntity(tcsbDistributionAddressEntity);
			Double aExtra = BigDecimalUtil.add(tcsbOrder.getBoxFeePrice(), tcsbOrder.getDistributionPrice());
			tcsbOrder.setTotalPrice(BigDecimalUtil.add(aExtra, totalMoney));
			Double dExtra = BigDecimalUtil.add(tcsbOrder.getShopDisPrice(), tcsbOrder.getUserDisPrice());
			tcsbOrder.setToPayPrice(BigDecimalUtil.sub(tcsbOrder.getTotalPrice(), dExtra));
		}
		
		/*//根据 shopId获取所有食物*/
        //根据 shopId获取所有食物分类
        TSUser user = getCurrentUser();
        if (!checkAdmin()) {
            List<TcsbFoodTypeEntity> tcsbFoodTypeEntity = tcsbFoodService.findHql("from TcsbFoodTypeEntity where shopId = ?", user.getShopId());
            req.setAttribute("tcsbFoodTypeEntity", tcsbFoodTypeEntity); 
            List<TcsbFoodEntity> tcsbFoodEntities = tcsbFoodService.findHql("from TcsbFoodEntity where shopId = ?", user.getShopId());
            req.setAttribute("tcsbFoodEntities", tcsbFoodEntities);
        }
		return new ModelAndView("com/tcsb/order/tcsbOrder-update");
	}
	
	/**
	 * 下单获取打印信息
	 * @param 
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "getPrintOrder")
	@ResponseBody
	public AjaxJson getPrintOrder(TcsbOrderEntity tcsbOrderEntity, HttpServletRequest req) {
		String message = null;
		AjaxJson ajaxJson = new AjaxJson();
		if (StringUtil.isNotEmpty(tcsbOrderEntity.getId())) {
			tcsbOrderEntity = tcsbOrderService.getEntity(TcsbOrderEntity.class, tcsbOrderEntity.getId());
			TcsbOrderPrintVO tcsbOrderPrintVO = new TcsbOrderPrintVO();
			if (tcsbOrderEntity!=null) {
				Double totalPrice = 0.0;
				//初始化子订单信息
				tcsbOrderPrintVO = setOrderPrintVO(tcsbOrderEntity,
						tcsbOrderPrintVO);
				//获取子订单项信息
				List<TcsbOrderItemPrintVO> tcsbOrderItemPrintVOs = new ArrayList<>();
				String sql = "select f.name,i.food_id as foodId,i.count,i.price,i.food_type_id as foodTypeId,i.standard_id as standardId,i.taste_id as tasteId,u.name as unitName,s.name as standardName "
						+ "from tcsb_order_item i " 
						+ "left join tcsb_order o on o.id=i.order_id "
						+ "left join tcsb_food  f on f.id = i.food_id "
						+ "left join tcsb_food_unit u on u.id =f.food_unit_id "
						+ "left join tcsb_food_standard s on s.id = i.standard_id where o.id =?";
				List<Map<String, Object>> orderItemMaps = systemService.findForJdbc(sql, tcsbOrderEntity.getId());
				for (Map<String, Object> map : orderItemMaps) {
					TcsbOrderItemPrintVO itemPrintVO = new TcsbOrderItemPrintVO();
					itemPrintVO.setCount((Integer)map.get("count"));
					itemPrintVO.setFoodName((String)map.get("name")); 
					itemPrintVO.setPrice((Double)map.get("price"));
					totalPrice = BigDecimalUtil.add(totalPrice, BigDecimalUtil.mul((Integer)map.get("count"), (Double)map.get("price")));
					itemPrintVO.setFoodId((String)map.get("foodId"));
					itemPrintVO.setUnitName((String)map.get("unitName"));
					if (StringUtil.isNotEmpty((String)map.get("standardName"))) {
						itemPrintVO.setStandardName((String)map.get("standardName"));
					}
					if (StringUtil.isNotEmpty((String)map.get("tasteId"))) {
						String tasteIds = (String)map.get("tasteId");
						String tasteid[] = tasteIds.split(",");
						if (tasteid.length==1) {
							TcsbFoodTasteFunctionEntity tcsbFoodTasteFunctionEntity = systemService.get(TcsbFoodTasteFunctionEntity.class, tasteid[0]);
							itemPrintVO.setTasteName(tcsbFoodTasteFunctionEntity.getStatename());
						}else {
							String tasteName ="";
							for (int i = 0; i < tasteid.length; i++) {
								TcsbFoodTasteFunctionEntity tcsbFoodTasteFunctionEntity = systemService.get(TcsbFoodTasteFunctionEntity.class, tasteid[i]);
								if (i==tasteid.length-1) {
									tasteName+=tcsbFoodTasteFunctionEntity.getStatename();
								}else {
									tasteName+=tcsbFoodTasteFunctionEntity.getStatename()+",";
								}
							}
							itemPrintVO.setTasteName(tasteName);
						}

					}
					tcsbOrderItemPrintVOs.add(itemPrintVO);
				}
				Double aextra = BigDecimalUtil.add(tcsbOrderPrintVO.getBoxFeePrice(), tcsbOrderPrintVO.getDistributionPrice()); 
				//总消费是菜的价格加上餐盒费和配送费
				tcsbOrderPrintVO.setTotalPrice(BigDecimalUtil.add(totalPrice, aextra));
				Double dExtra = BigDecimalUtil.add(tcsbOrderPrintVO.getShopDisPrice(), tcsbOrderPrintVO.getUserDisPrice());
				Double toPayPrice = BigDecimalUtil.sub(totalPrice, dExtra);
				toPayPrice = BigDecimalUtil.add(toPayPrice, aextra);
				tcsbOrderPrintVO.setToPayPrice(toPayPrice);
				//获取打印机信息
				List<TcsbPrinterEntity> tcsbPrinterEntities = systemService.findHql("from TcsbPrinterEntity where shopId = ? and autoPrint = ?", tcsbOrderEntity.getShopId(),"1");
				
				if (!tcsbPrinterEntities.isEmpty()) {
					
					//存打印机信息
					for (TcsbPrinterEntity tcsbPrinterEntity : tcsbPrinterEntities) {
						List<TcsbPrinterFoodFunEntity>  tcsbPrinterFoodFunEntities = systemService.findHql("from TcsbPrinterFoodFunEntity where printerId = ?", tcsbPrinterEntity.getId());
						List<String>  tempId = new ArrayList<>();
						for (int i = 0; i < tcsbPrinterFoodFunEntities.size(); i++) {
							tempId.add(tcsbPrinterFoodFunEntities.get(i).getFoodId());
						}
						CriteriaQuery cq = new CriteriaQuery(TcsbFoodEntity.class);
						cq.add(Restrictions.in("id", tempId));
						List<TcsbFoodEntity> tcsbFoodEntities =  systemService.getListByCriteriaQuery(
			    				cq, false);
						//打印机有分配权限
						if (!tcsbFoodEntities.isEmpty()) {
							//创建所有食物id集合
							List<String> tempList = new ArrayList<>();
							for (int i = 0; i < tcsbFoodEntities.size(); i++) {
								tempList.add(tcsbFoodEntities.get(i).getId());
							}
							//权限中是否有匹配中的食物
							for (int j = 0; j < tcsbOrderItemPrintVOs.size(); j++) {
								if (tempList.contains(tcsbOrderItemPrintVOs.get(j).getFoodId())) {
									if (tcsbPrinterEntity.getTcsbOrderItemPrintVOs()!=null) {
										tcsbPrinterEntity.getTcsbOrderItemPrintVOs().add(tcsbOrderItemPrintVOs.get(j));
									}else {
										List<TcsbOrderItemPrintVO> tcsbOrderItemPrintVOs2 = new ArrayList<>();
										tcsbOrderItemPrintVOs2.add(tcsbOrderItemPrintVOs.get(j));
										tcsbPrinterEntity.setTcsbOrderItemPrintVOs(tcsbOrderItemPrintVOs2);
									}
								}
							}
						}else {
							//打印机有分配权限
						}
					}
					//存取打印机中有食物的打印机
					List<TcsbPrinterEntity> tcsbPrinterEntitiesNow = new ArrayList<>();
					for (int i = 0; i < tcsbPrinterEntities.size(); i++) {
						if (tcsbPrinterEntities.get(i).getTcsbOrderItemPrintVOs()!=null) {
							tcsbPrinterEntitiesNow.add(tcsbPrinterEntities.get(i));
						}
					}
					tcsbOrderPrintVO.setTcsbPrinterEntities(tcsbPrinterEntitiesNow);
					ajaxJson.setMsg("打印信息获取成功");
					ajaxJson.setSuccess(true);
					ajaxJson.setObj(tcsbOrderPrintVO);
				}else {
					ajaxJson.setSuccess(false);
					ajaxJson.setMsg("没有设置打印机");
				}
			}
		}
		return ajaxJson;
	}

	private TcsbOrderPrintVO setOrderPrintVO(TcsbOrderEntity tcsbOrderEntity,
			TcsbOrderPrintVO tcsbOrderPrintVO) {
		tcsbOrderPrintVO.setBoxFeePrice(tcsbOrderEntity.getBoxFeePrice());
		tcsbOrderPrintVO.setCreateTime(DateUtil.date2Str(tcsbOrderEntity.getCreateTime()));
		tcsbOrderPrintVO.setDistributionPrice(tcsbOrderEntity.getDistributionPrice());
		tcsbOrderPrintVO.setNote(tcsbOrderEntity.getNote());
		tcsbOrderPrintVO.setOrderId(tcsbOrderEntity.getId());
		tcsbOrderPrintVO.setOrderNo(tcsbOrderEntity.getOrderNo());
		tcsbOrderPrintVO.setPayTime(DateUtil.date2Str(tcsbOrderEntity.getPayTime()));
		//设置店铺优惠和优惠券
		if (StringUtil.isNotEmpty(tcsbOrderEntity.getDiscountActivityId())) {
			TcsbDiscountActivityEntity tcsbDiscountActivityEntity = tcsbDiscountActivityService.get(TcsbDiscountActivityEntity.class, tcsbOrderEntity.getDiscountActivityId());
			TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplateEntity = tcsbShopFullcutTemplateService.get(TcsbShopFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
			tcsbOrderPrintVO.setShopDisPrice(Double.parseDouble(tcsbShopFullcutTemplateEntity.getDiscount()+""));
		}else {
			tcsbOrderPrintVO.setShopDisPrice(0.0);
		}
		if (StringUtil.isNotEmpty(tcsbOrderEntity.getCouponId())) {
			TcsbCouponEntity tcsbCouponEntity = tcsbCouponService.get(TcsbCouponEntity.class, tcsbOrderEntity.getCouponId());
			TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = tcsbFullcutTemplateService.get(TcsbFullcutTemplateEntity.class, tcsbCouponEntity.getFullcutTemplateId());
			tcsbOrderPrintVO.setUserDisPrice(Double.parseDouble(tcsbFullcutTemplateEntity.getDiscount()+""));
		}else {
			tcsbOrderPrintVO.setUserDisPrice(0.0);
		}
		//设置配送地址
		TcsbDistributionAddressEntity tcsbDistributionAddressEntity = tcsbDistributionAddressService.get(TcsbDistributionAddressEntity.class, tcsbOrderEntity.getDistributionAddressId());
		tcsbOrderPrintVO.setTcsbDistributionAddressEntity(tcsbDistributionAddressEntity);
		
		
		TcsbShopEntity tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, tcsbOrderEntity.getShopId());
		tcsbOrderPrintVO.setShopName(tcsbShopEntity.getName());
		return tcsbOrderPrintVO;
	}
	
	/**
	 * 订单打印预览跳转(版本3分权限根据食物使用lodop)
	 * @param 
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "print3")
	public ModelAndView print3(TcsbOrderEntity tcsbOrderEntity, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbOrderEntity.getId())) {
			tcsbOrderEntity = tcsbOrderService.getEntity(TcsbOrderEntity.class, tcsbOrderEntity.getId());
			TcsbOrderPrintVO tcsbOrderPrintVO = new TcsbOrderPrintVO();
			if (tcsbOrderEntity!=null) {
				Double totalPrice = 0.0;
				//初始化子订单信息
				tcsbOrderPrintVO = setOrderPrintVO(tcsbOrderEntity,
						tcsbOrderPrintVO);
				//获取子订单项信息
				List<TcsbOrderItemPrintVO> tcsbOrderItemPrintVOs = new ArrayList<>();
				String sql = "select f.name,i.food_id as foodId,i.count,i.price,i.food_type_id as foodTypeId,i.standard_id as standardId,i.taste_id as tasteId,u.name as unitName,s.name as standardName "
						+ "from tcsb_order_item i " 
						+ "left join tcsb_order o on o.id=i.order_id "
						+ "left join tcsb_food  f on f.id = i.food_id "
						+ "left join tcsb_food_unit u on u.id =f.food_unit_id "
						+ "left join tcsb_food_standard s on s.id = i.standard_id where o.id =?";
				List<Map<String, Object>> orderItemMaps = systemService.findForJdbc(sql, tcsbOrderEntity.getId());
				for (Map<String, Object> map : orderItemMaps) {
					TcsbOrderItemPrintVO itemPrintVO = new TcsbOrderItemPrintVO();
					itemPrintVO.setCount((Integer)map.get("count"));
					itemPrintVO.setFoodName((String)map.get("name")); 
					itemPrintVO.setPrice((Double)map.get("price"));
					totalPrice = BigDecimalUtil.add(totalPrice, BigDecimalUtil.mul((Integer)map.get("count"), (Double)map.get("price")));
					itemPrintVO.setFoodId((String)map.get("foodId"));
					itemPrintVO.setUnitName((String)map.get("unitName"));
					if (StringUtil.isNotEmpty((String)map.get("standardName"))) {
						itemPrintVO.setStandardName((String)map.get("standardName"));
					}
					if (StringUtil.isNotEmpty((String)map.get("tasteId"))) {
						String tasteIds = (String)map.get("tasteId");
						String tasteid[] = tasteIds.split(",");
						if (tasteid.length==1) {
							TcsbFoodTasteFunctionEntity tcsbFoodTasteFunctionEntity = systemService.get(TcsbFoodTasteFunctionEntity.class, tasteid[0]);
							itemPrintVO.setTasteName(tcsbFoodTasteFunctionEntity.getStatename());
						}else {
							String tasteName ="";
							for (int i = 0; i < tasteid.length; i++) {
								TcsbFoodTasteFunctionEntity tcsbFoodTasteFunctionEntity = systemService.get(TcsbFoodTasteFunctionEntity.class, tasteid[i]);
								if (i==tasteid.length-1) {
									tasteName+=tcsbFoodTasteFunctionEntity.getStatename();
								}else {
									tasteName+=tcsbFoodTasteFunctionEntity.getStatename()+",";
								}
							}
							itemPrintVO.setTasteName(tasteName);
						}

					}
					tcsbOrderItemPrintVOs.add(itemPrintVO);
				}
				Double aextra = BigDecimalUtil.add(tcsbOrderPrintVO.getBoxFeePrice(), tcsbOrderPrintVO.getDistributionPrice()); 
				//总消费是菜的价格加上餐盒费和配送费
				tcsbOrderPrintVO.setTotalPrice(BigDecimalUtil.add(totalPrice, aextra));
				Double dExtra = BigDecimalUtil.add(tcsbOrderPrintVO.getShopDisPrice(), tcsbOrderPrintVO.getUserDisPrice());
				Double toPayPrice = BigDecimalUtil.sub(totalPrice, dExtra);
				toPayPrice = BigDecimalUtil.add(toPayPrice, aextra);
				tcsbOrderPrintVO.setToPayPrice(toPayPrice);
				//获取打印机信息
				List<TcsbPrinterEntity> tcsbPrinterEntities = systemService.findHql("from TcsbPrinterEntity where shopId = ?", getCurrentUser().getShopId());
				
				if (!tcsbPrinterEntities.isEmpty()) {
					
					//存打印机信息
					for (TcsbPrinterEntity tcsbPrinterEntity : tcsbPrinterEntities) {
						List<TcsbPrinterFoodFunEntity>  tcsbPrinterFoodFunEntities = systemService.findHql("from TcsbPrinterFoodFunEntity where printerId = ?", tcsbPrinterEntity.getId());
						List<String>  tempId = new ArrayList<>();
						for (int i = 0; i < tcsbPrinterFoodFunEntities.size(); i++) {
							tempId.add(tcsbPrinterFoodFunEntities.get(i).getFoodId());
						}
						CriteriaQuery cq = new CriteriaQuery(TcsbFoodEntity.class);
						cq.add(Restrictions.in("id", tempId));
						List<TcsbFoodEntity> tcsbFoodEntities =  systemService.getListByCriteriaQuery(
			    				cq, false);
						//打印机有分配权限
						if (!tcsbFoodEntities.isEmpty()) {
							//创建所有食物id集合
							List<String> tempList = new ArrayList<>();
							for (int i = 0; i < tcsbFoodEntities.size(); i++) {
								tempList.add(tcsbFoodEntities.get(i).getId());
							}
							//权限中是否有匹配中的食物
							for (int j = 0; j < tcsbOrderItemPrintVOs.size(); j++) {
								if (tempList.contains(tcsbOrderItemPrintVOs.get(j).getFoodId())) {
									if (tcsbPrinterEntity.getTcsbOrderItemPrintVOs()!=null) {
										tcsbPrinterEntity.getTcsbOrderItemPrintVOs().add(tcsbOrderItemPrintVOs.get(j));
									}else {
										List<TcsbOrderItemPrintVO> tcsbOrderItemPrintVOs2 = new ArrayList<>();
										tcsbOrderItemPrintVOs2.add(tcsbOrderItemPrintVOs.get(j));
										tcsbPrinterEntity.setTcsbOrderItemPrintVOs(tcsbOrderItemPrintVOs2);
									}
								}
							}
						}else {
							//打印机有分配权限
						}
					}
					//存取打印机中有食物的打印机
					List<TcsbPrinterEntity> tcsbPrinterEntitiesNow = new ArrayList<>();
					for (int i = 0; i < tcsbPrinterEntities.size(); i++) {
						if (tcsbPrinterEntities.get(i).getTcsbOrderItemPrintVOs()!=null) {
							tcsbPrinterEntitiesNow.add(tcsbPrinterEntities.get(i));
						}
					}
					tcsbOrderPrintVO.setTcsbPrinterEntities(tcsbPrinterEntitiesNow);
					req.setAttribute("tcsbOrderPage", tcsbOrderPrintVO);
				}else {
					tcsbOrderPrintVO.setTcsbOrderItemPrintVOs(tcsbOrderItemPrintVOs);
					req.setAttribute("tcsbOrderPage", tcsbOrderPrintVO);
				}
			}
		}
		return new ModelAndView("com/tcsb/order/tcsbOrderPrint3");
	}
	
	
	
	/**
	 * 加载明细列表[订单项管理]
	 * 
	 * @return
	 */
	@RequestMapping(params = "tcsbOrderItemList")
	public ModelAndView tcsbOrderItemList(TcsbOrderEntity tcsbOrder, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object id0 = tcsbOrder.getId();
		//===================================================================================
		//查询-订单项管理
	    String hql0 = "from TcsbOrderItemEntity where 1 = 1 AND oRDER_ID = ? ";
	    try{
	    	List<TcsbOrderItemEntity> tcsbOrderItemEntityList = systemService.findHql(hql0,id0);
	    	  for (int i = 0; i < tcsbOrderItemEntityList.size(); i++) {
	                //根据foodid获取食品信息
	                TcsbFoodEntity foodEntity = tcsbFoodService.get(TcsbFoodEntity.class, tcsbOrderItemEntityList.get(i).getFoodId());
	                TcsbFoodTypeEntity tcsbFoodTypeEntity = tcsbFoodTypeService.get(TcsbFoodTypeEntity.class, foodEntity.getFoodTypeId());
	                tcsbOrderItemEntityList.get(i).setFoodTypeId(tcsbFoodTypeEntity.getId());

	            }
	            req.setAttribute("tcsbOrderItemList", tcsbOrderItemEntityList);
	            //根据 shopId获取所有食物
	            TcsbOrderEntity tcsbOrderEntity = tcsbOrderService.get(TcsbOrderEntity.class,tcsbOrder.getId());
	            List<TcsbFoodEntity> tcsbFoodEntities = tcsbFoodService.findHql("from TcsbFoodEntity where shopId = ?", tcsbOrderEntity.getShopId());
	            req.setAttribute("tcsbFoodEntities", tcsbFoodEntities);

	            List<TcsbFoodTypeEntity> tcsbFoodTypeEntity = tcsbFoodService.findHql("from TcsbFoodTypeEntity where shopId = ?", tcsbOrderEntity.getShopId());
	            req.setAttribute("tcsbFoodTypeEntity", tcsbFoodTypeEntity);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/tcsb/orderitem/tcsbOrderItemList");
	}

    /**
    * 导出excel
    *
    * @param request
    * @param response
    */
    @RequestMapping(params = "exportXls")
    public String exportXls(TcsbOrderEntity tcsbOrder,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,ModelMap map) {
    	CriteriaQuery cq = new CriteriaQuery(TcsbOrderEntity.class, dataGrid);
    	//查询条件组装器
    	org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbOrder);
    	try{
    	//自定义追加查询条件
    	}catch (Exception e) {
    		throw new BusinessException(e.getMessage());
    	}
    	cq.add();
    	List<TcsbOrderEntity> list=this.tcsbOrderService.getListByCriteriaQuery(cq, false);
    	List<TcsbOrderPage> pageList=new ArrayList<TcsbOrderPage>();
        if(list!=null&&list.size()>0){
        	for(TcsbOrderEntity entity:list){
        		try{
        		TcsbOrderPage page=new TcsbOrderPage();
        		   MyBeanUtils.copyBeanNotNull2Bean(entity,page);
            	    Object id0 = entity.getId();
				    String hql0 = "from TcsbOrderItemEntity where 1 = 1 AND oRDER_ID = ? ";
        	        List<TcsbOrderItemEntity> tcsbOrderItemEntityList = systemService.findHql(hql0,id0);
            		page.setTcsbOrderItemList(tcsbOrderItemEntityList);
            		pageList.add(page);
            	}catch(Exception e){
            		logger.info(e.getMessage());
            	}
            }
        }
        map.put(NormalExcelConstants.FILE_NAME,"订单");
        map.put(NormalExcelConstants.CLASS,TcsbOrderPage.class);
        map.put(NormalExcelConstants.PARAMS,new ExportParams("订单列表", "导出人:Jeecg",
            "导出信息"));
        map.put(NormalExcelConstants.DATA_LIST,pageList);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

    /**
	 * 通过excel导入数据
	 * @param request
	 * @param
	 * @return
	 */
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(2);
			params.setNeedSave(true);
			try {
				List<TcsbOrderPage> list =  ExcelImportUtil.importExcel(file.getInputStream(), TcsbOrderPage.class, params);
				TcsbOrderEntity entity1=null;
				for (TcsbOrderPage page : list) {
					entity1=new TcsbOrderEntity();
					MyBeanUtils.copyBeanNotNull2Bean(page,entity1);
		            tcsbOrderService.addMain(entity1, page.getTcsbOrderItemList());
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			}
			return j;
	}
	/**
	* 导出excel 使模板
	*/
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ModelMap map) {
		map.put(NormalExcelConstants.FILE_NAME,"订单");
		map.put(NormalExcelConstants.CLASS,TcsbOrderPage.class);
		map.put(NormalExcelConstants.PARAMS,new ExportParams("订单列表", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
		"导出信息"));
		map.put(NormalExcelConstants.DATA_LIST,new ArrayList());
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	* 导入功能跳转
	*
	* @return
	*/
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "tcsbOrderController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	 /**
     * 生成订单
     * 只返回订单号
     *
     * @return
     */
    @RequestMapping(value = "saveOrderReturnOrderId", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxJsonApi saveOrderReturnOrderId(HttpServletRequest request,String order) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            TcsbOrderVO tcsbOrderVO = JSONHelper.fromJsonToObject(order, TcsbOrderVO.class);
            map = tcsbOrderService.saveOrderReturnOrderId(tcsbOrderVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        ajaxJsonApi.setSuccess(true);
        ajaxJsonApi.setObj(map);
        return ajaxJsonApi;
    }
    
    /**
     * 获取订单信息
     * @param request
     * @param orderId
     * @return
     */
    @RequestMapping(value = "getOrderByOrderId", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxJsonApi getOrderByOrderId(HttpServletRequest request,String orderId) {
        TcsbOrderReturnVO tcsbOrderReturnVO = tcsbOrderService.getOrderByOrderId(orderId);
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        ajaxJsonApi.setSuccess(true);
        ajaxJsonApi.setObj(tcsbOrderReturnVO);
        return ajaxJsonApi;
    }
	
    /**
     * 取消订单
     * @param request
     * @param orderId
     * @return
     */
    @RequestMapping(value = "cancelOrderByOrderId", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxJsonApi cancelOrderByOrderId(HttpServletRequest request,String orderId) {
        TcsbOrderEntity tcsbOrderEntity = tcsbOrderService.get(TcsbOrderEntity.class, orderId);
        tcsbOrderEntity.setPayStatus("2");
        tcsbOrderEntity.setStatus("canceled");
        tcsbOrderService.saveOrUpdate(tcsbOrderEntity);
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        ajaxJsonApi.setSuccess(true);
        ajaxJsonApi.setMsg("取消订单成功");
        return ajaxJsonApi;
    }
	

    
 	
 	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<TcsbOrderEntity> list() {
		List<TcsbOrderEntity> listTcsbOrders=tcsbOrderService.getList(TcsbOrderEntity.class);
		return listTcsbOrders;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbOrderEntity task = tcsbOrderService.get(TcsbOrderEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}
 	
 	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbOrderPage tcsbOrderPage, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbOrderPage>> failures = validator.validate(tcsbOrderPage);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		List<TcsbOrderItemEntity> tcsbOrderItemList =  tcsbOrderPage.getTcsbOrderItemList();
		
		TcsbOrderEntity tcsbOrder = new TcsbOrderEntity();
		try{
			MyBeanUtils.copyBeanNotNull2Bean(tcsbOrder,tcsbOrderPage);
		}catch(Exception e){
            logger.info(e.getMessage());
        }
		tcsbOrderService.addMain(tcsbOrder, tcsbOrderItemList);

		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbOrderPage.getId();
		URI uri = uriBuilder.path("/rest/tcsbOrderController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbOrderPage tcsbOrderPage) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbOrderPage>> failures = validator.validate(tcsbOrderPage);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		List<TcsbOrderItemEntity> tcsbOrderItemList =  tcsbOrderPage.getTcsbOrderItemList();
		
		TcsbOrderEntity tcsbOrder = new TcsbOrderEntity();
		try{
			MyBeanUtils.copyBeanNotNull2Bean(tcsbOrder,tcsbOrderPage);
		}catch(Exception e){
            logger.info(e.getMessage());
        }
		tcsbOrderService.updateMain(tcsbOrder, tcsbOrderItemList);

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		TcsbOrderEntity tcsbOrder = tcsbOrderService.get(TcsbOrderEntity.class, id);
		tcsbOrderService.delMain(tcsbOrder);
	}
}
