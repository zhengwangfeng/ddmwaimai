package com.tcsb.food.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.ListSorter;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.food.service.TcsbFoodServiceI;
import com.tcsb.food.vo.FoodTypeVO;
import com.tcsb.food.vo.FoodVO;
import com.tcsb.foodstandard.VO.FoodStandardPageVo;
import com.tcsb.foodstandard.VO.FoodStandardVo;
import com.tcsb.foodstandard.VO.FoodTasteAndStandardVo;
import com.tcsb.foodstandard.entity.TcsbFoodStandardEntity;
import com.tcsb.foodstandardrelationship.entity.TcsbFoodStandardRelationshipEntity;
import com.tcsb.foodstock.entity.TcsbFoodStockEntity;
import com.tcsb.foodtype.entity.TcsbFoodTypeEntity;
import com.tcsb.foodtype.service.TcsbFoodTypeServiceI;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.tcsbfoodfunction.entity.TcsbFoodFunctionEntity;
import com.tcsb.tcsbfoodtastefunction.entity.TcsbFoodTasteFunctionEntity;
import com.tcsb.tcsbfoodtastefunction.vo.FoodTastePageVo;
import com.tcsb.tcsbfoodtastefunction.vo.FoodTasteVo;
import com.tcsb.tcsbfoodunit.entity.TcsbFoodUnitEntity;
import com.tcsb.tcsbfoodunit.service.TcsbFoodUnitServiceI;

