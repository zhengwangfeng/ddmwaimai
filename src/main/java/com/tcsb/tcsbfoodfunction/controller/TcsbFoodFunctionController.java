package com.tcsb.tcsbfoodfunction.controller;
import com.tcsb.tcsbfoodfunction.entity.TcsbFoodFunctionEntity;
import com.tcsb.tcsbfoodfunction.service.TcsbFoodFunctionServiceI;
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
 * @Description: tcsb_food_function
 * @author onlineGenerator
 * @date 2017-06-21 17:25:44
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbFoodFunctionController")
public class TcsbFoodFunctionController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbFoodFunctionController.class);

	@Autowired
	private TcsbFoodFunctionServiceI tcsbFoodFunctionService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * tcsb_food_function列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/tcsbfoodfunction/tcsbFoodFunctionList");
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
	public void datagrid(TcsbFoodFunctionEntity tcsbFoodFunction,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbFoodFunctionEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbFoodFunction, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbFoodFunctionService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除tcsb_food_function
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbFoodFunctionEntity tcsbFoodFunction, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbFoodFunction = systemService.getEntity(TcsbFoodFunctionEntity.class, tcsbFoodFunction.getId());
		message = "tcsb_food_function删除成功";
		try{
			tcsbFoodFunctionService.delete(tcsbFoodFunction);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "tcsb_food_function删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除tcsb_food_function
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "tcsb_food_function删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbFoodFunctionEntity tcsbFoodFunction = systemService.getEntity(TcsbFoodFunctionEntity.class, 
				id
				);
				tcsbFoodFunctionService.delete(tcsbFoodFunction);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "tcsb_food_function删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加tcsb_food_function
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbFoodFunctionEntity tcsbFoodFunction, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "tcsb_food_function添加成功";
		try{
			tcsbFoodFunctionService.save(tcsbFoodFunction);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "tcsb_food_function添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新tcsb_food_function
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbFoodFunctionEntity tcsbFoodFunction, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "tcsb_food_function更新成功";
		TcsbFoodFunctionEntity t = tcsbFoodFunctionService.get(TcsbFoodFunctionEntity.class, tcsbFoodFunction.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbFoodFunction, t);
			tcsbFoodFunctionService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "tcsb_food_function更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * tcsb_food_function新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbFoodFunctionEntity tcsbFoodFunction, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbFoodFunction.getId())) {
			tcsbFoodFunction = tcsbFoodFunctionService.getEntity(TcsbFoodFunctionEntity.class, tcsbFoodFunction.getId());
			req.setAttribute("tcsbFoodFunctionPage", tcsbFoodFunction);
		}
		return new ModelAndView("com/tcsb/tcsbfoodfunction/tcsbFoodFunction-add");
	}
	/**
	 * tcsb_food_function编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbFoodFunctionEntity tcsbFoodFunction, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbFoodFunction.getId())) {
			tcsbFoodFunction = tcsbFoodFunctionService.getEntity(TcsbFoodFunctionEntity.class, tcsbFoodFunction.getId());
			req.setAttribute("tcsbFoodFunctionPage", tcsbFoodFunction);
		}
		return new ModelAndView("com/tcsb/tcsbfoodfunction/tcsbFoodFunction-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbFoodFunctionController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbFoodFunctionEntity tcsbFoodFunction,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbFoodFunctionEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbFoodFunction, request.getParameterMap());
		List<TcsbFoodFunctionEntity> tcsbFoodFunctions = this.tcsbFoodFunctionService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"tcsb_food_function");
		modelMap.put(NormalExcelConstants.CLASS,TcsbFoodFunctionEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("tcsb_food_function列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbFoodFunctions);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbFoodFunctionEntity tcsbFoodFunction,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"tcsb_food_function");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbFoodFunctionEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("tcsb_food_function列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbFoodFunctionEntity> listTcsbFoodFunctionEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbFoodFunctionEntity.class,params);
				for (TcsbFoodFunctionEntity tcsbFoodFunction : listTcsbFoodFunctionEntitys) {
					tcsbFoodFunctionService.save(tcsbFoodFunction);
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
	public List<TcsbFoodFunctionEntity> list() {
		List<TcsbFoodFunctionEntity> listTcsbFoodFunctions=tcsbFoodFunctionService.getList(TcsbFoodFunctionEntity.class);
		return listTcsbFoodFunctions;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbFoodFunctionEntity task = tcsbFoodFunctionService.get(TcsbFoodFunctionEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbFoodFunctionEntity tcsbFoodFunction, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbFoodFunctionEntity>> failures = validator.validate(tcsbFoodFunction);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbFoodFunctionService.save(tcsbFoodFunction);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbFoodFunction.getId();
		URI uri = uriBuilder.path("/rest/tcsbFoodFunctionController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbFoodFunctionEntity tcsbFoodFunction) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbFoodFunctionEntity>> failures = validator.validate(tcsbFoodFunction);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbFoodFunctionService.saveOrUpdate(tcsbFoodFunction);
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
		tcsbFoodFunctionService.deleteEntityById(TcsbFoodFunctionEntity.class, id);
	}
}
