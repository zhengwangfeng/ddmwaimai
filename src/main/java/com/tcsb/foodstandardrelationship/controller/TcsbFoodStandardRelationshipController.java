package com.tcsb.foodstandardrelationship.controller;
import com.tcsb.foodstandardrelationship.entity.TcsbFoodStandardRelationshipEntity;
import com.tcsb.foodstandardrelationship.service.TcsbFoodStandardRelationshipServiceI;
import java.util.ArrayList;
import java.util.List;
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
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import org.jeecgframework.core.util.ExceptionUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
 * @Description: 食物规格关联表
 * @author onlineGenerator
 * @date 2017-11-27 13:50:38
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbFoodStandardRelationshipController")
public class TcsbFoodStandardRelationshipController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbFoodStandardRelationshipController.class);

	@Autowired
	private TcsbFoodStandardRelationshipServiceI tcsbFoodStandardRelationshipService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 食物规格关联表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/foodstandardrelationship/tcsbFoodStandardRelationshipList");
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
	public void datagrid(TcsbFoodStandardRelationshipEntity tcsbFoodStandardRelationship,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbFoodStandardRelationshipEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbFoodStandardRelationship, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbFoodStandardRelationshipService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除食物规格关联表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbFoodStandardRelationshipEntity tcsbFoodStandardRelationship, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbFoodStandardRelationship = systemService.getEntity(TcsbFoodStandardRelationshipEntity.class, tcsbFoodStandardRelationship.getId());
		message = "食物规格关联表删除成功";
		try{
			tcsbFoodStandardRelationshipService.delete(tcsbFoodStandardRelationship);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "食物规格关联表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除食物规格关联表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "食物规格关联表删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbFoodStandardRelationshipEntity tcsbFoodStandardRelationship = systemService.getEntity(TcsbFoodStandardRelationshipEntity.class, 
				id
				);
				tcsbFoodStandardRelationshipService.delete(tcsbFoodStandardRelationship);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "食物规格关联表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加食物规格关联表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbFoodStandardRelationshipEntity tcsbFoodStandardRelationship, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "食物规格关联表添加成功";
		try{
			tcsbFoodStandardRelationshipService.save(tcsbFoodStandardRelationship);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "食物规格关联表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新食物规格关联表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbFoodStandardRelationshipEntity tcsbFoodStandardRelationship, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "食物规格关联表更新成功";
		TcsbFoodStandardRelationshipEntity t = tcsbFoodStandardRelationshipService.get(TcsbFoodStandardRelationshipEntity.class, tcsbFoodStandardRelationship.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbFoodStandardRelationship, t);
			tcsbFoodStandardRelationshipService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "食物规格关联表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 食物规格关联表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbFoodStandardRelationshipEntity tcsbFoodStandardRelationship, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbFoodStandardRelationship.getId())) {
			tcsbFoodStandardRelationship = tcsbFoodStandardRelationshipService.getEntity(TcsbFoodStandardRelationshipEntity.class, tcsbFoodStandardRelationship.getId());
			req.setAttribute("tcsbFoodStandardRelationshipPage", tcsbFoodStandardRelationship);
		}
		return new ModelAndView("com/tcsb/foodstandardrelationship/tcsbFoodStandardRelationship-add");
	}
	/**
	 * 食物规格关联表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbFoodStandardRelationshipEntity tcsbFoodStandardRelationship, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbFoodStandardRelationship.getId())) {
			tcsbFoodStandardRelationship = tcsbFoodStandardRelationshipService.getEntity(TcsbFoodStandardRelationshipEntity.class, tcsbFoodStandardRelationship.getId());
			req.setAttribute("tcsbFoodStandardRelationshipPage", tcsbFoodStandardRelationship);
		}
		return new ModelAndView("com/tcsb/foodstandardrelationship/tcsbFoodStandardRelationship-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbFoodStandardRelationshipController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbFoodStandardRelationshipEntity tcsbFoodStandardRelationship,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbFoodStandardRelationshipEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbFoodStandardRelationship, request.getParameterMap());
		List<TcsbFoodStandardRelationshipEntity> tcsbFoodStandardRelationships = this.tcsbFoodStandardRelationshipService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"食物规格关联表");
		modelMap.put(NormalExcelConstants.CLASS,TcsbFoodStandardRelationshipEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("食物规格关联表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbFoodStandardRelationships);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbFoodStandardRelationshipEntity tcsbFoodStandardRelationship,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"食物规格关联表");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbFoodStandardRelationshipEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("食物规格关联表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbFoodStandardRelationshipEntity> listTcsbFoodStandardRelationshipEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbFoodStandardRelationshipEntity.class,params);
				for (TcsbFoodStandardRelationshipEntity tcsbFoodStandardRelationship : listTcsbFoodStandardRelationshipEntitys) {
					tcsbFoodStandardRelationshipService.save(tcsbFoodStandardRelationship);
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
	public List<TcsbFoodStandardRelationshipEntity> list() {
		List<TcsbFoodStandardRelationshipEntity> listTcsbFoodStandardRelationships=tcsbFoodStandardRelationshipService.getList(TcsbFoodStandardRelationshipEntity.class);
		return listTcsbFoodStandardRelationships;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbFoodStandardRelationshipEntity task = tcsbFoodStandardRelationshipService.get(TcsbFoodStandardRelationshipEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbFoodStandardRelationshipEntity tcsbFoodStandardRelationship, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbFoodStandardRelationshipEntity>> failures = validator.validate(tcsbFoodStandardRelationship);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbFoodStandardRelationshipService.save(tcsbFoodStandardRelationship);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbFoodStandardRelationship.getId();
		URI uri = uriBuilder.path("/rest/tcsbFoodStandardRelationshipController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbFoodStandardRelationshipEntity tcsbFoodStandardRelationship) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbFoodStandardRelationshipEntity>> failures = validator.validate(tcsbFoodStandardRelationship);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbFoodStandardRelationshipService.saveOrUpdate(tcsbFoodStandardRelationship);
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
		tcsbFoodStandardRelationshipService.deleteEntityById(TcsbFoodStandardRelationshipEntity.class, id);
	}
}
