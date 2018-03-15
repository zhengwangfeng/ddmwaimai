package com.tcsb.distributionscopeattr.controller;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
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

import com.tcsb.distributionscope.entity.TcsbDistributionScopeEntity;
import com.tcsb.distributionscope.service.TcsbDistributionScopeServiceI;
import com.tcsb.distributionscopeattr.entity.TcsbDistributionScopeAttrEntity;
import com.tcsb.distributionscopeattr.service.TcsbDistributionScopeAttrServiceI;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.foodtype.entity.TcsbFoodTypeEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;

/**   
 * @Title: Controller  
 * @Description: 店铺配送范围属性
 * @author onlineGenerator
 * @date 2018-01-09 15:56:45
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbDistributionScopeAttrController")
public class TcsbDistributionScopeAttrController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbDistributionScopeAttrController.class);

	@Autowired
	private TcsbDistributionScopeAttrServiceI tcsbDistributionScopeAttrService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbDistributionScopeServiceI tcsbDistributionScopeService;
	@Autowired
	private TcsbShopServiceI tcsbShopService;


	/**
	 * 店铺配送范围属性列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/distributionscopeattr/tcsbDistributionScopeAttrList");
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
	public void datagrid(TcsbDistributionScopeAttrEntity tcsbDistributionScopeAttr,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbDistributionScopeAttrEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbDistributionScopeAttr, request.getParameterMap());
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
		this.tcsbDistributionScopeAttrService.getDataGridReturn(cq, true);
		List<TcsbDistributionScopeAttrEntity> tcsbDistributionScopeAttrEntitietemp = new ArrayList<>();
		//设置配送范围
		if (!dataGrid.getResults().isEmpty()) {
			List<TcsbDistributionScopeAttrEntity> tcsbDistributionScopeAttrEntities = dataGrid.getResults();
			for (TcsbDistributionScopeAttrEntity tcsbDistributionScopeAttrEntity : tcsbDistributionScopeAttrEntities) {
				//查找配送范围
				 TcsbDistributionScopeEntity tcsbDistributionScopeEntity = tcsbDistributionScopeService.get(TcsbDistributionScopeEntity.class, tcsbDistributionScopeAttrEntity.getDistributionScopeId());
				 TcsbDistributionScopeAttrEntity tcsbDistributionScopeAttrEntitynew  = new TcsbDistributionScopeAttrEntity();
				 try {
					MyBeanUtils.copyBeanNotNull2Bean(tcsbDistributionScopeAttrEntity, tcsbDistributionScopeAttrEntitynew);
					tcsbDistributionScopeAttrEntitynew.setMileage(tcsbDistributionScopeEntity.getMileage());
					tcsbDistributionScopeAttrEntitietemp.add(tcsbDistributionScopeAttrEntitynew);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
			}
		}
		dataGrid.setResults(tcsbDistributionScopeAttrEntitietemp);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除店铺配送范围属性
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbDistributionScopeAttrEntity tcsbDistributionScopeAttr, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbDistributionScopeAttr = systemService.getEntity(TcsbDistributionScopeAttrEntity.class, tcsbDistributionScopeAttr.getId());
		message = "店铺配送范围属性删除成功";
		try{
			tcsbDistributionScopeAttrService.delete(tcsbDistributionScopeAttr);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺配送范围属性删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除店铺配送范围属性
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺配送范围属性删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbDistributionScopeAttrEntity tcsbDistributionScopeAttr = systemService.getEntity(TcsbDistributionScopeAttrEntity.class, 
				id
				);
				tcsbDistributionScopeAttrService.delete(tcsbDistributionScopeAttr);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺配送范围属性删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加店铺配送范围属性
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbDistributionScopeAttrEntity tcsbDistributionScopeAttr, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺配送范围属性添加成功";
		try{
			// 获取当前用户
			TSUser user = getCurrentUser();
			// 查询商家店铺
			TcsbShopEntity tcsbShopEntity = tcsbShopService
					.findUniqueByProperty(TcsbShopEntity.class, "userId",
							user.getId());
			//查看是否已经配置过配送范围属性
			TcsbDistributionScopeAttrEntity tcsbDistributionScopeAttrEntity = tcsbDistributionScopeAttrService.findUniqueByProperty(TcsbDistributionScopeAttrEntity.class, "shopId", tcsbShopEntity.getId());
			if (StringUtil.isNotEmpty(tcsbDistributionScopeAttrEntity)) {
				message = "该店铺已配置过配送范围属性";
			}else {
				tcsbDistributionScopeAttr.setNote("该店铺起送金额:"+tcsbDistributionScopeAttr.getBeginSendMoney()+"配送费:"+tcsbDistributionScopeAttr.getDistributionFee());
				tcsbDistributionScopeAttr.setShopId(tcsbShopEntity.getId());
				tcsbDistributionScopeAttrService.save(tcsbDistributionScopeAttr);
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺配送范围属性添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新店铺配送范围属性
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbDistributionScopeAttrEntity tcsbDistributionScopeAttr, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺配送范围属性更新成功";
		TcsbDistributionScopeAttrEntity t = tcsbDistributionScopeAttrService.get(TcsbDistributionScopeAttrEntity.class, tcsbDistributionScopeAttr.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbDistributionScopeAttr, t);
			tcsbDistributionScopeAttrService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "店铺配送范围属性更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 店铺配送范围属性新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbDistributionScopeAttrEntity tcsbDistributionScopeAttr, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbDistributionScopeAttr.getId())) {
			tcsbDistributionScopeAttr = tcsbDistributionScopeAttrService.getEntity(TcsbDistributionScopeAttrEntity.class, tcsbDistributionScopeAttr.getId());
			req.setAttribute("tcsbDistributionScopeAttrPage", tcsbDistributionScopeAttr);
		}
		//获取店铺配送范围
		List<TcsbDistributionScopeEntity> tcsbDistributionScopeEntities = new ArrayList<TcsbDistributionScopeEntity>();
		if (checkAdmin()) {
			tcsbDistributionScopeEntities = tcsbDistributionScopeService
					.getList(TcsbDistributionScopeEntity.class);
		} else {
			TSUser tsUser = getCurrentUser();
			tcsbDistributionScopeEntities = tcsbDistributionScopeService.findByProperty(
					TcsbDistributionScopeEntity.class, "shopId", tsUser.getShopId());
		}
		req.setAttribute("tcsbDistributionScopeEntities", tcsbDistributionScopeEntities);
		return new ModelAndView("com/tcsb/distributionscopeattr/tcsbDistributionScopeAttr-add");
	}
	/**
	 * 店铺配送范围属性编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbDistributionScopeAttrEntity tcsbDistributionScopeAttr, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbDistributionScopeAttr.getId())) {
			tcsbDistributionScopeAttr = tcsbDistributionScopeAttrService.getEntity(TcsbDistributionScopeAttrEntity.class, tcsbDistributionScopeAttr.getId());
			req.setAttribute("tcsbDistributionScopeAttrPage", tcsbDistributionScopeAttr);
		}
		//获取店铺配送范围
				List<TcsbDistributionScopeEntity> tcsbDistributionScopeEntities = new ArrayList<TcsbDistributionScopeEntity>();
				if (checkAdmin()) {
					tcsbDistributionScopeEntities = tcsbDistributionScopeService
							.getList(TcsbDistributionScopeEntity.class);
				} else {
					TSUser tsUser = getCurrentUser();
					tcsbDistributionScopeEntities = tcsbDistributionScopeService.findByProperty(
							TcsbDistributionScopeEntity.class, "shopId", tsUser.getShopId());
				}
				req.setAttribute("tcsbDistributionScopeEntities", tcsbDistributionScopeEntities);
		return new ModelAndView("com/tcsb/distributionscopeattr/tcsbDistributionScopeAttr-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbDistributionScopeAttrController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbDistributionScopeAttrEntity tcsbDistributionScopeAttr,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbDistributionScopeAttrEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbDistributionScopeAttr, request.getParameterMap());
		List<TcsbDistributionScopeAttrEntity> tcsbDistributionScopeAttrs = this.tcsbDistributionScopeAttrService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"店铺配送范围属性");
		modelMap.put(NormalExcelConstants.CLASS,TcsbDistributionScopeAttrEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("店铺配送范围属性列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbDistributionScopeAttrs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbDistributionScopeAttrEntity tcsbDistributionScopeAttr,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"店铺配送范围属性");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbDistributionScopeAttrEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("店铺配送范围属性列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbDistributionScopeAttrEntity> listTcsbDistributionScopeAttrEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbDistributionScopeAttrEntity.class,params);
				for (TcsbDistributionScopeAttrEntity tcsbDistributionScopeAttr : listTcsbDistributionScopeAttrEntitys) {
					tcsbDistributionScopeAttrService.save(tcsbDistributionScopeAttr);
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
	public List<TcsbDistributionScopeAttrEntity> list() {
		List<TcsbDistributionScopeAttrEntity> listTcsbDistributionScopeAttrs=tcsbDistributionScopeAttrService.getList(TcsbDistributionScopeAttrEntity.class);
		return listTcsbDistributionScopeAttrs;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbDistributionScopeAttrEntity task = tcsbDistributionScopeAttrService.get(TcsbDistributionScopeAttrEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbDistributionScopeAttrEntity tcsbDistributionScopeAttr, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbDistributionScopeAttrEntity>> failures = validator.validate(tcsbDistributionScopeAttr);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbDistributionScopeAttrService.save(tcsbDistributionScopeAttr);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbDistributionScopeAttr.getId();
		URI uri = uriBuilder.path("/rest/tcsbDistributionScopeAttrController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbDistributionScopeAttrEntity tcsbDistributionScopeAttr) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbDistributionScopeAttrEntity>> failures = validator.validate(tcsbDistributionScopeAttr);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbDistributionScopeAttrService.saveOrUpdate(tcsbDistributionScopeAttr);
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
		tcsbDistributionScopeAttrService.deleteEntityById(TcsbDistributionScopeAttrEntity.class, id);
	}
}
