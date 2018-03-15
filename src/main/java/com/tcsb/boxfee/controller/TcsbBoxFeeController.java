package com.tcsb.boxfee.controller;
import com.tcsb.boxfee.entity.TcsbBoxFeeEntity;
import com.tcsb.boxfee.service.TcsbBoxFeeServiceI;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;

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
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
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
 * @Description: 餐盒费设置
 * @author onlineGenerator
 * @date 2018-01-09 11:36:36
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbBoxFeeController")
public class TcsbBoxFeeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbBoxFeeController.class);

	@Autowired
	private TcsbBoxFeeServiceI tcsbBoxFeeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	


	/**
	 * 餐盒费设置列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		String shopsReplace = "";
		List<TcsbShopEntity> tcsbShopEntities = systemService.getList(TcsbShopEntity.class);
		for (TcsbShopEntity tcsbShopEntity : tcsbShopEntities) {
			if (shopsReplace.length() > 0) {
				shopsReplace += ",";
			}
			shopsReplace += tcsbShopEntity.getName() + "_" + tcsbShopEntity.getId();
		}
		//如果是admin用户展示店铺选择
		TSUser tsUser = getCurrentUser();
		if (tsUser.getUserName().equals("admin")) {
			request.setAttribute("isAdmin", true);
		}
		request.setAttribute("shopsReplace", shopsReplace);
		return new ModelAndView("com/tcsb/boxfee/tcsbBoxFeeList");
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
	public void datagrid(TcsbBoxFeeEntity tcsbBoxFee,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbBoxFeeEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbBoxFee, request.getParameterMap());
		try{
		//自定义追加查询条件
			TSUser user=getCurrentUser();
			if (!checkAdmin()) {
				cq.add(Restrictions.eq("shopId", user.getShopId()));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbBoxFeeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除餐盒费设置
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbBoxFeeEntity tcsbBoxFee, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbBoxFee = systemService.getEntity(TcsbBoxFeeEntity.class, tcsbBoxFee.getId());
		message = "餐盒费设置删除成功";
		try{
			tcsbBoxFeeService.delete(tcsbBoxFee);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "餐盒费设置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除餐盒费设置
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "餐盒费设置删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbBoxFeeEntity tcsbBoxFee = systemService.getEntity(TcsbBoxFeeEntity.class, 
				id
				);
				tcsbBoxFeeService.delete(tcsbBoxFee);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "餐盒费设置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加餐盒费设置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbBoxFeeEntity tcsbBoxFee, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "餐盒费设置添加成功";
		try{
			
			// 获取当前用户
			TSUser user = getCurrentUser();
			// 查询商家店铺
			TcsbShopEntity tcsbShopEntity = tcsbShopService
					.findUniqueByProperty(TcsbShopEntity.class, "userId",
							user.getId());
			//查看当前商家是否已经有设置餐盒费
			TcsbBoxFeeEntity tcsbBoxFeeEntity = tcsbBoxFeeService.findUniqueByProperty(TcsbBoxFeeEntity.class, "shopId", tcsbShopEntity.getId());
			if (StringUtil.isNotEmpty(tcsbBoxFeeEntity)) {
				message = "餐盒费已经设置过";
			}else {
				tcsbBoxFee.setShopId(tcsbShopEntity.getId());
				tcsbBoxFeeService.save(tcsbBoxFee);
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "餐盒费设置添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新餐盒费设置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbBoxFeeEntity tcsbBoxFee, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "餐盒费设置更新成功";
		TcsbBoxFeeEntity t = tcsbBoxFeeService.get(TcsbBoxFeeEntity.class, tcsbBoxFee.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbBoxFee, t);
			tcsbBoxFeeService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "餐盒费设置更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 餐盒费设置新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbBoxFeeEntity tcsbBoxFee, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbBoxFee.getId())) {
			tcsbBoxFee = tcsbBoxFeeService.getEntity(TcsbBoxFeeEntity.class, tcsbBoxFee.getId());
			req.setAttribute("tcsbBoxFeePage", tcsbBoxFee);
		}
		return new ModelAndView("com/tcsb/boxfee/tcsbBoxFee-add");
	}
	/**
	 * 餐盒费设置编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbBoxFeeEntity tcsbBoxFee, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbBoxFee.getId())) {
			tcsbBoxFee = tcsbBoxFeeService.getEntity(TcsbBoxFeeEntity.class, tcsbBoxFee.getId());
			req.setAttribute("tcsbBoxFeePage", tcsbBoxFee);
		}
		return new ModelAndView("com/tcsb/boxfee/tcsbBoxFee-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbBoxFeeController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbBoxFeeEntity tcsbBoxFee,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbBoxFeeEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbBoxFee, request.getParameterMap());
		List<TcsbBoxFeeEntity> tcsbBoxFees = this.tcsbBoxFeeService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"餐盒费设置");
		modelMap.put(NormalExcelConstants.CLASS,TcsbBoxFeeEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("餐盒费设置列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbBoxFees);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbBoxFeeEntity tcsbBoxFee,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"餐盒费设置");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbBoxFeeEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("餐盒费设置列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbBoxFeeEntity> listTcsbBoxFeeEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbBoxFeeEntity.class,params);
				for (TcsbBoxFeeEntity tcsbBoxFee : listTcsbBoxFeeEntitys) {
					tcsbBoxFeeService.save(tcsbBoxFee);
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
	public List<TcsbBoxFeeEntity> list() {
		List<TcsbBoxFeeEntity> listTcsbBoxFees=tcsbBoxFeeService.getList(TcsbBoxFeeEntity.class);
		return listTcsbBoxFees;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbBoxFeeEntity task = tcsbBoxFeeService.get(TcsbBoxFeeEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbBoxFeeEntity tcsbBoxFee, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbBoxFeeEntity>> failures = validator.validate(tcsbBoxFee);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbBoxFeeService.save(tcsbBoxFee);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbBoxFee.getId();
		URI uri = uriBuilder.path("/rest/tcsbBoxFeeController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbBoxFeeEntity tcsbBoxFee) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbBoxFeeEntity>> failures = validator.validate(tcsbBoxFee);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbBoxFeeService.saveOrUpdate(tcsbBoxFee);
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
		tcsbBoxFeeService.deleteEntityById(TcsbBoxFeeEntity.class, id);
	}
}