/**
 * @Title: Controller
 * @Description: 美食
 * @author onlineGenerator
 * @date 2017-02-28 16:32:34
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/tcsbFoodController")
public class TcsbFoodController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(TcsbFoodController.class);

	@Autowired
	private TcsbFoodServiceI tcsbFoodService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbFoodTypeServiceI tcsbFoodTypeService;
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	@Autowired
	private TcsbFoodUnitServiceI tcsbFoodUnitService;
	

	/**
	 * 美食列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		TSUser user = getCurrentUser();
		// 查询商家店铺
		List<TcsbFoodTypeEntity> tcsbFoodTypeEntities = null;
		if (!checkAdmin()) {
			tcsbFoodTypeEntities = tcsbFoodTypeService.findByProperty(
					TcsbFoodTypeEntity.class, "shopId", user.getShopId());
		} else {
			tcsbFoodTypeEntities = tcsbFoodTypeService
					.getList(TcsbFoodTypeEntity.class);
		}
		String foodTypeReplace = "";
		for (TcsbFoodTypeEntity tcsbFoodTypeEntitie : tcsbFoodTypeEntities) {
			if (foodTypeReplace.length() > 0) {
				foodTypeReplace += ",";
			}
			foodTypeReplace += tcsbFoodTypeEntitie.getName() + "_"
					+ tcsbFoodTypeEntitie.getId();
		}
		request.setAttribute("foodTypeReplace", foodTypeReplace);
		return new ModelAndView("com/tcsb/food/tcsbFoodList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TcsbFoodEntity tcsbFood, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbFoodEntity.class, dataGrid);
		TSUser user = getCurrentUser();
		if (!checkAdmin()) {
			// 查询商家店铺
			cq.eq("shopId", user.getShopId());
			cq.addOrder("orders",SortDirection.desc);
		}
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				tcsbFood, request.getParameterMap());
		try {
			// 自定义追加查询条件
			cq.eq("isdelete", 0);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbFoodService.getDataGridReturn(cq, true);
		// 图片展示路径
		List<TcsbFoodEntity> resultList = dataGrid.getResults();
		
		List<TcsbFoodEntity> tcsbFoodEntitydTO = new ArrayList<>();
		TcsbFoodEntity tcsbFoodEntity;
		
		if (StringUtil.isNotEmpty(resultList) && resultList.size() > 0) {
			for (TcsbFoodEntity one : resultList) {
				tcsbFoodEntity = new TcsbFoodEntity();
				try {
					BeanUtils.copyProperties(tcsbFoodEntity, one);
					String img = one.getImg();
					if (StringUtil.isNotEmpty(img)) {
						String files = getCkPath();
						tcsbFoodEntity.setImg(files + img);
					}
					
					if(StringUtil.isNotEmpty(one.getFoodUnitId())){
						TcsbFoodUnitEntity tcsbFoodUnitEntity = tcsbFoodService.get(TcsbFoodUnitEntity.class, one.getFoodUnitId());
						if(StringUtil.isNotEmpty(tcsbFoodUnitEntity)){
							tcsbFoodEntity.setUnitName(tcsbFoodUnitEntity.getName());
						}
					}
					
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tcsbFoodEntitydTO.add(tcsbFoodEntity);
			}
			
		}
		dataGrid.setResults(tcsbFoodEntitydTO);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**

	 * 食物规格信息列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "standardList")
	public ModelAndView standardList(HttpServletRequest request) {

		request.setAttribute("foodId", request.getParameter("foodId"));
		return new ModelAndView("com/tcsb/food/foodStandardList");
	}

	/**
	 * 删除美食
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbFoodEntity tcsbFood, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbFood = systemService.getEntity(TcsbFoodEntity.class,
				tcsbFood.getId());
		message = "美食删除成功";
		try {
			tcsbFood.setIsdelete(1);
			//同时保存快照给PC端同步用
			tcsbFoodService.saveOrUpdate(tcsbFood);
			systemService.addLog(message, Globals.Log_Type_DEL,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "美食删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	@RequestMapping(params = "getFood")
	@ResponseBody
	public AjaxJson getFood(String foodId,String orderId, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "美食获取成功";
		
		if(StringUtil.isEmpty(orderId)){
			TcsbFoodEntity tcsbFood = new TcsbFoodEntity();
			try {
				tcsbFood = systemService.getEntity(TcsbFoodEntity.class,foodId);
				if(StringUtil.isNotEmpty(tcsbFood.getFoodUnitId())){
					TcsbFoodUnitEntity foodUnitEntity = tcsbShopService.get(TcsbFoodUnitEntity.class, tcsbFood.getFoodUnitId());
					if(StringUtil.isNotEmpty(foodUnitEntity)){
						tcsbFood.setUnitName(foodUnitEntity.getName());
					}else{
						tcsbFood.setUnitName("份_");
					}
				}else{
					//tcsbFood.setIsFloat("0");
					tcsbFood.setUnitName("份_");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				message = "美食获取失败";
				throw new BusinessException(e.getMessage());
			}
			j.setMsg(message);
			j.setSuccess(true);
			j.setObj(tcsbFood);
		}else{
			//根据orderid跟fooid查找该食品是否已存在订单
			String hql = "from TcsbOrderItemEntity where orderId='"+orderId+"' and foodId='"+foodId+"'";
			/*TcsbOrderItemEntity tcsbOrderItemEntity = systemService.singleResult(hql);
			if(StringUtil.isNotEmpty(tcsbOrderItemEntity)){
				message = "美食获取失败";
				j.setMsg(message);
				j.setSuccess(false);
				//j.setObj(tcsbFood);
			}else{
				TcsbFoodEntity tcsbFood = new TcsbFoodEntity();
				try {
					tcsbFood = systemService.getEntity(TcsbFoodEntity.class,foodId);
				} catch (Exception e) {
					e.printStackTrace();
					message = "美食获取失败";
					throw new BusinessException(e.getMessage());
				}
				j.setMsg(message);
				j.setSuccess(true);
				j.setObj(tcsbFood);
			}
			*/
		}
		return j;
	}
	
	/**
	 * 批量删除美食
	 * 
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "美食删除成功";
		try {
			for (String id : ids.split(",")) {
				TcsbFoodEntity tcsbFood = systemService.getEntity(
						TcsbFoodEntity.class, id);
				tcsbFoodService.delete(tcsbFood);
				systemService.addLog(message, Globals.Log_Type_DEL,
						Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "美食删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加美食
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbFoodEntity tcsbFood, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "美食添加成功";
		try {
			// 获取当前用户
			TSUser user = getCurrentUser();
			// 查询商家店铺
			TcsbShopEntity tcsbShopEntity = tcsbShopService
					.findUniqueByProperty(TcsbShopEntity.class, "userId",
							user.getId());
			tcsbFood.setShopId(tcsbShopEntity.getId());
			if (!StringUtil.isEmpty(tcsbFood.getImg())) {
				tcsbFood.setImg(filterCkPath(tcsbFood.getImg()));
			}
			tcsbFood.setIsdelete(0);
			//同时保存快照给PC端同步用
			tcsbFoodService.saveAndInitStock(tcsbFood);
			systemService.addLog(message, Globals.Log_Type_INSERT,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "美食添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新美食
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbFoodEntity tcsbFood, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "美食更新成功";
		TcsbFoodEntity t = tcsbFoodService.get(TcsbFoodEntity.class,
				tcsbFood.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbFood, t);
			if (!StringUtil.isEmpty(t.getImg())) {
				t.setImg(filterCkPath(tcsbFood.getImg()));
			}
			//同时保存快照给PC端同步用
			tcsbFoodService.saveOrUpdateAndInitStock(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "美食更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 更新美食上下架状态
	 *
	 * @return
	 */
	@RequestMapping(params = "stateUpdate")
	@ResponseBody
	public String stateUpdate(TcsbFoodEntity tcsbFood, HttpServletRequest request) {
		
		TcsbFoodEntity t = tcsbFoodService.get(TcsbFoodEntity.class,
				tcsbFood.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbFood, t);
			tcsbFoodService.saveOrUpdate(t);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	
		return "<script>window.location.href='tcsbFoodController.do?list'</script>";
	}
	

	/**
	 * 美食新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbFoodEntity tcsbFood, HttpServletRequest req) {
		TSUser tsUser = getCurrentUser();
		// 得到商家菜品
		/*
		 * List tcsbFoodTypeEntities = tcsbFoodTypeService.findByQueryString(
		 * "select t.id,t.name from TcsbFoodTypeEntity t,TcsbShopEntity s where t.shopId=s.id and s.userId='"
		 * +tsUser.getId()+"'"); List<TcsbFoodTypeEntity> tcsbFoodTypeEntities2
		 * = new ArrayList<TcsbFoodTypeEntity>(); Map<String, Object> map = new
		 * HashMap<String, Object>();; if (!tcsbFoodTypeEntities.isEmpty()) {
		 * for (Object object:tcsbFoodTypeEntities) { Object[] obj = (Object[])
		 * object; map.put("id", obj[0]); map.put("name", obj[1]);
		 * TcsbFoodTypeEntity tcsbFoodTypeEntity = new TcsbFoodTypeEntity(); try
		 * { MyBeanUtils.copyMap2Bean(tcsbFoodTypeEntity, map);
		 * tcsbFoodTypeEntities2.add(tcsbFoodTypeEntity); } catch
		 * (IllegalAccessException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (InvocationTargetException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } } }
		 */
		List<TcsbFoodTypeEntity> tcsbFoodTypeEntities = new ArrayList<TcsbFoodTypeEntity>();
		if (checkAdmin()) {
			tcsbFoodTypeEntities = tcsbFoodTypeService
					.getList(TcsbFoodTypeEntity.class);
		} else {
			tcsbFoodTypeEntities = tcsbFoodTypeService.findByProperty(
					TcsbFoodTypeEntity.class, "shopId", tsUser.getShopId());
		}
		req.setAttribute("tcsbFoodTypeEntities", tcsbFoodTypeEntities);
		if (StringUtil.isNotEmpty(tcsbFood.getId())) {
			tcsbFood = tcsbFoodService.getEntity(TcsbFoodEntity.class,
					tcsbFood.getId());
			req.setAttribute("tcsbFoodPage", tcsbFood);
		}
		
		List<TcsbFoodUnitEntity> tcsbFoodUnitList = tcsbFoodService.findByQueryString("from TcsbFoodUnitEntity");
		req.setAttribute("tcsbFoodUnitList", tcsbFoodUnitList);
		return new ModelAndView("com/tcsb/food/tcsbFood-add");
	}
	
	/**
	 * 初始化库存
	 * @throws Exception
	 */
	@Deprecated
	@RequestMapping(params = "initFoodStock")
	public void initFoodStock() throws Exception{
		List<TcsbFoodEntity> tcsbFoodEntities = systemService.getList(TcsbFoodEntity.class);
		for (TcsbFoodEntity tcsbFoodEntity : tcsbFoodEntities) {
			TcsbFoodStockEntity tcsbFoodStockEntity = new TcsbFoodStockEntity();
			tcsbFoodStockEntity.setFoodId(tcsbFoodEntity.getId());
			tcsbFoodStockEntity.setFoodTypeId(tcsbFoodEntity.getFoodTypeId());
			tcsbFoodStockEntity.setShopId(tcsbFoodEntity.getShopId());
			tcsbFoodStockEntity.setStockType("0");
			systemService.save(tcsbFoodStockEntity);
		}
	}
	
	/**
	 * 美食编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbFoodEntity tcsbFood, HttpServletRequest req) {
		TSUser tsUser = getCurrentUser();
		// 得到商家菜品
		/*
		 * List tcsbFoodTypeEntities = tcsbFoodTypeService.findByQueryString(
		 * "select t.id,t.name from TcsbFoodTypeEntity t,TcsbShopEntity s where t.shopId=s.id and s.userId='"
		 * +tsUser.getId()+"'"); List<TcsbFoodTypeEntity> tcsbFoodTypeEntities2
		 * = new ArrayList<TcsbFoodTypeEntity>(); Map<String, Object> map = new
		 * HashMap<String, Object>();; if (!tcsbFoodTypeEntities.isEmpty()) {
		 * for (Object object:tcsbFoodTypeEntities) { Object[] obj = (Object[])
		 * object; map.put("id", obj[0]); map.put("name", obj[1]);
		 * TcsbFoodTypeEntity tcsbFoodTypeEntity = new TcsbFoodTypeEntity(); try
		 * { MyBeanUtils.copyMap2Bean(tcsbFoodTypeEntity, map);
		 * tcsbFoodTypeEntities2.add(tcsbFoodTypeEntity); } catch
		 * (IllegalAccessException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (InvocationTargetException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } } }
		 */
		List<TcsbFoodTypeEntity> tcsbFoodTypeEntities = new ArrayList<TcsbFoodTypeEntity>();
		if (checkAdmin()) {
			tcsbFoodTypeEntities = tcsbFoodTypeService
					.getList(TcsbFoodTypeEntity.class);
		} else {
			tcsbFoodTypeEntities = tcsbFoodTypeService.findByProperty(
					TcsbFoodTypeEntity.class, "shopId", tsUser.getShopId());
		}
		req.setAttribute("tcsbFoodTypeEntities", tcsbFoodTypeEntities);
		if (StringUtil.isNotEmpty(tcsbFood.getId())) {
			tcsbFood = tcsbFoodService.getEntity(TcsbFoodEntity.class,
					tcsbFood.getId());
			//获取食物单位
			if(StringUtil.isNotEmpty(tcsbFood.getFoodUnitId())){
				TcsbFoodUnitEntity tcsbFoodUnitEntity = tcsbFoodService.get(TcsbFoodUnitEntity.class, tcsbFood.getFoodUnitId());
				req.setAttribute("tcsbFoodUnitEntity", tcsbFoodUnitEntity);
			}
			
			if (!StringUtil.isEmpty(tcsbFood.getImg())) {
				TcsbFoodEntity tcsbFoodEntity = new TcsbFoodEntity();
				try {
					MyBeanUtils.copyBeanNotNull2Bean(tcsbFood, tcsbFoodEntity);
					tcsbFoodEntity.setImg(getCkPath() + tcsbFood.getImg());
					req.setAttribute("tcsbFoodPage", tcsbFoodEntity);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				req.setAttribute("tcsbFoodPage", tcsbFood);
			}
		}
		
		List<TcsbFoodUnitEntity> tcsbFoodUnitList = tcsbFoodService.findByQueryString("from TcsbFoodUnitEntity");
		req.setAttribute("tcsbFoodUnitList", tcsbFoodUnitList);
		
		return new ModelAndView("com/tcsb/food/tcsbFood-update");
	}

	
	/**
	 * 根据菜品获取食物
	 *
	 * @return
	 */
	@RequestMapping(params = "getFoodByFoodTypeId")
	@ResponseBody
	public AjaxJson getFoodByFoodTypeId(String foodTypeId,HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "获取食品成功";
		List<TcsbFoodEntity> tcsbFoodEntities = new ArrayList<>();
		try{
			tcsbFoodEntities = tcsbFoodService.findHql("from TcsbFoodEntity where foodTypeId = ?", foodTypeId);
		}catch(Exception e){
			e.printStackTrace();
			message = "获取食品失败";
			throw new BusinessException(e.getMessage());
		}
		j.setObj(tcsbFoodEntities);
		j.setSuccess(true);
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "tcsbFoodController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbFoodEntity tcsbFood,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbFoodEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				tcsbFood, request.getParameterMap());
		List<TcsbFoodEntity> tcsbFoods = this.tcsbFoodService
				.getListByCriteriaQuery(cq, false);
		modelMap.put(NormalExcelConstants.FILE_NAME, "美食");
		modelMap.put(NormalExcelConstants.CLASS, TcsbFoodEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("美食列表",
				"导出人:" + ResourceUtil.getSessionUserName().getRealName(),
				"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, tcsbFoods);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbFoodEntity tcsbFood,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid, ModelMap modelMap) {
		modelMap.put(NormalExcelConstants.FILE_NAME, "美食");
		modelMap.put(NormalExcelConstants.CLASS, TcsbFoodEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("美食列表",
				"导出人:" + ResourceUtil.getSessionUserName().getRealName(),
				"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, new ArrayList());
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request,
			HttpServletResponse response) {
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
				List<TcsbFoodEntity> listTcsbFoodEntitys = ExcelImportUtil
						.importExcel(file.getInputStream(),
								TcsbFoodEntity.class, params);
				for (TcsbFoodEntity tcsbFood : listTcsbFoodEntitys) {
					tcsbFoodService.save(tcsbFood);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			} finally {
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
	public List<TcsbFoodEntity> list() {
		List<TcsbFoodEntity> listTcsbFoods = tcsbFoodService
				.getList(TcsbFoodEntity.class);
		return listTcsbFoods;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbFoodEntity task = tcsbFoodService.get(TcsbFoodEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbFoodEntity tcsbFood,
			UriComponentsBuilder uriBuilder) {
		// 调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbFoodEntity>> failures = validator
				.validate(tcsbFood);
		if (!failures.isEmpty()) {
			return new ResponseEntity(
					BeanValidators.extractPropertyAndMessage(failures),
					HttpStatus.BAD_REQUEST);
		}

		// 保存
		try {
			tcsbFoodService.save(tcsbFood);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		// 按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbFood.getId();
		URI uri = uriBuilder.path("/rest/tcsbFoodController/" + id).build()
				.toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbFoodEntity tcsbFood) {
		// 调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbFoodEntity>> failures = validator
				.validate(tcsbFood);
		if (!failures.isEmpty()) {
			return new ResponseEntity(
					BeanValidators.extractPropertyAndMessage(failures),
					HttpStatus.BAD_REQUEST);
		}

		// 保存
		try {
			tcsbFoodService.saveOrUpdate(tcsbFood);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		// 按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		tcsbFoodService.deleteEntityById(TcsbFoodEntity.class, id);
	}

	@RequestMapping(value = "/listByShopId",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AjaxJsonApi listByShopId(@RequestParam String shopId,HttpServletRequest request,HttpServletResponse response){
		//下面为分类与食物集合体
		//List<Map<String, Object>> listTcsbFoodEntitys = tcsbFoodService.findForJdbc("select id,is_dis as isDis,food_type_id as foodTypeId,orders,name,price,img,food_unit_id from tcsb_food where shop_id = ? and status=1 and is_delete=0 order by food_type_id",shopId);
		List<Map<String, Object>> listTcsbFoodEntitys = tcsbFoodService.findForJdbc("select s.stock,s.stock_type as stockType,f.id,f.is_dis as isDis,f.food_type_id as foodTypeId,f.orders,f.name,f.price,f.img,f.food_unit_id from tcsb_food f left join tcsb_food_stock s on f.id = s.food_id   where f.shop_id = ? and f.status=1 and f.is_delete=0 order by f.food_type_id",shopId);
		List<FoodTypeVO> foodTypeVOs = new ArrayList<>();
		List<FoodVO> foodVOs = new ArrayList<>();
		
		for (int i = 0; i < listTcsbFoodEntitys.size(); i++) {
			//是否有重复的
			boolean flag = false;
			Map<String, Object> map =listTcsbFoodEntitys.get(i);
			String foodTypeId = (String) map.get("foodTypeId");
			int  index = 0;
			if (!foodTypeVOs.isEmpty()) {
				for (int j = 0; j < foodTypeVOs.size(); j++) {
					FoodTypeVO foodTypeVO = foodTypeVOs.get(j);
					if (foodTypeVO.getId().equals(foodTypeId)) {
						//查找到相同菜品
						flag = true;
						index = j;
						break;
					}
				}
				//重复的就重新添加
				if (flag) {
					FoodTypeVO foodTypeVO =foodTypeVOs.get(index);
					FoodVO foodVO = new FoodVO();
					foodVO.setFoodTypeId(foodTypeId);
					foodVO.setId((String)map.get("id"));
					foodVO.setImg(getCkPath()+(String)map.get("img"));
					foodVO.setName((String)map.get("name"));
					foodVO.setPrice((Double)map.get("price"));
					foodVO.setOrders((Integer)map.get("orders"));
					//库存设置
					foodVO.setStock((Integer)map.get("stock"));
					if ((Integer)map.get("stock")<=10) {
						foodVO.setCanOrder(false);
					}else {
						foodVO.setCanOrder(true);
					}
					if ("N".equals((String)map.get("isDis"))){
						foodVO.setIsNotDis(true);
					}else {
						foodVO.setIsNotDis(false);
					}
					foodVO.setNum(0);
					String unitId = (String)map.get("food_unit_id");
					//设置单位名称
					if(StringUtil.isNotEmpty(unitId)){
						TcsbFoodUnitEntity foodUnit = tcsbFoodUnitService.get(TcsbFoodUnitEntity.class, unitId);
						if(StringUtil.isNotEmpty(foodUnit)){
							foodVO.setUnitName(foodUnit.getName());
						}
					}
					String sql = "select id from tcsb_food_function where foodid='"+(String)map.get("id")+"'";
					List<Object> ovoList1 = tcsbShopService.findListbySql(sql);
					if(ovoList1.size()>0){
						foodVO.setFoodTaste(true);
					}else{
						foodVO.setFoodTaste(false);
					}
					List<TcsbFoodStandardRelationshipEntity> funsize = tcsbFoodService.findByProperty(TcsbFoodStandardRelationshipEntity.class, "foodId", (String)map.get("id"));
		            if (funsize.size() > 0) {
		              foodVO.setFoodstandard(true);
		              foodVO.setFoodTaste(true);
		            } else {
		              foodVO.setFoodstandard(false);
		            }
					
					foodTypeVO.getList().add(foodVO);
				}else {
					FoodTypeVO foodTypeVO = new FoodTypeVO();
					map =listTcsbFoodEntitys.get(i);
					//根据foodTypeId去获取菜品名称
					TcsbFoodTypeEntity tcsbFoodTypeEntity = tcsbFoodTypeService.findUniqueByProperty(TcsbFoodTypeEntity.class, "id", foodTypeId);
					foodTypeVO.setId(foodTypeId);
					foodTypeVO.setName(tcsbFoodTypeEntity.getName());
					foodTypeVO.setOrders(tcsbFoodTypeEntity.getOrders());
					FoodVO foodVO = new FoodVO();
					foodVO.setFoodTypeId(foodTypeId);
					foodVO.setId((String)map.get("id"));
					foodVO.setImg(getCkPath()+(String)map.get("img"));
					foodVO.setName((String)map.get("name"));
					foodVO.setPrice((Double)map.get("price"));
					//库存设置
					foodVO.setStock((Integer)map.get("stock"));
					if ((Integer)map.get("stock")<=10) {
						foodVO.setCanOrder(false);
					}else {
						foodVO.setCanOrder(true);
					}
					if ("N".equals((String)map.get("isDis"))){
						foodVO.setIsNotDis(true);
					}else {
						foodVO.setIsNotDis(false);
					}
					foodVO.setNum(0);
					foodVO.setOrders((Integer)map.get("orders"));
					String unitId = (String)map.get("food_unit_id");
					if(StringUtil.isNotEmpty(unitId)){
						TcsbFoodUnitEntity foodUnit = tcsbFoodUnitService.get(TcsbFoodUnitEntity.class, unitId);
						if(StringUtil.isNotEmpty(foodUnit)){
							foodVO.setUnitName(foodUnit.getName());
						}
					}
					
					String sql = "select id from tcsb_food_function where foodid='"+(String)map.get("id")+"'";
					List<Object> ovoList1 = tcsbShopService.findListbySql(sql);
					if(ovoList1.size()>0){
						foodVO.setFoodTaste(true);
					}else{
						foodVO.setFoodTaste(false);
					}
					List<TcsbFoodStandardRelationshipEntity> funsize = tcsbFoodService.findByProperty(TcsbFoodStandardRelationshipEntity.class, "foodId", (String)map.get("id"));
		            if (funsize.size() > 0) {
		              foodVO.setFoodstandard(true);
		              foodVO.setFoodTaste(true);
		            } else {
		              foodVO.setFoodstandard(false);
		            }
					List<FoodVO> foodVOsnew = new ArrayList<>();
					foodVOsnew.add(foodVO);
					foodTypeVO.setList(foodVOsnew);
					foodTypeVOs.add(foodTypeVO);
				}
				
			}else {
				FoodTypeVO foodTypeVO = new FoodTypeVO();
				map =listTcsbFoodEntitys.get(i);
				//根据foodTypeId去获取菜品名称
				TcsbFoodTypeEntity tcsbFoodTypeEntity = tcsbFoodTypeService.findUniqueByProperty(TcsbFoodTypeEntity.class, "id", foodTypeId);
				foodTypeVO.setId(foodTypeId);
				foodTypeVO.setName(tcsbFoodTypeEntity.getName());
				foodTypeVO.setOrders(tcsbFoodTypeEntity.getOrders());
				FoodVO foodVO = new FoodVO();
				foodVO.setFoodTypeId(foodTypeId);
				foodVO.setId((String)map.get("id"));
				foodVO.setImg(getCkPath()+(String)map.get("img"));
				foodVO.setName((String)map.get("name"));
				foodVO.setPrice((Double)map.get("price"));
				//库存设置
				foodVO.setStock((Integer)map.get("stock"));
				if ((Integer)map.get("stock")<=10) {
					foodVO.setCanOrder(false);
				}else {
					foodVO.setCanOrder(true);
				}
				if ("N".equals((String)map.get("isDis"))){
					foodVO.setIsNotDis(true);
				}else {
					foodVO.setIsNotDis(false);
				}
				foodVO.setNum(0);
				foodVO.setOrders((Integer)map.get("orders"));
				
				String unitId = (String)map.get("food_unit_id");
				if(StringUtil.isNotEmpty(unitId)){
					TcsbFoodUnitEntity foodUnit = tcsbFoodUnitService.get(TcsbFoodUnitEntity.class, unitId);
					if(StringUtil.isNotEmpty(foodUnit)){
						foodVO.setUnitName(foodUnit.getName());
					}
				}
				String sql = "select id from tcsb_food_function where foodid='"+(String)map.get("id")+"'";
				List<Object> ovoList1 = tcsbFoodService.findListbySql(sql);
				if(ovoList1.size()>0){
					foodVO.setFoodTaste(true);
				}else{
					foodVO.setFoodTaste(false);
				}
				List<TcsbFoodStandardRelationshipEntity> funsize = tcsbFoodService.findByProperty(TcsbFoodStandardRelationshipEntity.class, "foodId", (String)map.get("id"));
	            if (funsize.size() > 0) {
	              foodVO.setFoodstandard(true);
	              foodVO.setFoodTaste(true);
	            } else {
	              foodVO.setFoodstandard(false);
	            }
				foodVOs.add(foodVO);
				foodTypeVO.setList(foodVOs);
				foodTypeVOs.add(foodTypeVO);
			}
		}
		//排序菜品
			ListSorter.sortDesc(foodTypeVOs, "orders");
		//排序食物
		for (int i = 0; i < foodTypeVOs.size(); i++) {
			List<FoodVO> tem = foodTypeVOs.get(i).getList();
			ListSorter.sortDesc(tem, "orders");
		}
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		ajaxJson.setMsg("查询成功");
		ajaxJson.setSuccess(true);
		ajaxJson.setObj(foodTypeVOs);
		return ajaxJson;
	}
	
	
	
	/**
	 * 获取食品规格
	 * @param foodId
	 * @param request
	 * @param response
	 * @return
	 *//*
	@RequestMapping(value = "/getfoodTasteFun",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject getfoodTasteFun(@RequestParam String foodId,HttpServletRequest request,HttpServletResponse response){
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		Map<Integer, List<TcsbFoodTasteFunctionEntity>> functionMap = new HashMap<Integer, List<TcsbFoodTasteFunctionEntity>>();
		Map<String, TcsbFoodTasteFunctionEntity> loginActionlist = new HashMap<String, TcsbFoodTasteFunctionEntity>();
		String hql="select distinct f from TcsbFoodFunctionEntity ru,TcsbFoodTasteFunctionEntity f where ru.tcsbFoodTasteFunctionEntity.id=f.id and ru.tcsbFoodEntity.id ='"+foodId+"'";
		 List<TcsbFoodTasteFunctionEntity> list1 = systemService.findByQueryString(hql);
		
		for (TcsbFoodTasteFunctionEntity tcsbFoodTasteFunctionEntity : list1) {
			loginActionlist.put(tcsbFoodTasteFunctionEntity.getId(),tcsbFoodTasteFunctionEntity);
		}
		
		
		if (loginActionlist.size() > 0) {
			Collection<TcsbFoodTasteFunctionEntity> allFunctions = loginActionlist.values();
			for (TcsbFoodTasteFunctionEntity function : allFunctions) {
	           
				if (!functionMap.containsKey(function.getFunctionlevel() + 0)) {
					functionMap.put(function.getFunctionlevel() + 0,
							new ArrayList<TcsbFoodTasteFunctionEntity>());
				}
				functionMap.get(function.getFunctionlevel() + 0).add(function);
			}
			// 菜单栏排序
			Collection<List<TcsbFoodTasteFunctionEntity>> c = functionMap.values();
			for (List<TcsbFoodTasteFunctionEntity> list : c) {
				Collections.sort(list, new NumberComparator3());
			}
		}
		List<TcsbFoodTasteFunctionEntity> tcsbFoodTasteFunctionEntity = functionMap.get(0);
		List<FoodTastePageVo> foodTastePageVoList = new ArrayList<>();
		FoodTastePageVo foodTastePageVo;
		for (TcsbFoodTasteFunctionEntity tcsbFoodTasteFunctionEntity2 : tcsbFoodTasteFunctionEntity) {
			List<FoodTasteVo> foodTasteVolist = new ArrayList<>();
			FoodTasteVo foodTasteVo;
			foodTastePageVo = new FoodTastePageVo();
			for (TcsbFoodTasteFunctionEntity tcsbFoodTasteFunctionEntity3 : tcsbFoodTasteFunctionEntity2.getTcsbFoodTasteFunctionEntitys()) {
				//主规格的情况
				if (tcsbFoodTasteFunctionEntity3.getFunctionlevel()==0) {
					foodTastePageVo.setFirstFun(tcsbFoodTasteFunctionEntity2.getStatename());
				}else {
					foodTastePageVo.setFirstFun(tcsbFoodTasteFunctionEntity2.getStatename());
					foodTasteVo = new FoodTasteVo();
					foodTasteVo.setId(tcsbFoodTasteFunctionEntity3.getId());
					foodTasteVo.setTasteName(tcsbFoodTasteFunctionEntity3.getStatename());
					foodTasteVolist.add(foodTasteVo);
				}
			}
			//foodTastePageVo.setFirstFun(tcsbFoodTasteFunctionEntity2.getStatename());
			
			//todo
			foodTastePageVo.setFoodTasteVo(foodTasteVolist);
			foodTastePageVoList.add(foodTastePageVo);
		}
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		ajaxJson.setMsg("查询成功");
		ajaxJson.setSuccess(true);
		ajaxJson.setObj(foodTastePageVoList);
		return new JSONPObject(callbackFunName, ajaxJson);
	}
	

	*//**
	 * 获取食品规格
	 * @param foodId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getfoodTasteFun",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AjaxJsonApi getfoodTasteFun(@RequestParam String foodId,HttpServletRequest request,HttpServletResponse response){
		List<TcsbFoodFunctionEntity> tcsbFoodFunctionEntities = tcsbFoodService.findHql("from TcsbFoodFunctionEntity t where t.tcsbFoodEntity.id = ?", foodId);
		//存主规格信息
		List<TcsbFoodFunctionEntity> tcsbFoodFunctionEntities2 = new ArrayList<>();
		//存不是主规格的信息
		List<TcsbFoodFunctionEntity> tcsbFoodFunctionEntities3 = new ArrayList<>();
		//获取规格信息
		for (TcsbFoodFunctionEntity tcsbFoodFunctionEntity : tcsbFoodFunctionEntities) {
			TcsbFoodTasteFunctionEntity tcsbFoodTasteFunctionEntity = tcsbFoodFunctionEntity.getTcsbFoodTasteFunctionEntity();
			//主规格情况
			if (tcsbFoodTasteFunctionEntity.getFunctionlevel()==0) {
				tcsbFoodFunctionEntities2.add(tcsbFoodFunctionEntity);
			}else {
				tcsbFoodFunctionEntities3.add(tcsbFoodFunctionEntity);
			}
		}
		//读主规格信息
		List<FoodTastePageVo> foodTastePageVoList = new ArrayList<>();
		
		for (int i = 0; i < tcsbFoodFunctionEntities2.size(); i++) {
			FoodTastePageVo foodTastePageVo = new FoodTastePageVo();
			foodTastePageVo.setFirstFun(tcsbFoodFunctionEntities2.get(i).getTcsbFoodTasteFunctionEntity().getStatename());
			List<TcsbFoodTasteFunctionEntity> tcsbFoodTasteFunctionEntities  = tcsbFoodFunctionEntities2.get(i).getTcsbFoodTasteFunctionEntity().getTcsbFoodTasteFunctionEntitys();
			List<String> neList = new ArrayList<>();
			for (TcsbFoodTasteFunctionEntity t : tcsbFoodTasteFunctionEntities) {
				neList.add(t.getId());
			}
			List<FoodTasteVo> foodTasteVolist = new ArrayList<>();
			for (int j = 0; j < tcsbFoodFunctionEntities3.size(); j++) {
				if (neList.contains(tcsbFoodFunctionEntities3.get(j).getTcsbFoodTasteFunctionEntity().getId())) {
					FoodTasteVo foodTasteVo = new FoodTasteVo();
					//foodTasteVo.setId(tcsbFoodFunctionEntities2.get(i).getId());
					foodTasteVo.setId(tcsbFoodFunctionEntities3.get(j).getTcsbFoodTasteFunctionEntity().getId());
					foodTasteVo.setTasteName(tcsbFoodFunctionEntities3.get(j).getTcsbFoodTasteFunctionEntity().getStatename());
					foodTasteVolist.add(foodTasteVo);
					
				}
			}
			foodTastePageVo.setFoodTasteVo(foodTasteVolist);
			foodTastePageVoList.add(foodTastePageVo);
		}
		
		//规格情况
		List<TcsbFoodStandardRelationshipEntity> standard = tcsbFoodService.findByProperty(TcsbFoodStandardRelationshipEntity.class, "foodId", foodId);
	    FoodStandardPageVo foodStandard = new FoodStandardPageVo();
	    List<FoodStandardVo> fstandardList = new ArrayList();
	    if (standard.size() > 0)
	    {
	      foodStandard.setStandaraFrist("规格");
	      for (TcsbFoodStandardRelationshipEntity s : standard)  
	    	  
	      {
	        FoodStandardVo fs = new FoodStandardVo();
	        TcsbFoodStandardEntity ts = tcsbFoodService.get(TcsbFoodStandardEntity.class, s.getFoodStandardId());
	        fs.setStandardId(s.getFoodStandardId());
	        fs.setStandardName(ts.getName());
	        fs.setStandardPrice(ts.getPrice().doubleValue());
	        fstandardList.add(fs);
	      }
	      foodStandard.setFoodStandard(fstandardList);
	    }
	    FoodTasteAndStandardVo fts = new FoodTasteAndStandardVo();
	    fts.setFoodTaste(foodTastePageVoList);
	    fts.setFoodStandard(foodStandard);
	    AjaxJsonApi ajaxJson = new AjaxJsonApi();
	    ajaxJson.setMsg("查询成功");
	    ajaxJson.setSuccess(true);
	    ajaxJson.setObj(fts);
		return ajaxJson;
	}
	/**
	 * 获取食品规格（后台）
	 * @param foodId
	 * @param request
	 * @param response
	 * @return
	 *//*
	@RequestMapping(params = "shopgetfoodTasteFun")
	@ResponseBody
	public List<FoodTastePageVo> shopgetfoodTasteFun(String foodId,HttpServletRequest request,HttpServletResponse response){
		
		Map<Integer, List<TcsbFoodTasteFunctionEntity>> functionMap = new HashMap<Integer, List<TcsbFoodTasteFunctionEntity>>();
		Map<String, TcsbFoodTasteFunctionEntity> loginActionlist = new HashMap<String, TcsbFoodTasteFunctionEntity>();
		String hql="select distinct f from TcsbFoodFunctionEntity ru,TcsbFoodTasteFunctionEntity f where ru.tcsbFoodTasteFunctionEntity.id=f.id and ru.tcsbFoodEntity.id ='"+foodId+"'";
		 List<TcsbFoodTasteFunctionEntity> list1 = systemService.findByQueryString(hql);
		
		for (TcsbFoodTasteFunctionEntity tcsbFoodTasteFunctionEntity : list1) {
			loginActionlist.put(tcsbFoodTasteFunctionEntity.getId(),tcsbFoodTasteFunctionEntity);
		}
		
		
		if (loginActionlist.size() > 0) {
			Collection<TcsbFoodTasteFunctionEntity> allFunctions = loginActionlist.values();
			for (TcsbFoodTasteFunctionEntity function : allFunctions) {
	           
				if (!functionMap.containsKey(function.getFunctionlevel() + 0)) {
					functionMap.put(function.getFunctionlevel() + 0,
							new ArrayList<TcsbFoodTasteFunctionEntity>());
				}
				functionMap.get(function.getFunctionlevel() + 0).add(function);
			}
			// 菜单栏排序
			Collection<List<TcsbFoodTasteFunctionEntity>> c = functionMap.values();
			for (List<TcsbFoodTasteFunctionEntity> list : c) {
				Collections.sort(list, new NumberComparator3());
			}
		}
		List<TcsbFoodTasteFunctionEntity> tcsbFoodTasteFunctionEntity = functionMap.get(0);
		List<FoodTastePageVo> foodTastePageVoList = new ArrayList<>();
		FoodTastePageVo foodTastePageVo;
		for (TcsbFoodTasteFunctionEntity tcsbFoodTasteFunctionEntity2 : tcsbFoodTasteFunctionEntity) {
			List<FoodTasteVo> foodTasteVolist = new ArrayList<>();
			FoodTasteVo foodTasteVo;
			foodTastePageVo = new FoodTastePageVo();
			for (TcsbFoodTasteFunctionEntity tcsbFoodTasteFunctionEntity3 : tcsbFoodTasteFunctionEntity2.getTcsbFoodTasteFunctionEntitys()) {
				foodTasteVo = new FoodTasteVo();
				foodTasteVo.setId(tcsbFoodTasteFunctionEntity3.getId());
				foodTasteVo.setTasteName(tcsbFoodTasteFunctionEntity3.getStatename());
				foodTasteVolist.add(foodTasteVo);
			}
			foodTastePageVo.setFirstFun(tcsbFoodTasteFunctionEntity2.getStatename());
			foodTastePageVo.setFoodTasteVo(foodTasteVolist);
			foodTastePageVoList.add(foodTastePageVo);
		}
		return foodTastePageVoList;
	}
	
	@RequestMapping(params = "checkfoodTasteFun")
	@ResponseBody
	public int checkfoodTasteFun(String foodId,HttpServletRequest request,HttpServletResponse response){
		String sql = "select id from tcsb_food_function where foodid='"+foodId+"'";
		List<Object> ovoList1 = tcsbShopCarService.findListbySql(sql);
		if(ovoList1.size()>0){
			return 1;
		}else{
			return 0;
		}
		
	}*/
	
	
	
}
