package com.tcsb.printerfoodfun.controller;
import com.tcsb.printerfoodfun.entity.TcsbPrinterFoodFunEntity;
import com.tcsb.printerfoodfun.service.TcsbPrinterFoodFunServiceI;
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
 * @Description: 打印食物菜单
 * @author onlineGenerator
 * @date 2017-07-14 14:19:10
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbPrinterFoodFunController")
public class TcsbPrinterFoodFunController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbPrinterFoodFunController.class);

	@Autowired
	private TcsbPrinterFoodFunServiceI tcsbPrinterFoodFunService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 打印食物菜单列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/printerfoodfun/tcsbPrinterFoodFunList");
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
	public void datagrid(TcsbPrinterFoodFunEntity tcsbPrinterFoodFun,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbPrinterFoodFunEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbPrinterFoodFun, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbPrinterFoodFunService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除打印食物菜单
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbPrinterFoodFunEntity tcsbPrinterFoodFun, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbPrinterFoodFun = systemService.getEntity(TcsbPrinterFoodFunEntity.class, tcsbPrinterFoodFun.getId());
		message = "打印食物菜单删除成功";
		try{
			tcsbPrinterFoodFunService.delete(tcsbPrinterFoodFun);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "打印食物菜单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除打印食物菜单
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "打印食物菜单删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbPrinterFoodFunEntity tcsbPrinterFoodFun = systemService.getEntity(TcsbPrinterFoodFunEntity.class, 
				id
				);
				tcsbPrinterFoodFunService.delete(tcsbPrinterFoodFun);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "打印食物菜单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加打印食物菜单
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbPrinterFoodFunEntity tcsbPrinterFoodFun, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "打印食物菜单添加成功";
		try{
			tcsbPrinterFoodFunService.save(tcsbPrinterFoodFun);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "打印食物菜单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新打印食物菜单
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbPrinterFoodFunEntity tcsbPrinterFoodFun, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "打印食物菜单更新成功";
		TcsbPrinterFoodFunEntity t = tcsbPrinterFoodFunService.get(TcsbPrinterFoodFunEntity.class, tcsbPrinterFoodFun.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbPrinterFoodFun, t);
			tcsbPrinterFoodFunService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "打印食物菜单更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 打印食物菜单新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbPrinterFoodFunEntity tcsbPrinterFoodFun, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbPrinterFoodFun.getId())) {
			tcsbPrinterFoodFun = tcsbPrinterFoodFunService.getEntity(TcsbPrinterFoodFunEntity.class, tcsbPrinterFoodFun.getId());
			req.setAttribute("tcsbPrinterFoodFunPage", tcsbPrinterFoodFun);
		}
		return new ModelAndView("com/tcsb/printerfoodfun/tcsbPrinterFoodFun-add");
	}
	/**
	 * 打印食物菜单编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbPrinterFoodFunEntity tcsbPrinterFoodFun, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbPrinterFoodFun.getId())) {
			tcsbPrinterFoodFun = tcsbPrinterFoodFunService.getEntity(TcsbPrinterFoodFunEntity.class, tcsbPrinterFoodFun.getId());
			req.setAttribute("tcsbPrinterFoodFunPage", tcsbPrinterFoodFun);
		}
		return new ModelAndView("com/tcsb/printerfoodfun/tcsbPrinterFoodFun-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbPrinterFoodFunController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbPrinterFoodFunEntity tcsbPrinterFoodFun,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbPrinterFoodFunEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbPrinterFoodFun, request.getParameterMap());
		List<TcsbPrinterFoodFunEntity> tcsbPrinterFoodFuns = this.tcsbPrinterFoodFunService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"打印食物菜单");
		modelMap.put(NormalExcelConstants.CLASS,TcsbPrinterFoodFunEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("打印食物菜单列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbPrinterFoodFuns);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbPrinterFoodFunEntity tcsbPrinterFoodFun,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"打印食物菜单");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbPrinterFoodFunEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("打印食物菜单列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbPrinterFoodFunEntity> listTcsbPrinterFoodFunEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbPrinterFoodFunEntity.class,params);
				for (TcsbPrinterFoodFunEntity tcsbPrinterFoodFun : listTcsbPrinterFoodFunEntitys) {
					tcsbPrinterFoodFunService.save(tcsbPrinterFoodFun);
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
	public List<TcsbPrinterFoodFunEntity> list() {
		List<TcsbPrinterFoodFunEntity> listTcsbPrinterFoodFuns=tcsbPrinterFoodFunService.getList(TcsbPrinterFoodFunEntity.class);
		return listTcsbPrinterFoodFuns;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbPrinterFoodFunEntity task = tcsbPrinterFoodFunService.get(TcsbPrinterFoodFunEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbPrinterFoodFunEntity tcsbPrinterFoodFun, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbPrinterFoodFunEntity>> failures = validator.validate(tcsbPrinterFoodFun);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbPrinterFoodFunService.save(tcsbPrinterFoodFun);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbPrinterFoodFun.getId();
		URI uri = uriBuilder.path("/rest/tcsbPrinterFoodFunController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbPrinterFoodFunEntity tcsbPrinterFoodFun) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbPrinterFoodFunEntity>> failures = validator.validate(tcsbPrinterFoodFun);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbPrinterFoodFunService.saveOrUpdate(tcsbPrinterFoodFun);
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
		tcsbPrinterFoodFunService.deleteEntityById(TcsbPrinterFoodFunEntity.class, id);
	}
}
