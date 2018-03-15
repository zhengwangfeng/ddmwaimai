package com.tcsb.foodstock.controller;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.food.service.TcsbFoodServiceI;
import com.tcsb.foodstock.entity.TcsbFoodStockEntity;
import com.tcsb.foodstock.service.TcsbFoodStockServiceI;
import com.tcsb.foodtype.entity.TcsbFoodTypeEntity;
import com.tcsb.foodtype.service.TcsbFoodTypeServiceI;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;

import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.util.ResourceUtil;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;

import org.jeecgframework.core.util.ExceptionUtil;
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
 * @Description: 食物库存设置
 * @author onlineGenerator
 * @date 2018-01-20 14:14:03
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbFoodStockController")
public class TcsbFoodStockController extends BaseController {
	/**
	 * Logger for this crlass
	 */
	private static final Logger logger = Logger.getLogger(TcsbFoodStockController.class);

	@Autowired
	private TcsbFoodStockServiceI tcsbFoodStockService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbFoodServiceI tcsbFoodService;
	@Autowired
	private TcsbFoodTypeServiceI tcsbFoodTypeService;
	


	/**
	 * 食物库存设置列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
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
		return new ModelAndView("com/tcsb/foodstock/tcsbFoodStockList");
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
	public void datagrid(TcsbFoodStockEntity tcsbFoodStock,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbFoodStockEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbFoodStock, request.getParameterMap());
		try{
		//自定义追加查询条件
			TSUser user = getCurrentUser();
			if (!checkAdmin()) {
				// 查询商家店铺
				cq.eq("shopId", user.getShopId());
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbFoodStockService.getDataGridReturn(cq, true);
		if (!dataGrid.getResults().isEmpty()) {
			List<TcsbFoodStockEntity> tcsbFoodStockEntities = dataGrid.getResults();
			for (TcsbFoodStockEntity tcsbFoodStockEntity : tcsbFoodStockEntities) {
				TcsbFoodEntity tcsbFoodEntity = systemService.get(TcsbFoodEntity.class, tcsbFoodStockEntity.getFoodId());
				tcsbFoodStockEntity.setFoodName(tcsbFoodEntity.getName());
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除食物库存设置
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbFoodStockEntity tcsbFoodStock, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbFoodStock = systemService.getEntity(TcsbFoodStockEntity.class, tcsbFoodStock.getId());
		message = "食物库存设置删除成功";
		try{
			tcsbFoodStockService.delete(tcsbFoodStock);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "食物库存设置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除食物库存设置
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "食物库存设置删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbFoodStockEntity tcsbFoodStock = systemService.getEntity(TcsbFoodStockEntity.class, 
				id
				);
				tcsbFoodStockService.delete(tcsbFoodStock);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "食物库存设置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加食物库存设置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbFoodStockEntity tcsbFoodStock, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "食物库存设置添加成功";
		try{
			tcsbFoodStockService.save(tcsbFoodStock);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "食物库存设置添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新食物库存设置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbFoodStockEntity tcsbFoodStock, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "食物库存设置更新成功";
		TcsbFoodStockEntity t = tcsbFoodStockService.get(TcsbFoodStockEntity.class, tcsbFoodStock.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbFoodStock, t);
			tcsbFoodStockService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "食物库存设置更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 食物库存设置新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbFoodStockEntity tcsbFoodStock, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbFoodStock.getId())) {
			tcsbFoodStock = tcsbFoodStockService.getEntity(TcsbFoodStockEntity.class, tcsbFoodStock.getId());
			req.setAttribute("tcsbFoodStockPage", tcsbFoodStock);
		}
		return new ModelAndView("com/tcsb/foodstock/tcsbFoodStock-add");
	}
	/**
	 * 食物库存设置编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbFoodStockEntity tcsbFoodStock, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbFoodStock.getId())) {
			tcsbFoodStock = tcsbFoodStockService.getEntity(TcsbFoodStockEntity.class, tcsbFoodStock.getId());
			req.setAttribute("tcsbFoodStockPage", tcsbFoodStock);
		}
		return new ModelAndView("com/tcsb/foodstock/tcsbFoodStock-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbFoodStockController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbFoodStockEntity tcsbFoodStock,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbFoodStockEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbFoodStock, request.getParameterMap());
		List<TcsbFoodStockEntity> tcsbFoodStocks = this.tcsbFoodStockService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"食物库存设置");
		modelMap.put(NormalExcelConstants.CLASS,TcsbFoodStockEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("食物库存设置列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbFoodStocks);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbFoodStockEntity tcsbFoodStock,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"食物库存设置");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbFoodStockEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("食物库存设置列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbFoodStockEntity> listTcsbFoodStockEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbFoodStockEntity.class,params);
				for (TcsbFoodStockEntity tcsbFoodStock : listTcsbFoodStockEntitys) {
					tcsbFoodStockService.save(tcsbFoodStock);
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
	public List<TcsbFoodStockEntity> list() {
		List<TcsbFoodStockEntity> listTcsbFoodStocks=tcsbFoodStockService.getList(TcsbFoodStockEntity.class);
		return listTcsbFoodStocks;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbFoodStockEntity task = tcsbFoodStockService.get(TcsbFoodStockEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbFoodStockEntity tcsbFoodStock, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbFoodStockEntity>> failures = validator.validate(tcsbFoodStock);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbFoodStockService.save(tcsbFoodStock);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbFoodStock.getId();
		URI uri = uriBuilder.path("/rest/tcsbFoodStockController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbFoodStockEntity tcsbFoodStock) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbFoodStockEntity>> failures = validator.validate(tcsbFoodStock);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbFoodStockService.saveOrUpdate(tcsbFoodStock);
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
		tcsbFoodStockService.deleteEntityById(TcsbFoodStockEntity.class, id);
	}
}
