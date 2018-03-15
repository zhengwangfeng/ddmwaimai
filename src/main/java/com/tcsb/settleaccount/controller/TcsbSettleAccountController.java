package com.tcsb.settleaccount.controller;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.order.service.TcsbOrderServiceI;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.settleaccount.entity.TcsbSettleAccountEntity;
import com.tcsb.settleaccount.service.TcsbSettleAccountServiceI;
import com.tcsb.settleaccount.vo.ExportSettleUtil;
import com.tcsb.settleaccount.vo.ShopIncomeVo;
import com.tcsb.settleaccount.vo.StatisticsSettleVo;
import com.tcsb.settleaccount.vo.TcsbSettleAccountEntityVo;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.tcsbshopbankcard.entity.TcsbShopBankcardEntity;

/**   
 * @Title: Controller  
 * @Description: 账单结算
 * @author onlineGenerator
 * @date 2017-05-15 19:58:02
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbSettleAccountController")
public class TcsbSettleAccountController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbSettleAccountController.class);

	@Autowired
	private TcsbSettleAccountServiceI tcsbSettleAccountService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	@Autowired
	private TcsbOrderServiceI tcsbOrderService;
	
	
	/**
	 * 营业收入列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "income")
	public ModelAndView incomt(HttpServletRequest request,String settleWeek,ModelMap modelMap) {
		modelMap.addAttribute("startTime", settleWeek);
		modelMap.addAttribute("endTime", settleWeek);
		return new ModelAndView("com/tcsb/incomestatistics/tcsbIncomeStatisticsListSettle");
	}
	
	
	

	/**
	 * 账单结算列表 页面跳转
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request,ModelMap modelMap,String shopId) throws Exception {
		TcsbShopEntity tcsbShopEntity = null;
		if(StringUtil.isNotEmpty(shopId)){
			tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, shopId);
			modelMap.addAttribute("shopId", shopId);
		}else{
			TSUser user = getCurrentUser();
			tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, user.getShopId());
		}
		
		//获取当前系统日期
		Calendar calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat),"yyyy-MM-dd");
		//检测是否有生成过结算数据
		CriteriaQuery cq = new CriteriaQuery(TcsbSettleAccountEntity.class);
		cq.add(Restrictions.eq("shopId", tcsbShopEntity.getId()));
		cq.addOrder("waitSettleData", SortDirection.desc);
		cq.setPageSize(1);
		List<TcsbSettleAccountEntity> TcsbSettleAccountList = tcsbSettleAccountService.getListByCriteriaQuery(cq, true);
		if(TcsbSettleAccountList.size()>0){
			//有生成过结算数据
				//获取最新一条的结算数据时间
			Calendar querycalendar = new GregorianCalendar(); 
			querycalendar.setTime(TcsbSettleAccountList.get(0).getWaitSettleData()); 
			querycalendar.add(querycalendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
			String ordersql = "select id,DATE_FORMAT( pay_time, '%Y-%m-%d' ) as paytime "
					+ " from tcsb_order where SHOP_ID=?"
					+ " and PAY_STATUS='1' and pay_time>=? and pay_time<?";
			
			List<Map<String,Object>> tcsbOrderEntityList = tcsbSettleAccountService.findForJdbc(ordersql,tcsbShopEntity.getId(),querycalendar.getTime(),calSrc.getTime());
			Double toPayPrice = 0.0;
			TcsbSettleAccountEntity tcsbSettleAccountEntity = new TcsbSettleAccountEntity();
			for (int i = 0; i < tcsbOrderEntityList.size(); i++) {
				tcsbSettleAccountEntity.setIsSettle("N");
				Calendar calDes = DateUtils.parseCalendar(tcsbOrderEntityList.get(i).get("paytime").toString(),"yyyy-MM-dd");
				Calendar calendar = new GregorianCalendar(); 
			    calendar.setTime(calDes.getTime()); 
			    calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
				tcsbSettleAccountEntity.setSettleData(calendar.getTime());
				tcsbSettleAccountEntity.setSettleWay(null);
				tcsbSettleAccountEntity.setShopId(tcsbShopEntity.getId());
				TcsbOrderEntity tcsbOrderEntity = tcsbOrderService.getOrderDetailByOrderId(tcsbOrderEntityList.get(i).get("id").toString());
				toPayPrice = BigDecimalUtil.add(toPayPrice, tcsbOrderEntity.getToPayPrice());
				tcsbSettleAccountEntity.setWaitSettleData(calDes.getTime());
				
			}
			tcsbSettleAccountEntity.setTotal(BigDecimalUtil.numericRetentionDecimal(toPayPrice, 2));
			tcsbSettleAccountService.save(tcsbSettleAccountEntity);
		}else{
			//没有生成结算数据，重新生成结算数据
			//String ordersql = "select DATE_FORMAT( CREATE_TIME, '%Y-%m-%d' ) as createTime, sum(online_price) as onlinePrice  from tcsb_order where SHOP_ID=? and PAY_STATUS=1 and CREATE_TIME<? group by DATE_FORMAT( CREATE_TIME, '%Y-%m-%d' )";
			String ordersql = "select id,DATE_FORMAT( pay_time, '%Y-%m-%d' ) as paytime "
					+ " from tcsb_order where SHOP_ID=?"
					+ " and PAY_STATUS='1'  and pay_time<?";
			List<Map<String,Object>> tcsbOrderEntityList = tcsbSettleAccountService.findForJdbc(ordersql,tcsbShopEntity.getId(),calSrc.getTime());
			Double toPayPrice = 0.0;
			TcsbSettleAccountEntity tcsbSettleAccountEntity = new TcsbSettleAccountEntity();
			for (int i = 0; i < tcsbOrderEntityList.size(); i++) {
				tcsbSettleAccountEntity.setIsSettle("N");
				Calendar calDes = DateUtils.parseCalendar(tcsbOrderEntityList.get(i).get("paytime").toString(),"yyyy-MM-dd");
				Calendar calendar = new GregorianCalendar(); 
			    calendar.setTime(calDes.getTime()); 
			    calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
				tcsbSettleAccountEntity.setSettleData(calendar.getTime());
				tcsbSettleAccountEntity.setSettleWay(null);
				tcsbSettleAccountEntity.setShopId(tcsbShopEntity.getId());
				TcsbOrderEntity tcsbOrderEntity = tcsbOrderService.getOrderDetailByOrderId(tcsbOrderEntityList.get(i).get("id").toString());
				toPayPrice = BigDecimalUtil.add(toPayPrice, tcsbOrderEntity.getToPayPrice());
				tcsbSettleAccountEntity.setWaitSettleData(calDes.getTime());
			}
			tcsbSettleAccountEntity.setTotal(BigDecimalUtil.numericRetentionDecimal(toPayPrice, 2));
			tcsbSettleAccountService.save(tcsbSettleAccountEntity);
		}
		
		//获取当前系统日期
		String todayDay = DateUtils.format(new Date(), "yyyy-MM-dd");
		//获取今日待结算的数据
		String todayordersql = "select id, DATE_FORMAT( pay_time, '%Y-%m-%d' ) as paytime "
				+ " from tcsb_order"
				+ " where SHOP_ID=? and PAY_STATUS='1' and pay_time>=?";
		List<Map<String,Object>> tcsbOrderEntityList = tcsbSettleAccountService.findForJdbc(todayordersql,tcsbShopEntity.getId(),todayDay);
		if(tcsbOrderEntityList.size()>0){
			Double toPayPrice = 0.0;
			for (Map<String, Object> map : tcsbOrderEntityList) {  
				TcsbOrderEntity tcsbOrderEntity = tcsbOrderService.getOrderDetailByOrderId((String)map.get("id"));
				toPayPrice = BigDecimalUtil.add(toPayPrice, tcsbOrderEntity.getToPayPrice()); 
			}
			Double poundages = BigDecimalUtil.mul(toPayPrice, 0.03);
			Double waitSettleTotal =  BigDecimalUtil.sub(Double.valueOf(toPayPrice), poundages);
			modelMap.addAttribute("thisWaitAllMoney", toPayPrice);
			modelMap.addAttribute("poundage", BigDecimalUtil.numericRetentionDecimal(poundages, 2));//手续费
			modelMap.addAttribute("waitSettleTotal", BigDecimalUtil.numericRetentionDecimal(waitSettleTotal, 2));
			Calendar t = DateUtils.parseCalendar(todayDay, "yyyy-MM-dd");
			t.add(t.DATE, 1);
			todayDay = DateUtils.format(t.getTime(), "yyyy-MM-dd");
			String dataweek = DateUtils.getDayOfWeekByDate(todayDay);
			modelMap.addAttribute("nextSettleData", todayDay+"  "+dataweek);
			
		}else{
			modelMap.addAttribute("thisWaitAllMoney", 0);
			modelMap.addAttribute("waitSettleTotal", 0);
			modelMap.addAttribute("poundage", 0);
			Calendar t = DateUtils.parseCalendar(todayDay, "yyyy-MM-dd");
			t.add(t.DATE, 1);
			todayDay = DateUtils.format(t.getTime(), "yyyy-MM-dd");
			String dataweek = DateUtils.getDayOfWeekByDate(todayDay);
			modelMap.addAttribute("nextSettleData", todayDay+"  "+dataweek);
		}
		return new ModelAndView("com/tcsb/settleaccount/tcsbSettleAccountList");
		
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
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TcsbSettleAccountEntity tcsbSettleAccount,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws IllegalAccessException, InvocationTargetException {
		
		TcsbShopEntity tcsbShopEntity = null;
		if(StringUtil.isNotEmpty(tcsbSettleAccount.getShopId())){
			tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, tcsbSettleAccount.getShopId());
		}else{
			TSUser user = getCurrentUser();
			//获取用户店铺信息
			tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, user.getShopId());
		}
		
		CriteriaQuery cq = new CriteriaQuery(TcsbSettleAccountEntity.class, dataGrid);
		cq.add(Restrictions.eq("shopId", tcsbShopEntity.getId()));
		cq.addOrder("settleData", SortDirection.desc);
		cq.add();
		List<TcsbSettleAccountEntity> TcsbSettleAccountList= tcsbSettleAccountService.getListByCriteriaQuery(cq, true);
		List<TcsbSettleAccountEntityVo> TcsbSettleAccountEntityVoList = new ArrayList<>();
		TcsbSettleAccountEntityVo TcsbSettleAccountEntityVo;
		for (TcsbSettleAccountEntity TcsbSettleAccountEntity : TcsbSettleAccountList) {
			TcsbSettleAccountEntityVo = new TcsbSettleAccountEntityVo();
			BeanUtils.copyProperties(TcsbSettleAccountEntityVo, TcsbSettleAccountEntity);
			double bound = BigDecimalUtil.mul(Double.valueOf(TcsbSettleAccountEntity.getTotal()), 0.03);
			double tatol = BigDecimalUtil.sub(Double.valueOf(TcsbSettleAccountEntity.getTotal()), bound);
			TcsbSettleAccountEntityVo.setTotal(BigDecimalUtil.numericRetentionDecimal(tatol, 2));
			TcsbSettleAccountEntityVo.setShopName(tcsbShopEntity.getName());
			TcsbSettleAccountEntityVo.setWaitSettleDatas(DateUtils.date_sdf.format(TcsbSettleAccountEntity.getWaitSettleData()));
			TcsbSettleAccountEntityVoList.add(TcsbSettleAccountEntityVo);
		}
		List<TcsbSettleAccountEntity> TcsbSettleAccountListcount = tcsbSettleAccountService.findByQueryString("from TcsbSettleAccountEntity where shopId='"+tcsbShopEntity.getId()+"'");
		dataGrid.setTotal(TcsbSettleAccountListcount.size());
		dataGrid.setResults(TcsbSettleAccountEntityVoList);
		TagUtil.datagrid(response, dataGrid);
	}

	
	
	
	
	
	/**
	 * 总后台获取账单结算信息
	 * @param tcsbSettleAccount
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws ParseException
	 */
	@RequestMapping(params = "admindatagrid")
	public void admindatagrid(TcsbSettleAccountEntity tcsbSettleAccount,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws IllegalAccessException, InvocationTargetException, ParseException {
		//	TODO
		CriteriaQuery cq = new CriteriaQuery(TcsbSettleAccountEntity.class, dataGrid);
		String query_settleData_begin = request.getParameter("settleData_begin");
		String query_settleData_end = request.getParameter("settleData_end");
		String isSettle = request.getParameter("isSettle");
		if(StringUtil.isNotEmpty(query_settleData_begin)){
			cq.ge("settleData", new SimpleDateFormat("yyyy-MM-dd").parse(query_settleData_begin));
		}
		if(StringUtil.isNotEmpty(query_settleData_end)){
			cq.le("settleData", new SimpleDateFormat("yyyy-MM-dd").parse(query_settleData_end));
		}
		if(StringUtil.isNotEmpty(isSettle)){
			cq.eq("isSettle", isSettle);
		}
		cq.addOrder("settleData", SortDirection.desc);
		cq.add();
		List<TcsbSettleAccountEntity> tcsbSettleAccountList= tcsbSettleAccountService.getListByCriteriaQuery(cq, true);

		List<TcsbSettleAccountEntityVo> TcsbSettleAccountEntityVoList = new ArrayList<>();
		TcsbSettleAccountEntityVo tcsbSettleAccountEntityVo;
		for (TcsbSettleAccountEntity tcsbSettleAccounts : tcsbSettleAccountList) {
			tcsbSettleAccountEntityVo = new TcsbSettleAccountEntityVo();
			BeanUtils.copyProperties(tcsbSettleAccountEntityVo, tcsbSettleAccounts);
			
			double bound = BigDecimalUtil.mul(Double.valueOf(tcsbSettleAccountEntityVo.getTotal()),0.03);
			double settleMoney = BigDecimalUtil.sub(Double.valueOf(tcsbSettleAccountEntityVo.getTotal()), bound);
			tcsbSettleAccountEntityVo.setActualSettleTotal(BigDecimalUtil.numericRetentionDecimal(settleMoney,2));
			TcsbShopEntity tcsbShopEntity = tcsbSettleAccountService.get(TcsbShopEntity.class, tcsbSettleAccounts.getShopId());
			tcsbSettleAccountEntityVo.setShopName(tcsbShopEntity.getName());
			TcsbSettleAccountEntityVoList.add(tcsbSettleAccountEntityVo);
		}
		List<TcsbSettleAccountEntity> TcsbSettleAccountListcount = tcsbSettleAccountService.findByQueryString("from TcsbSettleAccountEntity");
		
		dataGrid.setTotal(TcsbSettleAccountListcount.size());
		dataGrid.setResults(TcsbSettleAccountEntityVoList);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "adminupdatesettle")
	public ModelAndView adminupdatesettle(HttpServletRequest request,String id,ModelMap modelMap) {
		request.setAttribute("id", id);
		return new ModelAndView("com/tcsb/settleaccount/tcsbSettleAccount-adminUpdateSettle");
	}
	
	@RequestMapping(params = "adminlist")
	public ModelAndView adminlist(HttpServletRequest request,String settleWeek,ModelMap modelMap) {
		return new ModelAndView("com/tcsb/settleaccount/tcsbSettleShopList");
	}
	
	/**
	 * 账单结算列表 页面跳转
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(params = "createSettle")
	public ModelAndView adminlist(HttpServletRequest request,ModelMap modelMap) throws Exception {
		//生成所有店铺的结算账单
		List<TcsbShopEntity> tcsbShopList = tcsbShopService.findByQueryString("from TcsbShopEntity");
		for (TcsbShopEntity tcsbShopEntity : tcsbShopList) {//获取当前系统日期
			Calendar calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat),"yyyy-MM-dd");
			//检测是否有生成过结算数据
			CriteriaQuery cq = new CriteriaQuery(TcsbSettleAccountEntity.class);
			cq.add(Restrictions.eq("shopId", tcsbShopEntity.getId()));
			cq.addOrder("waitSettleData", SortDirection.desc);
			cq.setPageSize(1);
			List<TcsbSettleAccountEntity> TcsbSettleAccountList = tcsbSettleAccountService.getListByCriteriaQuery(cq, true);
			if(TcsbSettleAccountList.size()>0){
				//有生成过结算数据
					//获取最新一条的结算数据时间
				Calendar querycalendar = new GregorianCalendar(); 
				querycalendar.setTime(TcsbSettleAccountList.get(0).getWaitSettleData()); 
				querycalendar.add(querycalendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
				String ordersql = "select id,DATE_FORMAT( pay_time, '%Y-%m-%d' ) as paytime "
						+ " from tcsb_order where SHOP_ID=?"
						+ " and PAY_STATUS='1' and pay_time>=? and pay_time<?";
				
				List<Map<String,Object>> tcsbOrderEntityList = tcsbSettleAccountService.findForJdbc(ordersql,tcsbShopEntity.getId(),querycalendar.getTime(),calSrc.getTime());
				Double toPayPrice = 0.0;
				TcsbSettleAccountEntity tcsbSettleAccountEntity = new TcsbSettleAccountEntity();
				for (int i = 0; i < tcsbOrderEntityList.size(); i++) {
					tcsbSettleAccountEntity.setIsSettle("N");
					Calendar calDes = DateUtils.parseCalendar(tcsbOrderEntityList.get(i).get("paytime").toString(),"yyyy-MM-dd");
					Calendar calendar = new GregorianCalendar(); 
				    calendar.setTime(calDes.getTime()); 
				    calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
					tcsbSettleAccountEntity.setSettleData(calendar.getTime());
					tcsbSettleAccountEntity.setSettleWay(null);
					tcsbSettleAccountEntity.setShopId(tcsbShopEntity.getId());
					TcsbOrderEntity tcsbOrderEntity = tcsbOrderService.getOrderDetailByOrderId(tcsbOrderEntityList.get(i).get("id").toString());
					toPayPrice = BigDecimalUtil.add(toPayPrice, tcsbOrderEntity.getToPayPrice());
					tcsbSettleAccountEntity.setWaitSettleData(calDes.getTime());
					
				}
				tcsbSettleAccountEntity.setTotal(BigDecimalUtil.numericRetentionDecimal(toPayPrice, 2));
				tcsbSettleAccountService.save(tcsbSettleAccountEntity);
			}else{
				//没有生成结算数据，重新生成结算数据
				//String ordersql = "select DATE_FORMAT( CREATE_TIME, '%Y-%m-%d' ) as createTime, sum(online_price) as onlinePrice  from tcsb_order where SHOP_ID=? and PAY_STATUS=1 and CREATE_TIME<? group by DATE_FORMAT( CREATE_TIME, '%Y-%m-%d' )";
				String ordersql = "select id,DATE_FORMAT( pay_time, '%Y-%m-%d' ) as paytime "
						+ " from tcsb_order where SHOP_ID=?"
						+ " and PAY_STATUS='1'  and pay_time<?";
				List<Map<String,Object>> tcsbOrderEntityList = tcsbSettleAccountService.findForJdbc(ordersql,tcsbShopEntity.getId(),calSrc.getTime());
				Double toPayPrice = 0.0;
				TcsbSettleAccountEntity tcsbSettleAccountEntity = new TcsbSettleAccountEntity();
				for (int i = 0; i < tcsbOrderEntityList.size(); i++) {
					tcsbSettleAccountEntity.setIsSettle("N");
					Calendar calDes = DateUtils.parseCalendar(tcsbOrderEntityList.get(i).get("paytime").toString(),"yyyy-MM-dd");
					Calendar calendar = new GregorianCalendar(); 
				    calendar.setTime(calDes.getTime()); 
				    calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
					tcsbSettleAccountEntity.setSettleData(calendar.getTime());
					tcsbSettleAccountEntity.setSettleWay(null);
					tcsbSettleAccountEntity.setShopId(tcsbShopEntity.getId());
					TcsbOrderEntity tcsbOrderEntity = tcsbOrderService.getOrderDetailByOrderId(tcsbOrderEntityList.get(i).get("id").toString());
					toPayPrice = BigDecimalUtil.add(toPayPrice, tcsbOrderEntity.getToPayPrice());
					tcsbSettleAccountEntity.setWaitSettleData(calDes.getTime());
				}
				tcsbSettleAccountEntity.setTotal(BigDecimalUtil.numericRetentionDecimal(toPayPrice, 2));
				tcsbSettleAccountService.save(tcsbSettleAccountEntity);
			}}
		/*List<TcsbShopEntity> tcsbShopList = tcsbShopService.findByQueryString("from TcsbShopEntity");
		for (TcsbShopEntity tcsbShopEntity : tcsbShopList) {

			CriteriaQuery cq = new CriteriaQuery(TcsbSettleAccountEntity.class);
			cq.add(Restrictions.eq("shopId", tcsbShopEntity.getId()));
			cq.addOrder("settleData", SortDirection.desc);
			cq.setPageSize(1);
			List<TcsbSettleAccountEntity> TcsbSettleAccountList = tcsbSettleAccountService.getListByCriteriaQuery(cq, true);
			System.out.println(TcsbSettleAccountList);
			if(TcsbSettleAccountList.size()>0){
				//获取上一次结算日期
				Calendar calDes = DateUtils.parseCalendar(DateUtils.date2Str(TcsbSettleAccountList.get(0).getSettleData(),DateUtils.datetimeFormat),"yyyy-MM-dd HH:mm:ss");
				Calendar calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat),"yyyy-MM-dd HH:mm:ss");
				int settledata = DateUtils.dateDiff('d', calSrc, calDes);
				//如果时间间隔差大于7，生成该时间段内的结算单
				if(settledata > 7){
					int i = settledata/7;//获取未结算的周期数
					for(int j=0;j<i;j++){
						CriteriaQuery ordercq = new CriteriaQuery(TcsbOrderEntity.class);
						ordercq.add(Restrictions.eq("shopId", tcsbShopEntity.getId()));
						ordercq.add(Restrictions.eq("payMethod", "0"));
						ordercq.add(Restrictions.eq("payStatus", "1"));
					     Calendar calendar = new GregorianCalendar(); 
					     calendar.setTime(TcsbSettleAccountList.get(0).getSettleData()); 
					     calendar.add(calendar.DATE,j*7);//把日期往后增加一天.整数往后推,负数往前移动 
					     Calendar calendar2 = new GregorianCalendar(); 
					     calendar2.setTime(TcsbSettleAccountList.get(0).getSettleData()); 
					     calendar2.add(calendar.DATE,(j+1)*7);//把日期往后增加一天.整数往后推,负数往前移动 
						ordercq.add(Restrictions.ge("createTime",new SimpleDateFormat("yyyy-MM-dd").parse( DateUtils.date_sdf.format(calendar.getTime()))));
						ordercq.add(Restrictions.lt("createTime",new SimpleDateFormat("yyyy-MM-dd").parse( DateUtils.date_sdf.format(calendar2.getTime()))));
						List<TcsbOrderEntity> TcsbOrderList = tcsbSettleAccountService.getListByCriteriaQuery(ordercq, false);
						double settletotal = 0.00;
						
						for (TcsbOrderEntity tcsbOrderEntity : TcsbOrderList) {
							System.out.println(tcsbOrderEntity.getId());
							settletotal += (tcsbOrderEntity.getOnlinePrice()+tcsbOrderEntity.getPlatformDiscountPrice());
						}
						TcsbSettleAccountEntity tst = new TcsbSettleAccountEntity();
						tst.setIsSettle("N");
						tst.setSettleData(new SimpleDateFormat("yyyy-MM-dd").parse( DateUtils.date_sdf.format(calendar2.getTime())));
						tst.setSettleWay("1");
						tst.setShopId(tcsbShopEntity.getId());
						tst.setTotal(settletotal + "");
						tcsbSettleAccountService.save(tst);
					}
				}else{
					//时间间隔小于7天,该时间段的结算期未到
					System.out.println("时间间隔小于7天,该时间段的结算期未到");
				}
			}else{
				///////用户没有结算，根据用户入住时间查找order
				//根据用户ID获取商家的入住时间
				//TSUser user = tcsbSettleAccountService.findUniqueByProperty(TSUser.class, "", value);
				
				Calendar calDes = DateUtils.parseCalendar(DateUtils.date2Str(tcsbShopEntity.getCreateDate(),DateUtils.datetimeFormat),"yyyy-MM-dd HH:mm:ss");
				Calendar calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat),"yyyy-MM-dd HH:mm:ss");
				int settledata = DateUtils.dateDiff('d', calSrc, calDes);
				//如果时间间隔差大于7，生成该时间段内的结算单
				if(settledata > 7){
					int i = settledata/7;//获取未结算的周期数
					for(int j=0;j<i;j++){
						CriteriaQuery ordercq = new CriteriaQuery(TcsbOrderEntity.class);
						ordercq.add(Restrictions.eq("shopId", tcsbShopEntity.getId()));
						ordercq.add(Restrictions.eq("payMethod", "0"));
						ordercq.add(Restrictions.eq("payStatus", "1"));
					     Calendar calendar = new GregorianCalendar(); 
					     calendar.setTime(tcsbShopEntity.getCreateDate()); 
					     calendar.add(calendar.DATE,j*7);//把日期往后增加一天.整数往后推,负数往前移动 
					     Calendar calendar2 = new GregorianCalendar(); 
					     calendar2.setTime(tcsbShopEntity.getCreateDate()); 
					     calendar2.add(calendar.DATE,(j+1)*7);//把日期往后增加一天.整数往后推,负数往前移动 
						ordercq.add(Restrictions.ge("createTime",new SimpleDateFormat("yyyy-MM-dd").parse( DateUtils.date_sdf.format(calendar.getTime()))));
						ordercq.add(Restrictions.lt("createTime",new SimpleDateFormat("yyyy-MM-dd").parse( DateUtils.date_sdf.format(calendar2.getTime()))));
						List<TcsbOrderEntity> TcsbOrderList = tcsbSettleAccountService.getListByCriteriaQuery(ordercq, false);
						double settletotal = 0.00;
						
						for (TcsbOrderEntity tcsbOrderEntity : TcsbOrderList) {
							System.out.println(tcsbOrderEntity.getId());
							settletotal += (tcsbOrderEntity.getOnlinePrice()+tcsbOrderEntity.getPlatformDiscountPrice());
						}
						TcsbSettleAccountEntity tst = new TcsbSettleAccountEntity();
						tst.setIsSettle("N");
						tst.setSettleData(new SimpleDateFormat("yyyy-MM-dd").parse( DateUtils.date_sdf.format(calendar2.getTime())));
						tst.setSettleWay("1");
						tst.setShopId(tcsbShopEntity.getId());
						tst.setTotal(settletotal + "");
						tcsbSettleAccountService.save(tst);
					}
				}else{
					//时间间隔小于7天,该时间段的结算期未到
					System.out.println("时间间隔小于7天,该时间段的结算期未到");
				}
			}
		}*/
	
		return new ModelAndView("com/tcsb/settleaccount/tcsbadminSettleAccountList");
	}

	
	
	/**
	 * 账单结算列表 页面跳转
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(params = "createSettlebydate")
	public ModelAndView createSettlebydate(HttpServletRequest request,ModelMap modelMap) throws Exception {
		//生成所有店铺的结算账单
		List<TcsbShopEntity> tcsbShopList = tcsbShopService.findByQueryString("from TcsbShopEntity");
		for (TcsbShopEntity tcsbShopEntity : tcsbShopList) {
			
			//获取当前系统日期
			Calendar calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat),"yyyy-MM-dd");
			//检测是否有生成过结算数据
			CriteriaQuery cq = new CriteriaQuery(TcsbSettleAccountEntity.class);
			cq.add(Restrictions.eq("shopId", tcsbShopEntity.getId()));
			cq.addOrder("waitSettleData", SortDirection.desc);
			cq.setPageSize(1);
			List<TcsbSettleAccountEntity> TcsbSettleAccountList = tcsbSettleAccountService.getListByCriteriaQuery(cq, true);
			if(TcsbSettleAccountList.size()>0){
				//有生成过结算数据
					//获取最新一条的结算数据时间
				//String ordersql = "select DATE_FORMAT( CREATE_TIME, '%Y-%m-%d' ) as createTime, sum(online_price) as onlinePrice  from tcsb_order where SHOP_ID=? and PAY_STATUS=1 and CREATE_TIME>=? and CREATE_TIME<? group by DATE_FORMAT( CREATE_TIME, '%Y-%m-%d' )";
				
				Calendar querycalendar = new GregorianCalendar(); 
				querycalendar.setTime(TcsbSettleAccountList.get(0).getWaitSettleData()); 
				querycalendar.add(querycalendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
				String ordersql = "select DATE_FORMAT( pay_time, '%Y-%m-%d' ) as paytime,"
						+ " sum(online_price) as onlinePrice  from tcsb_order where SHOP_ID=?"
						+ " and PAY_STATUS=1 and pay_method=0 and pay_time>=? and pay_time<?"
						+ " group by DATE_FORMAT( pay_time, '%Y-%m-%d' )";
				
				List<Map<String,Object>> tcsbOrderEntityList = tcsbSettleAccountService.findForJdbc(ordersql,tcsbShopEntity.getId(),querycalendar.getTime(),calSrc.getTime());
				for (int i = 0; i < tcsbOrderEntityList.size(); i++) {
					TcsbSettleAccountEntity tcsbSettleAccountEntity = new TcsbSettleAccountEntity();
					tcsbSettleAccountEntity.setIsSettle("N");
					Calendar calDes = DateUtils.parseCalendar(tcsbOrderEntityList.get(i).get("createTime").toString(),"yyyy-MM-dd");
					Calendar calendar = new GregorianCalendar(); 
				    calendar.setTime(calDes.getTime()); 
				    calendar.add(calendar.DATE,2);//把日期往后增加一天.整数往后推,负数往前移动 
					tcsbSettleAccountEntity.setSettleData(calendar.getTime());
					tcsbSettleAccountEntity.setSettleWay(null);
					tcsbSettleAccountEntity.setShopId(tcsbShopEntity.getId());
					System.out.println("=============================");
					System.out.println(tcsbOrderEntityList.get(i).get("onlinePrice").toString());
					tcsbSettleAccountEntity.setTotal(BigDecimalUtil.numericRetentionDecimal(tcsbOrderEntityList.get(i).get("onlinePrice").toString(), 2));
					tcsbSettleAccountEntity.setWaitSettleData(calDes.getTime());
					tcsbSettleAccountService.save(tcsbSettleAccountEntity);
				}
			}else{
				//没有生成结算数据，重新生成结算数据
				//String ordersql = "select DATE_FORMAT( CREATE_TIME, '%Y-%m-%d' ) as createTime, sum(online_price) as onlinePrice  from tcsb_order where SHOP_ID=? and PAY_STATUS=1 and CREATE_TIME<? group by DATE_FORMAT( CREATE_TIME, '%Y-%m-%d' )";
				String ordersql = "select DATE_FORMAT( pay_time, '%Y-%m-%d' ) as paytime,"
						+ " sum(online_price) as onlinePrice  from tcsb_order where SHOP_ID=?"
						+ " and PAY_STATUS=1 and pay_method=0 and pay_time<?"
						+ " group by DATE_FORMAT( pay_time, '%Y-%m-%d' )";
				
				List<Map<String,Object>> tcsbOrderEntityList = tcsbSettleAccountService.findForJdbc(ordersql,tcsbShopEntity.getId(),calSrc.getTime());
				for (int i = 0; i < tcsbOrderEntityList.size(); i++) {
					TcsbSettleAccountEntity tcsbSettleAccountEntity = new TcsbSettleAccountEntity();
					tcsbSettleAccountEntity.setIsSettle("N");
					Calendar calDes = DateUtils.parseCalendar(tcsbOrderEntityList.get(i).get("createTime").toString(),"yyyy-MM-dd");
					Calendar calendar = new GregorianCalendar(); 
				    calendar.setTime(calDes.getTime()); 
				    calendar.add(calendar.DATE,2);//把日期往后增加一天.整数往后推,负数往前移动 
					tcsbSettleAccountEntity.setSettleData(calendar.getTime());
					tcsbSettleAccountEntity.setSettleWay(null);
					tcsbSettleAccountEntity.setShopId(tcsbShopEntity.getId());
					tcsbSettleAccountEntity.setTotal(tcsbOrderEntityList.get(i).get("onlinePrice").toString());
					tcsbSettleAccountEntity.setWaitSettleData(calDes.getTime());
					tcsbSettleAccountService.save(tcsbSettleAccountEntity);
				}
			}
			
		}
		return new ModelAndView("com/tcsb/settleaccount/tcsb_SettleAccount_List");
	}

	
	
	
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */

	@RequestMapping(params = "datedatagrid")
	public void datedatagrid(StatisticsSettleVo tcsbSettleAccount,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws IllegalAccessException, InvocationTargetException {
		//TODO
		String startTime = request.getParameter("waitSettleDatas_begin");
		String endTime = request.getParameter("waitSettleDatas_end");
		System.out.println("============================");
		System.out.println(startTime);
		System.out.println(endTime);
		System.out.println("============================");
		String sql = "";
		String countsql ="";
		String footerwaitSettleDatas = "";
		if(StringUtil.isNotEmpty(startTime) && StringUtil.isNotEmpty(endTime)){
			sql = "SELECT DATE_FORMAT( wait_settle_data, '%Y-%m-%d' ) as settleData,sum(total) as alltotal,"
					+"(SELECT SUM(total) FROM tcsb_settle_account where wait_settle_data=settle_Data and IS_SETTLE = 'Y') AS TOTAL,"
					+"(SELECT SUM(total) FROM tcsb_settle_account where wait_settle_data=settle_Data and IS_SETTLE = 'N') AS isnotTOTAL"
					+" FROM tcsb_settle_account where wait_settle_data BETWEEN '"+startTime+"' AND '"+endTime+"'"
					+" GROUP BY DATE_FORMAT( wait_settle_data, '%Y-%m-%d' ) order by wait_settle_data desc";
			
			dataGrid.setTotal(10);
			/*countsql = "SELECT DATE_FORMAT( wait_settle_data, '%Y-%m-%d' ) as settleData FROM tcsb_settle_account where wait_settle_data BETWEEN '"+startTime+"' AND '"+endTime+"'"
					+" GROUP BY DATE_FORMAT( wait_settle_data, '%Y-%m-%d' )";
			dataGrid.setRows(30);*/
			footerwaitSettleDatas = startTime+"-"+endTime;
		}else{
			sql = "SELECT DATE_FORMAT( wait_settle_data, '%Y-%m-%d' ) as settleData,sum(total) as alltotal,"
					+"(SELECT SUM(total) FROM tcsb_settle_account where wait_settle_data=settle_Data and IS_SETTLE = 'Y') AS TOTAL,"
					+"(SELECT SUM(total) FROM tcsb_settle_account where wait_settle_data=settle_Data and IS_SETTLE = 'N') AS isnotTOTAL"
					+" FROM tcsb_settle_account"
					+" GROUP BY DATE_FORMAT( wait_settle_data, '%Y-%m-%d' ) order by wait_settle_data desc limit "+
					(dataGrid.getPage()-1)*10
					+","+dataGrid.getRows();
			countsql = "SELECT DATE_FORMAT( wait_settle_data, '%Y-%m-%d' ) as settleData FROM tcsb_settle_account GROUP BY DATE_FORMAT( wait_settle_data, '%Y-%m-%d' )";
			List<Object> countlist = tcsbSettleAccountService.findListbySql(countsql);
			dataGrid.setTotal(countlist.size());
		}
	

		
		List<Object> ovoList = tcsbSettleAccountService.findListbySql(sql);
		
		
		
		
		
		
		List<StatisticsSettleVo> statisticsSettleVoList = new ArrayList<>();
		StatisticsSettleVo statisticsSettleVo;
		
		for(Iterator iterator = ovoList.iterator();iterator.hasNext();){ 
			statisticsSettleVo = new StatisticsSettleVo();
			statisticsSettleVo.setId("1");
            Object[] objects = (Object[]) iterator.next(); 
            //System.out.println(objects[0].toString());
            statisticsSettleVo.setWaitSettleDatas(objects[0].toString());
            //System.out.println(objects[1].toString());
            if(StringUtil.isNotEmpty(objects[1])){
            	statisticsSettleVo.setAlltotal(objects[1].toString());
            	double bound = BigDecimalUtil.mul(Double.valueOf(objects[1].toString()), 0.03);
            	statisticsSettleVo.setProfit(BigDecimalUtil.numericRetentionDecimal(bound, 2));
            }else{
            	statisticsSettleVo.setAlltotal("0");
            	statisticsSettleVo.setProfit("0");
            	
            }
            
           // System.out.println("已结算:");
            if(StringUtil.isNotEmpty(objects[2])){
            	statisticsSettleVo.setIstotal(objects[2].toString());
            	//System.out.println(objects[2].toString());
            }else{
            	statisticsSettleVo.setIstotal("0");
            	//System.out.println(0);
            }
            //System.out.println("未结算:");
            if(StringUtil.isNotEmpty(objects[3])){
            	statisticsSettleVo.setIsnottotal(objects[3].toString());
            	
            	double bound = BigDecimalUtil.mul(Double.valueOf(objects[3].toString()), 0.03);
    			double needtatol = BigDecimalUtil.sub(Double.valueOf(statisticsSettleVo.getIsnottotal()), bound);
            	statisticsSettleVo.setNeedtotal(BigDecimalUtil.numericRetentionDecimal(needtatol, 2));
            	//System.out.println(objects[3].toString());
            }else{
            	statisticsSettleVo.setIsnottotal("0");
            	statisticsSettleVo.setNeedtotal("0");
            	//System.out.println(0);
            }
            
            
            
            
            statisticsSettleVoList.add(statisticsSettleVo);
          

	}
		
		
		
		
		double footeralltotal = 0.00;
		
		double footeristotal = 0.00;
		
		double footerisnottotal = 0.00;
		
		double footerneedtotal = 0.00;
		
		double footerprofit = 0.00;
		
		for (StatisticsSettleVo statisticsSettleVos : statisticsSettleVoList) {
			footeralltotal = BigDecimalUtil.add(footeralltotal, Double.valueOf(statisticsSettleVos.getAlltotal()));
			footeristotal = BigDecimalUtil.add(footeristotal, Double.valueOf(statisticsSettleVos.getIstotal()));
			footerisnottotal = BigDecimalUtil.add(footerisnottotal, Double.valueOf(statisticsSettleVos.getIsnottotal()));
			footerneedtotal = BigDecimalUtil.add(footerneedtotal, Double.valueOf(statisticsSettleVos.getNeedtotal()));
			footerprofit = BigDecimalUtil.add(footerprofit, Double.valueOf(statisticsSettleVos.getProfit()));
		}
		
		
		
		dataGrid.setFooter("id:<font color ='black'>统计</font>,"+
	    		 "waitSettleDatas:<font color ='red'><B>"+footerwaitSettleDatas+"</B></font>,"+
	    		 "alltotal:<font color ='red'><B>"+BigDecimalUtil.numericRetentionDecimal(footeralltotal, 2)+"</B></font>,"+
	    		 "istotal:<font color ='red'><B>"+BigDecimalUtil.numericRetentionDecimal(footeristotal, 2)+"</B></font>,"+
	    		 "isnottotal:<font color ='red'><B>"+BigDecimalUtil.numericRetentionDecimal(footerisnottotal, 2)+"</B></font>,"+
	    		 "needtotal:<font color ='red'><B>"+BigDecimalUtil.numericRetentionDecimal(footerneedtotal, 2)+"</B></font>,"+
	    		 "profit:<font color ='red'><B>"+BigDecimalUtil.numericRetentionDecimal(footerprofit, 2)+"</B></font>");
		dataGrid.setResults(statisticsSettleVoList);
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	
	
	/**
	 * 删除账单结算
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbSettleAccountEntity tcsbSettleAccount, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbSettleAccount = systemService.getEntity(TcsbSettleAccountEntity.class, tcsbSettleAccount.getId());
		message = "账单结算删除成功";
		try{
			tcsbSettleAccountService.delete(tcsbSettleAccount);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "账单结算删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新结算状态
	 * @param tcsbSettleAccount
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "doBetSettle")
	@ResponseBody
	public AjaxJson doBetSettle(TcsbSettleAccountEntity tcsbSettleAccount, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String  settleWay = tcsbSettleAccount.getSettleWay();
		tcsbSettleAccount = systemService.getEntity(TcsbSettleAccountEntity.class, tcsbSettleAccount.getId());
		message = "账单结算成功";
		try{
			tcsbSettleAccount.setIsSettle("Y");
			tcsbSettleAccount.setSettleWay(settleWay);
			tcsbSettleAccountService.saveOrUpdate(tcsbSettleAccount);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "账单结算失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除账单结算
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "账单结算删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbSettleAccountEntity tcsbSettleAccount = systemService.getEntity(TcsbSettleAccountEntity.class, 
				id
				);
				tcsbSettleAccountService.delete(tcsbSettleAccount);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "账单结算删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加账单结算
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbSettleAccountEntity tcsbSettleAccount, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "账单结算添加成功";
		try{
			tcsbSettleAccountService.save(tcsbSettleAccount);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "账单结算添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新账单结算
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbSettleAccountEntity tcsbSettleAccount, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "账单结算更新成功";
		TcsbSettleAccountEntity t = tcsbSettleAccountService.get(TcsbSettleAccountEntity.class, tcsbSettleAccount.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbSettleAccount, t);
			tcsbSettleAccountService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "账单结算更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 账单结算新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbSettleAccountEntity tcsbSettleAccount, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbSettleAccount.getId())) {
			tcsbSettleAccount = tcsbSettleAccountService.getEntity(TcsbSettleAccountEntity.class, tcsbSettleAccount.getId());
			req.setAttribute("tcsbSettleAccountPage", tcsbSettleAccount);
		}
		return new ModelAndView("com/tcsb/settleaccount/tcsbSettleAccount-add");
	}
	/**
	 * 账单结算编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbSettleAccountEntity tcsbSettleAccount, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbSettleAccount.getId())) {
			tcsbSettleAccount = tcsbSettleAccountService.getEntity(TcsbSettleAccountEntity.class, tcsbSettleAccount.getId());
			req.setAttribute("tcsbSettleAccountPage", tcsbSettleAccount);
		}
		return new ModelAndView("com/tcsb/settleaccount/tcsbSettleAccount-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbSettleAccountController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbSettleAccountEntity tcsbSettleAccount,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbSettleAccountEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbSettleAccount, request.getParameterMap());
		List<TcsbSettleAccountEntity> tcsbSettleAccounts = this.tcsbSettleAccountService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"账单结算");
		modelMap.put(NormalExcelConstants.CLASS,ExportSettleUtil.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams());
		List<ExportSettleUtil> exList = new ArrayList<>();
		ExportSettleUtil ex;
		
		int i = 1;
		for (TcsbSettleAccountEntity tcsbSettleAccountEntity : tcsbSettleAccounts) {
			ex = new ExportSettleUtil();
			ex.setId(i);//*序号
			ex.setPayerClientAccount("35101536001052521357");//*付款方客户账号
			ex.setPayerClientName("厦门国通宝网络科技有限公司");//*付款方账户名称
			
			TcsbShopBankcardEntity tcsbShopBankcard = tcsbSettleAccountService.findUniqueByProperty(TcsbShopBankcardEntity.class, "shopId", tcsbSettleAccountEntity.getShopId());
			if(StringUtil.isNotEmpty(tcsbShopBankcard)){
				ex.setBeneficiaryBankCode(tcsbShopBankcard.getBeneficiaryBankcode());//*收款方行别代码（01-本行 02-国内他行）
				ex.setBeneficiaryAccount(tcsbShopBankcard.getBeneficiaryAccount());//*收款方客户账号
				ex.setBeneficiaryName(tcsbShopBankcard.getBeneficiaryName());//*收款方账户名称
				ex.setBeneficiaryOpeningBankName(tcsbShopBankcard.getBeneficiaryOpeningBankName());//收款方开户行名称
				ex.setBeneficiaryLineNumber("");//收款方联行号
				ex.setClientSerialNumber("");//客户方流水号
			}else{
				ex.setBeneficiaryBankCode("");//*收款方行别代码（01-本行 02-国内他行）
				ex.setBeneficiaryAccount("");//*收款方客户账号
				ex.setBeneficiaryName("");//*收款方账户名称
				ex.setBeneficiaryOpeningBankName("");//收款方开户行名称
				ex.setBeneficiaryLineNumber("");//收款方联行号
				ex.setClientSerialNumber("");//客户方流水号
			}
			
			double bound = BigDecimalUtil.mul(Double.valueOf(tcsbSettleAccountEntity.getTotal()),0.03);
			double settleMoney = BigDecimalUtil.sub(Double.valueOf(tcsbSettleAccountEntity.getTotal()), bound);
			ex.setSettleMoney(BigDecimalUtil.numericRetentionDecimal(settleMoney,2));//*金额
			ex.setPurpose("");//*用途
			ex.setNote("");//备注
			exList.add(ex);
			i++;
		}
		modelMap.put(NormalExcelConstants.DATA_LIST,exList);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbSettleAccountEntity tcsbSettleAccount,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"账单结算");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbSettleAccountEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("账单结算列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
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
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<TcsbSettleAccountEntity> listTcsbSettleAccountEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbSettleAccountEntity.class,params);
				for (TcsbSettleAccountEntity tcsbSettleAccount : listTcsbSettleAccountEntitys) {
					tcsbSettleAccountService.save(tcsbSettleAccount);
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
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<TcsbSettleAccountEntity> list() {
		List<TcsbSettleAccountEntity> listTcsbSettleAccounts=tcsbSettleAccountService.getList(TcsbSettleAccountEntity.class);
		return listTcsbSettleAccounts;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbSettleAccountEntity task = tcsbSettleAccountService.get(TcsbSettleAccountEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbSettleAccountEntity tcsbSettleAccount, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbSettleAccountEntity>> failures = validator.validate(tcsbSettleAccount);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbSettleAccountService.save(tcsbSettleAccount);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbSettleAccount.getId();
		URI uri = uriBuilder.path("/rest/tcsbSettleAccountController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbSettleAccountEntity tcsbSettleAccount) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbSettleAccountEntity>> failures = validator.validate(tcsbSettleAccount);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbSettleAccountService.saveOrUpdate(tcsbSettleAccount);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		tcsbSettleAccountService.deleteEntityById(TcsbSettleAccountEntity.class, id);
	}
	
	/**
	 * 
	 * @param tcsbSettleAccount
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws ParseException
	 */
	@RequestMapping(params = "shopdatagrid")
	public void shopdatagrid(TcsbShopEntity tcsbShop,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws IllegalAccessException, InvocationTargetException, ParseException {
		//获取店铺信息
		CriteriaQuery cq = new CriteriaQuery(TcsbShopEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				tcsbShop, request.getParameterMap());
		try {
			if (tcsbShop.getName()!=null) {
				cq.like("name", tcsbShop.getName());
			}
			// 自定义追加查询条件
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbShopService.getDataGridReturn(cq, true);
		
		List<TcsbShopEntity> tcsbShopEntityList = dataGrid.getResults();
		List<ShopIncomeVo> shopIncomeVoList = new ArrayList<>();
		ShopIncomeVo shopIncomeVo;
		for (TcsbShopEntity tcsbShopEntity : tcsbShopEntityList) {
			shopIncomeVo = new ShopIncomeVo();
			shopIncomeVo.setId(tcsbShopEntity.getId());
			shopIncomeVo.setShopName(tcsbShopEntity.getName());
			//根据店铺Id获取店铺的消费数据
			String hql = "from TcsbOrderEntity where shopId='"+tcsbShopEntity.getId()+"' and payStatus='1'";
			List<TcsbOrderEntity> tcsbOrderEntityList = tcsbShopService.findByQueryString(hql);
			if (!tcsbOrderEntityList.isEmpty()) {
				Double boxFeePrice = 0.0;
 				Double distributionPrice = 0.0;
				Double shopDisPrice = 0.0;
				Double userDisPrice = 0.0;
 				Double totalPrice = 0.0;
				Double toPayPrice = 0.0;
				for (TcsbOrderEntity tcsbOrderEntity : tcsbOrderEntityList) {
					tcsbOrderEntity = tcsbOrderService.getOrderDetailByOrderId(tcsbOrderEntity.getId());
					boxFeePrice = BigDecimalUtil.add(boxFeePrice, tcsbOrderEntity.getBoxFeePrice());
					distributionPrice = BigDecimalUtil.add(distributionPrice, tcsbOrderEntity.getDistributionPrice());
					shopDisPrice = BigDecimalUtil.add(shopDisPrice, tcsbOrderEntity.getShopDisPrice());
					userDisPrice = BigDecimalUtil.add(userDisPrice, tcsbOrderEntity.getUserDisPrice());
					totalPrice = BigDecimalUtil.add(totalPrice, tcsbOrderEntity.getTotalPrice());
					toPayPrice = BigDecimalUtil.add(toPayPrice, tcsbOrderEntity.getToPayPrice());
				}
				shopIncomeVo.setBoxFeePrice(boxFeePrice);
				shopIncomeVo.setDistributionPrice(distributionPrice);
				shopIncomeVo.setShopDisPrice(shopDisPrice);
				shopIncomeVo.setToPayPrice(toPayPrice);
				shopIncomeVo.setTotalPrice(totalPrice);
				shopIncomeVo.setUserDisPrice(userDisPrice);
				
			}else {
				shopIncomeVo.setBoxFeePrice(0.0);
				shopIncomeVo.setDistributionPrice(0.0);
				shopIncomeVo.setShopDisPrice(0.0);
				shopIncomeVo.setToPayPrice(0.0);
				shopIncomeVo.setTotalPrice(0.0);
				shopIncomeVo.setUserDisPrice(0.0);
			}
			shopIncomeVoList.add(shopIncomeVo);
		}


		dataGrid.setResults(shopIncomeVoList);
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	
	
}
